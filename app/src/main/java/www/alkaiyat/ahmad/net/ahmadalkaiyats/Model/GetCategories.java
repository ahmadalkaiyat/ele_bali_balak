package www.alkaiyat.ahmad.net.ahmadalkaiyats.Model;

/**
 * Created by Ahmad Alkaiyat on 12/26/2016.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetCategories {
    @SerializedName("Gorup_id")
    private Integer GroupId;
    @SerializedName("Gorup_desc")
    private String GroupDesc;

    public GetCategories(Integer GroupId, String GroupDesc) {
        this.GroupId = GroupId;
        this.GroupDesc = GroupDesc;
    }

    public Integer getGroupId() {
        return GroupId;
    }

    public String getGroupDesc() {
        return GroupDesc;
    }

    public String getOverview() {
        return "OverView here";
    }

    public Double getVoteAverage() {
        return 8.2;
    }


}
