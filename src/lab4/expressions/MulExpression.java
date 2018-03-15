package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class MulExpression extends Expression {
	
	public MulExpression(Expression e1, Expression e2) {
		super(ExpressionType.MUL);
		e[0] = e1;
		e[1] = e2;
	}

	@Override
	public double eval(Vector v) {
		return makeFinite(e[0].eval(v) * e[1].eval(v));
	}

}
