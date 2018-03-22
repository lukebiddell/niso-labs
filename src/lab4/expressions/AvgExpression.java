package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class AvgExpression extends Expression {

	public AvgExpression(Expression e1, Expression e2) {
		super(ExpressionType.AVG);
		this.e[0] = e1;
		this.e[1] = e2;
	}

	@Override
	public double eval(Vector v) {
		int n = v.size();

		int k = (int) (Math.abs(Math.floor(e[0].eval(v))) % n);
		int l = (int) (Math.abs(Math.floor(e[1].eval(v))) % n);

		if (k == l)
			return 0;

		int min = Math.min(k, l);
		int max = Math.max(k, l);

		double sum = 0;

		for (int t = min; t < max; t++) {
			sum += v.get(t);
		}

		return makeFinite(sum / (max - min));
	}

}