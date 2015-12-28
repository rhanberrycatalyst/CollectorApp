package xmen.collectorapp.util;

import javax.persistence.PersistenceException;

public class Errors {

	public static final String ERR0001 = "ERR0001: value longer then maximum limit.";
	public static final String ERR0002 = "ERR0002: value cannot be empty.";
	public static final String ERR0003 = "ERR0003: value can only contain lower case.";
	public static final String ERR0004 = "ERR0004: value is not in catalog format AAA-000000000000";
	public static final String ERR0005 = "ERR0005: duplicate entry in database";
	public static final String ERR0006 = "ERR0006: unknown database error";
	public static final String ERR0007 = "ERR0007: value cannot contain non-letters.";
	public static final String ERR0008 = "ERR0008: entity ID doesn't exist in database, can't be updated.";
	
	public static String convertDatabaseExceptionToERRMessage(PersistenceException exception)
	{
		if( exception instanceof UpdatingNonExistantEntityException)
			return ERR0008;
		
		if (Validator.isNotUniqueException(exception))
			return ERR0005;
		
		return ERR0006;

	}
}
