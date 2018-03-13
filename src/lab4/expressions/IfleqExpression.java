package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class IfleqExpression extends Expression {

	private Expression e1, e2, e3, e4;

	public IfleqExpression(Expression e1, Expression e2, Expression e3, Expression e4) {
		this.e1 = e1;
		this.e2 = e2;
		this.e3 = e3;
		this.e4 = e4;
	}

	@Override
	public double eval(Vector v) {
		return e1.eval(v) <= e2.eval(v) ? e3.eval(v) : e4.eval(v);
	}

}