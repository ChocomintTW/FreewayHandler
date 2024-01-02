package net.chocomint.freeway_handler.road;

import main.java.net.chocomint.freeway_handler.utils.UnitTrans;

public record RoadMileage(String roadName, RoadType roadType, float km) {

	@Override
	public String toString() {
		return roadName + " " + UnitTrans.float2km(km);
	}
}
