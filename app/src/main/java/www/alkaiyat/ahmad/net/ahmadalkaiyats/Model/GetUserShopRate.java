package www.alkaiyat.ahmad.net.ahmadalkaiyats.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmad Alkaiyat on 2/5/2017.
 */

public class GetUserShopRate {
    private String rate;

    private String rate_note;


    GetUserShopRate(String rate , String rate_note){
        this.rate = rate;
        this.rate_note=rate_note;
    }

    public String getrate() {
        return rate;
    }

    public String getrate_note() {
        return rate_note;
    }


}
