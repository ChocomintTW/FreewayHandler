package net.chocomint.freeway_handler;

import net.chocomint.freeway_handler.utils.CoordinateWithSectionId;
import net.chocomint.freeway_handler.road.Section;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreewayHandler {
	public static List<Section> SECTION_LIST = null;
	public static List<CoordinateWithSectionId> COORDINATE_LIST = null;
	public static Map<String, String> SHAPE_MAP = null;

	public static void init() throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Section
		Node root = builder.parse(new File("src/main/resources/Section.xml")).getDocumentElement();
		NodeList sectionList = root.getChildNodes().item(7).getChildNodes();

		List<Section> sections = new ArrayList<>();
		for (int i = 0; i < sectionList.getLength(); i++) {
			if (sectionList.item(i).getNodeType() == Node.ELEMENT_NODE)
				sections.add(Section.fromNode(sectionList.item(i)));
		}
		SECTION_LIST = sections;

		// Shape
		root = builder.parse(new File("src/main/resources/SectionShape.xml")).getDocumentElement();
		sectionList = root.getChildNodes().item(7).getChildNodes();

		List<CoordinateWithSectionId> list = new ArrayList<>();
		Map<String, String> shapeMap = new HashMap<>();
		for (int i = 0; i < sectionList.getLength(); i++) {
			if (sectionList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				String id = sectionList.item(i).getChildNodes().item(1).getTextContent();
				String shape = sectionList.item(i).getChildNodes().item(3).getTextContent();

				shapeMap.put(id, shape);

				for (String pair : shape.replaceAll("LINESTRING\\(|\\)", "").split(",")) {
					list.add(CoordinateWithSectionId.fromPair(id, pair));
				}
			}
		}
		COORDINATE_LIST = list;
		SHAPE_MAP = shapeMap;
	}

	public static void ifNullInit() throws ParserConfigurationException, IOException, SAXException {
		if (SECTION_LIST == null) init();
	}
}
