package www.alkaiyat.ahmad.net.ahmadalkaiyats.Model;

/**
 * Created by Ahmad Alkaiyat on 1/7/2017.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetShopItemsResponse {
    @SerializedName("results")
    private List<GetShopItems> results;

    public List<GetShopItems> getResults() {
        return results;
    }

    public void setResults(List<GetShopItems> results) {
        this.results = results;
    }

}
