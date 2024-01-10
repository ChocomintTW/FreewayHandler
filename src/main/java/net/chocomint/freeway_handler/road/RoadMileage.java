package net.chocomint.freeway_handler.road;

import net.chocomint.freeway_handler.utils.UnitTrans;

import java.util.Objects;

public final class RoadMileage {
	public final String roadName;
	public final RoadType roadType;
	public final float km;

	public RoadMileage(String roadName, RoadType roadType, float km) {
		this.roadName = roadName;
		this.roadType = roadType;
		this.km = km;
	}

	@Override
	public String toString() {
		return roadName + " " + UnitTrans.float2km(km);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RoadMileage that = (RoadMileage) o;
		return Float.compare(km, that.km) == 0 && Objects.equals(roadName, that.roadName) && roadType == that.roadType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(roadName, roadType, km);
	}
}
