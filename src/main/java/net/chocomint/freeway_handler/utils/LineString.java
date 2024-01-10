package net.chocomint.freeway_handler.utils;

import java.util.ArrayList;
import java.util.List;

public class LineString {
	public static List<Coordinate> linestring2coordinateList(String linestring) {
		List<Coordinate> list = new ArrayList<>();
		for (String str : linestring.replaceAll("LINESTRING\\(|\\)", "").split(",")) {
			list.add(Coordinate.fromShapeFormat(str));
		}
		return list;
	}
}
