package www.alkaiyat.ahmad.net.ahmadalkaiyats.adapter;

/**
 * Created by Ahmad Alkaiyat on 1/7/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import Retrofit.AhmadApi;
import retrofit.RetrofitError;
import retrofit.client.Response;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetGeneralresponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopBranches;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetTrending;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.R;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.shopsDetails.Shops;

public class ShopBranchesAdapter extends RecyclerView.Adapter<ShopBranchesAdapter.MyViewHolder>  {

    private List<GetShopBranches> getShopBranches;
    private Context mContext;
    private String user_lat , user_long;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
       public TextView branchName,branch_distance,branch_phone_number,Branch_get_directionTv,Branch_AddressET;

        public MyViewHolder(View view) {
            super(view);
            branchName = (TextView) view.findViewById(R.id.branchName);
            branch_distance = (TextView) view.findViewById(R.id.branch_distance);
            branch_phone_number =(TextView) view.findViewById(R.id.branch_phone_number);
            Branch_get_directionTv =(TextView) view.findViewById(R.id.Branch_get_directionTv);
            Branch_AddressET =(TextView) view.findViewById(R.id.Branch_AddressET);
            Branch_AddressET.setKeyListener(null);

            /*
            Get Distance
             */
            /* click lister code*/
            view.setClickable(true);
            view.setFocusableInTouchMode(true);
        }
    }


    public ShopBranchesAdapter(List<GetShopBranches> getShopBranches, Context mContext , String user_lat, String user_long) {
        this.getShopBranches = getShopBranches;
        this.mContext = mContext;
        this.user_lat = user_lat;
        this.user_long = user_long;
    }

    @Override
    public ShopBranchesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_branches_cardview, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String shop_latitude = getShopBranches.get(position).getLatitude();
        String shop_longitude = getShopBranches.get(position).getLongitude();

        holder.branchName.setText(getShopBranches.get(position).getBranchNAme()+"");
        holder.branch_distance.setText(get_distance(shop_latitude,shop_longitude));
        holder.branch_phone_number.setText(getShopBranches.get(position).getBranchPhoneNumber());
        holder.Branch_AddressET.setText(getShopBranches.get(position).getBranchAddress());

        /* Call the branch*/
        holder.branch_phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + holder.branch_phone_number.getText()));
                mContext.getApplicationContext().startActivity(intent);
            }
        });
        /* End Call the branch*/

        /* to get the Directions*/
        holder.Branch_get_directionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = getShopBranches.get(position).getLatitude();
                String longitude = getShopBranches.get(position).getLongitude();
                String label = getShopBranches.get(position).getBranchNAme();
                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                mContext.getApplicationContext().startActivity(intent);
            }
        });
        /* end getting the Directions*/
    }// end onBindViewHolder

    public String get_distance (String targetLang , String targetLong )
    {

        try {
            Location mallLoc = new Location("");
            mallLoc.setLatitude(Double.parseDouble(targetLang));
            mallLoc.setLongitude(Double.parseDouble(targetLong));

            Location userLoc = new Location("");
            userLoc.setLatitude(Double.parseDouble(user_lat));
            userLoc.setLongitude(Double.parseDouble(user_long));

            float distance = mallLoc.distanceTo(userLoc) / 1000;
            return String.format(Locale.getDefault(), "%.2f", distance)
                    + "";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return "Unknown";
        }
    }


    @Override
    public int getItemCount() {
        return getShopBranches.size();
    }

}
