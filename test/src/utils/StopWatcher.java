package utils;

public class StopWatcher {
	long start = System.currentTimeMillis();
	public long watch(){
		return (System.currentTimeMillis()-start);
	}
}
