package com.android.gowtham.city_hunt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.android.gowtham.city_hunt.R.layout.fragment_login;


    public class Login extends Fragment {

        public Button login;
        LoginModule loginModule;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            rootView = inflater.inflate(fragment_login, container, false);
            loginModule = new LoginModule(getActivity(),2);
            login = (Button) rootView.findViewById(R.id.button11);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                            loginModule.inLogin(getActivity());
                }

            });

                return rootView;
        }

    }