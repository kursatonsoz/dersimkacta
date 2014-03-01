package com.kursatonsoz.dersimkacta;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DisplayAdapterG extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> dersAdi;
	private ArrayList<String> dersSaat;
	private ArrayList<String> dersYer;
	private ArrayList<String> dersUyari;

	public DisplayAdapterG(Context c, ArrayList<String> adi,
			ArrayList<String> saat, ArrayList<String> yer,
			ArrayList<String> uyari) {
		this.mContext = c;

		this.dersAdi = adi;
		this.dersSaat = saat;
		this.dersYer = yer;
		this.dersUyari = uyari;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return dersAdi.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int pos, View child, ViewGroup parent) {
		Holder mHolder;
		LayoutInflater layoutInflater;
		if (child == null) {
			layoutInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child = layoutInflater.inflate(R.layout.mainlist, null);
			mHolder = new Holder();
			mHolder.txt_adi = (TextView) child.findViewById(R.id.adgst);
			mHolder.txt_saat = (TextView) child.findViewById(R.id.saatgst);
			mHolder.txt_yer = (TextView) child.findViewById(R.id.yergst);
			mHolder.txt_uyari = (TextView) child.findViewById(R.id.uyarigst);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}

		mHolder.txt_adi.setText(dersAdi.get(pos));
		mHolder.txt_saat.setText(dersSaat.get(pos));
		mHolder.txt_yer.setText(dersYer.get(pos));
		mHolder.txt_uyari.setText(dersUyari.get(pos));

		return child;
	}

	public class Holder {
		TextView txt_adi;
		TextView txt_saat;
		TextView txt_yer;
		TextView txt_uyari;
	}

}
