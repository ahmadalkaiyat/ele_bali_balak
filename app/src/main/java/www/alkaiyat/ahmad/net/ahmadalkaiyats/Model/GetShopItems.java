package www.alkaiyat.ahmad.net.ahmadalkaiyats.Model;

/**
 * Created by Ahmad Alkaiyat on 1/7/2017.
 */
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetShopItems implements Serializable {
    @SerializedName("shop_id")
    private Integer ShopId;
    @SerializedName("section_id")
    private Integer SectionID;
    @SerializedName("section_description")
    private String SectionDescription;
    @SerializedName("item_id")
    private Integer ItemId;
    @SerializedName("item_name")
    private String ItemName;
    @SerializedName("Item_desctiption")
    private String ItemDescription;


    public GetShopItems(Integer ShopId , Integer SectionID , String SectionDescription ,Integer ItemId, String ItemName, String ItemDescription){
        this.ShopId = ShopId;
        this.SectionID=SectionID;
        this.SectionDescription=SectionDescription;
        this.ItemId=ItemId;
        this.ItemName=ItemName;
        this.ItemDescription=ItemDescription;
    }

    public GetShopItems  (Integer ShopId)
    {
        this.ShopId = ShopId;
    }

    public Integer getShopId(){
        return ShopId;
    }
    public Integer SectionID(){
        return SectionID;
    }

    public String getSectionDescription()
    {
        return SectionDescription;
    }

    public Integer getItemId(){return ItemId;}
    public  String getItemName(){return ItemName;}
    public String getItemDescription(){return ItemDescription;}
}
