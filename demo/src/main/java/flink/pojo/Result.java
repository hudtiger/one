package flink.pojo;

public class Result {
	String subject;
	String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	long counter;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public long getCounter() {
		return counter;
	}
	public void setCounter(long counter) {
		this.counter = counter;
	}
}
