package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class LogExpression extends Expression {

	private Expression e1;
	private static final double log2 = Math.log(2);

	public LogExpression(Expression e1) {
		this.e1 = e1;
	}

	@Override
	public double eval(Vector v) {
		return Math.log(e1.eval(v)) / log2;
	}

}