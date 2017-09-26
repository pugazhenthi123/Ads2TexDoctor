package com.ads2tex.ads2texdoctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ads2tex.ads2texdoctor.Fragments.HomeFragment;
import com.ads2tex.ads2texdoctor.NavigationDrawer.NavigationDrawerCallbacks;
import com.ads2tex.ads2texdoctor.Pojo.Home_Dialog_Drug_All_Unit;
import com.ads2tex.ads2texdoctor.Pojo.Home_Dialog_Drug_Select_Unit;
import com.ads2tex.ads2texdoctor.Pojo.Userinfo;
import com.ads2tex.ads2texdoctor.R;

import java.util.List;

public class HomeDialogDrugSelectUnitAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<Home_Dialog_Drug_Select_Unit> home_dialog_drug_select_unitList;

    public HomeDialogDrugSelectUnitAdapter(Context context, List<Home_Dialog_Drug_Select_Unit> home_dialog_drug_select_unitList) {
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
        HomeDialogDrugSelectUnitAdapter.ViewHolder holder = null;

        if ( convertView == null )
        {
        /* There is no view at this position, we create a new one.
           In this case by inflating an xml layout */
            convertView = inflater.inflate(R.layout.home_dialog_select_drug, null);
            holder = new ViewHolder();
            holder.name_tv = (TextView) convertView.findViewById(R.id.home_dialog_drug_select_name_tv);
            holder.remove_btn = (Button) convertView.findViewById(R.id.home_dialog_drug_select_remove_btn);
            convertView.setTag (holder);
        }
        else
        {
        /* We recycle a View that already exists */
            holder = (HomeDialogDrugSelectUnitAdapter.ViewHolder) convertView.getTag();

        }
            holder.name_tv.setText(home_dialog_drug_select_unitList.get(position).getName()+"("+ Userinfo.getDrug_unit_name_List().get(Userinfo.getDrug_unit_no_List().indexOf(home_dialog_drug_select_unitList.get(position).getUnit()))+")");
            holder.remove_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0;i<HomeFragment.home_dialog_drug_all_unitList.size();i++)
                    {
                        if(HomeFragment.home_dialog_drug_all_unitList.get(i).getSno()==HomeFragment.home_dialog_drug_select_unitList.get(position).getSno())
                        {
                            for(int j=0;j<HomeFragment.home_dialog_drug_all_unitList.get(i).getHome_dialog_drug_unitList().size();j++)
                            {
                                if(HomeFragment.home_dialog_drug_all_unitList.get(i).getHome_dialog_drug_unitList().get(j).getSno()==HomeFragment.home_dialog_drug_select_unitList.get(position).getUnit())
                                {
                                    HomeFragment.home_dialog_drug_all_unitList.get(i).getHome_dialog_drug_unitList().get(j).setCheck(false);
                                }
                            }
                        }
                    }
                    HomeFragment.home_dialog_drug_select_unitList.remove(position);
                    HomeFragment.checkList();
                    HomeFragment.homeDialogDrugAllUnitAdapter.notifyDataSetChanged();
                }
            });
        return convertView;
    }
    private static class ViewHolder{
        TextView name_tv;
        Button remove_btn;
    }

}
