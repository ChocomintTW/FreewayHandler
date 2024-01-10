package net.chocomint.freeway_handler;

import net.chocomint.freeway_handler.road.RoadMileage;
import net.chocomint.freeway_handler.road.interchange.Interchange;
import net.chocomint.freeway_handler.road.interchange.InterchangeType;
import net.chocomint.freeway_handler.road.interchange.SystemInterchange;
import net.chocomint.freeway_handler.utils.Coordinate;
import net.chocomint.freeway_handler.road.Section;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FreewayHandler {
	public static List<Section> SECTION_LIST = null;
	public static List<Coordinate> COORDINATE_LIST = null;
	public static Map<Coordinate, String> COORDINATE_ID_MAP = null;
	public static Map<String, String> SHAPE_MAP = null;
	public static List<Interchange> INTERCHANGE_LIST = null;

	public static void init() throws ParserConfigurationException, IOException, SAXException {
		ClassLoader loader = FreewayHandler.class.getClassLoader();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Section
		Node root = builder.parse(loader.getResourceAsStream("Section.xml")).getDocumentElement();
		NodeList sectionList = root.getChildNodes().item(7).getChildNodes();

		List<Section> sections = new ArrayList<>();
		for (int i = 0; i < sectionList.getLength(); i++) {
			if (sectionList.item(i).getNodeType() == Node.ELEMENT_NODE)
				sections.add(Section.fromNode(sectionList.item(i)));
		}
		SECTION_LIST = sections;

		// Shape
		root = builder.parse(loader.getResourceAsStream("SectionShape.xml")).getDocumentElement();
		sectionList = root.getChildNodes().item(7).getChildNodes();

		List<Coordinate> list = new ArrayList<>();
		Map<String, String> shapeMap = new HashMap<>();
		Map<Coordinate, String> coordinateMap = new HashMap<>();
		for (int i = 0; i < sectionList.getLength(); i++) {
			if (sectionList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				String id = sectionList.item(i).getChildNodes().item(1).getTextContent();
				String shape = sectionList.item(i).getChildNodes().item(3).getTextContent();

				shapeMap.put(id, shape);

				for (String str : shape.replaceAll("LINESTRING\\(|\\)", "").split(",")) {
					Coordinate coordinate = Coordinate.fromShapeFormat(str);
					list.add(coordinate);
					coordinateMap.put(coordinate, id);
				}
			}
		}
		COORDINATE_LIST = list;
		COORDINATE_ID_MAP = coordinateMap;
		SHAPE_MAP = shapeMap;

		// Interchange
		INTERCHANGE_LIST = getInterchanges(sections);
	}

	private static List<Interchange> getInterchanges(List<Section> sections) {
		List<Interchange> icList = new ArrayList<>();
		Set<String> usedRoadSet = new HashSet<>();
		Map<String, Section> sicMap = new HashMap<>();
		for (Section section : sections) {
			String name = section.start;
			String key = section.roadName + " " + name;
			if (!usedRoadSet.contains(key)) {
				// Coordinate
				String ls = SHAPE_MAP.get(section.id);
				Coordinate coordinate = Coordinate.ERROR;
				if (ls != null) {
					Matcher matcher = Pattern.compile("\\(([^ ,]+ [^ ,]+)").matcher(ls);
					String[] pair = matcher.find() ? matcher.group(1).split(" ") : null;
					assert pair != null;
					coordinate = new Coordinate(Double.parseDouble(pair[0]), Double.parseDouble(pair[1]));
				}

				InterchangeType type = getInterchangeType(name);

				if (type == InterchangeType.SIC) {
					if (sicMap.containsKey(name) && !Objects.equals(sicMap.get(name).roadName, section.roadName)) {
						Section another = sicMap.get(name);
						icList.add(new SystemInterchange(name, coordinate,
								new RoadMileage(another.roadName, another.roadType, another.startKm),
								new RoadMileage(section.roadName, section.roadType, section.startKm)));
						usedRoadSet.add(key);
					} else
						sicMap.put(name, section);
				} else {
					icList.add(new Interchange(type, name, coordinate,
							new RoadMileage(section.roadName, section.roadType, section.startKm)));
					usedRoadSet.add(key);
				}
			}
		}
		return icList;
	}

	private static InterchangeType getInterchangeType(String name) {
		// Exception Handle
		String[] sicExceptions = {"南港交流道", "高雄端"};
		String[] tcExceptions = {"汐止端", "楊梅端"};

		InterchangeType type = InterchangeType.IC;
		if (Arrays.asList(sicExceptions).contains(name) || name.contains("系統")) type = InterchangeType.SIC;
		else if (Arrays.asList(tcExceptions).contains(name) || name.contains("轉接")) type = InterchangeType.TC;
		else if (name.contains("服務區")) type = InterchangeType.SA;
		else if (name.contains("端")) type = InterchangeType.TP;
		return type;
	}

	public static void ifNullInit() throws ParserConfigurationException, IOException, SAXException {
		if (SECTION_LIST == null) init();
	}
}
