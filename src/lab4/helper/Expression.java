package lab4.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import lab4.expressions.ExpressionType;

public abstract class Expression {

	protected final ExpressionType type;
	protected final Expression[] e;

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
		return isTerminal() ? 0
				: 1 + Arrays.stream(e).mapToInt(Expression::depth).max().orElseThrow(IllegalStateException::new);

	}

	public int size() {
		return isTerminal() ? 1 : 1 + Arrays.stream(e).mapToInt(Expression::size).sum();
	}

	public int arity() {
		return e.length;
	}

	public boolean isTerminal() {
		return arity() == 0;
	}

	public static LinkedList<Expression> crossOver(Expression e1_orig, Expression e2_orig) {
		Expression e1 = e1_orig.clone();
		Expression e2 = e2_orig.clone();
		
		

		return null;
	}

	public Expression clone() {
		// Expression e = ExpressionFactory.(type);
		return ExpressionFactory.clone(this);
	}

	public ArrayList<Expression> children() {
		return new ArrayList<>(Arrays.asList(e));
	}

	public ExpressionType getType() {
		return type;
	}

}