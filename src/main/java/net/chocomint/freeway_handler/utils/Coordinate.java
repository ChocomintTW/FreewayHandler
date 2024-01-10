package net.chocomint.freeway_handler.utils;

import java.util.Objects;

public final class Coordinate {
	public static final Coordinate ERROR = new Coordinate(-1, -1);
	public final double longitude;
	public final double latitude;

	/**
	 * @param longitude 經度
	 * @param latitude  緯度
	 */
	public Coordinate(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public static double distance(Coordinate c1, Coordinate c2) {
		return Math.sqrt(Math.pow(c1.longitude - c2.longitude, 2) + Math.pow(c1.latitude - c2.latitude, 2));
	}

	public static double realDistance(Coordinate c1, Coordinate c2) {
		return Math.sqrt(Math.pow((c1.longitude - c2.longitude) * 101.7, 2) + Math.pow((c1.latitude - c2.latitude) * 110.948, 2));
	}

	public static boolean isDifferentSide(Coordinate c, Coordinate c1, Coordinate c2) {
		return (c1.longitude - c.longitude) * (c2.longitude - c.longitude) + (c1.latitude - c.latitude) * (c2.latitude - c.latitude) < 0;
	}

	public boolean isError() {
		return this.longitude == -1 && this.latitude == -1;
	}

	public static Coordinate parse(String str) {
		String[] sp = str.split(",");
		return new Coordinate(Double.parseDouble(sp[1]), Double.parseDouble(sp[0]));
	}

	public static Coordinate fromShapeFormat(String str) {
		String[] sp = str.split(" ");
		return new Coordinate(Double.parseDouble(sp[0]), Double.parseDouble(sp[1]));
	}

	@Override
	public String toString() {
		return "(" + longitude + ", " + latitude + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (Coordinate) obj;
		return Double.doubleToLongBits(this.longitude) == Double.doubleToLongBits(that.longitude) &&
				Double.doubleToLongBits(this.latitude) == Double.doubleToLongBits(that.latitude);
	}

	@Override
	public int hashCode() {
		return Objects.hash(longitude, latitude);
	}

}
