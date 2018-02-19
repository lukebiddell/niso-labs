package helper;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import javax.naming.directory.InvalidAttributeValueException;

public class BitString {
	private int length;
	private BitSet bitset;

	public BitString(int length) {
		this.length = length;
		bitset = new BitSet(length);
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

	public static BitString fromClauseForm(String clauseform) {

		String[] values = clauseform.split(" ");

		BitString bs = new BitString(values.length);

		for (int i = 0; i < values.length; i++) {
			bs.getBitSet().set(i, Integer.parseInt(values[i]) > 0);
		}
		
		return bs;

	}
	
	public void flip(){
		bitset.flip(0, length);
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
		BitString bs = new BitString(length);
		bs.bitset = (BitSet) bitset.clone();
		return bs;
	}

	@Override
	public boolean equals(Object v) {
		if (v instanceof BitString) {
			BitString bs = (BitString) v;
			return bs.toString().equals(this.toString());
		} else {
			return false;
		}

	}
}
