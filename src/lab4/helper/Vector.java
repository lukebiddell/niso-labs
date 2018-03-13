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

	public Vector sum(Vector v) {
		assert (this.values.length == v.values.length);

		double[] values = new double[this.values.length];
		Arrays.setAll(values, i -> this.values[i] + v.values[i]);

		return new Vector(values);
	}

	public Vector sub(Vector v) {
		assert (this.values.length == v.values.length);

		double[] values = new double[this.values.length];
		Arrays.setAll(values, i -> this.values[i] - v.values[i]);

		return new Vector(values);
	}

	public Vector mul(Vector v) {
		assert (this.values.length == v.values.length);

		double[] values = new double[this.values.length];
		Arrays.setAll(values, i -> this.values[i] * v.values[i]);

		return new Vector(values);
	}

	public Vector div(Vector v) {
		assert (this.values.length == v.values.length);

		double[] values = new double[this.values.length];
		Arrays.setAll(values, i -> this.values[i] / v.values[i]);

		return new Vector(values);
	}

	public Vector pow(Vector v) {
		assert (this.values.length == v.values.length);

		double[] values = new double[this.values.length];
		Arrays.setAll(values, i -> Math.pow(this.values[i], v.values[i]));

		return new Vector(values);
	}

	public Vector sqrt() {
		double[] values = new double[this.values.length];
		Arrays.setAll(values, i -> Math.sqrt(this.values[i]));

		return new Vector(values);
	}

	public Vector log() {
		double[] values = new double[this.values.length];
		Arrays.setAll(values, i -> Math.log(this.values[i]));

		return new Vector(values);
	}

	public Vector exp() {
		double[] values = new double[this.values.length];
		Arrays.setAll(values, i -> Math.exp(this.values[i]));

		return new Vector(values);
	}

	public Vector max(Vector v) {
		assert (this.values.length == v.values.length);

		double[] values = new double[this.values.length];
		Arrays.setAll(values, i -> Math.max(this.values[i], v.values[i]));

		return new Vector(values);
	}

	public Vector ifleq(Vector v2, Vector v3, Vector v4) {
		// TODO
		// assert(this.values.length == v.values.length);

		double[] values = new double[this.values.length];
		// Arrays.setAll(values, i -> Math.max(this.values[i], v.values[i]));

		return new Vector(values);
	}
}
