package com.android.gowtham.city_hunt;

import android.app.Dialog;
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
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Loginscreen extends ActionBarActivity {
    EditText name,email,pass,cpass;
    String sname,semail,spass,scpass;
    Dialog pDialog;
    InputStream is;
    String line,result;
    int code;
    Dialog dialog;
    TextView textView;
    Button ok;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ed4255")));
        dialog = new Dialog(Loginscreen.this);
        dialog.setContentView(R.layout.confirmation);
        textView = (TextView)dialog.findViewById(R.id.textView8);
        ok = (Button)dialog.findViewById(R.id.button22);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        Button signup = (Button) findViewById(R.id.button12);
        name = (EditText) findViewById(R.id.editText7);
        email = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        cpass = (EditText) findViewById(R.id.editText8);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname = name.getText().toString();
                semail = email.getText().toString();
                spass = pass.getText().toString();
                scpass = cpass.getText().toString();
                if (sname.length()==0) {
                    Toast.makeText(getApplicationContext(), "Please Enter the username", Toast.LENGTH_SHORT).show();
                } else{
                        if (isEmailValid(semail)){
                            if (spass.length()==0){
                                Toast.makeText(getApplicationContext(),"Please Set Password to your account",Toast.LENGTH_SHORT).show();
                            } else{
                                if (scpass.equals(spass)){
                                    new Asyntask().execute();
                                } else{
                                    Toast.makeText(getApplicationContext(),"Password doesn't match",Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else{
                            Toast.makeText(getApplicationContext(),"Enter a valid email",Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        });
    }
    class Asyntask extends AsyncTask<String, String, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new Dialog(Loginscreen.this);
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
            pDialog.setContentView(R.layout.progress);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("email", semail));
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://honeykumar.byethost17.com/signup.php");
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
                if (code==2) {
                    final String username = "cityhuntcc@gmail.com";
                    final String password = "aspirelife";
                    String sub = "Confirm registration";
                    url = "http://honeykumar.byethost17.com/csignup.php?name=" + sname.replaceAll(" ",".").trim() + "&mail=" + semail.trim()+"&pass="+spass.trim();
                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");

                    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

                    try {

                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("from-email@gmail.com"));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(semail));
                        message.setSubject(sub);
                        message.setText(url);
                        Transport.send(message);
                        pDialog.dismiss();
                        Loginscreen.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                        dialog("A verification link has been sent to your email, click on the link to complete the process", "One more step away");
                            }
                        });

                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }else if (code==1){
                    Loginscreen.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Email was already used", Toast.LENGTH_SHORT).show();
                        }
                    });
                    }

            } catch (Exception e) {
                Log.e("Fail3", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(pDialog.isShowing())
                pDialog.dismiss();
        }
    }

    public void dialog(String text,String title){
        textView.setText(text);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
        dialog.setTitle(title);
        dialog.show();
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
