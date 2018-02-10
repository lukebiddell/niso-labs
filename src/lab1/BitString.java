package lab1;


import java.util.BitSet;
import java.util.concurrent.ThreadLocalRandom;

public class BitString {
	private int length;
	private BitSet bitset;

	public BitString(int length) {
		this.length = length;
		bitset = new BitSet(5);
	}

	public BitString(String bits) {
		length = bits.length();
		bitset = fromString(bits);
	}

	public void uniformRandomise() {
		bitset.clear();
		for (int i = 0; i < length; i++) {
			if (ThreadLocalRandom.current().nextBoolean())
				bitset.set(i);
		}
	}

	public int oneMax() {
		int ones = 0;
		for (int i = 0; i < length; i++) {
			if (bitset.get(i))
				ones++;
		}
		return ones;
	}

	private BitSet fromString(String binary) {
		BitSet bitset = new BitSet(binary.length());
		for (int i = 0; i < binary.length(); i++) {
			if (binary.charAt(i) == '1') {
				bitset.set(i);
			}
		}
		return bitset;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (bitset.get(i)) {
				sb.append("1");
			} else {
				sb.append("0");
			}
		}
		return sb.toString();
	}

	public int size() {
		return length;
	}

	public BitSet getBitSet() {
		return bitset;
	}

	public BitString clone() {
		return new BitString(this.toString());
	}
}
