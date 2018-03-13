package lab4.helper;

import java.util.OptionalDouble;

public abstract class Expression {
	
	public abstract double eval(Vector v);
	
	private double fitness(TrainingLine l){
		return l.getY() - eval(l.getX());
	}
	
	public double fitness(TrainingData data){
			OptionalDouble v = data.getLines().stream().mapToDouble(l -> Math.pow(fitness(l), 2)).average();
			double fitness = v.orElseThrow(IllegalStateException::new);
			
			return fitness;
	}
	
	
}