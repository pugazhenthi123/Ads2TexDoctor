package com.ads2tex.ads2texdoctor;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ads2tex.ads2texdoctor.App.AppController;
import com.ads2tex.ads2texdoctor.Config.Config;
import com.ads2tex.ads2texdoctor.Pojo.Userinfo;
import com.ads2tex.ads2texdoctor.Utils.ConnectionDetector;
import com.ads2tex.ads2texdoctor.volley.Request;
import com.ads2tex.ads2texdoctor.volley.Response;
import com.ads2tex.ads2texdoctor.volley.VolleyError;
import com.ads2tex.ads2texdoctor.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private Toolbar mToolbar;
    Button Login, F_pass;
    EditText u_name, u_pass;
    public Context mContext;
    public View view2;
    String TAG = "Login Page",LoginRes = "Login Response: ",CheckImeiRes="Checkimei Response:";
    public RelativeLayout linlaHeaderProgress;
    public static boolean doubleBackToExitPressedOnce = false;
    private TelephonyManager mTelephonyManager;

    // Internet detector
    ConnectionDetector cd;

    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        linlaHeaderProgress = (RelativeLayout) findViewById(R.id.linlaHeaderProgresslogin);
        linlaHeaderProgress.setVisibility(View.VISIBLE);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setLogo(R.drawable.ic_account_menu);
            getSupportActionBar().setTitle(R.string.myacc);
        }
        Login = (Button) findViewById(R.id.u_log);
        F_pass = (Button) findViewById(R.id.u_fp);
        u_name = (EditText) findViewById(R.id.u_name);
        u_pass = (EditText) findViewById(R.id.u_pass);
        linlaHeaderProgress = (RelativeLayout) findViewById(R.id.linlaHeaderProgresslogin);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        getImei();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentfoc();
                if (view2 != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                }
                if (u_name.getText().length() == 0) {
                    Toast.makeText(mContext, R.string.enteryouremail, Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(u_name.getText().toString().replace(" ", ""))) {
                    Toast.makeText(mContext, R.string.enteryourvalidemail, Toast.LENGTH_SHORT).show();
                } else if (u_pass.getText().length() == 0) {
                    Toast.makeText(mContext, R.string.enteryourpass, Toast.LENGTH_SHORT).show();

                } else {
                    Login.setEnabled(false);
                    Login.setText(R.string.wait);
                    checkLogin(u_name.getText().toString().replace(" ", ""), u_pass.getText().toString());
                }
            }
        });

        F_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentfoc();
                if (view2 != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                }
                final View dialogView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.email, null, false);
                final EditText edittext = (EditText) dialogView.findViewById(R.id.alertemail);
                final Button button = (Button) dialogView.findViewById(R.id.alertbutton);
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setTitle(R.string.forgotpass);
                builder1.setIcon(mContext.getResources().getDrawable(android.R.drawable.ic_dialog_email));
                builder1.setMessage(R.string.enteryouremail);
                builder1.setCancelable(true);
                builder1.setView(dialogView);
                final AlertDialog alert11 = builder1.create();
                alert11.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getCurrentfoc();
                        if (view2 != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                        }
                        if (!isValidEmail(edittext.getText().toString().replace(" ", ""))) {
                            Toast.makeText(mContext, R.string.enteryourvalidemail, Toast.LENGTH_SHORT).show();
                        } else {
                            ForgotPassword(edittext.getText().toString());
                            alert11.cancel();
                            Toast.makeText(mContext, edittext.getText().toString().replace(" ", ""), Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

    }


    public void getImei()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
                getImei();
            } else {
                Userinfo.setImei(getDeviceImei());
                checkimei();
            }
        } else {
            try {
                Userinfo.setImei(getDeviceImei());
            } catch (Exception e) {
                Toast.makeText(mContext, R.string.give_app_permission, Toast.LENGTH_LONG).show();
            }
            checkimei();
        }
    }

    private String getDeviceImei() {

        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyManager.getDeviceId();
    }

    public void getCurrentfoc() {
        view2 = this.getCurrentFocus();
    }

    private void checkLogin(final String email, final String password) {
        setLayoutVisibility(true);
        // Tag used to cancel the request
        final String tag_string_req = "req_login";

        final String uri = Config.getServer_address()
                + "/myacc_json.php";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                uri, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i(TAG, LoginRes + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        Userinfo.setUserid(String.valueOf(jObj.getJSONObject("doctor").getInt("sno")));
                        Userinfo.setUsername(email);
                        Userinfo.setPassword(password);
                        Userinfo.setLogo_link(jObj.getString("hospital_logo_link"));
                        Userinfo.setHospital(Integer.valueOf(jObj.getString("hospital")));
                        String Msg = jObj.getString("msg");
                        Toast.makeText(mContext, Msg, Toast.LENGTH_LONG).show();
                        // Launch Login activity
                        Intent intent = new Intent(Login.this,
                                Splash.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        Login.setEnabled(true);
                        Login.setText(R.string.login);
                        String Msg = mContext.getResources().getString(R.string.wrong_email_or_pass);
                        if (jObj.getString("msg").equals(Msg)) {
                            Toast.makeText(mContext, Msg, Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                            builder1.setTitle(R.string.activation);
                            builder1.setIcon(mContext.getResources().getDrawable(R.drawable.ic_menu_check));
                            builder1.setMessage(jObj.getString("msg"));
                            builder1.setCancelable(false);
                            builder1.setPositiveButton(R.string.DialogPositive,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            Intent intent = new Intent(
                                                    mContext,
                                                    Login.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    }
                    setLayoutVisibility(false);
                } catch (JSONException e) {
                    setLayoutVisibility(false);
                    // JSON error
                    e.printStackTrace();
                    AppController.getInstance().cancelPendingRequests(tag_string_req);
                    Login.setEnabled(true);
                    Login.setText(R.string.login);
                    Log.e(TAG, R.string.login+R.string.Error+":" + e.getMessage());
                    Toast.makeText(mContext, R.string.checkiconnect, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                setLayoutVisibility(false);
                AppController.getInstance().cancelPendingRequests(tag_string_req);
                Login.setEnabled(true);
                Login.setText(R.string.login);
                Log.d(TAG, R.string.login+R.string.Error+":" + error.getMessage());
                Toast.makeText(mContext, R.string.checkiconnect, Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("tag", "login");
                params.put("email", email);
                params.put("password", password);
                params.put("imei", Userinfo.getImei());
                Log.d("loginimei", Userinfo.getImei());


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void ForgotPassword(final String email) {
        setLayoutVisibility(true);
        // Tag used to cancel the request
        String tag_string_req = "req_resetpass";
        F_pass = (Button) findViewById(R.id.u_fp);
        F_pass.setEnabled(false);

        final String uri = Config.getServer_address()
                + "/myacc_json.php";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                uri, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "ResetPass Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite

                        // Launch login activity
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                        builder1.setTitle("Set Password");
                        builder1.setIcon(mContext.getResources().getDrawable(R.drawable.ic_menu_check));
                        builder1.setMessage("New Password Sent to Your Mail");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        Intent intent = new Intent(
                                                mContext,
                                                Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String Msg = jObj.getString("msg");
                        Toast.makeText(mContext,
                                Msg, Toast.LENGTH_LONG).show();
                    }
                    F_pass = (Button) findViewById(R.id.u_fp);
                    F_pass.setEnabled(true);
                    setLayoutVisibility(false);
                } catch (JSONException e) {
                    setLayoutVisibility(false);
                    e.printStackTrace();
                    F_pass = (Button) findViewById(R.id.u_fp);
                    F_pass.setEnabled(true);
                    Toast.makeText(mContext,
                            R.string.checkiconnect, Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                setLayoutVisibility(false);
                F_pass = (Button) findViewById(R.id.u_fp);
                F_pass.setEnabled(true);
                Log.e(TAG, "ResetPass Error: " + error.getMessage());
                Toast.makeText(mContext,
                        R.string.checkiconnect, Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("tag", "resetpass");
                params.put("email", email);
                params.put("state", "fpass");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void checkimei() {
        setLayoutVisibility(true);
        // Tag used to cancel the request
        String tag_string_req = "check_imei";
        final String uri = Config.getServer_address()
                + "/homeinfo_json.php";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                uri, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i(TAG, CheckImeiRes + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (error) {
                        Userinfo.setUserid("");
                        Userinfo.setUsername("");
                        Userinfo.setPassword("");

                    } else {
                        Userinfo.setUserid(jObj.getString("sno"));
                        Userinfo.setUsername(jObj.getString("email"));
                        Userinfo.setPassword(jObj.getString("pass"));
                        Userinfo.setLogo_link(jObj.getString("hospital_logo_link"));
                        Userinfo.setHospital(Integer.valueOf(jObj.getString("hospital")));
                        // Launch Login activity
                        Intent intent = new Intent(Login.this,
                                Splash.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    setLayoutVisibility(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                    CheckInternet_Connection();
                    setLayoutVisibility(false);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                CheckInternet_Connection();
                setLayoutVisibility(false);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();

                params.put("tag", "receivedata");
                params.put("imei", Userinfo.getImei());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void CheckInternet_Connection()
    {
        cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
            builder1.setTitle("Internet Connection");
            builder1.setIcon(mContext.getResources().getDrawable(R.drawable.fail));
            builder1.setMessage("Please Check Your Internet Connection");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            CheckInternet_Connection();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }else{
            checkimei();
        }
    }

    public void setLayoutVisibility(Boolean check)
    {
        if(check)
        {
            linlaHeaderProgress = (RelativeLayout) findViewById(R.id.linlaHeaderProgresslogin);
            linlaHeaderProgress.setVisibility(View.VISIBLE);
        }else {
            linlaHeaderProgress = (RelativeLayout) findViewById(R.id.linlaHeaderProgresslogin);
            linlaHeaderProgress.setVisibility(View.GONE);
        }


    }

    @Override
    public void onBackPressed() {
        linlaHeaderProgress = (RelativeLayout) findViewById(R.id.linlaHeaderProgresslogin);
        getCurrentfoc();
        if (view2 != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
        }
        if (linlaHeaderProgress.getVisibility() == View.VISIBLE) {
            Toast.makeText(this, "Please Wait....",
                    Toast.LENGTH_SHORT).show();
        } else {
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
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
}
