package com.kursatonsoz.dersimkacta;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Ekle extends Activity implements OnClickListener {
	private Button btn_save;
	private EditText ders_adiTx, ders_yeriTx;
	private Spinner ders_saatSp, ders_dkSp, ders_gunSp;
	private Database mHelper;
	private SQLiteDatabase dataBase;
	private String id, dersAdi, saat, dakika, gun, yer;
	private boolean isUpdate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ekle);

		btn_save = (Button) findViewById(R.id.save_btn);
		ders_adiTx = (EditText) findViewById(R.id.ders_adiTxt);
		ders_yeriTx = (EditText) findViewById(R.id.ders_yeriTxt);
		ders_saatSp = (Spinner) findViewById(R.id.spinner1);
		ders_dkSp = (Spinner) findViewById(R.id.spinner2);
		ders_gunSp = (Spinner) findViewById(R.id.spinner3);

		isUpdate = getIntent().getExtras().getBoolean("update");
		if (isUpdate) {
			id = getIntent().getExtras().getString("ID");
			dersAdi = getIntent().getExtras().getString("dersAdi");
			yer = getIntent().getExtras().getString("dersYer");
			saat = getIntent().getExtras().getString("dersSaat");
			dakika = getIntent().getExtras().getString("dersDk");
			gun = getIntent().getExtras().getString("dersGun");

			ders_adiTx.setText(dersAdi);
			ders_yeriTx.setText(yer);

			ders_saatSp.setSelection(getIndex(ders_saatSp, saat));
			ders_dkSp.setSelection(getIndex(ders_dkSp, dakika));
			ders_gunSp.setSelection(getIndex(ders_gunSp, gun));

		}

		btn_save.setOnClickListener(this);

		mHelper = new Database(this);

	}

	private int getIndex(Spinner spinner, String myString) {
		int index = 0;

		for (int i = 0; i < spinner.getCount(); i++) {
			if (spinner.getItemAtPosition(i).toString()
					.equalsIgnoreCase(myString)) {
				index = i;
				i = spinner.getCount();
			}
		}
		return index;
	}

	// kaydet butonu olaylari
	public void onClick(View v) {
		ders_saatSp = (Spinner) findViewById(R.id.spinner1);
		ders_dkSp = (Spinner) findViewById(R.id.spinner2);
		ders_gunSp = (Spinner) findViewById(R.id.spinner3);

		dersAdi = ders_adiTx.getText().toString().trim();
		yer = ders_yeriTx.getText().toString().trim();
		saat = String.valueOf(ders_saatSp.getSelectedItem());
		dakika = String.valueOf(ders_dkSp.getSelectedItem());
		gun = String.valueOf(ders_gunSp.getSelectedItem());

		if (dersAdi.length() > 0 && yer.length() > 0) {
			saveData();
		} else {
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
					Ekle.this);
			alertBuilder.setTitle("Geçersiz Veri");
			alertBuilder.setMessage("Lütfen, Boş Bırakmayınız");
			alertBuilder.setPositiveButton("Tamam",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();

						}
					});
			alertBuilder.create().show();
		}

	}

	/**
	 * veritabanina kayit asamasi
	 */
	private void saveData() {
		dataBase = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(Database.KEY_DERSADI, dersAdi);
		values.put(Database.KEY_YER, yer);
		values.put(Database.KEY_SAAT, saat);
		values.put(Database.KEY_DAKIKA, dakika);
		values.put(Database.KEY_GUN, gun);

		System.out.println("");
		if (isUpdate) {
			dataBase.update(Database.TABLE_NAME, values, Database.KEY_ID + "="
					+ id, null);
		} else {
			dataBase.insert(Database.TABLE_NAME, null, values);
		}
		dataBase.close();
		finish();

	}

}
