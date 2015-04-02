package com.android.gowtham.city_hunt;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Description extends ActionBarActivity {

    Dialog pDialog;
    String line,result,title,desc,city,date,seats,status,contactper,contactnum,email,venue,popular,sorg;
    InputStream is;
    TextView titles,ven,dates,des,estatus,seat,cper,cnum,cemail,popul,org;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ed4255")));

        Intent i = getIntent();
        title = i.getStringExtra("title");
        org = (TextView)findViewById(R.id.textView37);
        popul = (TextView)findViewById(R.id.textView25);
        titles = (TextView)findViewById(R.id.textView11);
        ven = (TextView)findViewById(R.id.editText17);
        dates = (TextView)findViewById(R.id.textView12);
        des = (TextView)findViewById(R.id.textView14);
        estatus = (TextView)findViewById(R.id.textView20);
        seat = (TextView)findViewById(R.id.textView17);
        cper = (TextView)findViewById(R.id.textView19);
        cnum = (TextView)findViewById(R.id.textView22);
        cemail = (TextView)findViewById(R.id.textView24);
        ImageView map = (ImageView)findViewById(R.id.imageView11);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = ven.getText().toString();
                s = s.replace(' ','+');
                Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+s));
                startActivity(geoIntent);
            }
        });



        new Asyntask().execute();

        TextView share = (TextView)findViewById(R.id.textView26);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hey,Look what i found!");
                i.putExtra(android.content.Intent.EXTRA_TEXT,"An event called " + title +" is happening at"+venue+
                        " on "+date+".\n"+ "The description is here : "+ desc+".\n"+"I thought you would find this interesting. Have a look at City-Hunt application.");
                startActivity(Intent.createChooser(i, "Share via"));
            }
        });

        ven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ven.getText().toString();
                s = s.replace(' ','+');
                Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+s));
                startActivity(geoIntent);
            }
        });
        TextView cal = (TextView)findViewById(R.id.textView27);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date1 = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                try {
                    date1 = formatter.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Calendar beginTime = Calendar.getInstance();
                beginTime.setTime(date1);
                beginTime.set(beginTime.get(Calendar.YEAR),beginTime.get(Calendar.MONTH)+1,beginTime.get(Calendar.DAY_OF_MONTH));

                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("title", title);
                intent.putExtra("description", desc);
                intent.putExtra("eventLocation",venue);
                intent.putExtra("beginTime", beginTime.getTimeInMillis());
                startActivity(intent);
            }
        });
    }

    class Asyntask extends AsyncTask<String,String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new Dialog(Description.this);
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


            Description.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {


                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray array = jsonObject.getJSONArray("my");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject c = array.getJSONObject(i);
                            title = c.optString("title");
                            popular = c.optString("popularity");
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
                            titles.setText(title);
                            ven.setText(venue + "," + city);
                            dates.setText(date);
                            des.setText(desc);
                            seat.setText(seats);
                            estatus.setText(status);
                            cper.setText(contactper);
                            cnum.setText(contactnum);
                            cemail.setText(email);
                            org.setText(sorg);
                            popul.setText(popular+"%");
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
}
