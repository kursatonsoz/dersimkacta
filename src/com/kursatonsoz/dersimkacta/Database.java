package com.kursatonsoz.dersimkacta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper{

	static String DATABASE_NAME="dersimkacta";
	public static final String TABLE_NAME="dersler";
	public static final String KEY_DERSADI="dersadi";
	public static final String KEY_GUN="gun";
	public static final String KEY_SAAT="saat";
	public static final String KEY_DAKIKA="dakika";
	public static final String KEY_YER="yer";
	public static final String KEY_ID="id";
	public Database(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_DERSADI+" TEXT, "+KEY_GUN+" TEXT, "+KEY_SAAT+" TEXT, "+KEY_DAKIKA+" TEXT, "+KEY_YER+" TEXT)";
		db.execSQL(CREATE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		onCreate(db);

	}

}
