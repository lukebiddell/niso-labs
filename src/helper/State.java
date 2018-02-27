package helper;

public final class State {

	private int stateNumber;

	private double probability;

	private double[] crowdedSTM;
	private double[] uncrowdedSTM;

	public State(int h, String[] state_str) {
		int p = 0;

		probability = Double.parseDouble(state_str[p]);

		crowdedSTM = new double[h];
		uncrowdedSTM = new double[h];

		for (int i = 0; i < h; i++) {
			p++;
			crowdedSTM[i] = Double.parseDouble(state_str[p]);
			uncrowdedSTM[i] = Double.parseDouble(state_str[p + h]);
		}

	}

	public State(int stateNumber, double probability, double[] crowdedSTM, double[] uncrowdedSTM) {
		this.stateNumber = stateNumber;
		this.probability = probability;
		this.crowdedSTM = crowdedSTM;
		this.uncrowdedSTM = uncrowdedSTM;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(probability);
		for(int i = 0; i < crowdedSTM.length; i++){
			sb.append(" ");
			sb.append(crowdedSTM[i]);
		}
		for(int i = 0; i < uncrowdedSTM.length; i++){
			sb.append(" ");
			sb.append(uncrowdedSTM[i]);
		}
		
		return sb.toString();
	}

}
