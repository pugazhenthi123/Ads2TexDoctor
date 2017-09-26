package com.ads2tex.ads2texdoctor.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ads2tex.ads2texdoctor.Adapter.HomeDialogDrugAllUnitAdapter;
import com.ads2tex.ads2texdoctor.Adapter.HomeDialogDrugSelectUnitAdapter;
import com.ads2tex.ads2texdoctor.Adapter.HomePatientHistoryAdapter;
import com.ads2tex.ads2texdoctor.Adapter.HomeRecordsAdapter;
import com.ads2tex.ads2texdoctor.Adapter.HomeTodayDrugAdapter;
import com.ads2tex.ads2texdoctor.App.AppController;
import com.ads2tex.ads2texdoctor.Config.Config;
import com.ads2tex.ads2texdoctor.Pojo.Home_Dialog_Drug_All_Unit;
import com.ads2tex.ads2texdoctor.Pojo.Home_Dialog_Drug_Select_Unit;
import com.ads2tex.ads2texdoctor.Pojo.Home_Dialog_Drug_Unit;
import com.ads2tex.ads2texdoctor.Pojo.PatientRecords;
import com.ads2tex.ads2texdoctor.Pojo.Patient_History;
import com.ads2tex.ads2texdoctor.Pojo.Userinfo;
import com.ads2tex.ads2texdoctor.R;
import com.ads2tex.ads2texdoctor.volley.Request;
import com.ads2tex.ads2texdoctor.volley.Response;
import com.ads2tex.ads2texdoctor.volley.VolleyError;
import com.ads2tex.ads2texdoctor.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ads2tex.ads2texdoctor.MainActivity.mContext;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    static String TAG = "JobMyadsRecords Fragment";
    public static int Patientno=0,checkpatient=0;
    public static String tag_string_req = "get_PatientRecords",patient_next_check_date,search_text="";
    public static Spinner Allsp,Patientlistsp,Patient_status_sp;
    public static Button Patientlistbtn,Patient_status_btn,Patient_refresh_btn;
    public static RelativeLayout Patientlistlyt,home_main_lyt;
    public static CardView home_card_drug_search_lyt;
    public static ListView PatientListview,Patient_history_listview,home_dialog_drug_all_search_lv,home_dialog_drug_select_search_lv,home_patient_today_drug_lv;
    public static Button tryagain,btn_view,home_dialog_drug_ok_btn,home_dialog_drug_clear_btn,patient_details_btn_view,patient_today_drugs_btn_view;
    public static ImageView arrow,patient_details_arrow,patient_today_drugs_arrow;
    public static TextView next_checkup_date_tv,Today_drugs_add_btn;
    public static AutoCompleteTextView home_dialog_drug_search_edittext;
    public static View view2;
    public static Context homeContext;
    public static View homeView;
    public static int list=0,unit=1,Patient_status=1;
    public static List<String> Patientnamelist = new ArrayList<>();
    public static List<Patient_History> patient_historyList = new ArrayList<>();
    public static List<PatientRecords> patientRecordslist = new ArrayList<>();
    public static List<Home_Dialog_Drug_All_Unit> home_dialog_drug_all_unitList = new ArrayList<>();
    public static List<Home_Dialog_Drug_Select_Unit> home_dialog_drug_select_unitList = new ArrayList<>();
    public static HomeRecordsAdapter homeRecordsAdapter;
    public static HomePatientHistoryAdapter homePatientHistoryAdapter;
    public static HomeDialogDrugAllUnitAdapter homeDialogDrugAllUnitAdapter;
    public static HomeDialogDrugSelectUnitAdapter homeDialogDrugSelectUnitAdapter;
    public static HomeTodayDrugAdapter homeTodayDrugAdapter;
    public static DatePickerDialog datePickerDialog;

    public static ArrayAdapter<String> PatientnameAdapter;
    public static ArrayAdapter<String> Patient_status_Adapter;
    public static LinearLayout linlaHeaderProgress,linlaHeaderProgress2;
    public static ArrayList<String> suggestions = new ArrayList<>();

