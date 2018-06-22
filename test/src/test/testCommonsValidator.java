package test;

import org.apache.commons.validator.GenericValidator;

public class testCommonsValidator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(GenericValidator.isDate("2015-06-06 12:23:56", "yyyy-MM-dd hh:mm:ss", true));
		System.out.println(GenericValidator.isDate("2015-6-06 12:23:56", "yyyy-MM-dd hh:mm:ss", true));
		System.out.println(GenericValidator.isDate("2015-6-06 12:23:56", "yyyy-MM-dd hh:mm:ss", false));
		System.out.println(GenericValidator.matchRegexp("2015-6-06 12:23:56", "\\d{4}-\\d{1,2}-\\d{1,2} (1)?\\d:[1-6]?\\d:[1-6]?\\d"));
	}

}
