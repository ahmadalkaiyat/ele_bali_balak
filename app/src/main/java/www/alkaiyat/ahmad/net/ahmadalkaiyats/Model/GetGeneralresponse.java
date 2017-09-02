package www.alkaiyat.ahmad.net.ahmadalkaiyats.Model;

/**
 * Created by Ahmad Alkaiyat on 12/26/2016.
 */

import com.google.gson.annotations.SerializedName;

public class GetGeneralresponse {
    private Integer success,shop_id;
    private String message;

    public GetGeneralresponse(Integer GroupId, String GroupDesc , Integer shop_id) {
        this.success = success;
        this.message = message;
        this.shop_id = shop_id;
    }

    public Integer getSuccess() {
        return success;
    }

    public String getmessage() {
        return message;
    }

    public Integer getShop_id() {
        return shop_id;
    }



}
