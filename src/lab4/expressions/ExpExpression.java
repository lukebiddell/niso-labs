package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class ExpExpression extends Expression {

	public ExpExpression(Expression e1) {
		super(ExpressionType.EXP);
		this.e[0] = e1;
	}

	@Override
	public double eval(Vector v) {
		return makeFinite(Math.exp(e[0].eval(v)));
	}

}