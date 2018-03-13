package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class DiffExpression extends Expression {

	private Expression e1, e2;

	public DiffExpression(Expression e1, Expression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}

	@Override
	public double eval(Vector v) {
		int n = v.size();
		int k = Math.floorMod((int) Math.floor(e1.eval(v)), n);
		int l = Math.floorMod((int) Math.floor(e2.eval(v)), n);
		
		return v.get(k) - v.get(l);
	}

}