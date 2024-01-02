package net.chocomint.freeway_handler.road;

import net.chocomint.freeway_handler.FreewayHandler;
import net.chocomint.freeway_handler.exceptions.OutOfRoadException;
import net.chocomint.freeway_handler.utils.Coordinate;
import net.chocomint.freeway_handler.utils.CoordinateWithSectionId;
import net.chocomint.freeway_handler.utils.LineString;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RoadLocator {
	public static Section nearestSection(Coordinate coordinate) throws OutOfRoadException {
		double nearestDist = Double.MAX_VALUE;
		CoordinateWithSectionId nearestCoordinate = new CoordinateWithSectionId("", new Coordinate(0, 0));
		for (CoordinateWithSectionId cw : FreewayHandler.COORDINATE_LIST) {
			double dist = Coordinate.distance(coordinate, cw.coordinate());
			if (dist < nearestDist) {
				nearestDist = dist;
				nearestCoordinate = cw;
			}
		}

		if (nearestDist > 1e-3)
			throw new OutOfRoadException();

		for (Section section : FreewayHandler.SECTION_LIST) {
			if (Objects.equals(section.id(), nearestCoordinate.id())) {
				return section;
			}
		}

		return null;
	}

	public static RoadMileage findRoadMileage(Coordinate coordinate) throws OutOfRoadException {
		Section section = nearestSection(coordinate);
		Map<String, String> shapeMap = FreewayHandler.SHAPE_MAP;

		assert section != null;
		List<Coordinate> list = LineString.linestring2coordinateList(shapeMap.get(section.id()));
		double length = section.length();
		double accumulation = 0;
		for (int i = 0; i < list.size() - 1; i++) {
			if (Coordinate.isDifferentSide(coordinate, list.get(i), list.get(i + 1))) {
				accumulation += Coordinate.realDistance(coordinate, list.get(i));
				break;
			}
			accumulation += Coordinate.realDistance(list.get(i), list.get(i + 1));
		}
		double km = (section.startKm() * (length - accumulation) + section.endKm() * accumulation) / length;

		return new RoadMileage(section.roadName(), section.roadType(), (float) km);
	}
}
