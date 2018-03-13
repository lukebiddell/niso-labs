package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class ExpExpression extends Expression {

	private Expression e1;

	public ExpExpression(Expression e1) {
		this.e1 = e1;
	}

	@Override
	public double eval(Vector v) {
		return Math.exp(e1.eval(v));
	}

}