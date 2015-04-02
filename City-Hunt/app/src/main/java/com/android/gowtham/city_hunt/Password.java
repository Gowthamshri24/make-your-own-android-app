package com.android.gowtham.city_hunt;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Password extends ActionBarActivity {

    String email,pass,cnfm,url;
    Dialog dialog,pDialog;
    TextView textView;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ed4255")));
        dialog = new Dialog(Password.this);
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
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        final EditText passw = (EditText) findViewById(R.id.editText19);
        final EditText cpass = (EditText) findViewById(R.id.editText20);
        Button submit = (Button)findViewById(R.id.button20);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = passw.getText().toString();
                cnfm = cpass.getText().toString();
                if (pass.length()==0)
                    Toast.makeText(getApplicationContext(),"Enter the password",Toast.LENGTH_SHORT).show();
                else{
                    if (cnfm.equals(pass))
                        new Asyntask().execute();
                    else
                        Toast.makeText(getApplicationContext(),"Password doesn't match",Toast.LENGTH_SHORT ).show();
                }

            }
        });

    }

    class Asyntask extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            pDialog = new Dialog(Password.this);
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
            pDialog.setContentView(R.layout.progress);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            final String username;
            username = "cityhuntcc@gmail.com";
            final String password = "aspirelife";
            String sub = "Confirm registration";
            url = "http://honeykumar.byethost17.com/forgetpass.php?mail=" + email.trim() + "&pass=" + pass.trim();
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");



            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    if ((username.length() > 0) && (password.length() > 0)) {
                        return new PasswordAuthentication(username, password);
                    }
                    return null;
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("from-email@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject(sub);
                message.setText(url);
                Transport.send(message);
                pDialog.dismiss();
                Password.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                dialog("Password changed successfully, click on the link sent to your mail to complete the process", "Success");

                    }
                });

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (pDialog.isShowing())pDialog.dismiss();
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
}
