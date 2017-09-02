package www.alkaiyat.ahmad.net.ahmadalkaiyats.Model;

/**
 * Created by Ahmad Alkaiyat on 1/7/2017.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetShopBreanchesResponse {
    @SerializedName("results")
    private List<GetShopBranches> results;

    public List<GetShopBranches> getResults() {
        return results;
    }

    public void setResults(List<GetShopBranches> results) {
        this.results = results;
    }

}
