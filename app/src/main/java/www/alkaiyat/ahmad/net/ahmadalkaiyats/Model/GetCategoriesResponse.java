package www.alkaiyat.ahmad.net.ahmadalkaiyats.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ahmad Alkaiyat on 12/26/2016.
 */
public class GetCategoriesResponse {
    @SerializedName("results")
    private List<GetCategories> results;

    public List<GetCategories> getResults() {
        return results;
    }

    public void setResults(List<GetCategories> results) {
        this.results = results;
    }

}


