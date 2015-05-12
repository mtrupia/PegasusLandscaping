package Personal_Project.persist;

import Personal_Project.persist.SqliteDatabase;

public class DatabaseProvider {
	private static IDatabase instance;
	
	public static IDatabase getInstance() {
		instance = new SqliteDatabase();
		return instance;
	}
}
