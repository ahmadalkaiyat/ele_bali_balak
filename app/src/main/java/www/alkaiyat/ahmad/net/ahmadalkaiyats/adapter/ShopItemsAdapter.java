package www.alkaiyat.ahmad.net.ahmadalkaiyats.adapter;

/**
 * Created by Ahmad Alkaiyat on 1/7/2017.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import Retrofit.AhmadApi;
import retrofit.RetrofitError;
import retrofit.client.Response;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetGeneralresponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopItems;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetTrending;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.R;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.UserLocalStore;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.shopsDetails.Shops;

import static www.alkaiyat.ahmad.net.ahmadalkaiyats.R.id.ItemIMG;

public class ShopItemsAdapter extends RecyclerView.Adapter<ShopItemsAdapter.MyViewHolder>  {

    private List<GetShopItems> getShopItemses;
    private Context mContext;
    private AhmadApi AhmadApi = new AhmadApi();
    private String Shop_id,Section_id,ImagePath,ShopName;


    public class MyViewHolder extends RecyclerView.ViewHolder {
       public TextView ItemName;
       public ImageView ItemIMG ,ShareToSocial;
        public  Uri bmpUri;



        public MyViewHolder(View view) {
            super(view);
            ItemName = (TextView) view.findViewById(R.id.ItemName);
            ItemIMG = (ImageView) view.findViewById(R.id.ItemIMG);
            ShareToSocial = (ImageView) view.findViewById(R.id.ShareToSocial);
            bmpUri = getLocalBitmapUri(ItemIMG);

            /* click lister code*/
            view.setClickable(true);
            view.setFocusableInTouchMode(true);
        }
    }


    public ShopItemsAdapter(List<GetShopItems> getShopItemses, Context mContext , String Shop_id , String Section_id ,String ShopName) {
        this.getShopItemses = getShopItemses;
        this.mContext = mContext;
        this.Shop_id=Shop_id;
        this.Section_id=Section_id;
        ImagePath = AhmadApi.getShops_img_items_path(Shop_id,Section_id);
        this.ShopName=ShopName;

    }

    @Override
    public ShopItemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_section_item_cardview, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {



        holder.ItemName.setText(getShopItemses.get(position).getItemName());
        //picasso part
        Picasso.with(mContext)
                .load(ImagePath + getShopItemses.get(position).getItemId() + ".png")
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error1)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerInside()
                .tag(mContext)
                .into(holder.ItemIMG);

        //end picasso part
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(v.getContext(), Shops.class);
                intent.putExtra("EXTRA_ShopId", getTrending.get(position).GetShopId()+"");
                intent.putExtra("EXTRA_ShopName", getTrending.get(position).GetShopName()+"");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.getApplicationContext().startActivity(intent);*/
            }
        });

        holder.ShareToSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri bmpUri = getLocalBitmapUri(holder.ItemIMG);
                if (bmpUri != null) {
                    // Construct a ShareIntent with link to image
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    shareIntent.putExtra(Intent.EXTRA_TEXT,"Check Out this "+holder.ItemName.getText()+" from "+ShopName + " Shared on footjai application");
                    shareIntent.setType("image/*");
                    // Launch sharing dialog for image
                    mContext.getApplicationContext().startActivity(Intent.createChooser(shareIntent, "Share Image"));
                } else {
                    // ...sharing failed, handle error
                }


            }
        });

        holder.ItemIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

            }
        });


    }// end onBindViewHolder

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return bmpUri;
    }

    @Override
    public int getItemCount() {
        return getShopItemses.size();
    }


    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ShopItemsAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ShopItemsAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
