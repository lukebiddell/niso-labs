package helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class MaxSatInstance {

	private int n, m, x;

	// private LinkedList<Clause> clauses;

	private BufferedReader br;

	public MaxSatInstance() {

	}

	public MaxSatInstance(String file) throws IOException {
		loadFile(file);
	}

	private void loadFile(String file) throws IOException{
		br = new BufferedReader(new FileReader(file));
		String line = null;  
		while ((line = br.readLine()) != null )  
		{  
			if(line.startsWith("p")){
				break;
			} 
			if(line.startsWith("c")){
				//break;
			}
		} 
	}

	private Clause getNextClause() throws IOException {
		String line = br.readLine();

		if (line == null)
			return null;
		else
			return new Clause(line);

	}
	
	public int countClausesSatisfied(BitString assignment) throws IOException{
		int count = 0;
		Clause clause = getNextClause();
		while(clause != null){
			count += clause.satisfiedByInt(assignment);
			clause = getNextClause();
		}
		return count;
		
	}

}
