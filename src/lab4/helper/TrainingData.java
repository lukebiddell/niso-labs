package lab4.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public final class TrainingData {

	private final LinkedList<TrainingLine> lines = new LinkedList<TrainingLine>();

	public static TrainingData parseFile(String file) throws IOException {
		TrainingData data = new TrainingData();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = br.readLine()) != null) {
			double[] values = Arrays.stream(line.split("\t"))
					.mapToDouble(Double::parseDouble)
					.toArray();
			data.lines.add(
					new TrainingLine(
							new Vector(
									Arrays.copyOf(values, values.length - 1)), 
							values[values.length - 1]));
		}
		br.close();

		return data;

		// throw new IllegalArgumentException("File doesn't contain p line");
	}
	
	public static TrainingData parseFile(String file, int n, int m) throws IOException{
		TrainingData data = parseFile(file);
		assert data.lines.size() == m;
		assert data.lines.getFirst().getN() == n;
		return data;
	}

	public String toString() {
		return lines.toString();
	}

	public LinkedList<TrainingLine> getLines(){
		return lines;
	}
}
