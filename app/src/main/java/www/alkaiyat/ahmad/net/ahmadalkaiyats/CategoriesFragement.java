package www.alkaiyat.ahmad.net.ahmadalkaiyats;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetCategoriesResponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.adapter.CategoriesAdapter;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetCategories;
import Retrofit.AhmadApi;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CategoriesFragement extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View myFragmentView;
    private SwipeRefreshLayout swipeRefreshLayout;
    EditText inputSearch;

    public CategoriesFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        myFragmentView = inflater.inflate(R.layout.fragment_categories, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);  //  to avoid automatically appear android keyboard when activity start

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
                                        fetchCategories();
                                    }
                                }
        );
        inputSearch = (EditText) myFragmentView.findViewById(R.id.InputSearchCategories);
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
    public void onRefresh() {
        fetchCategories();
    }

    private void fetchCategories() {
        swipeRefreshLayout.setRefreshing(true);
        final RecyclerView recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.categories_recycler_view);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        AhmadApi.getActivitiesRestClient().categories(
                new retrofit.Callback<GetCategoriesResponse>() {
                    @Override
                    public void success(GetCategoriesResponse getCategoriesResponse, Response response) {
                        int statusCode = response.getStatus();
                        List<GetCategories> getcategories = getCategoriesResponse.getResults();
                        recyclerView.setAdapter(new CategoriesAdapter(getcategories, R.layout.listcategories, getActivity().getApplicationContext()));
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
        final RecyclerView recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.categories_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        AhmadApi.getActivitiesRestClient().get_Categories_withname(inputSearch.getText().toString() + "",
                new retrofit.Callback<GetCategoriesResponse>() {
                    @Override
                    public void success(GetCategoriesResponse getCategoriesResponse, Response response) {
                        int statusCode = response.getStatus();
                        List<GetCategories> getcategories = getCategoriesResponse.getResults();
                        recyclerView.setAdapter(new CategoriesAdapter(getcategories, R.layout.listcategories, getActivity().getApplicationContext()));
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

