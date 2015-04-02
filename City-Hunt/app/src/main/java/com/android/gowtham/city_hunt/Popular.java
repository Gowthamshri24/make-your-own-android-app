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


public class Popular extends Fragment {

    Dialog pDialog;
    InputStream is;
    String result,line;
    JSONArray array;
    ArrayList<HashMap<String,String>> mylist;
    HashMap<String,String> map;
    SpecialAdapter1 mSchedule;
    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_popular, container, false);
        list = (ListView)rootView.findViewById(R.id.listView3);
        new Asyntask().execute();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView title= (TextView)view.findViewById(R.id.textView33);
                String s = title.getText().toString();
                if (title.equals("NO events")){

                }
                else {
                    Intent i = new Intent(getActivity(), Description.class);
                    i.putExtra("title", s);
                    startActivity(i);
                }
            }
        });
        return rootView;
    }

    class Asyntask extends AsyncTask<String,String, String> {
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
            nameValuePairs.add(new BasicNameValuePair("cat", "All"));
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://honeykumar.byethost17.com/new.php");
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


            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {

                        JSONObject jsonObject = new JSONObject(result);
                        array = jsonObject.getJSONArray("my");
                        mylist = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject c = array.getJSONObject(i);
                            map = new HashMap<>();
                            map.put("title",c.optString("title") );
                            map.put("venue", c.optString("city"));
                            map.put("date",c.optString("date"));
                            map.put("category", c.optString("category"));
                            mylist.add(map);

                            mSchedule = new SpecialAdapter1(getActivity(), mylist, R.layout.popular, new String[]{"title", "venue", "date", "category"}, new int[]{R.id.textView33, R.id.textView34, R.id.textView35});
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



}
