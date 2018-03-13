package lab4.helper;

public class TrainingLine {

	private Vector x;
	private double y;
	
	public TrainingLine(Vector x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return x.toString() + y;
	}
	
	public int getN(){
		return x.size();
	}
	
	public Vector getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}

}
