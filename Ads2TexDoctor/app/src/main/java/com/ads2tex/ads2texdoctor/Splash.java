package com.ads2tex.ads2texdoctor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ads2tex.ads2texdoctor.App.AppController;
import com.ads2tex.ads2texdoctor.Config.Config;
import com.ads2tex.ads2texdoctor.Pojo.Userinfo;
import com.ads2tex.ads2texdoctor.Utils.LoadImageTask;
import com.ads2tex.ads2texdoctor.volley.Request;
import com.ads2tex.ads2texdoctor.volley.Response;
import com.ads2tex.ads2texdoctor.volley.VolleyError;
import com.ads2tex.ads2texdoctor.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;


public class Splash extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, LoadImageTask.Listener {
    public static Button login;
    public TextView ne, promo;
    public static GifImageView loader;
    public static String tag_string_req = "get_field",url="";
    public static SliderLayout mDemoSlider;
    public static HashMap<String, String> url_maps = new HashMap<>();
    public static List<String> linklist = new ArrayList<>();
    public static List<String> namelist = new ArrayList<>();
    public static RelativeLayout sliderlyt;
    public static Context scontext;
    public static LinearLayout browser;
    public static TextView b_tv;
    public static Button b_btn;
    public static ProgressBar progressBar;
    public static ImageView hospital_logo;
    public static boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        scontext = this;
        login = (Button) findViewById(R.id.go);
        ne = (TextView) findViewById(R.id.ne);
        ne.setVisibility(View.GONE);
        browser  = (LinearLayout)findViewById(R.id.browser);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setMax(100);
        b_btn = (Button)findViewById(R.id.b_btn);
        hospital_logo = (ImageView) findViewById(R.id.hospital_logo);
        new LoadImageTask(this).execute(Config.getLogoAddress()+Userinfo.getLogo_link());

//        Picasso.with(this)
//                .load(uri)
//                .into(new Target() {
//                    @Override
//                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//               /* Save the bitmap or do something with it here */
//
//                        // Set it in the ImageView
//                        hospital_logo.setImageBitmap(bitmap);
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Drawable errorDrawable) {
//
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                    }
//                });
        b_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ne.getVisibility() == View.VISIBLE) {
                    ne.setVisibility(View.GONE);
                    loader = (GifImageView)findViewById(R.id.loader);
                    loader.setVisibility(View.VISIBLE);
                    login.setText("");
                    login.setEnabled(false);
                    if (!url_maps.isEmpty()) {
                        for (String name : url_maps.keySet()) {
                            TextSliderView textSliderView = new TextSliderView(scontext);
                            // initialize a SliderLayout
                            textSliderView
                                    .description(name)
                                    .image(url_maps.get(name))
                                    .setScaleType(BaseSliderView.ScaleType.Fit)
                                    .setOnSliderClickListener(Splash.this);

                            //add your extra information
                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle()
                                    .putString("extra", name);

                            mDemoSlider.addSlider(textSliderView);
                        }
                        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                        mDemoSlider.setDuration(100);
                        mDemoSlider.addOnPageChangeListener(Splash.this);
                    }
                    geturls();
                } else {
                    login.setText("");
                    login.setEnabled(false);
                    onStop();
                    Intent intent = new Intent(Splash.this,
                                MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
        geturls();
    }


    public void geturls() {
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        sliderlyt = (RelativeLayout) findViewById(R.id.sliderlyt);
        promo = (TextView) findViewById(R.id.promo);
        b_tv = (TextView) findViewById(R.id.browser_tv);
        url_maps.clear();
        // Tag used to cancel the request

        final String uri = Config.getServer_address()
                + "/slider_json.php";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                uri, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("Slider", "JobMyadsRecords Response: " + response.toString());
                try {
                    linklist.clear();
                    namelist.clear();
                    JSONObject jObj = new JSONObject(response);
                    if (jObj.getString("status").equals("found")) {
                        promo.setText(jObj.getString("title"));
                        b_tv.setText(jObj.getString("header"));
                        JSONArray arr = jObj.getJSONArray("result");
                        for (int i = 1; i < arr.length() + 1; i++) {
                            JSONObject jobj2 = arr.getJSONObject(i - 1);
                            if (jobj2.getInt("status") == 1) {
                                String[] sp = jobj2.getString("img").split("\\.");
                                url_maps.put(sp[0], Config.getBannerAddress()  + jobj2.getString("img").replace(" ", "%20"));
                                if(jobj2.getString("website").equals(""))
                                {
                                    linklist.add("empty");
                                }
                                else {
                                    linklist.add(jobj2.getString("website"));
                                }
                                namelist.add(sp[0]);
                                Log.d("imgname", sp[0]);
                                Log.d("Imageurl", Config.getBannerAddress()  + jobj2.getString("img").replace(" ", "%20"));

                            } else {

                            }
                        }
                        if (!url_maps.isEmpty()) {
                            for (String name : url_maps.keySet()) {
                                TextSliderView textSliderView = new TextSliderView(scontext);
                                // initialize a SliderLayout
                                textSliderView
                                        .description(name)
                                        .image(url_maps.get(name))
                                        .setScaleType(BaseSliderView.ScaleType.Fit);

                                //add your extra information
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle()
                                        .putString("extra", name);

                                mDemoSlider.addSlider(textSliderView);
                            }
                            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                            mDemoSlider.setDuration(100);
                            mDemoSlider.addOnPageChangeListener(Splash.this);
                        }
                    } else {
                        sliderlyt.setVisibility(View.GONE);
                    }
                    getAllDatas();
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(scontext);
                    builder1.setTitle("Internet Connection");
                    builder1.setIcon(scontext.getResources().getDrawable(R.drawable.fail));
                    builder1.setMessage("Please Check Your Internet Connection");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    geturls();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Slider", "JobMyadsRecords Error: " + error.getMessage());
                AlertDialog.Builder builder1 = new AlertDialog.Builder(scontext);
                builder1.setTitle("Internet Connection");
                builder1.setIcon(scontext.getResources().getDrawable(R.drawable.fail));
                builder1.setMessage("Please Check Your Internet Connection");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                geturls();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("tag", "slider");
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public void getAllDatas() {
        Userinfo.ClearAllList();
        final String uri = Config.getServer_address()
                + "/homeinfo_json.php";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                uri, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Splash", "Splash Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    Userinfo.setApplink(jObj.getString("link"));
                    Userinfo.setVersion(jObj.getString("VersionName"));
                    Userinfo.setAbout(jObj.getString("About"));
                    Userinfo.setShareapp(jObj.getString("Share"));
                    Userinfo.setCboard(jObj.getString("cboard"));
                    if (!jObj.getString("VersionName").equals(BuildConfig.VERSION_NAME)) {
                        Userinfo.setUpdate(true);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(scontext);
                        builder1.setTitle("Update");
                        builder1.setIcon(scontext.getResources().getDrawable(R.drawable.ic_menu_check));
                        builder1.setMessage(jObj.getString("Update"));
                        builder1.setCancelable(false);
                        builder1.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
//                                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Userinfo.getApplink())));
                                        } catch (android.content.ActivityNotFoundException anfe) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Userinfo.getApplink())));
                                        }
                                        dialog.cancel();
                                    }
                                });
                        builder1.setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }

                    JSONArray arr = jObj.getJSONArray("result_patient");
                    List<String> result_patient = new ArrayList<>();
                    List<Integer> result_patient_no = new ArrayList<>();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jobj1 = arr.getJSONObject(i);
                        result_patient_no.add(jobj1.getInt("sno"));
                        result_patient.add(jobj1.getString("name"));
                    }
                    Userinfo.setPatient_name_List(result_patient);
                    Userinfo.setPatient_no_List(result_patient_no);

                    JSONArray arr2 = jObj.getJSONArray("result_drug_unit");
                    List<String> result_drug_unit = new ArrayList<>();
                    List<Integer> result_drug_unit_no = new ArrayList<>();
                    for (int i = 0; i < arr2.length(); i++) {
                        JSONObject jobj2 = arr2.getJSONObject(i);
                        result_drug_unit_no.add(jobj2.getInt("sno"));
                        result_drug_unit.add(jobj2.getString("name"));
                    }
                    Userinfo.setDrug_unit_name_List(result_drug_unit);
                    Userinfo.setDrug_unit_no_List(result_drug_unit_no);

                    JSONArray arr3= jObj.getJSONArray("result_patient_status");
                    List<String> result_patient_status = new ArrayList<>();
                    List<Integer> result_patient_status_no = new ArrayList<>();
                    List<String> result_patient_status_color = new ArrayList<>();
                    for (int i = 0; i < arr3.length(); i++) {
                        JSONObject jobj3 = arr3.getJSONObject(i);
                        result_patient_status_no.add(jobj3.getInt("sno"));
                        result_patient_status.add(jobj3.getString("name"));
                        result_patient_status_color.add(jobj3.getString("color"));
                    }
                    Userinfo.setPatient_status_List(result_patient_status);
                    Userinfo.setPatient_status_no_List(result_patient_status_no);
                    Userinfo.setPatient_status_color_List(result_patient_status_color);

                    JSONArray arr4 = jObj.getJSONArray("result_drug_type");
                    List<String> result_drug_type = new ArrayList<>();
                    List<Integer> result_drug_type_no = new ArrayList<>();
                    for (int i = 0; i < arr4.length(); i++) {
                        JSONObject jobj4 = arr4.getJSONObject(i);
                        result_drug_type_no.add(jobj4.getInt("sno"));
                        result_drug_type.add(jobj4.getString("name"));
                    }
                    Userinfo.setDrug_type_name_List(result_drug_type);
                    Userinfo.setDrug_type_no_List(result_drug_type_no);

                    JSONArray arr5 = jObj.getJSONArray("result_disease");
                    List<String> result_disease = new ArrayList<>();
                    List<Integer> result_disease_no = new ArrayList<>();
                    for (int i = 0; i < arr5.length(); i++) {
                        JSONObject jobj5 = arr5.getJSONObject(i);
                        result_disease_no.add(jobj5.getInt("sno"));
                        result_disease.add(jobj5.getString("name"));
                    }
                    Userinfo.setDisease_name_List(result_disease);
                    Userinfo.setDisease_no_List(result_disease_no);

                    JSONArray arr6 = jObj.getJSONArray("result_drug");
                    List<String> result_drugs = new ArrayList<>();
                    List<Integer> result_drugs_no = new ArrayList<>();
                    for (int i = 0; i < arr6.length(); i++) {
                        JSONObject jobj6 = arr6.getJSONObject(i);
                        result_drugs_no.add(jobj6.getInt("sno"));
                        result_drugs.add(jobj6.getString("name"));
                    }
                    Userinfo.setDrug_name_List(result_drugs);
                    Userinfo.setDrug_no_List(result_drugs_no);

                    JSONArray arr7 = jObj.getJSONArray("result_manufacturer");
                    List<String> result_manufacturer = new ArrayList<>();
                    List<Integer> result_manufacturer_no = new ArrayList<>();
                    for (int i = 0; i < arr7.length(); i++) {
                        JSONObject jobj7 = arr7.getJSONObject(i);
                        result_manufacturer_no.add(jobj7.getInt("sno"));
                        result_manufacturer.add(jobj7.getString("name"));
                    }
                    Userinfo.setDrug_manufacturer_name_List(result_manufacturer);
                    Userinfo.setDrug_manufacturer_no_List(result_manufacturer_no);

                    JSONArray arr8 = jObj.getJSONArray("result_generic");
                    List<String> result_generic = new ArrayList<>();
                    List<Integer> result_generic_no = new ArrayList<>();
                    for (int i = 0; i < arr8.length(); i++) {
                        JSONObject jobj8 = arr8.getJSONObject(i);
                        result_generic_no.add(jobj8.getInt("sno"));
                        result_generic.add(jobj8.getString("name"));
                    }
                    Userinfo.setDrug_generic_name_List(result_generic);
                    Userinfo.setDrug_generic_no_List(result_generic_no);

                    login = (Button) findViewById(R.id.go);
                    login.setEnabled(true);
                    ne = (TextView) findViewById(R.id.ne);
                    if (jObj.has("result_patient")) {
                        login.setText(R.string.Go);
                    } else {
                        Toast.makeText(scontext, R.string.checkiconnect, Toast.LENGTH_SHORT).show();
                        login.setText(R.string.tryagain);
                        loader = (GifImageView)findViewById(R.id.loader);
                        loader.setVisibility(View.GONE);
                        ne.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(scontext, R.string.checkiconnect, Toast.LENGTH_SHORT).show();
                    AppController.getInstance().cancelPendingRequests(tag_string_req);
                    ne = (TextView) findViewById(R.id.ne);
                    ne.setVisibility(View.VISIBLE);
                    login = (Button) findViewById(R.id.go);
                    login.setEnabled(true);
                    login.setText(R.string.tryagain);
                    loader = (GifImageView)findViewById(R.id.loader);
                    loader.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Splash", "Splash Error: " + error.getMessage());
                AppController.getInstance().cancelPendingRequests(tag_string_req);
                ne = (TextView) findViewById(R.id.ne);
                ne.setVisibility(View.VISIBLE);
                login = (Button) findViewById(R.id.go);
                login.setEnabled(true);
                login.setText(R.string.tryagain);
                loader = (GifImageView)findViewById(R.id.loader);
                loader.setVisibility(View.GONE);
                Toast.makeText(scontext, R.string.checkiconnect, Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("tag", "SpinnerAll");
                params.put("hospital", String.valueOf(Userinfo.getHospital()));
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    @Override
    protected void onPause() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.startAutoCycle();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();


    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
        }
        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit",
                Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        hospital_logo = (ImageView) findViewById(R.id.hospital_logo);
        hospital_logo.setImageBitmap(bitmap);
    }

    @Override
    public void onError() {

    }
}
