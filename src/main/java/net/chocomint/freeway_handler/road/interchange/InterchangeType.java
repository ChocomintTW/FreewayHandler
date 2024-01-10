package net.chocomint.freeway_handler.road.interchange;

public enum InterchangeType {
	IC("Interchange", "IC"),
	SIC("System Interchange", "SIC"), // 兩條道路處理
	TC("Transfer Connection", "TC"),
	SA("Service Area", "SA"),
	TP("Top", "TP");

	public final String name;
	public final String abbr;

	InterchangeType(String name, String abbr) {
		this.name = name;
		this.abbr = abbr;
	}

	@Override
	public String toString() {
		return abbr;
	}
}
