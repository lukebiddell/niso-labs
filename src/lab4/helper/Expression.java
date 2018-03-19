package lab4.helper;

import java.util.Arrays;
import java.util.OptionalDouble;

import lab4.expressions.ExpressionType;

public abstract class Expression {

	protected ExpressionType type;
	protected Expression[] e;

	public abstract double eval(Vector v);

	protected Expression(ExpressionType type) {
		this.type = type;
		e = new Expression[type.size()];
	}

	private double fitness(TrainingLine l) {
		return l.getY() - eval(l.getX());
	}

	public double fitness(TrainingData data) {
		return data.getLines().stream().mapToDouble(l -> Math.pow(fitness(l), 2)).average()
				.orElseThrow(IllegalStateException::new);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("(").append(type.toString().toLowerCase());
		Arrays.stream(e).forEach(s -> sb.append(" ").append(s));
		return sb.append(")").toString();
	}

	public static double makeFinite(double value) {
		return Double.isFinite(value) ? value : 0;
	}

	public int depth() {
		if (!isTerminal()) {
			return 1 + Arrays.stream(e).mapToInt(exp -> exp.depth()).max().getAsInt();
		} else {
			return 0;
		}
	}

	public int arity() {
		return e.length;
	}

	public boolean isTerminal() {
		return e.length == 0;
	}

}