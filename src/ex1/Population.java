package ex1;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("serial")
public class Population extends ArrayList<BitString> {

	public Population() {
	}

	public Population(String population_str) {
		addPopulationListStr(population_str);
	}
	
	public void populateUniformly(int pop_size, int bitstr_length){
		clear();
		for(int i = 0; i < pop_size; i++){
			BitString bitstr = new BitString(bitstr_length);
			bitstr.uniformRandomise();
			add(bitstr);
		}
	}

	public void addPopulationListStr(String population_str) {
		StringTokenizer st = new StringTokenizer(population_str, " ");
		while (st.hasMoreTokens()) {
			add(new BitString(st.nextToken()));
		}
	}

	public BitString tournament(int k) {
		ArrayList<BitString> best_fighters = new ArrayList<BitString>();
		int best_fitness = -1;

		for (int i = 0; i <= k; i++) {
			int rand = ThreadLocalRandom.current().nextInt(size());

			BitString fighter = get(rand);
			int oneMax = fighter.oneMax();
			if (oneMax >= best_fitness) {
				if (oneMax > best_fitness) {
					best_fighters.clear();
					best_fitness = oneMax;
				}

				best_fighters.add(fighter);
			}

		}

		return best_fighters.get(ThreadLocalRandom.current().nextInt(best_fighters.size()));
	}

	/**
	 * without replacement public BitString tournament(int k){
	 * ArrayList<Integer> fighter_indices = new ArrayList<Integer>();
	 * ArrayList<BitString> best_fighters = new ArrayList<BitString>(); int
	 * best_fitness = -1;
	 * 
	 * 
	 * for(int i = 0; i <= k; i++){ int rand =
	 * ThreadLocalRandom.current().nextInt(size());
	 * while(fighter_indices.contains(rand)){ rand =
	 * ThreadLocalRandom.current().nextInt(size()); }
	 * 
	 * BitString fighter = get(rand); if(fighter.oneMax() >= best_fitness){ if
	 * (fighter.oneMax() > best_fitness){ best_fighters.clear(); best_fitness =
	 * fighter.oneMax(); }
	 * 
	 * best_fighters.add(fighter); }
	 * 
	 * fighter_indices.add(rand); }
	 * 
	 * return
	 * best_fighters.get(ThreadLocalRandom.current().nextInt(best_fighters.size()));
	 * }
	 **/

}
