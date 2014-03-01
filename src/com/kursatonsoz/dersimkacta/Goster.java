package com.kursatonsoz.dersimkacta;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Goster extends Activity {

	private Database db;
	private SQLiteDatabase dataBase;

	private ArrayList<String> ders_id = new ArrayList<String>();
	private ArrayList<String> ders_adi = new ArrayList<String>();
	private ArrayList<String> ders_gun = new ArrayList<String>();
	private ArrayList<String> ders_saat = new ArrayList<String>();
	private ArrayList<String> ders_dk = new ArrayList<String>();
	private ArrayList<String> ders_yer = new ArrayList<String>();

	private ListView dersList;
	private AlertDialog.Builder build;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goster);

		dersList = (ListView) findViewById(R.id.List);

		db = new Database(this);

		// yeni kayit eklemek icin actigim intent
		findViewById(R.id.btnAdd).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(), Ekle.class);
				i.putExtra("update", false);
				startActivity(i);

			}
		});

		// tiklayinca update olayi olacak
		dersList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent i = new Intent(getApplicationContext(), Ekle.class);
				i.putExtra("dersAdi", ders_adi.get(arg2));
				i.putExtra("dersGun", ders_gun.get(arg2));
				i.putExtra("dersSaat", ders_saat.get(arg2));
				i.putExtra("dersDk", ders_dk.get(arg2));
				i.putExtra("dersYer", ders_yer.get(arg2));
				i.putExtra("ID", ders_id.get(arg2));
				i.putExtra("update", true);
				startActivity(i);

			}
		});

		// uzun basilirsa silinsin mi tarzi diyalog
		dersList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {

				build = new AlertDialog.Builder(Goster.this);
				build.setTitle(ders_adi.get(arg2) + " siliniyor");
				build.setMessage("Emin misiniz ?");
				build.setPositiveButton("Evet",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								Toast.makeText(
										getApplicationContext(),
										ders_adi.get(arg2)
												+ " silindi.", 3000).show();

								dataBase.delete(
										Database.TABLE_NAME,
										Database.KEY_ID + "="
												+ ders_id.get(arg2), null);
								displayData();
								dialog.cancel();
							}
						});

				build.setNegativeButton("Hayır",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				AlertDialog alert = build.create();
				alert.show();

				return true;
			}
		});
	}
	
	

	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}

	/**
	 * database'den cekiyorum
	 */
	private void displayData() {
		dataBase = db.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
				+ Database.TABLE_NAME, null);

		ders_id.clear();
		ders_adi.clear();
		ders_gun.clear();
		ders_saat.clear();
		ders_dk.clear();
		ders_yer.clear();
		if (mCursor.moveToFirst()) {
			do {
				ders_id.add(mCursor.getString(mCursor
						.getColumnIndex(Database.KEY_ID)));
				ders_adi.add(mCursor.getString(mCursor
						.getColumnIndex(Database.KEY_DERSADI)));
				ders_gun.add(mCursor.getString(mCursor
						.getColumnIndex(Database.KEY_GUN)));
				ders_saat.add(mCursor.getString(mCursor
						.getColumnIndex(Database.KEY_SAAT)));
				ders_dk.add(mCursor.getString(mCursor
						.getColumnIndex(Database.KEY_DAKIKA)));
				ders_yer.add(mCursor.getString(mCursor
						.getColumnIndex(Database.KEY_YER)));

			} while (mCursor.moveToNext());
		}
		DisplayAdapter disadpt = new DisplayAdapter(Goster.this,
				ders_adi, ders_saat ,ders_dk, ders_gun,ders_yer);
		dersList.setAdapter(disadpt);
		mCursor.close();
	}

}
