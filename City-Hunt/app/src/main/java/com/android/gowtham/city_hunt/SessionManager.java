package com.android.gowtham.city_hunt;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;
	
	// Editor for Shared preferences
	Editor editor;
	
	// Context
	Context _context;
	
	// Shared pref mode
	int PRIVATE_MODE = 0;
	
	// Sharedpref file name
	private static final String PREF_NAME = "City-Hunt";

	
	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";

    private static final String CITY = "City";

    private static final String IS_FIRST = "IsFirst";

    private static final String IS_INS = "IsINS";

    private static final String IS_MY = "Ismy";
	
	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "name";

    public static final String KEY_ID = "id";
	
	// Email address (make variable public to access from outside)
	public static final String KEY_EMAIL = "email";
	
	// Constructor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	/**
	 * Create login session
	 * */
	public void createLoginSession(String name, String email,int id){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);

        editor.putInt(KEY_ID, id);
		
		// Storing name in pref
		editor.putString(KEY_NAME, name);
		
		// Storing email in pref
		editor.putString(KEY_EMAIL, email);
		
		// commit changes
		editor.commit();
	}
    public void makeFirst(){
        editor.putBoolean(IS_FIRST, true);
        editor.commit();
    }

    public void makeIns(){
        editor.putBoolean(IS_INS,true);
        editor.commit();
    }

    public void inCity(String city){
        editor.putString(CITY,city);
        editor.commit();
    }

    public void makemy(){
        editor.putBoolean(IS_MY,true);
        editor.commit();
    }


	
	
	
	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user;
        user = new HashMap<>();

        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

		user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(KEY_ID, String.valueOf(pref.getInt(KEY_ID, 0)));

		return user;
	}
	
	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
        makeFirst();
	}
	
	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn(){

        return pref.getBoolean(IS_LOGIN, false);
	}

    public boolean isFirstIn(){
        return pref.getBoolean(IS_FIRST, false);
    }

    public boolean isIns(){
        return pref.getBoolean(IS_INS, false);
    }

    public boolean isMy(){
        return pref.getBoolean(IS_MY,false);
    }
}
