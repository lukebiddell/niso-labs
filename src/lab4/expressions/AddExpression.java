package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class AddExpression extends Expression {

	private Expression e1, e2;
	
	public AddExpression(Expression e1, Expression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}

	@Override
	public double eval(Vector v) {
		return e1.eval(v) + e2.eval(v);
	}

}
