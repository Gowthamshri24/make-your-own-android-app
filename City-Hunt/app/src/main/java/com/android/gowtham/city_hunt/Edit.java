package com.android.gowtham.city_hunt;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Edit extends ActionBarActivity {

    String title,venue,date,desc,contactnum,contactper,email,url,org,city,seats,status,cate;
    Dialog pDialog,dialog;
    TextView tit,textView;
    EditText ven,dat,des,contnum,contper,contemail,sorg,inrs;
    Button update,set,ok;
    Spinner stat,seat,cat,citys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dialog = new Dialog(Edit.this);
        dialog.setContentView(R.layout.confirmation);
        textView = (TextView)dialog.findViewById(R.id.textView8);
        ok = (Button)dialog.findViewById(R.id.button22);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    dialog.dismiss();
                    startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                    finish();

            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ed4255")));


        Intent intent = getIntent();
        title=intent.getStringExtra("title");

        venue=intent.getStringExtra("venue");
        org = intent.getStringExtra("org");
        date=intent.getStringExtra("date");
        desc=intent.getStringExtra("des");
        contactnum=intent.getStringExtra("cnum");
        contactper=intent.getStringExtra("cper");
        email=intent.getStringExtra("cmail");

        stat = (Spinner)findViewById(R.id.spinner3);
        seat = (Spinner)findViewById(R.id.spinner4);

        cat = (Spinner)findViewById(R.id.spinner);
        citys = (Spinner)findViewById(R.id.spinner2);

        inrs = (EditText)findViewById(R.id.editText12);

        sorg = (EditText)findViewById(R.id.editText21);
        sorg.setText(org);
        tit = (TextView) findViewById(R.id.editText9);
        tit.setText(title);
        ven = (EditText) findViewById(R.id.editText13);
        ven.setText(venue);
        des = (EditText) findViewById(R.id.editText10);
        des.setText(desc);
        contnum = (EditText) findViewById(R.id.editText15);
        contnum.setText(contactnum);
        contper = (EditText) findViewById(R.id.editText14);
        contper.setText(contactper);
        contemail = (EditText) findViewById(R.id.editText16);
        contemail.setText(email);
        set = (Button)findViewById(R.id.button21);
        dat = (EditText) findViewById(R.id.editText11);
        dat.setText(date);
        String[] seatss={"No of Seats","> 100","100-500","500-1000","1000+","Unlimited"};
        String[] statuss = {"Entry Status","Free","Paid","Paid - Vary"};
        String[] items = {"Choose Category","Events","Festival","Business","Sports","College"};
        String[] cities = {"Select Your City","Bengaluru","Mumbai","Chennai","Delhi","Hyderabad","Gurgaon","Coimbatore", "Kolkata","Pune","Chandigarh","Cochin","Abohar","Adipur","Agartala","Agra","Ahmedgarh","Ahmednagar","Ajmer","Akola","Allahabad","Ambala","Amristar","Anand","Anchal","Angamaly","Ariyalur","Arni","Aruppukotttai","Aurangabad","Balaghat","Barnala","Banga","Banswara","Bareilly","Bathinda","Beed","Belgaum","Bharuch","Bhavnagar","Bhilai","Bhilwara","Bhiwadi","Bhopal","Bhubaneshwar","Bidar","Bijnor","Bikaner","Bilaspur","Boisar","Bolpur","Burdwan","Champa","Chandrapur","Chhindwara","Chinnamandem","Coorg","Cuddalore","Darjeeling","Dausa","Davengere","Dehradun","Dhanbad(Jharkand)","Dharuhera","Dhule","Dibrugarh","Dindigul","Durg","Durgapur","Erode","Gandhinagar","Goa","Gorakhpur","Gulbarga","Guntur","Guwahati","Gwalior","Haldia","Hamirpur(HP)","Haridwar","Hisar","Hoshiarpur","Hubli","Idar","Indore","Jalandhar","Jalgaon","Jabalpur","Jammu","Jamnagar","Jamshedpur","Jangareddy Gudem","Jhajjir","Jodhpur","Junagadh","Kakinada","Kanchipuram","Kanpur","Karimnagar","Kathua","Khammam","Khandwa","Khanna","Khopoli","Kishangarh","Kolhapur","Kollam","Korba","Kota","Kotakpura","Kottayam","Kumbakonam","Kundli","Kurnool","Kurukshetra","Latur","Lonavala","Lucknow","Ludhiana","Madurai","Mahad","Malegaon","Manali","Mangalore","Manipal","Mansa","Mathura","Mavellikara","Mettupalayam","Meerut","Moga","Mohali","Moradabad","Mysore","Nadiad","Nagpur","Nanded","Nashik","Navasari","Nawanshahr","Neemrana","Neemuch","Nizamabad","Palanpur","Panchgani","Panipat","Palghar","Parbhani","Parvathipuram","Patan","Pathankot","Patiala","Patna","Patran","Peddapalli","Pen","Pondicherry","Pudhukottai","Raipur","Rajahmundry","Rajkot","Ramnagar","Ranchi","Rongjeng","Ratlam","Renukoot","Rewari","Rishikesh","Rohtak","Rudrapur","Rupnagar","Salem","Sangli","Sangrur","Sehore","Siddapet","Sikar","Shillong","Shimla","Shivpuri","Silchar","Siliguri","Sirsa","Solapur","Sri Ganganagar","Surat","Thenkasi","Thalayolaparambu","Tilda Neora","Tinsukia","Thrissur","Tirunelveli","Tirupur","Trichy","Yrivandram","Tumkur","Udaipur","Ujjain","Vadodara","Valsad","Vapi","Varanasi","Vellore","Vijayawada","Vizag","Vizianagaram","Warangal","Wardha"};

        cat.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.spinner, items));
        citys.setAdapter(new ArrayAdapter<>(getApplicationContext(),R.layout.spinner,cities));
        stat.setAdapter(new ArrayAdapter<>(getApplicationContext(),R.layout.spinner,statuss));
        seat.setAdapter(new ArrayAdapter<>(getApplicationContext(),R.layout.spinner,seatss));

        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.spinnerview);
                cate = textView.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        citys.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.spinnerview);
                city = textView.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        stat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.spinnerview);
                status = textView.getText().toString();
                if (status.equals("Paid")){
                    inrs.setVisibility(View.VISIBLE);
                    inrs.setText("");
                }else if (status.equals("Free") || status.equals("Paid - Vary")){
                    inrs.setVisibility(View.INVISIBLE);
                    inrs.setText(status);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        seat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view.findViewById(R.id.spinnerview);
                seats = textView.getText().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear +1;

                String mydate = year + "-"+monthOfYear+"-"+dayOfMonth;
                dat.setText(mydate);
            }
        };
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                Dialog dialog1 = new DatePickerDialog(getApplicationContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog1.show();

            }
        });

        update = (Button) findViewById(R.id.button13);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = tit.getText().toString();
                org = sorg.getText().toString();
                desc = des.getText().toString();
                venue = ven.getText().toString();
                date = dat.getText().toString();
                contactper = contper.getText().toString();
                contactnum = contnum.getText().toString();
                email = contemail.getText().toString();
                status = inrs.getText().toString();
                if (title.length()==0)
                    Toast.makeText(getApplicationContext(), "Enter the title of the Event", Toast.LENGTH_SHORT).show();
                else{
                    if (sorg.length()==0)
                        Toast.makeText(getApplicationContext(),"Enter the Organisation name",Toast.LENGTH_SHORT).show();
                    else{
                        if (desc.length()==0)
                            Toast.makeText(getApplicationContext(),"Enter some words about the event",Toast.LENGTH_SHORT).show();
                        else{
                            if (cate.equals("Choose Category"))
                                Toast.makeText(getApplicationContext(),"Choose the category",Toast.LENGTH_SHORT).show();
                            else{
                                if (city.equals("Select Your City"))
                                    Toast.makeText(getApplicationContext(),"Enter the city",Toast.LENGTH_SHORT).show();
                                else {
                                    if (status.equals("Entry Status"))
                                        Toast.makeText(getApplicationContext(),"Entry status of the event",Toast.LENGTH_SHORT).show();
                                    else{
                                        if (date.length()==0)
                                            Toast.makeText(getApplicationContext(),"Enter the date",Toast.LENGTH_SHORT).show();
                                        else {
                                            if (venue.length() == 0)
                                                Toast.makeText(getApplicationContext(), "Enter the venue of the event", Toast.LENGTH_SHORT).show();
                                            else {
                                                if (seats.equals("No of Seats"))
                                                    Toast.makeText(getApplicationContext(), "Select No of seats", Toast.LENGTH_SHORT).show();
                                                else {
                                                    if (contactper.length() == 0)
                                                        Toast.makeText(getApplicationContext(), "Enter Contact person", Toast.LENGTH_SHORT).show();
                                                    else {
                                                        if (Loginscreen.isEmailValid(email)) {
                                                            if (contactnum.length() == 0) {
                                                                Toast.makeText(getApplicationContext(), "Enter the Contact number", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                new Asyntask().execute();
                                                            }
                                                        } else
                                                            Toast.makeText(getApplicationContext(), "Enter Contact Email", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        });


    }

    class Asyntask extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new Dialog(Edit.this);
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
            pDialog.setContentView(R.layout.progress);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            final String username = "cityhuntcc@gmail.com";
            final String password = "aspirelife";
            String sub = "Confirm registration";
            url = "http://honeykumar.byethost17.com/edit.php?&org=" + org.replaceAll(" ",".").trim() + "&city=" + city.replaceAll(" ",".").trim() + "&title=" + title.replaceAll(" ",".").trim()+ "&cat=" + cate.trim() + "&date=" + date.trim() + "&des=" + desc.replaceAll(" ",".").trim()+ "&ven=" + venue.replaceAll(" ",".").trim()+ "&per=" + contactper.replaceAll(" ",".").trim()+ "&con=" + contactnum.trim()+ "&seat=" + seats.replaceAll(" ",".").trim()+ "&status=" + status.replaceAll(" ",".").trim()+ "&email=" + email.trim();
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
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("cityhuntcc@gmail.com"));
                message.setSubject(sub);
                message.setText(url);
                Transport.send(message);
                Edit.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog("Edit was successful, Your post will be updated shortly","Great!");
                    }
                });

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
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
