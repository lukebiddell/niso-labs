package lab4.expressions;

import lab4.helper.Expression;
import lab4.helper.Vector;

public class NumberExpression extends Expression {

	private double value;

	public NumberExpression(double value) {
		super(ExpressionType.NUMBER);
		this.value = value;
	}

	@Override
	public double eval(Vector v) {
		return value;
	}
	
	@Override
	public String toString(){
		return Double.toString(value);
	}

}
