package elFarolBar;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public final class Strategy {

	private final int h;
	private State[] states;

	private int simulatedState;
	private boolean simulatedDecision;

	// private int week = 0;
	private int payoff = 0;

	public Strategy(int h) {
		this.h = h;
		this.states = new State[h];
	}

	public static Strategy parseStrategy(String strat_str) {

		String[] strat = strat_str.split(" ");

		int p = 0;

		int h = Integer.parseInt(strat[p]);

		Strategy s = new Strategy(h);

		s.states = new State[h];

		for (int i = 0; i < h; i++) {
			p++;

			double probability = Double.parseDouble(strat[p]);

			double[] crowdedSTM = new double[h];
			double[] uncrowdedSTM = new double[h];

			for (int j = 0; j < h; j++) {
				p++;
				crowdedSTM[j] = Double.parseDouble(strat[p]);
				uncrowdedSTM[j] = Double.parseDouble(strat[p + h]);
			}

			s.states[i] = new State(probability, crowdedSTM, uncrowdedSTM);

			p += h;

			assert p < strat.length;
		}

		return s;

	}

	public static Strategy uniformRandom(int h) {
		Strategy strat = new Strategy(h);

		for (int i = 0; i < h; i++) {
			strat.states[i] = State.uniformRandom(i, h);
		}
		
		strat.simulatedState = ThreadLocalRandom.current().nextInt(h);
		strat.simulatedDecision = ThreadLocalRandom.current().nextDouble() < strat.getState(strat.simulatedState).getProbability();
		
		

		// System.out.println(strat);

		return strat;
	}

	public int getSimulatedState() {
		return simulatedState;
	}

	public boolean isSimulatedDecision() {
		return simulatedDecision;
	}

	public Strategy mutate(double chi, double range) {
		Strategy strat = clone();

		double rate = chi / (2 * strat.states.length + 1) * strat.states.length;

		strat.states = Arrays.stream(strat.states).map(s -> s.mutate(rate, range)).toArray(State[]::new);

		//System.out.println(Arrays.toString(states));
		
		return strat;
	}
	
	public Strategy mutate2(double chi, double range, int precision) {
		Strategy strat = clone();

		double rate = chi / (2 * strat.states.length + 1) * strat.states.length;

		strat.states = Arrays.stream(strat.states).map(s -> s.mutate2(rate, range, precision)).toArray(State[]::new);

		//System.out.println(Arrays.toString(states));
		
		return strat;
	}


	public static Strategy globalDiscreteRecombination(Strategy mother, Strategy father) {
		// TODO

		Strategy child = new Strategy(mother.h);

		for (int i = 0; i < child.h; i++) {
			child.states[i] = State.globalDiscreteRecombination(mother.states[i], father.states[i]); // TODO
		}

		return child;
	}

	public void updatePayoff(boolean crowded) {
		if (simulatedDecision != crowded)
			payoff++;
	}

	public int getPayoff() {
		return payoff;
	}

	public void simulateStep(int state_no, boolean crowded) {
		simulatedState = state_no;
		simulateStep(crowded);
	}

	public void simulateStep(boolean crowded) {
		simulatedState = getState(simulatedState).simulateStep(crowded);
		simulatedDecision = ThreadLocalRandom.current().nextDouble() < getState(simulatedState).getProbability();
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

	public Strategy clone() {
		Strategy strat = new Strategy(h);
		strat.states = states;
		return strat;
	}

}
