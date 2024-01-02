package net.chocomint.freeway_handler.utils;

public class UnitTrans {
	public static float km2float(String km) {
		return Float.parseFloat(km.replaceAll("K\\+", "."));
	}

	public static String float2km(float f) {
		int integerPart = (int)f;
		int dec = (int)((f - integerPart) * 1000);
		return integerPart + "K+" + String.format("%03d", dec);
	}
}
