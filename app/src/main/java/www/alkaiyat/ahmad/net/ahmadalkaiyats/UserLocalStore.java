package www.alkaiyat.ahmad.net.ahmadalkaiyats;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Locale;

/**
 * Created by Ahmad Alkaiyat on 1/30/2016.
 */
public class UserLocalStore {
    public static final String sp_name="userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(sp_name, 0);
    }


    /*******the below part check if there a user logged in*********/
    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("loggedIn",false)==true){
            return true;
        }else{
            return false;
        }
    }

    /*******the below part check if thee user want to login*********/
    public boolean ifWantLogin(){
        if(userLocalDatabase.getBoolean("wantlogin",false)==true){
            return true;
        }else{
            return false;
        }
    }


    /*******the below to get the logged in User*********/
    public String getLoggedUser(){
        return userLocalDatabase.getString("username","0");
    }

    public String getUser_id(){
        return userLocalDatabase.getString("user_id","0");
    }

    public String getUserLat()
    {
        return userLocalDatabase.getString("UserLat","");
    }
    public String getUserLong()
    {
        return userLocalDatabase.getString("UserLong","");
    }

    public String getUserCountry(Context context)
    {
        return  userLocalDatabase.getString("UserCountry",get_country(context));
    }

    public String getUserOperCountry(Context context)
    {
        return  userLocalDatabase.getString("UserOperCountry",get_country(context));
    }

    public  void setUserCountry(String UserCountry){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("UserCountry", UserCountry);
        spEditor.commit();
    }
    public  void setUserOperCountry(String UserOperCountry){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("UserOperCountry", UserOperCountry);
        spEditor.commit();
    }

    public static String get_country(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        }
        catch (Exception e) { }
        return null;
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.putString("user_id", String.valueOf(user.user_id));
        spEditor.commit();
    }


    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public void setWanLogIn(boolean wantlogin) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("wantlogin", wantlogin);
        spEditor.commit();
    }

    public  void setUserLocation(String Lat,String Long)
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("UserLat", Lat);
        spEditor.putString("UserLong", Long);
        spEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }



}
