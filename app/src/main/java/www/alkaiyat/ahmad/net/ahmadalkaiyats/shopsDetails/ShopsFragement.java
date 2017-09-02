package www.alkaiyat.ahmad.net.ahmadalkaiyats.shopsDetails;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Retrofit.AhmadApi;
import retrofit.RetrofitError;
import retrofit.client.Response;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopItems;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopItemsResponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopSections;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopSectionsResponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetTrending;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetTrindingResponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.R;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.UserLocalStore;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.adapter.ShopItemsAdapter;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.adapter.TrendingAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopsFragement extends Fragment  {

    String fragtitle,LoggedInUserId,countryCode,shop_id,SectionId,ShopName;
    TextView testfrag;
    UserLocalStore userLocalStore;
    private View myFragmentView;
    boolean IsLoggedIn;
    private RecyclerView recyclerView;
    private ShopItemsAdapter mAdapter;
    private ArrayList<GetShopItems> getShopItemses;


    public ShopsFragement(String section,String shop_id , String SectionId , String ShopName) {
        // Required empty public constructor
        fragtitle = section;
        this.shop_id = shop_id;
        this.SectionId = SectionId;
        this.ShopName = ShopName;
    }

    public ShopsFragement() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_shops, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);  //  to avoid automatically appear android keyboard when activity start
        userLocalStore = new UserLocalStore(getActivity());
        IsLoggedIn = userLocalStore.getUserLoggedIn();
        LoggedInUserId=userLocalStore.getUser_id();

        getShopItemses = new ArrayList<>();
        mAdapter = new ShopItemsAdapter(getShopItemses, getActivity().getApplicationContext(),shop_id,SectionId ,ShopName );

        final RecyclerView recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.shop_section_items_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnItemTouchListener(new ShopItemsAdapter.RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ShopItemsAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("5ya6", view.getId()+"");
                Bundle bundle = new Bundle();
               bundle.putSerializable("getShopItemses", getShopItemses);
                bundle.putInt("position", position);
             //   FragmentTransaction ft = getFragmentManager ().beginTransaction();
                android.app.FragmentManager ft =getActivity().getFragmentManager();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        /* get the ItemInside the shop*/
        fetchShopSectionItems();

       /* testfrag = (TextView) myFragmentView.findViewById(R.id.testfrag);

        countryCode = userLocalStore.getUserOperCountry(getContext());
        testfrag.setText(fragtitle + " country is "+countryCode);
        userLocalStore.setUserOperCountry("jo");
        countryCode = userLocalStore.getUserOperCountry(getContext());
        Toast.makeText(getActivity(), " country is "+countryCode ,Toast.LENGTH_SHORT).show();*/


        return myFragmentView;

    }

public void fetchShopSectionItems()
{
    final RecyclerView recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.shop_section_items_recycler_view);
    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), 2);
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

    AhmadApi.getActivitiesRestClient().get_shopItems(shop_id,SectionId,
            new retrofit.Callback<GetShopItemsResponse>() {
                @Override
                public void success(GetShopItemsResponse getShopItemsResponse, Response response) {
                    int statusCode = response.getStatus();
                    List<GetShopItems> getShopsItemses = getShopItemsResponse.getResults();
                    getShopItemses = (ArrayList<GetShopItems>) getShopsItemses;
                    recyclerView.setAdapter(new ShopItemsAdapter(getShopsItemses, getActivity().getApplicationContext(),shop_id,SectionId ,ShopName ));

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity(), "Opps! Something went wrong! It could be your Internet connection!", Toast.LENGTH_SHORT).show();
                    // stopping swipe refresh

                }
            });

}


}
