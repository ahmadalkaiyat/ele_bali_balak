package www.alkaiyat.ahmad.net.ahmadalkaiyats;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetTrending;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetTrindingResponse;
import Retrofit.AhmadApi;
import retrofit.RetrofitError;
import retrofit.client.Response;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.adapter.TrendingAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendingFragement extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private View myFragmentView;
    private SwipeRefreshLayout swipeRefreshLayout;
    EditText inputSearch;
    UserLocalStore userLocalStore;
    boolean IsLoggedIn;
    String LoggedInUserId;

    public TrendingFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_trending, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);  //  to avoid automatically appear android keyboard when activity start
        userLocalStore = new UserLocalStore(getActivity());
        IsLoggedIn = userLocalStore.getUserLoggedIn();
        LoggedInUserId=userLocalStore.getUser_id();
        swipeRefreshLayout = (SwipeRefreshLayout) myFragmentView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        fetchTrendingList();
                                    }
                                }
        );
        inputSearch = (EditText) myFragmentView.findViewById(R.id.inputSearch);
        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                fetchTrendingList_with_name();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        return myFragmentView;

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        fetchTrendingList();
    }


    private void fetchTrendingList() {
        swipeRefreshLayout.setRefreshing(true);
        final RecyclerView recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.trending_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        AhmadApi.getActivitiesRestClient().get_trending(LoggedInUserId,
                new retrofit.Callback<GetTrindingResponse>() {
                    @Override
                    public void success(GetTrindingResponse getTrendingResponse, Response response) {
                        int statusCode = response.getStatus();
                        List<GetTrending> gettrending = getTrendingResponse.getResults();
                        recyclerView.setAdapter(new TrendingAdapter(gettrending, getActivity().getApplicationContext(),LoggedInUserId,IsLoggedIn));
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getActivity(), "Opps! Something went wrong! It could be your Internet connection!", Toast.LENGTH_SHORT).show();
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

    }

    private void fetchTrendingList_with_name() {
        swipeRefreshLayout.setRefreshing(true);
        final RecyclerView recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.trending_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        AhmadApi.getActivitiesRestClient().get_trending_withname(LoggedInUserId,inputSearch.getText().toString()+"",
                new retrofit.Callback<GetTrindingResponse>() {
                    @Override
                    public void success(GetTrindingResponse getTrendingResponse, Response response) {
                        int statusCode = response.getStatus();
                        List<GetTrending> gettrending = getTrendingResponse.getResults();
                        recyclerView.setAdapter(new TrendingAdapter(gettrending, getActivity().getApplicationContext(),LoggedInUserId,IsLoggedIn));
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getActivity(), "Opps! Something went wrong! It could be your Internet connection!", Toast.LENGTH_SHORT).show();
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

    }

}
