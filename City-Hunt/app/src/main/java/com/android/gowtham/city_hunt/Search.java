package com.android.gowtham.city_hunt;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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
import java.util.HashMap;

public class Search extends Fragment implements View.OnClickListener {
    SessionManager session;
    Dialog pDialog;
    LoginModule loginModule;
    Button all,editloc,events,festival,business,sports,college;
    InputStream is = null;
    String line = null;
    String result = null;
    int code;
    Button likeus;
    String sname,smail,count;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        loginModule = new LoginModule(getActivity(),2);
        session = new SessionManager(getActivity());
        new Asyntask2().execute();



        //editlocation
        editloc = (Button)rootView.findViewById(R.id.button8);
        editloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getActivity(),PickRegion.class));
            }
        });
        all = (Button)rootView.findViewById(R.id.button3);
        events = (Button)rootView.findViewById(R.id.button2);
        festival = (Button)rootView.findViewById(R.id.button4);
        business = (Button)rootView.findViewById(R.id.button5);
        college = (Button)rootView.findViewById(R.id.button7);
        sports = (Button)rootView.findViewById(R.id.button6);
        all.setOnClickListener(this);
        events.setOnClickListener(this);
        festival.setOnClickListener(this);
        business.setOnClickListener(this);
        sports.setOnClickListener(this);
        college.setOnClickListener(this);
        Button myevents= (Button)rootView.findViewById(R.id.button9);
        likeus = (Button)rootView.findViewById(R.id.button10);
        likeus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.isLoggedIn()){
                    HashMap<String, String> user = session.getUserDetails();

                    // name
                    sname = user.get(SessionManager.KEY_NAME);
                    smail = user.get(SessionManager.KEY_EMAIL);
                    new Asyntask1().execute();

                }else{
                   loginModule.inLogin(getActivity());
                }
            }
        });
        myevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session.isLoggedIn()){
                    Intent i = new Intent(getActivity(),MyEvents.class);
                    startActivity(i);
                }else{
                    loginModule.inLogin(getActivity());
                }
            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button3:
                startActivity(new Intent(getActivity(),MainActivity3.class).putExtra("cat","All"));
                break;
            case R.id.button2:
                startActivity(new Intent(getActivity(),MainActivity3.class).putExtra("cat","Events"));
                break;
            case R.id.button4:
                startActivity(new Intent(getActivity(),MainActivity3.class).putExtra("cat","Festival"));
                break;
            case R.id.button5:
                startActivity(new Intent(getActivity(),MainActivity3.class).putExtra("cat","Business"));
                break;
            case R.id.button6:
                startActivity(new Intent(getActivity(),MainActivity3.class).putExtra("cat","Sports"));
                break;
            case R.id.button7:
                startActivity(new Intent(getActivity(),MainActivity3.class).putExtra("cat","College"));
                break;
        }
        getActivity().finish();
    }


    class Asyntask1 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new Dialog(getActivity());
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
            pDialog.setContentView(R.layout.progress);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("name", sname));
            nameValuePairs.add(new BasicNameValuePair("mail", smail));
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://honeykumar.byethost17.com/like.php");
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
                code = jsonObject.getInt("code");

                if (code == 1) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "You have already liked us", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(code == 2){
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Thanks for liking us", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(),MainActivity2.class));
                            getActivity().finish();
                        }
                    });
                }else {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Sorry, TryAgain", Toast.LENGTH_SHORT).show();
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
            pDialog.dismiss();
        }
    }
    class Asyntask2 extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... args) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("name", sname));

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://honeykumar.byethost17.com/count.php");
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
                code = jsonObject.getInt("code");
                count = String.valueOf(code);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        likeus.setText("Like Us" + "\t \t" + count);
                    }
                });
                }catch (Exception e) {
                Log.e("Fail3", e.toString());
            }
            return null;
        }

    }

}