package net.chocomint.freeway_handler.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Time {
	public static LocalDateTime parse(String text) {
		return OffsetDateTime.parse("2024-01-02T20:51:00+08:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME).toLocalDateTime();
	}
}
