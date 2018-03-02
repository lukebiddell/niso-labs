package elFarolBar;

import java.util.ArrayList;

import helper.BitString;

public class StrategyPopulation extends ArrayList<Strategy> {

	public StrategyPopulation() {

	}

	public void populateUniformly(int pop_size, int bitstr_length) {
		clear();
		for (int i = 0; i < pop_size; i++) {
			BitString bitstr = new BitString(bitstr_length);
			bitstr.uniformRandomise();
			add(bitstr);
		}
	}
}
