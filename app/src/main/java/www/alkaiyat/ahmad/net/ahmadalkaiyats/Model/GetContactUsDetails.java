package www.alkaiyat.ahmad.net.ahmadalkaiyats.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmad Alkaiyat on 2/5/2017.
 */

public class GetContactUsDetails {
    @SerializedName("phone_number")
    private String PhoneNumber;

    @SerializedName("email_address")
    private String EmailAddress;

    @SerializedName("Privacy_Policy")
    private String PrivacyPolicy;

    private String aboutUs;

    GetContactUsDetails(String ShopAbout){
        this.PhoneNumber = PhoneNumber;
        this.EmailAddress=EmailAddress;
        this.PrivacyPolicy=PrivacyPolicy;
        this.aboutUs=aboutUs;
    }

    public String getShopAbout() {
        return aboutUs;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public String getPrivacyPolicy() {
        return PrivacyPolicy;
    }

}
