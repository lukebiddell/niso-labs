package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class IfleqExpression extends Expression {

	public IfleqExpression(Expression e1, Expression e2, Expression e3, Expression e4) {
		super(ExpressionType.IFLEQ);
		this.e[0] = e1;
		this.e[1] = e2;
		this.e[2] = e3;
		this.e[3] = e4;
	}

	@Override
	public double eval(Vector v) {
		return makeFinite(e[0].eval(v) <= e[1].eval(v) ? e[2].eval(v) : e[3].eval(v));
	}

}