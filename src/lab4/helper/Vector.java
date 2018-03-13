package lab4.helper;

import java.util.Arrays;

public final class Vector {

	private final double[] values;

	public Vector(double[] values) {
		this.values = values;
	}

	public double get(int i) {
		if (i < 0 || i >= values.length) {
			System.err.println("Invalid get " + i);
		}
		return values[i];

		// return i >= 0 || i < values.length ? values[i] : null;
	}

	public int size() {
		return values.length;
	}
	
	public String toString(){
		return Arrays.toString(values);
	}

	public static Vector parse(String x_str, int n) {
		double[] values = Arrays.stream(x_str.split(" ")).mapToDouble(Double::parseDouble).toArray();

		assert n == values.length;

		return new Vector(values);
	}
}
