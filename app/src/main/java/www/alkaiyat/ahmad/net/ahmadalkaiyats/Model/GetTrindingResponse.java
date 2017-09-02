package www.alkaiyat.ahmad.net.ahmadalkaiyats.Model;

/**
 * Created by Ahmad Alkaiyat on 1/7/2017.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTrindingResponse {
    @SerializedName("results")
    private List<GetTrending> results;

    public List<GetTrending> getResults() {
        return results;
    }

    public void setResults(List<GetTrending> results) {
        this.results = results;
    }

}
