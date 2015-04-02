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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
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


public class MainActivity3 extends ActionBarActivity {
    SpecialAdapter mSchedule;
    JSONArray array;
    String cat,title,postedby,date,category,city;
    ListView list;
    ArrayList<HashMap<String, String>> mylist;
    HashMap<String, String> map;
    Dialog pDialog;
    InputStream is;
    String line,result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3);
        Intent intent = getIntent();
        cat = intent.getStringExtra("cat");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00aced")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(cat);
        new Asyntask().execute();


        list = (ListView) findViewById(R.id.listView2);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView title= (TextView)view.findViewById(R.id.TRAIN_CELL);
                String s = title.getText().toString();
                if (title.equals("NO events")){

                }
                else {
                    Intent i = new Intent(getApplicationContext(), Description.class);
                    i.putExtra("title", s);
                    startActivity(i);
                }
            }
        });
    }

    class Asyntask extends AsyncTask<String,String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new Dialog(MainActivity3.this);
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
            pDialog.setContentView(R.layout.progress);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {



            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("cat", cat));
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://honeykumar.byethost17.com/selectwc.php");
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


            MainActivity3.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

            try {

                JSONObject jsonObject = new JSONObject(result);
                array = jsonObject.getJSONArray("my");
                mylist = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject c = array.getJSONObject(i);
                        map = new HashMap<>();
                        title = c.optString("title");
                        postedby = c.optString("posted");
                        date = c.optString("date");
                        category = c.optString("category");
                        city = c.optString("city");
                        map.put("title", title);
                        map.put("venue", city);
                        map.put("posted", "Posted by " + postedby);
                        map.put("date", date);
                        map.put("category", category);
                        mylist.add(map);

                        mSchedule = new SpecialAdapter(getApplicationContext(), mylist, R.layout.list_item, new String[]{"title", "venue", "posted", "date", "category"}, new int[]{R.id.TRAIN_CELL, R.id.TO_CELL, R.id.FROM_CELL, R.id.textView10});
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


            if (item.getItemId() == android.R.id.home ) {
                startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                finish();
                return true;
            }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            startActivity(new Intent(getApplicationContext(),MainActivity2.class));
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
