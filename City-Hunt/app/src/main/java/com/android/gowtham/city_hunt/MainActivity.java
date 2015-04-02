package com.android.gowtham.city_hunt;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import static com.android.gowtham.city_hunt.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.android.gowtham.city_hunt.CommonUtilities.EXTRA_MESSAGE;
import static com.android.gowtham.city_hunt.CommonUtilities.SENDER_ID;


public class MainActivity extends Activity {
    SessionManager session;
    LoginModule loginModule;
    ConnectionDetector connectionDetector;
    EditText user,pass;
    String usern,passw;
    static String Email;
    AsyncTask<Void, Void, Void> mRegisterTask;
    Button signin,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());
        loginModule = new LoginModule(getApplicationContext(),1);
        connectionDetector = new ConnectionDetector(getApplicationContext());


        if (session.isFirstIn()){
            startActivity(new Intent(getApplicationContext(),MainActivity3.class).putExtra("cat","All"));
            finish();
        }
        user = (EditText)findViewById(R.id.editText3);
        pass = (EditText)findViewById(R.id.editText4);
        TextView tv = (TextView)findViewById(R.id.textView36);
        tv.setText(Html.fromHtml("<u>FORGOT PASSWORD?</u>"));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
            }
        });
        signin = (Button)findViewById(R.id.button);
        signup = (Button) findViewById(R.id.button23);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),Loginscreen.class);
                startActivity(i);
            }
        });
        AccountManager accManager = AccountManager.get(getApplicationContext());
        Account acc[] = accManager.getAccounts();
        for (int i=0;i<=1;i++) {
            Email = acc[0].name;
        }

        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);

        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                DISPLAY_MESSAGE_ACTION));

        final String regId = GCMRegistrar.getRegistrationId(this);

        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(this, SENDER_ID);
        } else {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(this)) {
                // Skips registration.
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = this;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Register on our server
                        // On server creates a new user
                        ServerUtilities.register(context, Email, regId);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            }
        }
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionDetector.isInternet()) {
                    usern = user.getText().toString();
                    passw = pass.getText().toString();
                    loginModule.login(usern,passw,MainActivity.this);
                }else{
                    Toast.makeText(getApplicationContext(),"Please Switch ON your Internet",Toast.LENGTH_SHORT).show();
                }
            }
        });
        TextView skip = (TextView) findViewById(R.id.textView2);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.makeFirst();
                startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                finish();
            }
        });
    }

    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());

            Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();

            // Releasing wake lock
            WakeLocker.release();
        }
    };

    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            Log.e("UnRegister Receiver Error", "> " + e.getMessage());
        }
        super.onDestroy();
    }
    }
