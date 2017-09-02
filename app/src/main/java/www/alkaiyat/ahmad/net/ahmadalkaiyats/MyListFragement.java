package www.alkaiyat.ahmad.net.ahmadalkaiyats;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import Retrofit.AhmadApi;
import retrofit.RetrofitError;
import retrofit.client.Response;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetTrending;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetTrindingResponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.adapter.MyListAdapter;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.adapter.TrendingAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyListFragement extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private View myFragmentView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Object Menu;
    EditText inputSearch;
    UserLocalStore userLocalStore;
    private boolean isViewShown = false;

    public MyListFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_my_list, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);  //  to avoid automatically appear android keyboard when activity start

        userLocalStore = new UserLocalStore(getActivity());

        swipeRefreshLayout = (SwipeRefreshLayout) myFragmentView.findViewById(R.id.swipe_refresh_layout_myList);
        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        if (!isViewShown) {
                     swipeRefreshLayout.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            swipeRefreshLayout.setRefreshing(true);
                                            fetchmylist();
                                        }
                                    }
            );}
            else{
            }


        inputSearch = (EditText) myFragmentView.findViewById(R.id.inputSearchMyList);
        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                fetchMyList_with_name();
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (getView() != null) {
                isViewShown = true;
/*  check if user is logged in to check his list*/
                if (!userLocalStore.getUserLoggedIn()) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Please Login!")
                            .setMessage("You need to Login to check your list")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    userLocalStore.setWanLogIn(true);
                                    getActivity().finish();
                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    userLocalStore.setWanLogIn(false);
                                    getActivity().finish();
                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
/*  End check if user is logged in to check his list*/
            } else {

                isViewShown = false;

            }
        } else
            //when the Fragemnt is invisible
            return;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        fetchmylist();
    }


    private void fetchmylist() {
        swipeRefreshLayout.setRefreshing(true);
        final RecyclerView recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.mylist_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        AhmadApi.getActivitiesRestClient().get_my_list(userLocalStore.getUser_id() + "",
                new retrofit.Callback<GetTrindingResponse>() {
                    @Override
                    public void success(GetTrindingResponse getTrendingResponse, Response response) {
                        int statusCode = response.getStatus();
                        List<GetTrending> gettrending = getTrendingResponse.getResults();
                        recyclerView.setAdapter(new MyListAdapter(gettrending, getActivity().getApplicationContext(), userLocalStore.getUser_id() + ""));
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (userLocalStore.getUserLoggedIn()) {
                            Toast.makeText(getActivity(), "Opps! Something went wrong! It could be your Internet connection!", Toast.LENGTH_SHORT).show();
                        }
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

    }

    private void fetchMyList_with_name() {
        swipeRefreshLayout.setRefreshing(true);
        final RecyclerView recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.mylist_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        AhmadApi.getActivitiesRestClient().get_my_list_withname(userLocalStore.getUser_id() + "", inputSearch.getText().toString() + "",
                new retrofit.Callback<GetTrindingResponse>() {
                    @Override
                    public void success(GetTrindingResponse getTrendingResponse, Response response) {
                        int statusCode = response.getStatus();
                        List<GetTrending> gettrending = getTrendingResponse.getResults();
                        recyclerView.setAdapter(new MyListAdapter(gettrending, getActivity().getApplicationContext(), userLocalStore.getUser_id() + ""));
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (userLocalStore.getUserLoggedIn()) {
                            Toast.makeText(getActivity(), "Opps! Something went wrong! It could be your Internet connection!", Toast.LENGTH_SHORT).show();
                        }
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

    }

}
