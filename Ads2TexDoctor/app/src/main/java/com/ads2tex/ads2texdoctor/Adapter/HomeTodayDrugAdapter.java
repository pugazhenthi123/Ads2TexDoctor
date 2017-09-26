package com.ads2tex.ads2texdoctor.Adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ads2tex.ads2texdoctor.Fragments.HomeFragment;
import com.ads2tex.ads2texdoctor.NavigationDrawer.NavigationDrawerCallbacks;
import com.ads2tex.ads2texdoctor.Pojo.Home_Dialog_Drug_Select_Unit;
import com.ads2tex.ads2texdoctor.Pojo.Userinfo;
import com.ads2tex.ads2texdoctor.R;

import java.util.ArrayList;
import java.util.List;

public class HomeTodayDrugAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<Home_Dialog_Drug_Select_Unit> home_dialog_drug_select_unitList;
    private Spinner Allsp;

    public HomeTodayDrugAdapter(Context context, List<Home_Dialog_Drug_Select_Unit> home_dialog_drug_select_unitList) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.home_dialog_drug_select_unitList = home_dialog_drug_select_unitList;
    }

    @Override
    public int getCount() {
        return home_dialog_drug_select_unitList.size();
    }

    @Override
    public Object getItem(int position) {
        return home_dialog_drug_select_unitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HomeTodayDrugAdapter.ViewHolder holder = null;

        if ( convertView == null )
        {
        /* There is no view at this position, we create a new one.
           In this case by inflating an xml layout */
            convertView = inflater.inflate(R.layout.home_adap_today_drugs, null);
            holder = new HomeTodayDrugAdapter.ViewHolder();
            holder.title_lyt = (LinearLayout) convertView.findViewById(R.id.home_adap_today_title_lyt); 
            holder.name_tv = (TextView) convertView.findViewById(R.id.home_adap_today_name_tv);
            holder.unit_tv = (TextView) convertView.findViewById(R.id.home_adap_today_unit_tv);
            holder.qty_sp = (Spinner) convertView.findViewById(R.id.home_adap_today_spinner_qty_sp);
            holder.qty_sp_btn = (Button) convertView.findViewById(R.id.home_adap_today_spinner_qty_sp_btn); 
            convertView.setTag (holder);
        }
        else
        {
        /* We recycle a View that already exists */
            holder = (HomeTodayDrugAdapter.ViewHolder) convertView.getTag();
        }
        holder.name_tv.setText(home_dialog_drug_select_unitList.get(position).getName());
        holder.unit_tv.setText(Userinfo.getDrug_unit_name_List().get(Userinfo.getDrug_unit_no_List().indexOf(home_dialog_drug_select_unitList.get(position).getUnit())));
        final ArrayList<Integer> Qty_list = new ArrayList<>();
        for(int i = 1; i<= Userinfo.getHome_today_qty_list_size(); i++)
        {
            Qty_list.add(i);
        }
        ArrayAdapter<Integer> home_adap_today_qty_adapter = new ArrayAdapter<>(
                context, R.layout.spinner_item, Qty_list);
        home_adap_today_qty_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.qty_sp.setAdapter(home_adap_today_qty_adapter);
        holder.qty_sp.setSelection(home_dialog_drug_select_unitList.get(position).getQty());
        final View finalConvertView = convertView;
        holder.qty_sp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerDialog(finalConvertView,Qty_list,R.id.home_adap_today_spinner_qty_sp,position);
            }
        });
        return convertView;
    }
    private static class ViewHolder{
        TextView name_tv,unit_tv;
        LinearLayout title_lyt;
        Spinner qty_sp;
        Button qty_sp_btn;
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

    public void SpinnerDialog(View view, final List<Integer> listall,final int id,final int pos)
    {
        Allsp = (Spinner) view.findViewById(id);
        final View dialogView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_spinner, null, false);
        final EditText edittext = (EditText) dialogView.findViewById(R.id.text);
        final ListView listView = (ListView) dialogView.findViewById(R.id.list);
        final ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, listall);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setCancelable(true);
        builder1.setView(dialogView);
        final AlertDialog alert11 = builder1.create();
        alert11.show();
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Allsp.setSelection(listall.indexOf(adapter.getItem(position)));
                alert11.dismiss();
                HomeFragment.home_dialog_drug_select_unitList.get(pos).setQty(position);
                HomeFragment.homeTodayDrugAdapter.notifyDataSetChanged();
            }
        });
    }

}
