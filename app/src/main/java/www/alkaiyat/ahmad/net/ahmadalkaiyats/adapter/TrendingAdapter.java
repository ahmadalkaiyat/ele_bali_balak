package www.alkaiyat.ahmad.net.ahmadalkaiyats.adapter;

/**
 * Created by Ahmad Alkaiyat on 1/7/2017.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import Retrofit.AhmadApi;
import retrofit.RetrofitError;
import retrofit.client.Response;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.HomePage;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.MainActivity;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetGeneralresponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetTrending;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.R;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.UserLocalStore;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.shopsDetails.ShopBranchLocation;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.shopsDetails.Shops;

public class TrendingAdapter  extends RecyclerView.Adapter<TrendingAdapter.MyViewHolder>  {

    private List<GetTrending> getTrending;
    private Context mContext;
    private AhmadApi AhmadApi = new AhmadApi();
    private String user_id, IsOnMyList ,IsOnSale;
    private boolean IsLogged;
    private  UserLocalStore userLocalStore ;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
       public TextView ShopName;
       public ImageView img_Shop_logo ,like1,like2;



        public MyViewHolder(View view) {
            super(view);
            ShopName = (TextView) view.findViewById(R.id.ShopName);
            img_Shop_logo = (ImageView) view.findViewById(R.id.ShopLogo);
            like1 = (ImageView) view.findViewById(R.id.like1);
            like2 = (ImageView) view.findViewById(R.id.like2);
            /* click lister code*/
            view.setClickable(true);
            view.setFocusableInTouchMode(true);
        }
    }


    public TrendingAdapter(List<GetTrending> getTrending, Context mContext , String user_id ,boolean Islogged) {
        this.getTrending = getTrending;
        this.mContext = mContext;
        this.user_id = user_id;
        this.IsLogged = Islogged;
    }

    @Override
    public TrendingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trending_cardview, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        IsOnMyList = getTrending.get(position).IsOnMyList()+"";
        IsOnSale = getTrending.get(position).IsOnSale()+"";
        if (((getTrending.get(position).IsOnMyList())==null?0:getTrending.get(position).IsOnMyList())==1){
            // is on user list  fill red heart
            holder.like1.setVisibility(View.GONE);
            holder.like2.setVisibility(View.VISIBLE);
        }else
        {
            // not in user List , fill gray heart
            holder.like2.setVisibility(View.GONE);
            holder.like1.setVisibility(View.VISIBLE);
        }

        holder.ShopName.setText(getTrending.get(position).GetShopName());
        //picasso part
        Picasso.with(mContext)
                .load(AhmadApi.getShops_img_path() + getTrending.get(position).GetShopName() + ".jpg")
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error1)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerInside()
                .tag(mContext)
                .into(holder.img_Shop_logo);

        //end picasso part
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Shops.class);
                intent.putExtra("EXTRA_ShopId", getTrending.get(position).GetShopId()+"");
                intent.putExtra("EXTRA_ShopName", getTrending.get(position).GetShopName()+"");
                intent.putExtra("Extra_isOnMyList", IsOnMyList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.getApplicationContext().startActivity(intent);
            }
        });

        holder.img_Shop_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Shops.class);
                intent.putExtra("EXTRA_ShopId", getTrending.get(position).GetShopId()+"");
                intent.putExtra("EXTRA_ShopName", getTrending.get(position).GetShopName()+"");
                intent.putExtra("Extra_isOnMyList", IsOnMyList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.getApplicationContext().startActivity(intent);
            }
        });

        holder.like1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (!IsLogged) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Please Login!")
                            .setMessage("You need login to add to your list")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                               /* userLocalStore.setWanLogIn(false);
                                finish();
                                startActivity(new Intent(Shops.this, MainActivity.class));*/
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
else{

                /* retrofit part*/
                AhmadApi.getActivitiesRestClient().add_to_my_list(user_id,
                        getTrending.get(position).GetShopId()+"",
                        new retrofit.Callback<GetGeneralresponse>() {
                            @Override
                            public void success(GetGeneralresponse getGeneralresponse, Response response) {
                                int statusCode = response.getStatus();
                                Toast.makeText(v.getContext(),getGeneralresponse.getmessage()+"",Toast.LENGTH_SHORT).show();
                                holder.like1.setVisibility(View.GONE);
                                holder.like2.setVisibility(View.VISIBLE);
                                IsOnMyList = "1";
                            }

                            @Override
                            public void failure(RetrofitError error) {


                            }
                        });
                /*end of retrofit*/
                }
            }
        });
        holder.like2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!IsLogged) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Please Login!")
                            .setMessage("You need to Delete from your list")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                               /* userLocalStore.setWanLogIn(false);
                                finish();
                                startActivity(new Intent(Shops.this, MainActivity.class));*/
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else
                {
                /* retrofit part*/
                AhmadApi.getActivitiesRestClient().delete_from_my_list(user_id,
                        getTrending.get(position).GetShopId()+"",
                        new retrofit.Callback<GetGeneralresponse>() {
                            @Override
                            public void success(GetGeneralresponse getGeneralresponse, Response response) {
                                int statusCode = response.getStatus();
                                Toast.makeText(v.getContext(),getGeneralresponse.getmessage()+"",Toast.LENGTH_SHORT).show();
                                holder.like2.setVisibility(View.GONE);
                                holder.like1.setVisibility(View.VISIBLE);
                                IsOnMyList = "0";
                            }

                            @Override
                            public void failure(RetrofitError error) {


                            }
                        });
                /*end of retrofit*/
                }
            }
        });
        

    }// end onBindViewHolder




    @Override
    public int getItemCount() {
        return getTrending.size();
    }

}
