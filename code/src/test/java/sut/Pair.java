package sut;

public class Pair<N, M> {
	
	private N first;
	private M second;
	
	public Pair(N first, M second) {
		this.first = first;
		this.second = second;
	}
	
	public N getFirst() {
		return first;
	}
	public M getSecond() {
		return second;
	}
	
}
