package com.android.gowtham.city_hunt;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.android.gowtham.city_hunt.R.drawable.ic_action_edit;
import static com.android.gowtham.city_hunt.R.drawable.ic_action_search;

public class MainActivity2 extends ActionBarActivity {

    SessionManager session;
    ConnectionDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF00E7E7")));
        actionBar.setTitle("  "+"CITY-HUNT");

        detector = new ConnectionDetector(getApplicationContext());

        session = new SessionManager(getApplicationContext());
        if (detector.isInternet()) {

        if (!session.isFirstIn()) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        } else {


            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            actionBar.addTab(actionBar.newTab().setText("Search").setIcon(ic_action_search).setTabListener(new TabListener<>(this, "search", Search.class)));

            if (session.isLoggedIn()) {
                actionBar.addTab(actionBar.newTab().setText("Post").setIcon(ic_action_edit).setTabListener(new TabListener<>(this, "Post", Post.class)));
            } else {
                actionBar.addTab(actionBar.newTab().setText("Post").setIcon(ic_action_edit).setTabListener(new TabListener<>(this, "Login", Login.class)));
            }
            actionBar.addTab(actionBar.newTab().setText("Popular").setIcon(R.drawable.popular).setTabListener(new TabListener<>(this,"Popular",Popular.class)));
        }
            }else{
                TextView in = (TextView)findViewById(R.id.textView30);
                TextView aw = (TextView)findViewById(R.id.textView31);
                ImageView imageView = (ImageView)findViewById(R.id.imageView14);
                imageView.setVisibility(View.VISIBLE);
                aw.setVisibility(View.VISIBLE);
                in.setVisibility(View.VISIBLE);
                Button button = (Button)findViewById(R.id.button18);
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (detector.isInternet()) {
                            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                            finish();
                        }
                    }
                });
            }

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(session.isLoggedIn()) {
            getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        }
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
            session.logoutUser();
            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
