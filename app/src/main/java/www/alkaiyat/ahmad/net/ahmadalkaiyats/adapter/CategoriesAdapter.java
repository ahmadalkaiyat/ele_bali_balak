package www.alkaiyat.ahmad.net.ahmadalkaiyats.adapter;

/**
 * Created by Ahmad Alkaiyat on 12/26/2016. for Retrofit on categoris
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Retrofit.AhmadApi;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.CategoryPage;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetCategories;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.R;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

    private List<GetCategories> getCategories;
    private int rowLayout;
    private Context context;
    private AhmadApi AhmadApi = new AhmadApi();

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        LinearLayout categoryLayout;
        TextView GroupDesc;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView img_Categories;
        FrameLayout FrameL_Categories;


        public CategoryViewHolder(View view) {
            super(view);
          //  categoryLayout = (LinearLayout) v.findViewById(R.id.categories_layout);  // listcategories.xms
            GroupDesc = (TextView) view.findViewById(R.id.category_description);
            img_Categories = (ImageView) view.findViewById(R.id.img_Categories);
            FrameL_Categories = (FrameLayout) view.findViewById(R.id.FrameL_Categories);
            /* click lister code*/
            view.setClickable(true);
            view.setFocusableInTouchMode(true);
        }
    }


    public CategoriesAdapter(List<GetCategories> getCategories, int rowLayout, Context context) {
        this.getCategories = getCategories;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public CategoriesAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new CategoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        holder.GroupDesc.setText(getCategories.get(position).getGroupDesc());
        //picasso part
        Picasso.with(context)
                .load(AhmadApi.getCategories_img_path() + getCategories.get(position).getGroupDesc() + ".png")
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.footjaimenuelogo)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerInside()
                .tag(context)
                .into(holder.img_Categories);

        //end picasso part
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoryPage.class);
                intent.putExtra("EXTRA_CategoryId", getCategories.get(position).getGroupId()+"");
                intent.putExtra("EXTRA_CategoryDesc", getCategories.get(position).getGroupDesc()+"");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });
        holder.img_Categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoryPage.class);
                intent.putExtra("EXTRA_CategoryId", getCategories.get(position).getGroupId()+"");
                intent.putExtra("EXTRA_CategoryDesc", getCategories.get(position).getGroupDesc()+"");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });
        holder.GroupDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoryPage.class);
                intent.putExtra("EXTRA_CategoryId", getCategories.get(position).getGroupId()+"");
                intent.putExtra("EXTRA_CategoryDesc", getCategories.get(position).getGroupDesc()+"");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });
        holder.FrameL_Categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoryPage.class);
                intent.putExtra("EXTRA_CategoryId", getCategories.get(position).getGroupId()+"");
                intent.putExtra("EXTRA_CategoryDesc", getCategories.get(position).getGroupDesc()+"");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });

    }// end onBindViewHolder

    @Override
    public int getItemCount() {
        return getCategories.size();
    }


}


