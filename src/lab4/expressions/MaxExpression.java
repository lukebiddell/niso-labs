package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class MaxExpression extends Expression {

	public MaxExpression(Expression e1, Expression e2) {
		super(ExpressionType.MAX);
		this.e[0] = e1;
		this.e[1] = e2;
	}

	@Override
	public double eval(Vector v) {
		return Math.max(e[0].eval(v), e[1].eval(v));
	}

}