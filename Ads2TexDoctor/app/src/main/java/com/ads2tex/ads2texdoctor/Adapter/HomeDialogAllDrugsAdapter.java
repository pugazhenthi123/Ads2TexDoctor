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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ads2tex.ads2texdoctor.Fragments.HomeFragment;
import com.ads2tex.ads2texdoctor.Pojo.Home_Dialog_All_Drugs;
import com.ads2tex.ads2texdoctor.Pojo.Home_Dialog_Drug_Select_Unit;
import com.ads2tex.ads2texdoctor.Pojo.Userinfo;
import com.ads2tex.ads2texdoctor.R;

import java.util.ArrayList;
import java.util.List;

public class HomeDialogAllDrugsAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private Context context;
    private List<Home_Dialog_All_Drugs> home_dialog_all_drugsList;

    //Allsp Object used for set Unit Selction For Particular Medicine
    private Spinner Allsp;

    public HomeDialogAllDrugsAdapter(Context context, List<Home_Dialog_All_Drugs> home_dialog_all_drugsList) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.home_dialog_all_drugsList = home_dialog_all_drugsList;
    }

    @Override
    public int getCount() {
        return home_dialog_all_drugsList.size();
    }

    @Override
    public Object getItem(int position) {
        return home_dialog_all_drugsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HomeDialogAllDrugsAdapter.ViewHolder holder = null;

        if ( convertView == null )
        {
        /* There is no view at this position, we create a new one.
           In this case by inflating an xml layout */
            convertView = inflater.inflate(R.layout.home_dialog_all_drug, null);
            holder = new ViewHolder();
            holder.name_tv = (TextView) convertView.findViewById(R.id.home_dialog_drug_all_name_tv);
            holder.generic_name_tv = (TextView) convertView.findViewById(R.id.home_dialog_drug_all_generic_txt);
            holder.manufacturer_tv = (TextView) convertView.findViewById(R.id.home_dialog_drug_all_manufacturer_txt);
            holder.diseases_tv = (TextView) convertView.findViewById(R.id.home_dialog_drug_all_diseases_txt);
            holder.unit_tv = (TextView) convertView.findViewById(R.id.home_dialog_drug_all_units_txt);
            holder.unitSp = (Spinner) convertView.findViewById(R.id.home_dialog_drug_all_drug_sp);
            holder.check_btn = (Button) convertView.findViewById(R.id.home_dialog_drug_all_check_btn);
            holder.sp_btn = (Button) convertView.findViewById(R.id.home_dialog_drug_all_sp_btn);
            holder.compare_btn = (Button) convertView.findViewById(R.id.home_dialog_drug_all_compare_btn);
            holder.compare_iv = (ImageView) convertView.findViewById(R.id.home_dialog_drug_all_add_compare_arrow);
            holder.compare_tv = (TextView) convertView.findViewById(R.id.home_dialog_drug_all_add_compare_tv);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.home_dialog_drug_all_check);
            convertView.setTag (holder);
        }
        else
        {
        /* We recycle a View that already exists */
            holder = (HomeDialogAllDrugsAdapter.ViewHolder) convertView.getTag();

        }

            holder.name_tv.setText(home_dialog_all_drugsList.get(position).getName());
            holder.generic_name_tv.setText(home_dialog_all_drugsList.get(position).getGeneric());
            holder.manufacturer_tv.setText(home_dialog_all_drugsList.get(position).getManufacturer());
            holder.diseases_tv.setText(home_dialog_all_drugsList.get(position).getDiseases());
            holder.unit_tv.setText(home_dialog_all_drugsList.get(position).getUnits());

            final ArrayList<String> home_dialog_spinner_list = new ArrayList<>();
            for (int i = 0; i<HomeFragment.home_dialog_all_drugsList.get(position).getHome_dialog_drug_unitList().size(); i++)
            {
                home_dialog_spinner_list.add(Userinfo.getDrug_unit_name_List().get(Userinfo.getDrug_unit_no_List().indexOf(HomeFragment.home_dialog_all_drugsList.get(position).getHome_dialog_drug_unitList().get(i).getSno())));
            }
            ArrayAdapter<String> home_dialog_drug_unitArrayAdapter = new ArrayAdapter<>(
                    context, R.layout.spinner_item, home_dialog_spinner_list);
            home_dialog_drug_unitArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.unitSp.setAdapter(home_dialog_drug_unitArrayAdapter);
            holder.unitSp.setSelection(home_dialog_all_drugsList.get(position).getUnit_pos());
            if(home_dialog_all_drugsList.get(position).getHome_dialog_drug_unitList().get(home_dialog_all_drugsList.get(position).getUnit_pos()).getCheck())
            {
                holder.checkBox.setChecked(true);
            }else{
                holder.checkBox.setChecked(false);
            }
            if(home_dialog_all_drugsList.get(position).getCompare_check())
            {
                holder.compare_tv.setText("Remove from Compare");
                holder.compare_iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_action_cancel));
            }else {
                holder.compare_tv.setText("Add to Compare");
                holder.compare_iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_add));
            }
            final View finalConvertView = convertView;
            holder.sp_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SpinnerDialog(finalConvertView,home_dialog_spinner_list,R.id.home_dialog_drug_all_drug_sp,position);
                }
            });
            holder.check_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int sno,unit_no,unit_pos;
                    final String name;
                    sno = HomeFragment.home_dialog_all_drugsList.get(position).getSno();
                    name = HomeFragment.home_dialog_all_drugsList.get(position).getName();
                    unit_pos = HomeFragment.home_dialog_all_drugsList.get(position).getUnit_pos();
                    unit_no = HomeFragment.home_dialog_all_drugsList.get(position).getHome_dialog_drug_unitList().get(unit_pos).getSno();

                    //Set Check value true and add that medicine to Selected List And Today Drug List
                    if(HomeFragment.home_dialog_all_drugsList.get(position).getHome_dialog_drug_unitList().get(unit_pos).getCheck())
                    {
                        HomeFragment.home_dialog_all_drugsList.get(position).getHome_dialog_drug_unitList().get(unit_pos).setCheck(false);
                        for(int i=0;i<HomeFragment.home_dialog_drug_select_unitList.size();i++)
                        {
                            if((HomeFragment.home_dialog_drug_select_unitList.get(i).getSno() == sno) && (HomeFragment.home_dialog_drug_select_unitList.get(i).getUnit() == unit_no))
                            {
                                HomeFragment.home_dialog_drug_select_unitList.remove(i);
                                break;
                            }
                        }
                    }else{
                        HomeFragment.home_dialog_all_drugsList.get(position).getHome_dialog_drug_unitList().get(unit_pos).setCheck(true);
                        Home_Dialog_Drug_Select_Unit home_dialog_drug_select_unit = new Home_Dialog_Drug_Select_Unit(sno,unit_no,name,1);
                        HomeFragment.home_dialog_drug_select_unitList.add(home_dialog_drug_select_unit);
                    }
                    HomeFragment.checkList();
                    HomeFragment.homeDialogAllDrugsAdapter.notifyDataSetChanged();
                }
            });

            holder.compare_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(home_dialog_all_drugsList.get(position).getCompare_check())
                    {
                        for(int i=0;i<HomeFragment.home_dialog_drug_compare_List.size();i++)
                        {
                            if(HomeFragment.home_dialog_drug_compare_List.get(i).getSno()== home_dialog_all_drugsList.get(position).getSno())
                            {
                                HomeFragment.home_dialog_drug_compare_List.remove(i);
                                break;
                            }
                        }
                        HomeFragment.home_dialog_all_drugsList.get(position).setCompare_check(false);
                    }else {
                        HomeFragment.home_dialog_drug_compare_List.add(home_dialog_all_drugsList.get(position));
                        HomeFragment.home_dialog_all_drugsList.get(position).setCompare_check(true);
                    }
                    HomeFragment.homeDialogAllDrugsAdapter.notifyDataSetChanged();
                    HomeFragment.homeDialogDrugCompareAdapter.notifyDataSetChanged();
                }
            });

        return convertView;
    }
    private static class ViewHolder{
        TextView name_tv,generic_name_tv,manufacturer_tv,diseases_tv,unit_tv,compare_tv;
        ImageView compare_iv;
        Button check_btn,sp_btn,compare_btn;
        CheckBox checkBox;
        Spinner unitSp;
    }

    public void SpinnerDialog(View view, final List<String> listall,final int id,final int pos)
    {
        Allsp = (Spinner) view.findViewById(id);
        final View dialogView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_spinner, null, false);
        final EditText edittext = (EditText) dialogView.findViewById(R.id.text);
        final ListView listView = (ListView) dialogView.findViewById(R.id.list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
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
                HomeFragment.home_dialog_all_drugsList.get(pos).setUnit_pos(position);
                HomeFragment.checkList();
                HomeFragment.homeDialogAllDrugsAdapter.notifyDataSetChanged();
            }
        });
    }

}
