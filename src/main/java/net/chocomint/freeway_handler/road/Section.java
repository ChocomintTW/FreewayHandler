package net.chocomint.freeway_handler.road;

import net.chocomint.freeway_handler.utils.UnitTrans;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Section {
	public final String id;
	public final String name;
	public final String roadId;
	public final String roadName;
	public final RoadType roadType;
	public final Direction direction;
	public final String start;
	public final String end;
	public final float startKm;
	public final float endKm;
	public final int speedLimit;

	public Section(String id, String name, String roadId, String roadName, RoadType roadType, Direction direction,
	               String start, String end, float startKm, float endKm, int speedLimit) {
		this.id = id;
		this.name = name;
		this.roadId = roadId;
		this.roadName = roadName;
		this.roadType = roadType;
		this.direction = direction;
		this.start = start;
		this.end = end;
		this.startKm = startKm;
		this.endKm = endKm;
		this.speedLimit = speedLimit;
	}

	public static Section fromNode(Node node) {
		NodeList child = node.getChildNodes();
		List<Node> list = new ArrayList<>();
		for (int i = 0; i < child.getLength(); i++) {
			if (child.item(i).getNodeType() == Node.ELEMENT_NODE)
				list.add(child.item(i));
		}

		String id = list.get(0).getTextContent();
		String name = list.get(1).getTextContent();
		String roadId = list.get(2).getTextContent();
		String roadName = list.get(3).getTextContent();
		RoadType roadType = RoadType.values()[Integer.parseInt(list.get(4).getTextContent())];
		Direction direction = Direction.fromAbbr(list.get(5).getTextContent().charAt(0));
		String start = list.get(6).getChildNodes().item(1).getTextContent();
		String end = list.get(6).getChildNodes().item(3).getTextContent();
		float startKm = UnitTrans.km2float(list.get(8).getChildNodes().item(1).getTextContent());
		float endKm = UnitTrans.km2float(list.get(8).getChildNodes().item(3).getTextContent());
		int speedLimit = Integer.parseInt(list.get(9).getTextContent());

		return new Section(id, name, roadId, roadName, roadType, direction, start, end, startKm, endKm, speedLimit);
	}

	public float length() {
		return Math.abs(endKm - startKm);
	}

	@Override
	public String toString() {
		return "[" + id + "] " + roadName + ": "
				+ start + "(" + UnitTrans.float2km(startKm) + ")" + " ~ "
				+ end + "(" + UnitTrans.float2km(endKm) + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (Section) obj;
		return Objects.equals(this.id, that.id) &&
				Objects.equals(this.name, that.name) &&
				Objects.equals(this.roadId, that.roadId) &&
				Objects.equals(this.roadName, that.roadName) &&
				Objects.equals(this.roadType, that.roadType) &&
				Objects.equals(this.direction, that.direction) &&
				Objects.equals(this.start, that.start) &&
				Objects.equals(this.end, that.end) &&
				Float.floatToIntBits(this.startKm) == Float.floatToIntBits(that.startKm) &&
				Float.floatToIntBits(this.endKm) == Float.floatToIntBits(that.endKm) &&
				this.speedLimit == that.speedLimit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, roadId, roadName, roadType, direction, start, end, startKm, endKm, speedLimit);
	}

}
