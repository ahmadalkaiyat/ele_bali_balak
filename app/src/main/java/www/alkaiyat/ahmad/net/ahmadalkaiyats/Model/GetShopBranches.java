package www.alkaiyat.ahmad.net.ahmadalkaiyats.Model;

/**
 * Created by Ahmad Alkaiyat on 1/7/2017.
 */
import com.google.gson.annotations.SerializedName;

public class GetShopBranches {
    @SerializedName("shop_id")
    private String ShopId;
    @SerializedName("sequence")
    private String sequence;
    @SerializedName("branch_name")
    private String BranchNAme;
    @SerializedName("Branch_address")
    private String BranchAddress;
    @SerializedName("Branch_phone_num")
    private String BranchPhoneNumber;
    @SerializedName("Branch_status")
    private String BranchStatus;
    @SerializedName("creation_date")
    private  String CreationDate;
    @SerializedName("Latitude")
    private  String Latitude;
    @SerializedName("Longitude")
    private  String Longitude;
    @SerializedName("Altitude")
    private  String Altitude;

    public GetShopBranches(String ShopId , String sequence, String BranchNAme , String BranchAddress
    , String BranchPhoneNumber , String BranchStatus , String CreationDate , String Latitude , String Longitude
    , String Altitude){
        this.ShopId = ShopId;
        this.sequence =sequence;
        this.BranchNAme =BranchNAme;
        this.BranchAddress = BranchAddress;
        this.BranchPhoneNumber=BranchPhoneNumber;
        this.BranchStatus=BranchStatus;
        this.CreationDate=CreationDate;
        this.Latitude=Latitude;
        this.Longitude=Longitude;
        this.Altitude=Altitude;

    }

    public String getShopId(){
        return ShopId;
    }

    public String getSequence ()
    {
        return sequence;
    }
     public  String getBranchNAme()
     {
         return  BranchNAme;
     }

    public String getLatitude()
    {
        return Latitude;
    }
    public  String  getLongitude()
    {
        return  Longitude;
    }
    public String getBranchPhoneNumber()
    {
        return BranchPhoneNumber;
    }
    public  String  getBranchAddress()
    {
        return  BranchAddress;
    }

}
