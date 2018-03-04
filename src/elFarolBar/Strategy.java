package elFarolBar;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public final class Strategy {

	private final int h;
	private final State[] states;

	private int simulatedState;
	private boolean simulatedDecision;

	// private int week = 0;
	private int payoff = 0;

	public Strategy(int h) {
		this.h = h;
		this.states = new State[h];
	}

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

	public static Strategy uniformRandom(int h) {
		Strategy strat = new Strategy(h);

		for (int i = 0; i < h; i++) {
			strat.states[i] = State.uniformRandom(i, h);
		}

		// System.out.println(strat);

		return strat;
	}

	public int getSimulatedState() {
		return simulatedState;
	}

	public boolean isSimulatedDecision() {
		return simulatedDecision;
	}

	public Strategy mutate() {
		// TODO
		return null;
	}

	public static Strategy globalDiscreteRecombination(Strategy mother, Strategy father) {
		// TODO
		
		Strategy child = new Strategy(mother.h);
		
		for(int i = 0; i < child.h; i++){
			child.states[i] = State.globalDiscreteRecombination(mother.states[i], father.states[i]); //TODO
		}
		
		return null;
	}

	public void updatePayoff(boolean crowded) {
		if (simulatedDecision != crowded)
			payoff++;
	}
	
	public int getPayoff(){
		return payoff;
	}

	public void simulateStep(int state_no, boolean crowded) {
		simulatedState = getState(state_no).simulateStep(crowded);
		simulatedDecision = ThreadLocalRandom.current().nextDouble() < getState(simulatedState).getProbability();

	}

	public void simulateStep(boolean crowded) {
		simulateStep(simulatedState, crowded);
	}

	public State getState(int s) {
		return states[s];
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(h);
		Arrays.stream(states).forEach(s -> sb.append(" ").append(s));
		return sb.toString();
	}

}
