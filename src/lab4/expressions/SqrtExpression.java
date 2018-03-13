package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class SqrtExpression extends Expression {

	private Expression e1;

	public SqrtExpression(Expression e1) {
		this.e1 = e1;
	}

	@Override
	public double eval(Vector v) {
		return Math.sqrt(e1.eval(v));
	}

}