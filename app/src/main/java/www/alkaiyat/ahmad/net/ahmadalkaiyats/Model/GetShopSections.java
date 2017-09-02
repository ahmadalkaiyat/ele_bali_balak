package www.alkaiyat.ahmad.net.ahmadalkaiyats.Model;

/**
 * Created by Ahmad Alkaiyat on 12/26/2016.
 */

import com.google.gson.annotations.SerializedName;

public class GetShopSections {
    @SerializedName("shop_id")
    private Integer ShopId;
    @SerializedName("description")
    private String SectionDescription;
    @SerializedName("section_id")
    private Integer SectionId;

    public  GetShopSections(){

    }

    public GetShopSections(Integer ShopId, String SectionDescription, Integer SectionId) {
        this.ShopId = ShopId;
        this.SectionDescription = SectionDescription;
        this.SectionId=SectionId;
    }

    public Integer getShopId() {
        return ShopId;
    }

    public String getSectionDescription() {
        return SectionDescription;
    }
    public Integer getSectionId(){
        return SectionId;
    }

}
