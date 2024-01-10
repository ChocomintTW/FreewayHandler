package net.chocomint.freeway_handler.road.interchange;

import net.chocomint.freeway_handler.road.RoadMileage;
import net.chocomint.freeway_handler.utils.Coordinate;

import java.util.Objects;

public class SystemInterchange extends Interchange {
	public final RoadMileage mileage2;

	public SystemInterchange(String name, Coordinate coordinate, RoadMileage mileage1, RoadMileage mileage2) {
		super(InterchangeType.SIC, name, coordinate, mileage1);
		this.mileage2 = mileage2;
	}

	@Override
	public String toString() {
		return name + " - " + mileage + " / " + mileage2;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		SystemInterchange that = (SystemInterchange) o;
		return Objects.equals(mileage2, that.mileage2);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), mileage2);
	}
}
