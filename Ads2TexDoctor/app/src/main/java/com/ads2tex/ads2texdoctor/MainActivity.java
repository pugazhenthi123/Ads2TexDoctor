package com.ads2tex.ads2texdoctor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ads2tex.ads2texdoctor.App.AppController;
import com.ads2tex.ads2texdoctor.Config.Config;
import com.ads2tex.ads2texdoctor.Fragments.HomeFragment;
import com.ads2tex.ads2texdoctor.Pojo.Userinfo;
import com.ads2tex.ads2texdoctor.volley.Request;
import com.ads2tex.ads2texdoctor.volley.Response;
import com.ads2tex.ads2texdoctor.volley.VolleyError;
import com.ads2tex.ads2texdoctor.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static Toolbar mToolbar;
    public static Context mContext;
    public static FragmentManager fragmentManagermain;

    /**
     * BackKey
     **/
    public static boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        mContext = this;

        fragmentManagermain = getSupportFragmentManager();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setLogo(R.drawable.ic_magnify_menu);
                getSupportActionBar().setTitle("Patient");
            }
            Fragment fr = new HomeFragment();
            switchFragment(fr);
    }

    public void switchFragment(Fragment baseFragment) {
        try {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            if (fm.findFragmentById(R.id.fragment_place) == null) {
                fragmentTransaction.add(R.id.fragment_place, baseFragment);
            } else {
                fragmentTransaction.replace(R.id.fragment_place, baseFragment);
            }
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
            builder1.setTitle("Logout?");
            builder1.setIcon(mContext.getResources().getDrawable(R.drawable.ic_logout));
            builder1.setMessage("Are You Sure To Logout?");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Logout();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Logout() {
        // Tag used to cancel the request
        String tag_string_req = "check_imei";
        final String uri = Config.getServer_address()
                + "/homeinfo_json.php";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                uri, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("RegisterGCM", "Checkimei Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Userinfo.setUserid("");
                        Userinfo.setUsername("");
                        Userinfo.setPassword("");
                        Intent intent = new Intent(MainActivity.this,
                                Login.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                    builder1.setTitle("Internet Connection");
                    builder1.setIcon(mContext.getResources().getDrawable(R.drawable.fail));
                    builder1.setMessage("Please Check Your Internet Connection");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Logout();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setTitle("Internet Connection");
                builder1.setIcon(mContext.getResources().getDrawable(R.drawable.fail));
                builder1.setMessage("Please Check Your Internet Connection");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Logout();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();

                params.put("tag", "logout");
                params.put("imei", Userinfo.getImei());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


}
