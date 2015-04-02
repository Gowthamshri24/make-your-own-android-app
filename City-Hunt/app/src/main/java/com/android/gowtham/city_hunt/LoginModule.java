package com.android.gowtham.city_hunt;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginModule {
    InputStream is;
    Dialog _dialog;
    EditText user,pass;
    Button signin,signup;
    SessionManager session;
    private Dialog pDialog;
    private String username,password,name;
    public String line,result;
    public Activity _activity;
    Context _contect;
    int _flag,code;

    public LoginModule(Context context,int flag){

        _contect = context;
        _flag = flag;
        session = new SessionManager(_contect);
    }

    public void inLogin(Activity activity){
        _activity = activity;
        _dialog = new Dialog(_activity);
        _dialog.setContentView(R.layout.activity_popsignin);
        TextView tv = (TextView)_dialog.findViewById(R.id.textView6);
        user = (EditText)_dialog.findViewById(R.id.editText5);
        pass = (EditText)_dialog.findViewById(R.id.editText6);
        signin = (Button)_dialog.findViewById(R.id.button14);
        signup = (Button)_dialog.findViewById(R.id.button15);
        _dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
        _dialog.setTitle("Login");
        _dialog.show();
        signin.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = user.getText().toString();
                password = pass.getText().toString();
                if(username.length()==0 && password.length()==0) {
                    Toast.makeText(_contect,"Please Enter username or password",Toast.LENGTH_SHORT).show();
                }else{
                    if (isEmailValid(username))
                    new Asyntask().execute();
                    else
                        Toast.makeText(_contect,"Please Enter the valid email",Toast.LENGTH_SHORT).show();
                }

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _dialog.dismiss();
                Intent i = new Intent(_contect,Loginscreen.class);
                _activity.startActivity(i);
            }
        });
        tv.setText(Html.fromHtml("<u>FORGOT PASSWORD?</u>"));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _activity.startActivity(new Intent(_contect, ForgotPassword.class));
            }
        });

    }

    public void login(String _username,String _password,Activity activity){
        username = _username;
        password = _password;
        _activity = activity;
        if(username.length()==0 && password.length()==0) {
            Toast.makeText(_contect,"Please Enter username or password",Toast.LENGTH_SHORT).show();
        }else{
            new Asyntask().execute();
        }

    }
    class Asyntask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new Dialog(_activity);
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
            pDialog.setContentView(R.layout.progress);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("username", username));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://honeykumar.byethost17.com/signin.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                Log.e("Pass1", "Connection Success");
            } catch (Exception e) {
                Log.e("Fail 1", e.toString());
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                is.close();
                result = sb.toString();
                Log.e("Pass2", "Connection success");
            } catch (Exception e) {
                Log.e("Fail2", e.toString());
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                name = jsonObject.getString("name");
                code = jsonObject.getInt("code");

                if (code !=0) {
                    if (_flag==1) {
                        _activity.runOnUiThread(new Runnable() {
                            public void run() {
                                session.createLoginSession(name, username, code);
                                session.makeFirst();
                                _activity.startActivity(new Intent(_contect,MainActivity2.class));
                                _activity.finish();
                                Toast.makeText(_contect, "Success", Toast.LENGTH_SHORT).show();
                            }

                        });
                    } else {
                        if (_flag == 2) {
                            _activity.runOnUiThread(new Runnable() {
                                public void run() {
                                    session.createLoginSession(name, username, code);
                                    _dialog.dismiss();
                                    _activity.startActivity(new Intent(_contect,MainActivity2.class));
                                    _activity.finish();
                                    Toast.makeText(_contect, "Success", Toast.LENGTH_SHORT).show();
                                }

                            });

                        }
                    }
                }else {
                    _activity.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(_contect, "Email or Password is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                Log.e("Fail3", e.toString());
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            if (pDialog.isShowing())
            pDialog.dismiss();
        }
    }
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
