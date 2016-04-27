package xziar.mylesson.data;

import java.io.File;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DBUtil
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
	private static final String insertSQL = "insert into lessons"
			+ "(Name,Place,TimeFrom,TimeLast,TimeWeek,WeekFrom,WeekTo,Color) "
			+ "values(?,?,?,?,?,?,?,?)";
	private static final String deleteSQL = "delete from lessons where LID=?";
	private static final String selectAllSQL = "select "
			+ "LID,Name,Place,TimeFrom,TimeLast,TimeWeek,WeekFrom,WeekTo,Color "
			+ "from lessons";
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
			delete();
			db.execSQL(initSQL);
		}
	}

	static public void add(LessonBean lb)
	{
		SQLiteStatement stmt = db.compileStatement(insertSQL);
		stmt.bindString(1, lb.lessonName);
		stmt.bindString(2, lb.place);
		stmt.bindLong(3, lb.timeFrom);
		stmt.bindLong(4, lb.timeLast);
		stmt.bindLong(5, lb.timeWeek);
		stmt.bindLong(6, lb.weekFrom);
		stmt.bindLong(7, lb.weekTo);
		stmt.bindLong(8, lb.color);
		try
		{
			long ret = stmt.executeInsert();
			lb.LID = (int) ret;
		}
		catch(SQLException e)
		{
			Log.e("sql", e.getLocalizedMessage());
		}
	}
	
	static public void delete()
	{
		db.execSQL("drop table lessons");
	}
	
	static public void delete(LessonBean lb)
	{
		SQLiteStatement stmt = db.compileStatement(deleteSQL);
		stmt.bindLong(1, lb.LID);
		try
		{
			int num = stmt.executeUpdateDelete();
			Log.v("tester", "finish delete,affect "+num);
		}
		catch(SQLException e)
		{
			Log.e("sql", e.getLocalizedMessage());
		}
	}
	
	static public LessonBean[] query()
	{
		Cursor cursor = db.rawQuery(selectAllSQL, null);
		int cnt = cursor.getCount();
		if(cnt == 0)
			return null;
		LessonBean[] lbs = new LessonBean[cnt];
		cursor.moveToFirst();
		for(int a=0;a<cnt;a++)
		{
			lbs[a] = new LessonBean();
			lbs[a].LID = cursor.getInt(0);
			lbs[a].lessonName = cursor.getString(1);
			lbs[a].place = cursor.getString(2);
			lbs[a].timeFrom = cursor.getInt(3);
			lbs[a].timeLast = cursor.getInt(4);
			lbs[a].timeWeek = cursor.getInt(5);
			lbs[a].weekFrom = cursor.getInt(6);
			lbs[a].weekTo = cursor.getInt(7);
			lbs[a].color = cursor.getInt(8);
			cursor.moveToNext();
		}
		return lbs;
	}
	
	static public void onExit()
	{
		if (db != null)
			db.close();
	}
}
