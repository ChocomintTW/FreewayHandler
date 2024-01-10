package net.chocomint.freeway_handler;

import net.chocomint.freeway_handler.exceptions.OutOfRoadException;
import net.chocomint.freeway_handler.road.RoadMileage;
import net.chocomint.freeway_handler.road.interchange.InterchangeType;
import net.chocomint.freeway_handler.utils.Coordinate;
import net.chocomint.freeway_handler.road.RoadLocator;
import net.chocomint.freeway_handler.utils.Time;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, OutOfRoadException {
		FreewayHandler.init();

//		FreewayHandler.INTERCHANGE_LIST.forEach(System.out::println);
		// .stream().filter(i -> i.type == InterchangeType.SIC)

		RoadMileage m = RoadLocator.findRoadMileage(Coordinate.parse("25.063790, 121.339217"));
		System.out.println(m);
	}

	public static void writeFile2Desktop() throws IOException {
		FileWriter writer1 = new FileWriter("C:/Users/user/Desktop/ic_list.txt");
		FreewayHandler.INTERCHANGE_LIST.forEach(interchange -> {
			try {
				if (!interchange.coordinate.isError())
					writer1.write(interchange.name + " " + interchange.coordinate.longitude + " " + interchange.coordinate.latitude + "\n");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		writer1.close();

		FileWriter writer2 = new FileWriter("C:/Users/user/Desktop/point_list.txt");
		FreewayHandler.COORDINATE_LIST.forEach(coordinate -> {
			try {
				writer2.write(coordinate.longitude + " " + coordinate.latitude + "\n");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		writer2.close();
	}

	public static void liveTrafficReader() throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Section
		Node root = builder.parse(new File("src/test/resources/LiveTraffic.xml")).getDocumentElement();
		LocalDateTime time = Time.parse(root.getChildNodes().item(1).getTextContent());

		NodeList liveTrafficList = root.getChildNodes().item(9).getChildNodes();
		Map<String, Integer> speedMap = new HashMap<>();
		for (int i = 0; i < liveTrafficList.getLength(); i++) {
			if (liveTrafficList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				String id = liveTrafficList.item(i).getChildNodes().item(1).getTextContent();
				int speed = Integer.parseInt(liveTrafficList.item(i).getChildNodes().item(5).getTextContent());
				// 250: 道路封閉
				// -2: 無此路段

				speedMap.put(id, speed);
			}
		}

		speedMap.forEach((id, v) -> System.out.println(id + ": " + v + " km/h"));
	}

	public static void find() throws ParserConfigurationException, IOException, SAXException {
		FreewayHandler.init();

		Coordinate coordinate = Coordinate.parse("22.571462, 120.528068");

		try {
			System.out.println(coordinate + " is at " + RoadLocator.findRoadMileage(coordinate));
		} catch (OutOfRoadException e) {
			System.out.println(coordinate + " is not at any road.");
		}
	}
}
