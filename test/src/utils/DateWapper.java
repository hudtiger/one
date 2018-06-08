package utils;

import java.util.Calendar;
import java.util.Date;

public class DateWapper {
	public static DateWapper Create(Date date) {
		return new DateWapper(date);
	}
	private Date date;
	
	private DateWapper(Date date) {
		this.date = date;
	}
	
	private void addInterval(int type,int amount) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(this.date);
		cal.add(type, amount);
		this.date  = cal.getTime();
	}
	
	public DateWapper AddMiliSeconds(int amount) {
		this.addInterval(Calendar.MILLISECOND, amount);	
		return this;
	}
	
	public DateWapper AddSeconds(int amount) {
		this.addInterval(Calendar.SECOND, amount);	
		return this;
	}
	
	public DateWapper AddMinutes(int amount) {
		this.addInterval(Calendar.MINUTE, amount);	
		return this;
	}
	
	public DateWapper AddHours(int amount) {
		this.addInterval(Calendar.HOUR, amount);	
		return this;
	}
	
	public DateWapper AddDays(int amount) {
		this.addInterval(Calendar.DATE, amount);	
		return this;
	}
	
	public DateWapper AddMonths(int amount) {
		this.addInterval(Calendar.MONTH, amount);	
		return this;
	}
	public DateWapper AddYears(int amount) {
		this.addInterval(Calendar.YEAR, amount);	
		return this;
	}
	public Date getDate() {
		return this.date;
	}
}
