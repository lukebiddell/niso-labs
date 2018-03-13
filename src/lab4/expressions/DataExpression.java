package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class DataExpression extends Expression {

	private Expression e1;

	public DataExpression(Expression e1) {
		this.e1 = e1;
	}

	@Override
	public double eval(Vector v) {
		int n = v.size();
		int k = Math.floorMod((int) Math.floor(e1.eval(v)), n);
		
		return v.get(k);
	}

}