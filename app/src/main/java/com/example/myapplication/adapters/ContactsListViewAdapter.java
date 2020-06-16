package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.classes.ContactsListViewItem;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ContactsListViewAdapter extends BaseAdapter  {
    // Adapter ArrayList
    private ArrayList<ContactsListViewItem> listViewItemList = new ArrayList<ContactsListViewItem>() ;
    protected ProgressBar spinner;

    // ListViewAdapter
    public ContactsListViewAdapter() {

    }


    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "listview_item" Layout
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.contacts_view, parent, false);
        }
            // View convertView2 = inflater.inflate(R.layout.listview_item, parent, false);

            // Layout inflate
            TextView hospital = (TextView) convertView.findViewById(R.id.hospitalText);
            TextView province = (TextView) convertView.findViewById(R.id.provinceText);
            TextView district = (TextView) convertView.findViewById(R.id.districtText);
            TextView municipality = (TextView) convertView.findViewById(R.id.municipalityText);
            TextView contact = (TextView) convertView.findViewById(R.id.contactText);

            // spinner = (ProgressBar) convertView2.findViewById(R.id.progressBar1);
            // Data Set(listViewItemList)
            ContactsListViewItem listViewItem = listViewItemList.get(position);
            hospital.setText(listViewItem.getHospitalStr());
            province.setText(listViewItem.getProvinceStr());
            district.setText(listViewItem.getDistrictStr());
            municipality.setText(listViewItem.getMunicipalityStr());
            contact.setText(listViewItem.getContactStr());

            //Log.i("size", String.valueOf(listViewItemList.size()));
            // if(listViewItemList.size()>0)
            return convertView;

        // else return convertView2;
    }

    // (position)
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // (position)
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }
    //add item
    public void addItem(String hospital, String province, String district,String municipality, String contacts) {
        ContactsListViewItem item = new ContactsListViewItem();
        item.setHospitalStr(hospital);
        item.setProvinceStr(province);
        item.setMunicipalityStr(municipality);
        item.setDistrictStr(district);
        item.setContactStr(contacts);
        listViewItemList.add(item);
    }
}
