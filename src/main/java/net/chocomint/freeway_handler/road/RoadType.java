package net.chocomint.freeway_handler.road;

public enum RoadType {
	NationalExpressway(0, "國道"),
	Expressway        (1, "快速道路"),
	UrbanExpressway   (2, "市區快速道路"),
	ProvincialHighway (3, "省道"),
	CountyHighway     (4, "縣道"),
	RuralHighway      (5, "鄉道"),
	UrbanRoad         (6, "市區一般道路"),
	Ramp              (7, "匝道");

	public final int id;
	public final String name;

	RoadType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
