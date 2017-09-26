package com.ads2tex.ads2texdoctor.Adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ads2tex.ads2texdoctor.App.AppController;
import com.ads2tex.ads2texdoctor.Config.Config;
import com.ads2tex.ads2texdoctor.Fragments.HomeFragment;
import com.ads2tex.ads2texdoctor.MainActivity;
import com.ads2tex.ads2texdoctor.NavigationDrawer.NavigationDrawerCallbacks;
import com.ads2tex.ads2texdoctor.Pojo.PatientDrugs;
import com.ads2tex.ads2texdoctor.Pojo.PatientRecords;
import com.ads2tex.ads2texdoctor.Pojo.Patient_History;
import com.ads2tex.ads2texdoctor.Pojo.Patient_Today_Drugs;
import com.ads2tex.ads2texdoctor.R;
import com.ads2tex.ads2texdoctor.volley.Request;
import com.ads2tex.ads2texdoctor.volley.Response;
import com.ads2tex.ads2texdoctor.volley.VolleyError;
import com.ads2tex.ads2texdoctor.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeRecordsAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private Context context;
    Bitmap bmp;
    private List<PatientRecords> patientRecordslist;


    public HomeRecordsAdapter(Context mContext, List<PatientRecords> customerrecordslist) {
        this.context = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.patientRecordslist = customerrecordslist;
    }

    @Override
    public int getCount() {
        return patientRecordslist.size();
    }

    @Override
    public Object getItem(int position) {
        return patientRecordslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if ( convertView == null )
        {
        /* There is no view at this position, we create a new one.
           In this case by inflating an xml layout */
            convertView = inflater.inflate(R.layout.home_adap_patient, null);
            holder = new ViewHolder();
            holder.patient_name_tv = (TextView) convertView.findViewById(R.id.home_adap_patient_name_tv);
            holder.diseases_tv = (TextView) convertView.findViewById(R.id.home_adap_diseases_tv);
            holder.no_of_check_ups_tv = (TextView) convertView.findViewById(R.id.home_adap_no_of_checkups_txt);
            holder.weight_tv = (TextView) convertView.findViewById(R.id.home_adap_weight_txt);
            holder.temp_tv = (TextView) convertView.findViewById(R.id.home_adap_temperature_txt);
            holder.sugar_tv = (TextView) convertView.findViewById(R.id.home_adap_sugar_txt);
            holder.pressure_tv = (TextView) convertView.findViewById(R.id.home_adap_pressure_txt);
            holder.date_tv = (TextView) convertView.findViewById(R.id.home_adap_patient_date_tv);
            holder.status_tv = (TextView) convertView.findViewById(R.id.home_adap_status_txt);
            holder.desc_tv = (TextView) convertView.findViewById(R.id.home_adap_description_tv);
            convertView.setTag (holder);
        }
        else
        {
        /* We recycle a View that already exists */
            holder = (ViewHolder) convertView.getTag();

        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        holder.patient_name_tv.setText(patientRecordslist.get(position).getName()+"("+patientRecordslist.get(position).getAge()+")");
        holder.diseases_tv.setText(patientRecordslist.get(position).getDiseases());
        holder.no_of_check_ups_tv.setText(": " +  patientRecordslist.get(position).getNoofchecks());
        holder.weight_tv.setText(": " + patientRecordslist.get(position).getWeight());
        holder.temp_tv.setText(": " + patientRecordslist.get(position).getTemperature());
        holder.temp_tv.setTextColor(Color.parseColor(getColor(1,patientRecordslist.get(position).getTemperature())));
        holder.sugar_tv.setText(": " + patientRecordslist.get(position).getSugar());
        holder.sugar_tv.setTextColor(Color.parseColor(getColor(2,patientRecordslist.get(position).getSugar())));
        holder.pressure_tv.setText(": " + patientRecordslist.get(position).getPressure());
        holder.pressure_tv.setTextColor(Color.parseColor(getColor(3,patientRecordslist.get(position).getPressure())));
        Date get_date = null;
        try {
            get_date = sdf.parse(patientRecordslist.get(position).getDate());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        holder.date_tv.setText(formatter.format(get_date));
        holder.status_tv.setText(patientRecordslist.get(position).getStatus());
        holder.status_tv.setTextColor(Color.parseColor(patientRecordslist.get(position).getStatus_color()));

        if(!patientRecordslist.get(position).getDescription().equals(""))
        {
            holder.desc_tv.setVisibility(View.VISIBLE);
            holder.desc_tv.setText(patientRecordslist.get(position).getDescription());
        }
        return convertView;
    }


    private static class ViewHolder{
        TextView patient_name_tv,diseases_tv,no_of_check_ups_tv,weight_tv,temp_tv,sugar_tv,pressure_tv,date_tv,status_tv,desc_tv;
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
