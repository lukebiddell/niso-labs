package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class DataExpression extends Expression {

	public DataExpression(Expression e1) {
		super(ExpressionType.DATA);
		this.e[0] = e1;
	}

	@Override
	public double eval(Vector v) {
		int n = v.size();
		int k = Math.floorMod((int) Math.floor(e[0].eval(v)), n);
		
		return makeFinite(v.get(k));
	}

}