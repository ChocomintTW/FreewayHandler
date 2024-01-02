package net.chocomint.freeway_handler.road;

import main.java.net.chocomint.freeway_handler.utils.UnitTrans;

public record RoadMileage(String roadName, RoadClass roadClass, float km) {

	@Override
	public String toString() {
		return roadName + " " + UnitTrans.float2km(km);
	}
}
