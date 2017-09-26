package com.ads2tex.ads2texdoctor.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ads2tex.ads2texdoctor.NavigationDrawer.NavigationDrawerCallbacks;
import com.ads2tex.ads2texdoctor.Pojo.Patient_History;
import com.ads2tex.ads2texdoctor.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class HomePatientHistoryAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<Patient_History> patient_historyList;

    public HomePatientHistoryAdapter(Context context, List<Patient_History> patient_historyList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.patient_historyList = patient_historyList;
    }

    @Override
    public int getCount() {
        return patient_historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return patient_historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomePatientHistoryAdapter.ViewHolder holder = null;

        if ( convertView == null )
        {
        /* There is no view at this position, we create a new one.
           In this case by inflating an xml layout */
            convertView = inflater.inflate(R.layout.home_adap_patient_history, null);
            holder = new ViewHolder();
            holder.date_tv = (TextView) convertView.findViewById(R.id.home_adap_history_date_tv);
            holder.temp_tv = (TextView) convertView.findViewById(R.id.home_adap_history_temperature_tv);
            holder.sugar_tv = (TextView) convertView.findViewById(R.id.home_adap_history_sugar_tv);
            holder.pressure_tv = (TextView) convertView.findViewById(R.id.home_adap_history_pressure_tv);
            holder.title_lyt = (LinearLayout) convertView.findViewById(R.id.home_adap_history_title_lyt);

            convertView.setTag (holder);
        }
        else
        {
        /* We recycle a View that already exists */
            holder = (HomePatientHistoryAdapter.ViewHolder) convertView.getTag();

        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date get_date = null;
        try {
            get_date = sdf.parse(patient_historyList.get(position).getDate());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        holder.date_tv.setText(formatter.format(get_date));
        holder.temp_tv.setText(String.valueOf(patient_historyList.get(position).getTempurature()));
        holder.temp_tv.setTextColor(Color.parseColor(getColor(1,patient_historyList.get(position).getTempurature())));
        holder.sugar_tv.setText(String.valueOf(patient_historyList.get(position).getSugar()));
        holder.sugar_tv.setTextColor(Color.parseColor(getColor(2,patient_historyList.get(position).getSugar())));
        holder.pressure_tv.setText(String.valueOf(patient_historyList.get(position).getPressure()));
        holder.pressure_tv.setTextColor(Color.parseColor(getColor(3,patient_historyList.get(position).getPressure())));
        if(position==0)
        {
            holder.title_lyt.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
    private static class ViewHolder{
        TextView date_tv,temp_tv,sugar_tv,pressure_tv;
        LinearLayout title_lyt;
    }
    public String getColor(int id,int val)
    {
        String color="#000000";
        if(id==1)
        {
            if(val<=100)
            {
                color = "#53B349";
            }else if(val > 100 && val <= 104){
                color = "#E6B900";
            }else{
                color = "#D97A65";
            }
        }else if(id==2){
            if(val<=80)
            {
                color = "#428bca";
            }else if(val > 80 && val <= 100)
            {
                color = "#53B349";
            }else if(val > 101 && val <= 125)
            {
                color = "#E6B900";
            }else if(val > 126){
                color = "#D97A65";
            }
        }else{
            if(val<=90)
            {
                color = "#428bca";
            }else if(val > 90 && val <= 120)
            {
                color = "#53B349";
            }else if(val > 120 && val <= 140)
            {
                color = "#E6B900";
            }else if(val > 140){
                color = "#D97A65";
            }
        }

        return color;
    }
}
