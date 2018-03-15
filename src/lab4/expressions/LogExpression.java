package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class LogExpression extends Expression {
	
	private static final double log2 = Math.log(2);

	public LogExpression(Expression e1) {
		super(ExpressionType.LOG);
		this.e[0] = e1;
	}

	@Override
	public double eval(Vector v) {
		return makeFinite(Math.log(e[0].eval(v)) / log2);
	}

}