package www.alkaiyat.ahmad.net.ahmadalkaiyats.Model;

/**
 * Created by Ahmad Alkaiyat on 1/7/2017.
 */
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetTrending {
    @SerializedName("shop_id")
    private Integer ShopId;
    @SerializedName("shop_name")
    private String ShopName;
    @SerializedName("is_on_my_list")
    private Integer IsOnMyList;
    @SerializedName("is_on_sale")
    private Integer IsOnSale;


    public GetTrending (Integer ShopId , String shopName , Integer IsOnMyList, Integer IsOnSale){
        this.ShopId = ShopId;
        this.ShopName =shopName;
        this.IsOnMyList =IsOnMyList;
        this.IsOnSale =IsOnSale;
    }

    public Integer GetShopId(){
        return ShopId;
    }

    public String GetShopName()
    {
        return ShopName;
    }

    public Integer IsOnMyList()
    {
        return IsOnMyList;
    }
    public Integer IsOnSale()
    {
        return IsOnSale;
    }
}
