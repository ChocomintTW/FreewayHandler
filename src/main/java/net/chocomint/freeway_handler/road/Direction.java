package net.chocomint.freeway_handler.road;

public enum Direction {
	NORTH('N', "north"),
	SOUTH('S', "south"),
	WEST ('W', "west"),
	EAST ('E', "east"),
	NONE ('\0', "none");

	public final char abbr;
	public final String name;

	Direction(char abbr, String name) {
		this.abbr = abbr;
		this.name = name;
	}

	public static Direction fromAbbr(char abbr) {
		return switch (abbr) {
			case 'N' -> NORTH;
			case 'S' -> SOUTH;
			case 'W' -> WEST;
			case 'E' -> EAST;
			default -> NONE;
		};
	}

	@Override
	public String toString() {
		return name;
	}
}
