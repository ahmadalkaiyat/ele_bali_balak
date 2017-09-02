package www.alkaiyat.ahmad.net.ahmadalkaiyats.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ahmad Alkaiyat on 12/26/2016.
 */
public class GetShopSectionsResponse {
    @SerializedName("results")
    private List<GetShopSections> results;

    public List<GetShopSections> getResults() {
        return results;
    }

    public void setResults(List<GetShopSections> results) {
        this.results = results;
    }

}


