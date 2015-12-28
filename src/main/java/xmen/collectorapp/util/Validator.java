package xmen.collectorapp.util;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;

import javax.persistence.PersistenceException;


public class Validator {

	public enum StringValidationOptions 
	{
		NOT_EMPTY, LOWER_CASE_ONLY, CATALOG_NUMBER_FORMAT, LETTERS_ONLY
	} 
	

	public static void validateString(Map<String, ArrayList<String>> errorMap,
			String name, String str, int max,
			EnumSet<StringValidationOptions> options) {
		ArrayList<String> errors = new ArrayList<String>();

		if (str != null && str.length() > max)
			errors.add(Errors.ERR0001);

		if (options.contains(StringValidationOptions.NOT_EMPTY))
			if (str == null || str.isEmpty() || str.length() < 1)
				errors.add(Errors.ERR0002);

		if (options.contains(StringValidationOptions.LOWER_CASE_ONLY))
			if (str!=null && str.toLowerCase() != str)
				errors.add(Errors.ERR0003);
		
		if (options.contains(StringValidationOptions.CATALOG_NUMBER_FORMAT))
			if (str!= null && !str.matches("^[A-Z]{3}-[0-9]{12}$"))
				errors.add(Errors.ERR0004);

		if (options.contains(StringValidationOptions.LETTERS_ONLY))
			if (str!=null && !str.matches("[A-Za-z]*"))
				errors.add(Errors.ERR0007);

		
		// We only need to store array/key in errorMap if errors were found.
		if (errors.size() > 0)
			errorMap.put(name, errors);
	}

	
	public static String getRootCauseMessage(Throwable e)
	{
		Throwable cause = e;
		while(cause.getCause() != null) {
		    cause = cause.getCause();
		}
		
		return cause.getMessage();
	}
	
	public static boolean isNotUniqueException(PersistenceException exception) {
		return getRootCauseMessage(exception).contains("duplicate key value violates unique");
	}
	


}
