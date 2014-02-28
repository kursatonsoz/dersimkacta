package com.kursatonsoz.dersimkacta;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DisplayAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> id;
	private ArrayList<String> dersAdi;
	private ArrayList<String> dersSaat;
	private ArrayList<String> dersDk;
	private ArrayList<String> dersGun;
	

	public DisplayAdapter(Context c, ArrayList<String> id,ArrayList<String> adi, ArrayList<String> saat, ArrayList<String> dk, ArrayList<String> gun) {
		this.mContext = c;

		this.id = id;
		this.dersAdi = adi;
		this.dersSaat = saat;
		this.dersDk = dk;
		this.dersGun = gun;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return id.size();
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
			layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child = layoutInflater.inflate(R.layout.liste, null);
			mHolder = new Holder();
			mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
			mHolder.txt_adi = (TextView) child.findViewById(R.id.adi);
			mHolder.txt_saat = (TextView) child.findViewById(R.id.saat);
			mHolder.txt_dk = (TextView) child.findViewById(R.id.dk);
			mHolder.txt_gun = (TextView) child.findViewById(R.id.gun);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
		mHolder.txt_id.setText(id.get(pos));
		
		mHolder.txt_adi.setText(dersAdi.get(pos));
		mHolder.txt_saat.setText(dersSaat.get(pos));
		mHolder.txt_dk.setText(dersDk.get(pos));
		mHolder.txt_gun.setText(dersGun.get(pos));

		return child;
	}

	public class Holder {
		TextView txt_id;
		TextView txt_adi;
		TextView txt_saat;
		TextView txt_dk;
		TextView txt_gun;
	}

}
