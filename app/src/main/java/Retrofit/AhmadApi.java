package Retrofit;

import android.util.Log;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;

/**
 * Created by Ahmad Alkaiyat on 1/30/2016.
 */
public class AhmadApi {

    private static String HostAddress = "http://192.168.1.27/WEBSERVICE/";
    private static String Categories_img_path = HostAddress+"images/Categories/";
    private static String Shops_img_path = HostAddress+"images/ShopsLogo/";

    public AhmadApi(){
        this.HostAddress=HostAddress;
        this.Categories_img_path = Categories_img_path;
        this.Shops_img_path = Shops_img_path;
    }

    public String getHostAddress()
    {
        return HostAddress;
    }

    public String getCategories_img_path()
    {
        return Categories_img_path;
    }
    public String getShops_img_path()
    {
        return Shops_img_path;
    }

    public String getShops_img_items_path(String shopId , String SectionId)
    {
        return  HostAddress+"images/Shops/"+shopId+"/"+SectionId+"/";
    }

    public static ActivitiesRestInterface getActivitiesRestClient() {
        ActivitiesRestInterface sActivitiesService = null;
        if (sActivitiesService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HostAddress)
                    .setConverter(new GsonConverter(utils.getGSONObject()))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setLog(new AndroidLog("retrofit logs"))
                    .build();
            sActivitiesService = restAdapter.create(ActivitiesRestInterface.class);
        }
        return sActivitiesService;
    }
}
