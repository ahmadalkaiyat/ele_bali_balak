package www.alkaiyat.ahmad.net.ahmadalkaiyats.shopsDetails;

import android.app.DialogFragment;
import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import Retrofit.AhmadApi;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopItems;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.R;

/**
 * Created by Ahmad Alkaiyat on 3/4/2017.
 */

public class SlideshowDialogFragment extends DialogFragment {
    private String ImagePath;
    private ArrayList<GetShopItems> getShopItemses ;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitle, lblDate;
    private int selectedPosition = 0;
    private Retrofit.AhmadApi AhmadApi = new AhmadApi();
    ImageView ShareToSocial;


    static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        lblCount = (TextView) v.findViewById(R.id.lbl_count);
        lblTitle = (TextView) v.findViewById(R.id.title);
        lblDate = (TextView) v.findViewById(R.id.date);
        ShareToSocial = (ImageView)v.findViewById(R.id.ShareToSocial);
        getShopItemses = (ArrayList<GetShopItems>) getArguments().getSerializable("getShopItemses");
        selectedPosition = getArguments().getInt("position");

       Log.d("5ya6", "position: " + selectedPosition);
       Log.d("5ya6", "images size: " + getShopItemses.size()+"");


        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setCurrentItem(selectedPosition);

        return v;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void displayMetaInfo(int position) {
        lblCount.setText((position + 1) + " of " + getShopItemses.size());
        GetShopItems getShopItem = getShopItemses.get(position);
        lblTitle.setText(getShopItem.getItemName()+"");
       lblDate.setText(getShopItem.getItemDescription()+"");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }
    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);
            ImageView imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);
            GetShopItems getShopItem =  getShopItemses.get(position);
            ImagePath= AhmadApi.getShops_img_items_path(getShopItem.getShopId()+"",getShopItem.SectionID()+"");
              Log.d("5ya6",ImagePath + getShopItem.getItemId() + ".png");

            Picasso.with(getContext())
                    .load(ImagePath + getShopItem.getItemId() + ".png")
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error1)
                    .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                    .centerInside()
                    .tag(getContext())
                    .into(imageViewPreview);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
         return   getShopItemses.size();

        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
