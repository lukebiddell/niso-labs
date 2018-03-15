package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class AddExpression extends Expression {
	
	public AddExpression(Expression e1, Expression e2) {
		super(ExpressionType.ADD);
		this.e[0] = e1;
		this.e[1] = e2;
	}

	@Override
	public double eval(Vector v) {
		return makeFinite(e[0].eval(v) + e[1].eval(v));
	}

}
