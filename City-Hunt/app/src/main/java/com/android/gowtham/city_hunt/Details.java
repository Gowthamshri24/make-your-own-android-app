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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Details extends ActionBarActivity {
    Dialog pDialog;
    TextView titles,ven,dates,des,estatus,seat,cper,cnum,cemail,org;
    String title,desc,city,date,seats,status,contactper,contactnum,email,venue,sorg;
    InputStream is = null;
    String line = null;
    String result = null;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ed4255")));


        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        titles = (TextView)findViewById(R.id.textView11);
        org = (TextView)findViewById(R.id.textView38);
        ven = (TextView)findViewById(R.id.editText17);
        dates = (TextView)findViewById(R.id.textView12);
        des = (TextView)findViewById(R.id.textView14);
        estatus = (TextView)findViewById(R.id.textView20);
        seat = (TextView)findViewById(R.id.textView17);
        cper = (TextView)findViewById(R.id.textView19);
        cnum = (TextView)findViewById(R.id.textView22);
        cemail = (TextView)findViewById(R.id.textView24);

        new Asyntask().execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),Edit.class);
            intent.putExtra("title",title);
            intent.putExtra("org",sorg);
            intent.putExtra("venue",venue);
            intent.putExtra("date",date);
            intent.putExtra("des",desc);
            intent.putExtra("cnum",contactnum);
            intent.putExtra("cper",contactper);
            intent.putExtra("cmail",email);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_delete){
            final Dialog dialog = new Dialog(Details.this);
            dialog.setContentView(R.layout.deletepopup);
            dialog.setTitle("Delete Confirmation");
            dialog.show();
            Button yes = (Button)dialog.findViewById(R.id.button16);
            Button no = (Button)dialog.findViewById(R.id.button17);
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                     new Asyntask2().execute();

                }
            });
            return true;
        }
        if (item.getItemId() == android.R.id.home ) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class Asyntask extends AsyncTask<String,String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new Dialog(Details.this);
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
            pDialog.setContentView(R.layout.progress);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {



            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("title",title ));
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://honeykumar.byethost17.com/select.php");
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


            Details.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {


                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray array = jsonObject.getJSONArray("my");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject c = array.getJSONObject(i);
                            title = c.optString("title");
                            sorg = c.optString("org");
                            venue = c.optString("venue");
                            date = c.optString("date");
                            desc = c.optString("des");
                            city = c.optString("city");
                            seats = c.optString("seats");
                            status = c.optString("status");
                            contactper = c.optString("person");
                            contactnum = c.optString("contact");
                            email = c.optString("email");
                            org.setText(sorg);
                            titles.setText(title);
                            ven.setText(venue + "," + city);
                            dates.setText(date);
                            des.setText(desc);
                            seat.setText(seats);
                            estatus.setText(status);
                            cper.setText(contactper);
                            cnum.setText(contactnum);
                            cemail.setText(email);
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
    class Asyntask2 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new Dialog(Details.this);
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
            pDialog.setContentView(R.layout.progress);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("title", title));
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://honeykumar.byethost17.com/delete.php");
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

                if (code ==1) {
                    Details.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }else {
                    Details.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
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
}