//Get Patient Records
    public static int scroll=0,startIndex = 0;
    public static int fetchCount = 10;
    public static Boolean flag_loading = true,pagescroll=false;

    //Get Search Records
    public static int scroll_search_drugs = 0,startIndex_search_drugs = 0;
    public static int fetchCount_search_drugs = 10;
    public static Boolean flag_loading_search_drugs = true,pagescroll_search_drugs=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeContext = getActivity();
        Userinfo.setHome_today_qty_list_size(50);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        scroll = 0;
        homeView = view;
        flag_loading = true;
        pagescroll=false;
        startIndex = 0;
        Patientlistsp = (Spinner) view.findViewById(R.id.patientlistsp);
        Patient_status_sp = (Spinner) view.findViewById(R.id.home_patient_status_sp);
        Patientlistlyt = (RelativeLayout) view.findViewById(R.id.patientlistlyt);

        tryagain =(Button)view.findViewById(R.id.tryagain);
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkpatient==0) {
                    getRecords();
                }else{
                    getSearchRecords();
                }
            }
        });

        PatientListview = (ListView) view.findViewById(R.id.recordslv);
        PatientListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                final int lastItem = i1 + i2;
                if (lastItem == i) {
                    if (!flag_loading) {
                        flag_loading = true;
                        pagescroll = true;
                        startIndex = startIndex + fetchCount;
                        getRecords();
                    }
                }
            }
        });
        homeRecordsAdapter = new HomeRecordsAdapter(mContext, patientRecordslist);
        PatientListview.setAdapter(homeRecordsAdapter);
        homePatientHistoryAdapter = new HomePatientHistoryAdapter(mContext, patient_historyList);
        Patient_history_listview = (ListView) homeView.findViewById(R.id.home_patient_history_lv);
        Patient_history_listview.setAdapter(homePatientHistoryAdapter);
        for(int i=0;i<patient_historyList.size();i++)
        {
            Patient_History patient_history = new Patient_History(patient_historyList.get(i).getDate(),patient_historyList.get(i).getTempurature(),patient_historyList.get(i).getSugar(),patient_historyList.get(i).getPressure());
            patient_historyList.add(patient_history);
        }
        homePatientHistoryAdapter.notifyDataSetChanged();
        Patientnamelist.clear();
        for (int i = 0; i < Userinfo.getPatient_name_List().size(); i++) {
            Patientnamelist.add(Userinfo.getPatient_name_List().get(i));
        }
        PatientnameAdapter = new ArrayAdapter<>(
                getActivity(), R.layout.spinner_item, Patientnamelist);
        PatientnameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Patientlistsp.setAdapter(PatientnameAdapter);

        patientRecordslist.clear();
        homeRecordsAdapter.notifyDataSetChanged();
        Patientlistbtn = (Button)view.findViewById(R.id.patientlistbtn);
        Patientlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerDialog(Patientnamelist,R.id.patientlistsp,0);
            }
        });
        Patient_refresh_btn = (Button) view.findViewById(R.id.home_token_refresh_btn);
        Patient_refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list=0;
                patientRecordslist.clear();
                if(AppController.getInstance().getRequestQueue() != null)
                {
                    AppController.getInstance().cancelPendingRequests(tag_string_req);
                }
                startIndex = 0;
                pagescroll = false;
                scroll=0;
                getRecords();
            }
        });
        //Patient Spinner
        Patient_status_Adapter = new ArrayAdapter<>(
                getActivity(), R.layout.spinner_item, Userinfo.getPatient_status_List());
        Patient_status_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Patient_status_sp.setAdapter(Patient_status_Adapter);

        Patient_status_btn = (Button)view.findViewById(R.id.home_status_list_btn);
        Patient_status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerDialog(Userinfo.getPatient_status_List(),R.id.home_patient_status_sp,1);
            }
        });

        patientRecordslist.clear();
        homeRecordsAdapter.notifyDataSetChanged();

        btn_view = (Button) view.findViewById(R.id.home_adap_btn_view);
        arrow = (ImageView) view.findViewById(R.id.home_adap_arrow);
        next_checkup_date_tv = (TextView) view.findViewById(R.id.home_adap_next_checkup_date_tv);

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Patient_history_listview.getVisibility()==View.VISIBLE)
                {
                    Patient_history_listview.setVisibility(View.GONE);
                    arrow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.down));
                }else{
                    Patient_history_listview.setVisibility(View.VISIBLE);
                    arrow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.up));
                }

            }
        });


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        next_checkup_date_tv.setText(dayOfMonth + "/"
                                + (monthOfYear+1) + "/" + year);
                        patient_next_check_date = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
                    }
                }, mYear, mMonth, mDay);
        next_checkup_date_tv.setText(mDay + "/"+ (mMonth+1) + "/" + mYear);
        patient_next_check_date = mYear + "-" + (mMonth+1) + "-" + mDay;
        next_checkup_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        //Today Drugs
        home_dialog_drug_ok_btn = (Button) view.findViewById(R.id.home_dialog_drug_ok_btn);
        home_dialog_drug_clear_btn = (Button) view.findViewById(R.id.home_dialog_drug_clear_btn); 
        home_dialog_drug_search_edittext = (AutoCompleteTextView) view.findViewById(R.id.home_dialog_search_tv);
        home_dialog_drug_select_search_lv = (ListView) view.findViewById(R.id.home_dialog_drug_select_unit_lv);
        home_dialog_drug_all_search_lv = (ListView) view.findViewById(R.id.home_dialog_drug_all_unit_lv);
        home_patient_today_drug_lv = (ListView) view.findViewById(R.id.home_patient_today_drug_lv);
        homeTodayDrugAdapter = new HomeTodayDrugAdapter(mContext,home_dialog_drug_select_unitList);
        home_patient_today_drug_lv.setAdapter(homeTodayDrugAdapter);
        homeDialogDrugSelectUnitAdapter = new HomeDialogDrugSelectUnitAdapter(mContext, home_dialog_drug_select_unitList);
        home_dialog_drug_select_search_lv.setAdapter(homeDialogDrugSelectUnitAdapter);
        homeDialogDrugAllUnitAdapter = new HomeDialogDrugAllUnitAdapter(mContext, home_dialog_drug_all_unitList);
        home_dialog_drug_all_search_lv.setAdapter(homeDialogDrugAllUnitAdapter);

        patient_details_btn_view = (Button) view.findViewById(R.id.home_patient_details_btn_view);
        patient_today_drugs_btn_view = (Button) view.findViewById(R.id.home_patient_today_drug_btn_view);
        patient_details_arrow = (ImageView) view.findViewById(R.id.home_patient_details_arrow);
        patient_today_drugs_arrow = (ImageView) view.findViewById(R.id.home_patient_today_drug_arrow);

        int n=Userinfo.getDrug_name_List().size();
        for (int i=0;i<n;i++)
        {
            suggestions.add(Userinfo.getDrug_name_List().get(i));
        }
        for (int i=0;i<Userinfo.getDisease_name_List().size();i++)
        {
            suggestions.add(Userinfo.getDisease_name_List().get(i));
        }
        for (int i=0;i<Userinfo.getDrug_manufacturer_name_List().size();i++)
        {
            suggestions.add(Userinfo.getDrug_manufacturer_name_List().get(i));
        }
        for (int i=0;i<Userinfo.getDrug_generic_name_List().size();i++)
        {
            suggestions.add(Userinfo.getDrug_generic_name_List().get(i));
        }

        for (int i=0;i<Userinfo.getDrug_unit_name_List().size();i++)
        {
            suggestions.add(Userinfo.getDrug_unit_name_List().get(i));
        }

        ArrayAdapter<String> adapter = new
                ArrayAdapter<>(homeContext,android.R.layout.select_dialog_item,suggestions);

        home_dialog_drug_search_edittext.setAdapter(adapter);
        home_dialog_drug_search_edittext.setThreshold(1);


        patient_details_btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PatientListview.getVisibility()==View.VISIBLE)
                {
                    PatientListview.setVisibility(View.GONE);
                    patient_details_arrow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.down));
                }else{
                    PatientListview.setVisibility(View.VISIBLE);
                    patient_details_arrow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.up));
                }
            }
        });

        patient_today_drugs_btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(home_patient_today_drug_lv.getVisibility()==View.VISIBLE)
                {
                    home_patient_today_drug_lv.setVisibility(View.GONE);
                    patient_today_drugs_arrow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.down));
                }else{
                    home_patient_today_drug_lv.setVisibility(View.VISIBLE);
                    patient_today_drugs_arrow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.up));
                }
            }
        });


        home_dialog_drug_search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search_text= home_dialog_drug_search_edittext.getText().toString();
                if (AppController.getInstance().getRequestQueue() != null) {
                    AppController.getInstance().cancelPendingRequests(tag_string_req);
                }
                startIndex_search_drugs = 0;
                pagescroll_search_drugs = false;
                scroll_search_drugs = 0;
                home_dialog_drug_all_unitList.clear();
                getSearchRecords();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        home_dialog_drug_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkpatient = 0;
                setLayoutVisibility(0,0,0,1);
            }
        });
        home_dialog_drug_clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_text="";
                home_dialog_drug_search_edittext.setText("");
                home_dialog_drug_select_unitList.clear();
                home_dialog_drug_all_unitList.clear();
                checkList();
                getSearchRecords();
            }
        });
        Today_drugs_add_btn = (TextView) view.findViewById(R.id.today_drugs_add_tv);
        Today_drugs_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkpatient = 1;
                startIndex_search_drugs = 0;
                pagescroll_search_drugs = false;
                scroll_search_drugs = 0;
                getSearchRecords();
            }
        });

        if(AppController.getInstance().getRequestQueue() != null)
        {
            AppController.getInstance().cancelPendingRequests(tag_string_req);
        }
        startIndex = 0;
        pagescroll = false;
        scroll=0;
        getRecords();
        return view;
    }

    public static void checkList()
    {
        home_dialog_drug_select_search_lv = (ListView) homeView.findViewById(R.id.home_dialog_drug_select_unit_lv);
        if(home_dialog_drug_select_unitList.size()==0)
        {
            setLayoutParams(50);
            home_dialog_drug_select_search_lv.setVisibility(View.GONE);
        }else{
            home_dialog_drug_select_search_lv.setVisibility(View.VISIBLE);
            setLayoutParams(150);
            homeDialogDrugSelectUnitAdapter.notifyDataSetChanged();
            homeTodayDrugAdapter.notifyDataSetChanged();
        }
    }

    public static void setLayoutParams(int margin)
    {
        LinearLayout home_dialog_drug_all_unit_lyt = (LinearLayout) homeView.findViewById(R.id.home_dialog_drug_all_unit_lyt);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ListView.LayoutParams.WRAP_CONTENT,
                ListView.LayoutParams.WRAP_CONTENT
        );
        params.bottomMargin=margin;
        home_dialog_drug_all_unit_lyt.setLayoutParams(params);
    }

    public static void setLayoutVisibility(int i,int j,int k,int l)
    {
        if(i==0)
        {
            linlaHeaderProgress = (LinearLayout) homeView.findViewById(R.id.no_connection_layout);
            linlaHeaderProgress.setVisibility(View.GONE);
        }else{
            linlaHeaderProgress = (LinearLayout) homeView.findViewById(R.id.no_connection_layout);
            linlaHeaderProgress.setVisibility(View.VISIBLE);
        }
        if(j==0)
        {
            linlaHeaderProgress2 = (LinearLayout) homeView.findViewById(R.id.home_progress);
            linlaHeaderProgress2.setVisibility(View.GONE);
        }else{
            linlaHeaderProgress2 = (LinearLayout) homeView.findViewById(R.id.home_progress);
            linlaHeaderProgress2.setVisibility(View.VISIBLE);
        }
        if(k==0)
        {
            home_card_drug_search_lyt = (CardView) homeView.findViewById(R.id.home_dialog_drug_card_lyt);
            home_card_drug_search_lyt.setVisibility(View.GONE);
        }else{
            home_card_drug_search_lyt = (CardView) homeView.findViewById(R.id.home_dialog_drug_card_lyt);
            home_card_drug_search_lyt.setVisibility(View.VISIBLE);
        }
        if(l==0)
        {
            home_main_lyt = (RelativeLayout) homeView.findViewById(R.id.home_main_lyt);
            home_main_lyt.setVisibility(View.GONE);
        }else{
            home_main_lyt = (RelativeLayout) homeView.findViewById(R.id.home_main_lyt);
            home_main_lyt.setVisibility(View.VISIBLE);
        }

    }

    public void SpinnerDialog(final List<String> listall, int id, final int n)
    {
        Allsp = (Spinner)homeView.findViewById(id);
        final View dialogView = ((LayoutInflater) homeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_spinner, null, false);
        final EditText edittext = (EditText) dialogView.findViewById(R.id.text);
        final ListView listView = (ListView) dialogView.findViewById(R.id.list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item, listall);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(homeContext);
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
                getCurrentfoc();
                if (view2 != null) {
                    InputMethodManager imm = (InputMethodManager) homeContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                }
                Allsp.setSelection(listall.indexOf(adapter.getItem(position)));
                alert11.dismiss();
                if(n == 0)
                {
                    list=1;
                    selectpatient(position);
                }
                else{
                    Patient_status=position;
                }
            }
        });
    }
    
    public void getCurrentfoc()
    {
        view2 = getActivity().getCurrentFocus();
    }

    public static void selectpatient(int pos) {
        Patientno = Userinfo.getPatient_no_List().get(pos);
        PatientnameAdapter.notifyDataSetChanged();
        patientRecordslist.clear();
        homeRecordsAdapter.notifyDataSetChanged();
        if(AppController.getInstance().getRequestQueue() != null)
        {
            AppController.getInstance().cancelPendingRequests(tag_string_req);
        }
        startIndex = 0;
        pagescroll = false;
        scroll=0;
        getRecords();
    }

    public static void getRecords() {
        Patientlistsp = (Spinner) homeView.findViewById(R.id.patientlistsp);
        setLayoutVisibility(0,1,0,0);
        // Tag used to cancel the request

        final String uri = Config.getServer_address()
                + "/homeinfo_json.php/";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                uri, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "PatientRecords Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    flag_loading = true;
                    if(!jObj.getString("status").equals("notfound")) {
                        int ct = 0;
                        if (!pagescroll) {
                            ct = Integer.valueOf(jObj.getString("count"));
                            if (ct > fetchCount) {
                                flag_loading = false;
                            }
                        } else {
                            pagescroll = false;
                            ct = Integer.valueOf(jObj.getString("count"));
                            ct = ct - startIndex;
                            if (ct > fetchCount) {
                                flag_loading = false;
                            }
                        }
                        JSONArray arr = jObj.getJSONArray("result");

                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject jobj2 = arr.getJSONObject(i);
                            //Add Pending Bill List
                            JSONArray arr2 = jObj.getJSONArray(String.valueOf(jobj2.getInt("sno")));
                            patient_historyList.clear();
                            int weight=0,temperature = 0,sugar=0,pressure=0;
                            String disease="";
                            //Add Patient History
                            for (int j = 0; j < arr2.length(); j++) {
                                JSONObject jobj3 = arr2.getJSONObject(j);
                                Patient_History patient_history = new Patient_History(jobj3.getString("date"),
                                        jobj3.getInt("temperature"),jobj3.getInt("sugar"),jobj3.getInt("pressure"));
                                patient_historyList.add(patient_history);
                                weight = jobj3.getInt("weight");
                                temperature = jobj3.getInt("temperature");
                                sugar = jobj3.getInt("sugar");
                                pressure = jobj3.getInt("pressure");
                                disease = jobj3.getString("disease_no");
                                String[] diseasesArray = disease.split(",");
                                for(int k=0;k<diseasesArray.length;k++)
                                {
                                   if(k==0)
                                   {
                                       disease = Userinfo.getDisease_name_List().get(Userinfo.getDisease_no_List().indexOf(Integer.valueOf(diseasesArray[0])));
                                   }else {
                                       disease = disease+","+Userinfo.getDisease_name_List().get(Userinfo.getDisease_no_List().indexOf(Integer.valueOf(diseasesArray[k])));
                                   }
                                }
                            }

                            //Add Patient Records
                            PatientRecords patientRecords = new PatientRecords(jobj2.getInt("sno"), jobj2.getString("name"),
                                    jobj2.getInt("age"),weight,arr2.length(),temperature,pressure,sugar,disease,jobj2.getString("date"),
                                    jobj2.getString("next_check_date"), Userinfo.getPatient_status_List().get(Userinfo.getPatient_status_no_List().indexOf(jobj2.getInt("status"))),
                                    Userinfo.getPatient_status_color_List().get(Userinfo.getPatient_status_no_List().indexOf(jobj2.getInt("status"))),
                                    jobj2.getString("description"),false,patient_historyList);
                            patientRecordslist.add(patientRecords);
                        }
                    }
                    Patientlistsp.setSelection(Patientnamelist.indexOf(patientRecordslist.get(0).getName()));
                    Patientno = patientRecordslist.get(0).getSno();
                    homePatientHistoryAdapter.notifyDataSetChanged();
                    homeRecordsAdapter.notifyDataSetChanged();
                    setLayoutVisibility(0,0,0,1);


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    AppController.getInstance().cancelPendingRequests(tag_string_req);
                    setLayoutVisibility(1,0,0,0);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "PatientRecords Error: " + error.getMessage());
                AppController.getInstance().cancelPendingRequests(tag_string_req);
                setLayoutVisibility(1,0,0,0);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("tag", "patient_records");
                params.put("patientno", String.valueOf(Patientno));
                params.put("doctorno", String.valueOf(Userinfo.getUserid()));
                params.put("list", String.valueOf(list));
                params.put("offset", String.valueOf(startIndex));
                params.put("count", String.valueOf(fetchCount));
                params.put("hospital", String.valueOf(Userinfo.getHospital()));
                Log.i("PatientNo", String.valueOf(Patientno));
                Log.i("hospitalno", String.valueOf(Userinfo.getHospital()));
                Log.i("offset", String.valueOf(startIndex));
                Log.i("count", String.valueOf(fetchCount));
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void getSearchRecords() {
        setLayoutVisibility(0,1,0,0);
        // Tag used to cancel the request

        final String uri = Config.getServer_address()
                + "/homeinfo_json.php/";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                uri, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "Search Drug Response:  " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    flag_loading_search_drugs = true;
                    if(!jObj.getString("status").equals("notfound")) {
                        int ct = 0;
                        if (!pagescroll_search_drugs) {
                            ct = Integer.valueOf(jObj.getString("count"));
                            if (ct > fetchCount_search_drugs) {
                                flag_loading_search_drugs = false;
                            }
                        } else {
                            pagescroll_search_drugs = false;
                            ct = Integer.valueOf(jObj.getString("count"));
                            ct = ct - startIndex_search_drugs;
                            if (ct > fetchCount_search_drugs) {
                                flag_loading_search_drugs = false;
                            }
                        }
                        JSONArray arr = jObj.getJSONArray("search");
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject jobj2 = arr.getJSONObject(i);
                            //Add Dialog Drug List
                            JSONArray arr2 = jObj.getJSONArray(String.valueOf(jobj2.getInt("sno")));
                            List<Home_Dialog_Drug_Unit> home_dialog_drug_unitList = new ArrayList<>();

                            //Add Search Drug Unit
                            for (int j = 0; j < arr2.length(); j++) {
                                JSONObject jobj3 = arr2.getJSONObject(j);
                                Home_Dialog_Drug_Unit home_dialog_drug_unit = new Home_Dialog_Drug_Unit(jobj3.getInt("unit_no"),false);
                                home_dialog_drug_unitList.add(home_dialog_drug_unit);
                            }
                            for(int l=0;l<home_dialog_drug_select_unitList.size();l++)
                            {
                                if(home_dialog_drug_select_unitList.get(l).getSno()==jobj2.getInt("sno"))
                                {
                                    for(int k=0;k<home_dialog_drug_unitList.size();k++)
                                    {
                                        if(home_dialog_drug_unitList.get(k).getSno() == home_dialog_drug_select_unitList.get(l).getUnit())
                                        {
                                            home_dialog_drug_unitList.get(k).setCheck(true);
                                        }
                                    }
                                }
                            }


                            //Add Search Drug Records
                            Home_Dialog_Drug_All_Unit home_dialog_drug_all_unit = new Home_Dialog_Drug_All_Unit(jobj2.getInt("sno"), jobj2.getString("name"),
                                    jobj2.getString("generic_search"),jobj2.getString("manufacturer_search"),jobj2.getString("unit_search"),jobj2.getString("diseases_search"),home_dialog_drug_unitList,false,0);
                            home_dialog_drug_all_unitList.add(home_dialog_drug_all_unit);

                        }
                    }else{

                    }
                    homeDialogDrugAllUnitAdapter.notifyDataSetChanged();
                    setLayoutVisibility(0,0,1,0);


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    AppController.getInstance().cancelPendingRequests(tag_string_req);
                    setLayoutVisibility(1,0,0,0);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Drug Records Error: " + error.getMessage());
                AppController.getInstance().cancelPendingRequests(tag_string_req);
                setLayoutVisibility(1,0,0,0);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("tag", "drug_search");
                params.put("search", search_text);
                params.put("offset", String.valueOf(startIndex_search_drugs));
                params.put("count", String.valueOf(fetchCount_search_drugs));
                Log.i("SearchText", search_text);
                Log.i("offset", String.valueOf(startIndex_search_drugs));
                Log.i("count", String.valueOf(fetchCount_search_drugs));
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
