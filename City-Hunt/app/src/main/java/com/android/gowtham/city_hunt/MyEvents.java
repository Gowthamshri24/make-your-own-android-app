package com.android.gowtham.city_hunt;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class MyEvents extends ActionBarActivity {
    SessionManager session;
    SpecialAdapter mSchedule;
    String sid,title,date,venue,category;
    Dialog pDialog,dialog;
    InputStream is = null;
    String line = null;
    String result = null;
    TextView textView;
    ListView list;
    Button ok;
    ArrayList<HashMap<String, String>> mylist;
    HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        dialog = new Dialog(MyEvents.this);
        dialog.setContentView(R.layout.confirmation);
        textView = (TextView)dialog.findViewById(R.id.textView8);
        ok = (Button)dialog.findViewById(R.id.button22);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dialog.dismiss();
                    session.makemy();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ed4255")));
        session = new SessionManager(getApplicationContext());
        list = (ListView)findViewById(R.id.listView);

        if(!session.isMy()) {
            dialog(" Got an event to share with the rest of the world? Go and publish your event. you can find all your published events here.", "My events");
            session.makeIns();
        }

        HashMap<String, String> user = session.getUserDetails();

        // name
        sid = user.get(SessionManager.KEY_ID);

        new Asyntask().execute();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView)view.findViewById(R.id.TRAIN_CELL);
                String s = textView.getText().toString().trim();
                Intent intent = new Intent(getApplicationContext(),Details.class);
                intent.putExtra("title", s);
                startActivity(intent);
            }
        });
    }
    class Asyntask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new Dialog(MyEvents.this);
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
            pDialog.setContentView(R.layout.progress);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {


            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("id",sid));
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://honeykumar.byethost17.com/myevents.php");
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


            MyEvents.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {


                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray array = jsonObject.getJSONArray("my");
                        mylist = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject c = array.getJSONObject(i);
                            map = new HashMap<>();
                            title = c.optString("title");
                            venue = c.optString("city");
                            date = c.optString("date");
                            category = c.optString("category");
                            map.put("title", title);
                            map.put("venue", venue);
                            map.put("date", date);
                            map.put("category",category);
                            mylist.add(map);
                            mSchedule = new SpecialAdapter(getApplicationContext(), mylist, R.layout.list_item,new String[] {"title", "venue", "category","date","category"}, new int[] {R.id.TRAIN_CELL,R.id.TO_CELL, R.id.FROM_CELL,R.id.textView10 });
                            list.setAdapter(mSchedule);
                        }
                    } catch (Exception e) {
                        Log.e("Fail3", e.toString());
                    }
                }
            });
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home ) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void dialog(String text,String title){
        textView.setText(text);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
        dialog.setTitle(title);
        dialog.show();
    }

}
