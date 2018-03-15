package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class SqrtExpression extends Expression {

	public SqrtExpression(Expression e1) {
		super(ExpressionType.SQRT);
		e[0] = e1;
	}

	@Override
	public double eval(Vector v) {
		return makeFinite(Math.sqrt(e[0].eval(v)));
	}

}