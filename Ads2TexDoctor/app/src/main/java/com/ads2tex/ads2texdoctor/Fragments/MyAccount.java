package com.ads2tex.ads2texdoctor.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ads2tex.ads2texdoctor.App.AppController;
import com.ads2tex.ads2texdoctor.Config.Config;
import com.ads2tex.ads2texdoctor.Login;
import com.ads2tex.ads2texdoctor.NavigationDrawer.NavigationDrawerCallbacks;
import com.ads2tex.ads2texdoctor.Pojo.Userinfo;
import com.ads2tex.ads2texdoctor.R;
import com.ads2tex.ads2texdoctor.volley.Request;
import com.ads2tex.ads2texdoctor.volley.Response;
import com.ads2tex.ads2texdoctor.volley.VolleyError;
import com.ads2tex.ads2texdoctor.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyAccount extends Fragment implements NavigationDrawerCallbacks {
public TextView chgpass,logout;
    public static Context context;
    public View view2;
    public static View myaccview;
    public RelativeLayout linlaHeaderProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        // Inflate the layout for this fragment
        context = getActivity();
        myaccview = view;
        chgpass = (TextView)view.findViewById(R.id.chgpass);
        logout = (TextView)view.findViewById(R.id.macclogout);
        chgpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentfoc();
                if (view2 != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                }
                final View dialogView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_forgetpass, null, false);
                final EditText newpass = (EditText) dialogView.findViewById(R.id.newpass);
                final EditText confirmpass = (EditText) dialogView.findViewById(R.id.confirmpass);
                final Button button = (Button) dialogView.findViewById(R.id.alertbutton);
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Change Password");
                builder1.setIcon(context.getResources().getDrawable(R.drawable.ic_key_variant));
                builder1.setCancelable(true);
                builder1.setView(dialogView);
                final AlertDialog alert11 = builder1.create();
                alert11.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getCurrentfoc();
                        if (view2 != null) {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                        }
                        if(newpass.getText().length()==0)
                        {
                            Toast.makeText(context,"Enter New Password", Toast.LENGTH_SHORT).show();
                        }
                        else if(confirmpass.getText().length()==0)
                        {
                            Toast.makeText(context,"Enter Confirm Password", Toast.LENGTH_SHORT).show();
                        }
                        else if (newpass.getText().length() < 5) {
                            Toast.makeText(context, "Password Min 5 Chars", Toast.LENGTH_SHORT).show();

                        } else if (newpass.getText().length() > 15) {
                            Toast.makeText(context, "Password Max 15 Chars", Toast.LENGTH_SHORT).show();

                        }else if(!newpass.getText().toString().equals(confirmpass.getText().toString()))
                        {
                            Toast.makeText(context,"Password Doesn't Match", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            alert11.cancel();
                            Userinfo.setPassword(newpass.getText().toString());
                            ChangePassword();
                        }

                    }
                });
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Logout?");
                builder1.setIcon(context.getResources().getDrawable(R.drawable.ic_logout));
                builder1.setMessage("Are You Sure To Logout?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Userinfo.setUserid("");
                                Userinfo.setUsername("");
                                Userinfo.setPassword("");
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
            }
        });

        return view;
    }

    public void getCurrentfoc()
    {
        view2 = getActivity().getCurrentFocus();
    }

    private void ChangePassword() {
        linlaHeaderProgress = (RelativeLayout) myaccview.findViewById(R.id.linlaHeaderProgresslogin);
        linlaHeaderProgress.setVisibility(View.VISIBLE);
        // Tag used to cancel the request
        String tag_string_req = "req_changepassword";

        final String uri = Config.getServer_address()
                + "/myacc_json.php";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                uri, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("Change Pass", "Change Pass Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setTitle("Success");
                        builder1.setIcon(context.getResources().getDrawable(R.drawable.ic_menu_check));
                        builder1.setMessage("Your Password Changed Successfully!");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    } else {
                        String Msg = jObj.getString("msg");
                        Toast.makeText(context,
                                Msg, Toast.LENGTH_LONG).show();
                    }
                    linlaHeaderProgress = (RelativeLayout)myaccview.findViewById(R.id.linlaHeaderProgresslogin);
                    linlaHeaderProgress.setVisibility(View.GONE);
                } catch (JSONException e) {
                    linlaHeaderProgress = (RelativeLayout)myaccview.findViewById(R.id.linlaHeaderProgresslogin);
                    linlaHeaderProgress.setVisibility(View.GONE);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setTitle("Internet Connection");
                    builder1.setIcon(context.getResources().getDrawable(R.drawable.fail));
                    builder1.setMessage("Please Check Your Internet Connection");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    ChangePassword();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                linlaHeaderProgress = (RelativeLayout) myaccview.findViewById(R.id.linlaHeaderProgresslogin);
                linlaHeaderProgress.setVisibility(View.GONE);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Internet Connection");
                builder1.setIcon(context.getResources().getDrawable(R.drawable.fail));
                builder1.setMessage("Please Check Your Internet Connection");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                ChangePassword();
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
                params.put("tag", "setpass");
                params.put("email", Userinfo.getUsername());
                params.put("pass", Userinfo.getPassword());
                params.put("imei",Userinfo.getImei());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
                        Intent intent = new Intent(getActivity(),
                                Login.class);
                        startActivity(intent);
                        getActivity().finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setTitle("Internet Connection");
                    builder1.setIcon(context.getResources().getDrawable(R.drawable.fail));
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
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Internet Connection");
                builder1.setIcon(context.getResources().getDrawable(R.drawable.fail));
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

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }
}
