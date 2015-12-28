package xmen.collectorapp.config;

public class SettingsConfig {

	/* Project information */
	public static final String PROJECT_ROOT_PACKAGE = "xmen.collectorapp";
	public static final String PROJECT_CONTROLLER_PACKAGE = "xmen.collectorapp.controller";
	
	/*Hibernate console settings*/
	public static final boolean HIBERNATE_SHOW_SQL_IN_CONSOLE = true;
	public static final boolean HIBERNATE_FORMAT_SQL_IN_CONSOLE = true;//only works if SHOW_SQL_IN_CONSOLE is true
	
	/* Hibernate datbase dialect settings */
	public static final String HIBERNATE_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
	
	/* Hibernate dll setting:
	 * list of possible options are, validate: validate the schema, makes no
	 * changes to the database, update: update the schema, create: creates the
	 * schema, destroying previous data, create-drop: drop the schema at the end
	 * of the session.
	 */
	public static final String HIBERNATE_SCHEMA_DLL_STRATEGY = "create";

	/* Database access settings */
	public static final String DATABASE_LOGIN_USERNAME = "postgres";
	public static final String DATABASE_LOGIN_PASSWORD = "";

	/* Database reference settings */
	public static final String DATABASE_NAME = "xmencollector";
	public static final String DATABASE_SCHEMA = "itemschema";

	/* Database connection settings */
	public static final String DATABASE_DRIVER_CLASS_NAME = "org.postgresql.Driver";
	public static final String DATABASE_HOST = "localhost";
	public static final String DATABASE_PORT = "5432";
	public static final String DATABASE_PROVIDER = "postgresql";
	public static final String DATABASE_AUTO_RECONNECT = "true";
	public static final String DATABASE_CONNECTION_URL = "jdbc:"
			+ SettingsConfig.DATABASE_PROVIDER + "://"
			+ SettingsConfig.DATABASE_HOST + ":" + SettingsConfig.DATABASE_PORT
			+ "/" + SettingsConfig.DATABASE_NAME + "?autoReconnect="
			+ SettingsConfig.DATABASE_AUTO_RECONNECT;
}
