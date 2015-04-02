package com.android.gowtham.city_hunt;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Post extends Fragment {

    SessionManager session;
    Dialog pDialog;
    String title,desc,cate,city,date,seats,status,inr,contactper,contactnum,email,venue,postby,url,sid,sorg;
    EditText titles,descs,dates,inrs,cper,cnum,cmail,ven,org;
    Spinner cat,citys,seat,stat;
    Button submit,bdate,ok;
    TextView textView;
    Dialog dialog;
    int flag;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_post, container, false);
        dialog = new Dialog(getActivity());
        session = new SessionManager(getActivity());
        dialog.setContentView(R.layout.confirmation);
        textView = (TextView)dialog.findViewById(R.id.textView8);
        ok = (Button)dialog.findViewById(R.id.button22);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==1)
                dialog.dismiss();
                if (flag==2){
                    dialog.dismiss();
                    startActivity(new Intent(getActivity(),MainActivity2.class));
                    getActivity().finish();
                }
            }
        });
        submit = (Button)rootView.findViewById(R.id.button13);
        bdate = (Button)rootView.findViewById(R.id.button21);
        stat = (Spinner)rootView.findViewById(R.id.spinner3);
        seat = (Spinner)rootView.findViewById(R.id.spinner4);

        cat = (Spinner)rootView.findViewById(R.id.spinner);
        citys = (Spinner)rootView.findViewById(R.id.spinner2);
        org = (EditText)rootView.findViewById(R.id.editText21);
        dates = (EditText)rootView.findViewById(R.id.editText11);
        titles = (EditText)rootView.findViewById(R.id.editText9);
        descs = (EditText)rootView.findViewById(R.id.editText10);
        inrs = (EditText)rootView.findViewById(R.id.editText12);
        cper = (EditText)rootView.findViewById(R.id.editText14);
        cnum = (EditText)rootView.findViewById(R.id.editText15);
        cmail = (EditText)rootView.findViewById(R.id.editText16);
        ven = (EditText)rootView.findViewById(R.id.editText13);
        String[] seatss={"No of Seats","below 100","100-500","500-1000","1000+","Unlimited"};
        String[] statuss = {"Entry Status","Free","Paid","Paid - Vary"};
        String[] items = {"Choose Category","Events","Festival","Business","Sports","College"};
        String[] cities = {"Select Your City","Bengaluru","Mumbai","Chennai","Delhi","Hyderabad","Gurgaon","Coimbatore", "Kolkata","Pune","Chandigarh","Cochin","Abohar","Adipur","Agartala","Agra","Ahmedgarh","Ahmednagar","Ajmer","Akola","Allahabad","Ambala","Amristar","Anand","Anchal","Angamaly","Ariyalur","Arni","Aruppukotttai","Aurangabad","Balaghat","Barnala","Banga","Banswara","Bareilly","Bathinda","Beed","Belgaum","Bharuch","Bhavnagar","Bhilai","Bhilwara","Bhiwadi","Bhopal","Bhubaneshwar","Bidar","Bijnor","Bikaner","Bilaspur","Boisar","Bolpur","Burdwan","Champa","Chandrapur","Chhindwara","Chinnamandem","Coorg","Cuddalore","Darjeeling","Dausa","Davengere","Dehradun","Dhanbad(Jharkand)","Dharuhera","Dhule","Dibrugarh","Dindigul","Durg","Durgapur","Erode","Gandhinagar","Goa","Gorakhpur","Gulbarga","Guntur","Guwahati","Gwalior","Haldia","Hamirpur(HP)","Haridwar","Hisar","Hoshiarpur","Hubli","Idar","Indore","Jalandhar","Jalgaon","Jabalpur","Jammu","Jamnagar","Jamshedpur","Jangareddy Gudem","Jhajjir","Jodhpur","Junagadh","Kakinada","Kanchipuram","Kanpur","Karimnagar","Kathua","Khammam","Khandwa","Khanna","Khopoli","Kishangarh","Kolhapur","Kollam","Korba","Kota","Kotakpura","Kottayam","Kumbakonam","Kundli","Kurnool","Kurukshetra","Latur","Lonavala","Lucknow","Ludhiana","Madurai","Mahad","Malegaon","Manali","Mangalore","Manipal","Mansa","Mathura","Mavellikara","Mettupalayam","Meerut","Moga","Mohali","Moradabad","Mysore","Nadiad","Nagpur","Nanded","Nashik","Navasari","Nawanshahr","Neemrana","Neemuch","Nizamabad","Palanpur","Panchgani","Panipat","Palghar","Parbhani","Parvathipuram","Patan","Pathankot","Patiala","Patna","Patran","Peddapalli","Pen","Pondicherry","Pudhukottai","Raipur","Rajahmundry","Rajkot","Ramnagar","Ranchi","Rongjeng","Ratlam","Renukoot","Rewari","Rishikesh","Rohtak","Rudrapur","Rupnagar","Salem","Sangli","Sangrur","Sehore","Siddapet","Sikar","Shillong","Shimla","Shivpuri","Silchar","Siliguri","Sirsa","Solapur","Sri Ganganagar","Surat","Thenkasi","Thalayolaparambu","Tilda Neora","Tinsukia","Thrissur","Tirunelveli","Tirupur","Trichy","Yrivandram","Tumkur","Udaipur","Ujjain","Vadodara","Valsad","Vapi","Varanasi","Vellore","Vijayawada","Vizag","Vizianagaram","Warangal","Wardha"};

        cat.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.spinner, items));
        citys.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner,cities));
        stat.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner,statuss));
        seat.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner,seatss));

        if(!session.isIns()) {
            dialog("Thanks for your interest in us.Post and share all your events with the world. \n\n Note:your post will be active only upon verification. Usually it takes 6hours,Thanks.", "Hey,welcome!",1);
            session.makeIns();
        }

        HashMap<String, String> user = session.getUserDetails();

        // name
        postby = user.get(SessionManager.KEY_NAME);
        sid = user.get(SessionManager.KEY_ID);


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
                dates.setText(mydate);
            }
        };
        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                Dialog dialog1 = new DatePickerDialog(getActivity(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                dialog1.show();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titles.getText().toString();
                sorg = org.getText().toString();
                desc = descs.getText().toString();
                venue = ven.getText().toString();
                date = dates.getText().toString();
                contactper = cper.getText().toString();
                contactnum = cnum.getText().toString();
                email = cmail.getText().toString();
                inr = inrs.getText().toString();
                if (title.length()==0)
                    Toast.makeText(getActivity(),"Enter the title of the Event",Toast.LENGTH_SHORT).show();
                else{
                    if (sorg.length()==0)
                        Toast.makeText(getActivity(),"Enter the Organisation name",Toast.LENGTH_SHORT).show();
                    else{
                    if (desc.length()==0)
                        Toast.makeText(getActivity(),"Enter some words about the event",Toast.LENGTH_SHORT).show();
                    else{
                        if (cate.equals("Choose Category"))
                            Toast.makeText(getActivity(),"Choose the category",Toast.LENGTH_SHORT).show();
                        else{
                            if (city.equals("Select Your City"))
                                Toast.makeText(getActivity(),"Enter the city",Toast.LENGTH_SHORT).show();
                            else {
                                if (status.equals("Entry Status"))
                                    Toast.makeText(getActivity(),"Entry status of the event",Toast.LENGTH_SHORT).show();
                                else{
                                    if (date.length()==0)
                                        Toast.makeText(getActivity(),"Enter the date",Toast.LENGTH_SHORT).show();
                                    else {
                                        if (venue.length() == 0)
                                            Toast.makeText(getActivity(), "Enter the venue of the event", Toast.LENGTH_SHORT).show();
                                        else {
                                            if (seats.equals("No of Seats"))
                                                Toast.makeText(getActivity(), "Select No of seats", Toast.LENGTH_SHORT).show();
                                            else {
                                                if (contactper.length() == 0)
                                                    Toast.makeText(getActivity(), "Enter Contact person", Toast.LENGTH_SHORT).show();
                                                else {
                                                    if (Loginscreen.isEmailValid(email)) {
                                                        if (contactnum.length() == 0) {
                                                            Toast.makeText(getActivity(), "Enter the Contact number", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            new Asyntask().execute();
                                                        }
                                                    } else
                                                        Toast.makeText(getActivity(), "Enter Contact Email", Toast.LENGTH_SHORT).show();
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
        return rootView;
    }

    class Asyntask extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            pDialog = new Dialog(getActivity());
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
            pDialog.setContentView(R.layout.progress);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            final String username = "cityhuntcc@gmail.com"; //your email id
            final String password = "aspirelife"; //yourgmailpassword
            String sub = "Confirm registration";
            url = "http://honeykumar.byethost17.com/demo.php?id=" + sid + "&org=" + sorg.replaceAll(" ",".").trim() + "&city=" + city.replaceAll(" ",".").trim() + "&title=" + title.replaceAll(" ",".").trim()+ "&cat=" + cate.trim() + "&date=" + date.trim() + "&des=" + desc.replaceAll(" ",".").trim()+ "&ven=" + venue.replaceAll(" ",".").trim()+ "&per=" + contactper.replaceAll(" ",".").trim()+ "&con=" + contactnum.trim()+ "&seat=" + seats.replaceAll(" ",".").trim()+ "&status=" + inr.replaceAll(" ",".").trim()+ "&email=" + email.trim()+ "&post=" + postby.replaceAll(" ",".").trim();
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
                pDialog.dismiss();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        dialog("Your post will be active soon,Thank you.","Success",2);
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

    public void dialog(String text,String title,int _flag){
        flag = _flag;
        textView.setText(text);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
        dialog.setTitle(title);
        dialog.show();
    }



}