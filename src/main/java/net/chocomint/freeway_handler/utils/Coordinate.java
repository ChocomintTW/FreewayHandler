package net.chocomint.freeway_handler.utils;

/**
 * @param longitude 經度
 * @param latitude  緯度
 */
public record Coordinate(double longitude, double latitude) {

	public static double distance(Coordinate c1, Coordinate c2) {
		return Math.sqrt(Math.pow(c1.longitude - c2.longitude, 2) + Math.pow(c1.latitude - c2.latitude, 2));
	}

	public static double realDistance(Coordinate c1, Coordinate c2) {
		return Math.sqrt(Math.pow((c1.longitude - c2.longitude) * 101.7, 2) + Math.pow((c1.latitude - c2.latitude) * 110.948, 2));
	}

	public static boolean isDifferentSide(Coordinate c, Coordinate c1, Coordinate c2) {
		return (c1.longitude - c.longitude) * (c2.longitude - c.longitude) + (c1.latitude - c.latitude) * (c2.latitude - c.latitude) < 0;
	}

	public static Coordinate parse(String str) {
		String[] sp = str.split(",");
		return new Coordinate(Double.parseDouble(sp[1]), Double.parseDouble(sp[0]));
	}

	@Override
	public String toString() {
		return "(" + longitude + ", " + latitude + ")";
	}
}
