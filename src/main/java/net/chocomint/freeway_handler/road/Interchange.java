package net.chocomint.freeway_handler.road;

import net.chocomint.freeway_handler.utils.UnitTrans;

public record Interchange(InterchangeType type, String name, float km, String roadName) {

	@Override
	public String toString() {
		return roadName + " " + UnitTrans.float2km(km) + " " + name;
	}
}
