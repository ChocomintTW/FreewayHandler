package net.chocomint.freeway_handler.road;

import main.java.net.chocomint.freeway_handler.utils.UnitTrans;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public record Section(String id, String name, String roadId, String roadName, RoadClass roadClass, Direction direction,
                      String start, String end, float startKm, float endKm, int speedLimit) {

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
		RoadClass roadClass = RoadClass.values()[Integer.parseInt(list.get(4).getTextContent())];
		Direction direction = Direction.fromAbbr(list.get(5).getTextContent().charAt(0));
		String start = list.get(6).getChildNodes().item(1).getTextContent();
		String end = list.get(6).getChildNodes().item(3).getTextContent();
		float startKm = UnitTrans.km2float(list.get(8).getChildNodes().item(1).getTextContent());
		float endKm = UnitTrans.km2float(list.get(8).getChildNodes().item(3).getTextContent());
		int speedLimit = Integer.parseInt(list.get(9).getTextContent());

		return new Section(id, name, roadId, roadName, roadClass, direction, start, end, startKm, endKm, speedLimit);
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
}
