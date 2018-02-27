package helper;

public final class Strategy {

	int h;
	State[] states;

	public Strategy(String strat_str) {
		
		String[] strat = strat_str.split(" ");

		int p = 0;

		h = Integer.parseInt(strat[p]);
		
		states = new State[h];

		for (int s = 0; s < h; s++) {
			p++;
			
			double probability = Double.parseDouble(strat[p]);

			double[] crowdedSTM = new double[h];
			double[] uncrowdedSTM = new double[h];

			for (int i = 0; i < h; i++) {
				p++;
				crowdedSTM[i] = Double.parseDouble(strat[p]);
				uncrowdedSTM[i] = Double.parseDouble(strat[p + h]);
			}

			states[s] = new State(s, probability, crowdedSTM, uncrowdedSTM);

			p += h;

			assert p < strat.length;
		}

	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(h);
		for(State state : states){
			sb.append(" ");
			sb.append(state);
		}
		return sb.toString();
	}

}
