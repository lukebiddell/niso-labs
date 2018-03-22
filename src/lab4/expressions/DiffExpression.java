package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class DiffExpression extends Expression {

	public DiffExpression(Expression e1, Expression e2) {
		super(ExpressionType.DIFF);
		this.e[0] = e1;
		this.e[1] = e2;
	}

	@Override
	public double eval(Vector v) {
		int n = v.size();
		
		//int k = Math.floorMod((int) Math.floor(e[0].eval(v)), n);
		//int l = Math.floorMod((int) Math.floor(e[1].eval(v)), n);
		
		int k = (int) Math.abs(Math.floor(e[0].eval(v))) % n;
		int l = (int) Math.abs(Math.floor(e[1].eval(v))) % n;

		return makeFinite(v.get(k) - v.get(l));
	}

}