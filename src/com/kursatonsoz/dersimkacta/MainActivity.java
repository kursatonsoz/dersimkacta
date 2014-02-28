package com.kursatonsoz.dersimkacta;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends Activity {

	private Database db = new Database(this);
	private SQLiteDatabase dataBase;

	private AdView adView;
	private PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Hatirlat(00, 0, 0);
		Hatirlat(0x08, 0, 0);
		LinearLayout layout = (LinearLayout) findViewById(R.id.reklam);
		adView = new AdView(this, AdSize.BANNER,
				"ca-app-pub-6711691671497768/9551407933");
		layout.addView(adView);
		AdRequest request = new AdRequest();

		adView.loadAd(request);

		final TextView tv = (TextView) findViewById(R.id.textView1);

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						tv.setText(displayData());
					}
				});
			}
		}, 0, 1000);
		Button sec = (Button) findViewById(R.id.button2);
		sec.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent("com.kursatonsoz.dersimkacta.GOSTER"));
			}
		});
	}

	private String displayData() {
		String veri = "";
		Calendar c = Calendar.getInstance();
		String saat = String.format("%02d", c.get(Calendar.HOUR_OF_DAY));
		String dk = String.format("%02d", c.get(Calendar.MINUTE));
		int sai = Integer.valueOf(saat);
		int dai = Integer.valueOf(dk);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE");
		Date d = new Date();
		String currentDay = sdf.format(d);
		String gun = gunCevir(currentDay);
		String x = "SELECT * FROM " + db.TABLE_NAME + " WHERE " + db.KEY_GUN
				+ "='" + gun + "'";
		if (gun != null) {
			veri = "Bugünkü Derslerin\n------------------------------------\n";
			dataBase = db.getWritableDatabase();
			Cursor mCursor = dataBase.rawQuery(x, null);
			if (mCursor.moveToFirst()) {

				do {
					int gelen_saat = Integer.valueOf(String.valueOf(mCursor
							.getString(mCursor.getColumnIndex(db.KEY_SAAT))));
					int gelen_dk = Integer.valueOf(String.valueOf(mCursor
							.getString(mCursor.getColumnIndex(db.KEY_DAKIKA))));
					// eger 30 dk gectiyse gonderme
					if ((sai - gelen_saat) >= 0 && (60 - gelen_dk + dai) > 30) {
						if ((sai-gelen_saat)==0 && (dai-gelen_dk) > 30) {
							continue;
						}

					}
					veri += "Ders Adı -> ";
					veri += String.valueOf(mCursor.getString(mCursor
							.getColumnIndex(db.KEY_DERSADI)));
					veri += "\n";
					veri += "Ders Saati -> ";
					veri += String.valueOf(mCursor.getString(mCursor
							.getColumnIndex(db.KEY_SAAT)));
					veri += ":";
					veri += String.valueOf(mCursor.getString(mCursor
							.getColumnIndex(db.KEY_DAKIKA)));
					veri += "\n";
					veri += "Ders Yeri -> ";
					veri += String.valueOf(mCursor.getString(mCursor
							.getColumnIndex(db.KEY_YER)));
					if ((sai - gelen_saat) <= 0) {
						if ((sai - gelen_saat) == 0) {
							if ((dai - gelen_dk) >= 0) {

								veri += "\nAcele Etmelisin ! Ders "
										+ String.valueOf(dai - gelen_dk)
										+ " dakika önce başladı.";

							} else {
								veri += "\nDersin Başlamasına "
										+ String.valueOf(gelen_dk - dai)
										+ " dakika kaldı.";
							}

						} else if ((sai - gelen_saat) < 0) {
							if((gelen_dk>dai)){
								veri += "\nDersin Başlamasına "
										+ String.valueOf(gelen_saat - sai)
										+ " saat "
										+ String.valueOf(gelen_dk - dai)
										+ " dakika kaldı.";
							}else{
								veri += "\nDersin Başlamasına "
										+ String.valueOf(gelen_saat - sai - 1)
										+ " saat "
										+ String.valueOf(60 + gelen_dk - dai)
										+ " dakika kaldı.";
							}
							

						}

					} else if ((sai - gelen_saat) == 1) {
						veri += "\nAcele Etmelisin ! Ders "
								+ String.valueOf(60 - gelen_dk + dai)
								+ " dakika önce başladı.";
					}
					veri += "\n-------------------------------------------\n";
				} while (mCursor.moveToNext());
			}
			mCursor.close();
			dataBase.close();
		}
		return veri;
	}

	private String gunCevir(String gun) {
		if ("Mon".equals(gun))
			return "Pazartesi";
		if ("Tue".equals(gun))
			return "Salı";
		if ("Wed".equals(gun))
			return "Çarşamba";
		if ("Thu".equals(gun))
			return "Perşembe";
		if ("Fri".equals(gun))
			return "Cuma";
		if ("Sat".equals(gun))
			return "Cumartesi";
		if ("Sun".equals(gun))
			return "Pazar";
		else
			return null;
	}

	private void Hatirlat(int saat, int dk, int sn) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, saat);
		calendar.set(Calendar.MINUTE, dk);
		calendar.set(Calendar.SECOND, sn);

		Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
		pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
				myIntent, 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
