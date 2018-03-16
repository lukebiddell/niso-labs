package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class PowExpression extends Expression {

	public PowExpression(Expression e1, Expression e2) {
		super(ExpressionType.POW);
		this.e[0] = e1;
		this.e[1] = e2;
	}

	@Override
	public double eval(Vector v) {
		return makeFinite(Math.pow(e[0].eval(v), e[1].eval(v)));
	}

}