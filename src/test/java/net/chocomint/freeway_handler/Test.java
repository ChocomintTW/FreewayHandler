package net.chocomint.freeway_handler;

import net.chocomint.freeway_handler.exceptions.OutOfRoadException;
import net.chocomint.freeway_handler.utils.Coordinate;
import net.chocomint.freeway_handler.utils.RoadLocator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Test {
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
		FreewayHandler.init();

		Coordinate coordinate = Coordinate.parse("23.982771, 120.987949");

		try {
			System.out.println(coordinate + " is at " + RoadLocator.findRoadMileage(coordinate));
		} catch (OutOfRoadException e) {
			System.out.println(coordinate + " is not at any road.");
		}
	}
}
