package www.alkaiyat.ahmad.net.ahmadalkaiyats.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmad Alkaiyat on 2/5/2017.
 */

public class GetShopContactDetails {
    @SerializedName("shop_about")
    private String ShopAbout;

    @SerializedName("shop_phone_number")
    private String ShopPhoneNumber;

    @SerializedName("shop_email_address")
    private String ShopEmailAddress;

    GetShopContactDetails(String ShopAbout,String ShopEmailAddress , String ShopPhoneNumber  ){
        this.ShopAbout = ShopAbout;
        this.ShopEmailAddress=ShopEmailAddress;
        this.ShopPhoneNumber=ShopPhoneNumber;
    }

    public String getShopAbout() {
        return ShopAbout;
    }

    public String getShopPhoneNumber() {
        return ShopPhoneNumber;
    }

    public String getShopEmailAddress() {
        return ShopEmailAddress;
    }

}
