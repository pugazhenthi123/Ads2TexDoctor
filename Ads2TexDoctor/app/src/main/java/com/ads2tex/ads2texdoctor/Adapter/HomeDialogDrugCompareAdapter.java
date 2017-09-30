package com.ads2tex.ads2texdoctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ads2tex.ads2texdoctor.Fragments.HomeFragment;
import com.ads2tex.ads2texdoctor.Pojo.Home_Dialog_All_Drugs;
import com.ads2tex.ads2texdoctor.Pojo.Userinfo;
import com.ads2tex.ads2texdoctor.R;

import java.util.ArrayList;
import java.util.List;

public class HomeDialogDrugCompareAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private Context context;
    private List<Home_Dialog_All_Drugs> home_dialog_drug_compare_List;


    public HomeDialogDrugCompareAdapter(Context context, List<Home_Dialog_All_Drugs> home_dialog_drug_compare_List) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.home_dialog_drug_compare_List = home_dialog_drug_compare_List;
    }

    @Override
    public int getCount() {
        return home_dialog_drug_compare_List.size();
    }

    @Override
    public Object getItem(int position) {
        return home_dialog_drug_compare_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HomeDialogDrugCompareAdapter.ViewHolder holder = null;

        if ( convertView == null )
        {
        /* There is no view at this position, we create a new one.
           In this case by inflating an xml layout */
            convertView = inflater.inflate(R.layout.home_dialog_compare_drug, null);
            holder = new ViewHolder();
            holder.name_tv = (TextView) convertView.findViewById(R.id.home_dialog_drug_compare_name_tv);
            holder.generic_name_tv = (TextView) convertView.findViewById(R.id.home_dialog_drug_compare_generic_txt);
            holder.manufacturer_tv = (TextView) convertView.findViewById(R.id.home_dialog_drug_compare_manufacturer_txt);
            holder.diseases_tv = (TextView) convertView.findViewById(R.id.home_dialog_drug_compare_diseases_txt);
            holder.unit_tv = (TextView) convertView.findViewById(R.id.home_dialog_drug_compare_units_txt);
            holder.compare_btn = (Button) convertView.findViewById(R.id.home_dialog_drug_all_compare_btn);
            holder.compare_iv = (ImageView) convertView.findViewById(R.id.home_dialog_drug_all_add_compare_arrow);
            holder.compare_tv = (TextView) convertView.findViewById(R.id.home_dialog_drug_all_add_compare_tv);
            convertView.setTag (holder);
        }
        else
        {
        /* We recycle a View that already exists */
            holder = (HomeDialogDrugCompareAdapter.ViewHolder) convertView.getTag();

        }

        holder.name_tv.setText(home_dialog_drug_compare_List.get(position).getName());
        holder.generic_name_tv.setText(home_dialog_drug_compare_List.get(position).getGeneric());
        holder.manufacturer_tv.setText(home_dialog_drug_compare_List.get(position).getManufacturer());
        holder.diseases_tv.setText(home_dialog_drug_compare_List.get(position).getDiseases());
        holder.unit_tv.setText(home_dialog_drug_compare_List.get(position).getUnits());

        final ArrayList<String> home_dialog_spinner_list = new ArrayList<>();
        for (int i = 0; i<HomeFragment.home_dialog_all_drugsList.get(position).getHome_dialog_drug_unitList().size(); i++)
        {
            home_dialog_spinner_list.add(Userinfo.getDrug_unit_name_List().get(Userinfo.getDrug_unit_no_List().indexOf(HomeFragment.home_dialog_all_drugsList.get(position).getHome_dialog_drug_unitList().get(i).getSno())));
        }
        ArrayAdapter<String> home_dialog_drug_unitArrayAdapter = new ArrayAdapter<>(
                context, R.layout.spinner_item, home_dialog_spinner_list);
        home_dialog_drug_unitArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        holder.compare_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i<HomeFragment.home_dialog_all_drugsList.size(); i++)
                {
                    if(HomeFragment.home_dialog_all_drugsList.get(i).getSno()==home_dialog_drug_compare_List.get(position).getSno())
                    {
                        HomeFragment.home_dialog_all_drugsList.get(i).setCompare_check(false);
                        break;
                    }
                }
                HomeFragment.home_dialog_drug_compare_List.remove(position);
                HomeFragment.homeDialogAllDrugsAdapter.notifyDataSetChanged();
                HomeFragment.homeDialogDrugCompareAdapter.notifyDataSetChanged();
            }
        });

        return convertView;
    }
    private static class ViewHolder{
        TextView name_tv,generic_name_tv,manufacturer_tv,diseases_tv,unit_tv,compare_tv;
        ImageView compare_iv;
        Button compare_btn;
    }

}
