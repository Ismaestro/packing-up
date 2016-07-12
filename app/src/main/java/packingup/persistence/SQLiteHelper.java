package packingup.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "items.db";
	private static final int DATABASE_VERSION = 1;

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(ItemQuery.DATABASE_CREATE);
		inserDataItems(database);
	}

	private void inserDataItems(SQLiteDatabase database) {
		SQLiteStatement statement = database.compileStatement(ItemQuery.DATABASE_INSERT_DATA);
		database.beginTransaction();
		statement.execute();
		database.setTransactionSuccessful();
		database.endTransaction();
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		database.execSQL(ItemQuery.DATABASE_DROP);
		this.onCreate(database);
	}

}
