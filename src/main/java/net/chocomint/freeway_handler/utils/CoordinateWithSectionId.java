package net.chocomint.freeway_handler.utils;

public record CoordinateWithSectionId(String id, Coordinate coordinate) {

	public static CoordinateWithSectionId fromPair(String id, String pair) {
		String[] sp = pair.split(" ");
		return new CoordinateWithSectionId(id, new Coordinate(Double.parseDouble(sp[0]), Double.parseDouble(sp[1])));
	}

	@Override
	public String toString() {
		return "(" + coordinate.longitude() + ", " + coordinate.latitude() + ") in " + id;
	}
}
