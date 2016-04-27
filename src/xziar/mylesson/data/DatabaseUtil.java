package xziar.mylesson.data;

import java.io.File;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseUtil
{
	private static final String dbName = "mylesson.db";
	private static final String initSQL = "create table lessons("
			+ "LID integer primary key autoincrement," 
			+ "Name text not null,"
			+ "Place text not null," 
			+ "TimeFrom integer not null,"
			+ "TimeLast integer not null," 
			+ "TimeWeek integer not null,"
			+ "WeekFrom integer not null," 
			+ "WeekTo integer not null,"
			+ "Color integer not null" 
			+ ");";
	private static SQLiteDatabase db;

	static public void onInit(File dir)
	{
		Log.v("tester", dir.getAbsolutePath());
		db = SQLiteDatabase.openOrCreateDatabase(dir.getAbsolutePath() + dbName, null);
		try
		{
			db.execSQL(initSQL);
		}
		catch(SQLException e)
		{
			Log.e("sql", e.getLocalizedMessage());
		}
	}

	static public void onExit()
	{
		if (db != null)
			db.close();
	}
}
