package net.chocomint.freeway_handler.road.interchange;

import net.chocomint.freeway_handler.road.RoadMileage;
import net.chocomint.freeway_handler.utils.Coordinate;
import net.chocomint.freeway_handler.utils.UnitTrans;

import java.util.Objects;

public class Interchange {
	public final InterchangeType type;
	public final String name;
	public final Coordinate coordinate;
	public final RoadMileage mileage;

	public Interchange(InterchangeType type, String name, Coordinate coordinate, RoadMileage mileage) {
		this.type = type;
		this.name = name;
		this.coordinate = coordinate;
		this.mileage = mileage;
	}

	@Override
	public String toString() {
		return mileage.roadName + " " + UnitTrans.float2km(mileage.km) + " " + name + " (" + type + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (Interchange) obj;
		return Objects.equals(this.type, that.type) &&
				Objects.equals(this.name, that.name) &&
				Objects.equals(this.coordinate, that.coordinate) &&
				Objects.equals(this.mileage, that.mileage);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, name, coordinate, mileage);
	}

}
