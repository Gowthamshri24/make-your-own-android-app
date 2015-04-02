package com.android.gowtham.city_hunt;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class PickRegion extends ActionBarActivity {

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_region);

        session = new SessionManager(getApplicationContext());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ed4255")));
        String[] topcities = {"Bengaluru","Mumbai","Chennai","Delhi","Hyderabad","Gurgaon","Coimbatore", "Kolkata","Pune","Chandigarh","Cochin"};
        String[] citi = {"Abohar","Adipur","Agartala","Agra","Ahmedgarh","Ahmednagar","Ajmer","Akola","Allahabad","Ambala","Amristar","Anand","Anchal","Angamaly","Ariyalur","Arni","Aruppukotttai","Aurangabad","Balaghat","Barnala","Banga","Banswara","Bareilly","Bathinda","Beed","Belgaum","Bharuch","Bhavnagar","Bhilai","Bhilwara","Bhiwadi","Bhopal","Bhubaneshwar","Bidar","Bijnor","Bikaner","Bilaspur","Boisar","Bolpur","Burdwan","Champa","Chandrapur","Chhindwara","Chinnamandem","Coorg","Cuddalore","Darjeeling","Dausa","Davengere","Dehradun","Dhanbad(Jharkand)","Dharuhera","Dhule","Dibrugarh","Dindigul","Durg","Durgapur","Erode","Gandhinagar","Goa","Gorakhpur","Gulbarga","Guntur","Guwahati","Gwalior","Haldia","Hamirpur(HP)","Haridwar","Hisar","Hoshiarpur","Hubli","Idar","Indore","Jalandhar","Jalgaon","Jabalpur","Jammu","Jamnagar","Jamshedpur","Jangareddy Gudem","Jhajjir","Jodhpur","Junagadh","Kakinada","Kanchipuram","Kanpur","Karimnagar","Kathua","Khammam","Khandwa","Khanna","Khopoli","Kishangarh","Kolhapur","Kollam","Korba","Kota","Kotakpura","Kottayam","Kumbakonam","Kundli","Kurnool","Kurukshetra","Latur","Lonavala","Lucknow","Ludhiana","Madurai","Mahad","Malegaon","Manali","Mangalore","Manipal","Mansa","Mathura","Mavellikara","Mettupalayam","Meerut","Moga","Mohali","Moradabad","Mysore","Nadiad","Nagpur","Nanded","Nashik","Navasari","Nawanshahr","Neemrana","Neemuch","Nizamabad","Palanpur","Panchgani","Panipat","Palghar","Parbhani","Parvathipuram","Patan","Pathankot","Patiala","Patna","Patran","Peddapalli","Pen","Pondicherry","Pudhukottai","Raipur","Rajahmundry","Rajkot","Ramnagar","Ranchi","Rongjeng","Ratlam","Renukoot","Rewari","Rishikesh","Rohtak","Rudrapur","Rupnagar","Salem","Sangli","Sangrur","Sehore","Siddapet","Sikar","Shillong","Shimla","Shivpuri","Silchar","Siliguri","Sirsa","Solapur","Sri Ganganagar","Surat","Thenkasi","Thalayolaparambu","Tilda Neora","Tinsukia","Thrissur","Tirunelveli","Tirupur","Trichy","Yrivandram","Tumkur","Udaipur","Ujjain","Vadodara","Valsad","Vapi","Varanasi","Vellore","Vijayawada","Vizag","Vizianagaram","Warangal","Wardha"};
        ListView city = (ListView)findViewById(R.id.listView4);
        ListView cities = (ListView)findViewById(R.id.listView5);
        city.setAdapter(new ArrayAdapter<>(this, R.layout.listview, topcities));
        cities.setAdapter(new ArrayAdapter<>(this,R.layout.listview,citi));

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView title= (TextView)view.findViewById(R.id.listitem);
                String s = title.getText().toString();
                session.inCity(s);
                finish();
            }
        });

        cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView title= (TextView)view.findViewById(R.id.listitem);
                String s = title.getText().toString();
                session.inCity(s);
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home ) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
