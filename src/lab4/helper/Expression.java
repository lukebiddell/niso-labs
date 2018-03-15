package lab4.helper;

import java.util.Arrays;
import java.util.OptionalDouble;

import lab4.expressions.ExpressionType;

public abstract class Expression {
	
	protected ExpressionType type;
	protected Expression[] e;

	public abstract double eval(Vector v);
	
	protected Expression(ExpressionType type){
		this.type = type;
		e = new Expression[type.size()];
	}
	
	private double fitness(TrainingLine l){
		return l.getY() - eval(l.getX());
	}
	
	public double fitness(TrainingData data){
			OptionalDouble v = data.getLines().stream().mapToDouble(l -> Math.pow(fitness(l), 2)).average();
			double fitness = v.orElseThrow(IllegalStateException::new);
			
			return fitness;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder("(").append(type.toString().toLowerCase());
		Arrays.stream(e).forEach(s -> sb.append(" ").append(s));
		return sb.append(")").toString();
	}
	
	public static double makeFinite(double value){
		return Double.isFinite(value) ? value : 0;
	}
	
}