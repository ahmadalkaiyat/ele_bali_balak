package www.alkaiyat.ahmad.net.ahmadalkaiyats;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import Retrofit.AhmadApi;
import retrofit.RetrofitError;
import retrofit.client.Response;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetTrending;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetTrindingResponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.R;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.adapter.TrendingAdapter;

public class CategoryPage extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private Object Menu;
    EditText inputSearch;
    UserLocalStore userLocalStore;
    String CatId, CateDesc;
    boolean IsLoggedIn;
    String LoggedInUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);

        /* getting the {atameteres from Categories Fragmenet / Categories Adapter */
        CatId = getIntent().getStringExtra("EXTRA_CategoryId");
        CateDesc = getIntent().getStringExtra("EXTRA_CategoryDesc");
        /* end of paraeter getting*/
        getSupportActionBar().setTitle(CateDesc);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        userLocalStore = new UserLocalStore(this);
        IsLoggedIn = userLocalStore.getUserLoggedIn();
        LoggedInUserId=userLocalStore.getUser_id();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
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
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.setHint("Search "+CateDesc);
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


    }// end of OnCreate


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        fetchTrendingList();
    }


    private void fetchTrendingList() {
        swipeRefreshLayout.setRefreshing(true);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.trending_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        AhmadApi.getActivitiesRestClient().insideCaegory(LoggedInUserId,CatId,
                new retrofit.Callback<GetTrindingResponse>() {
                    @Override
                    public void success(GetTrindingResponse getTrendingResponse, Response response) {
                        int statusCode = response.getStatus();
                        List<GetTrending> gettrending = getTrendingResponse.getResults();
                        recyclerView.setAdapter(new TrendingAdapter(gettrending, getApplicationContext(), LoggedInUserId ,IsLoggedIn));
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(CategoryPage.this, "Opps! Something went wrong! It could be your Internet connection.", Toast.LENGTH_SHORT).show();
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

    }

    private void fetchTrendingList_with_name() {
        swipeRefreshLayout.setRefreshing(true);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.trending_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        AhmadApi.getActivitiesRestClient().insideCaegory_WithName(LoggedInUserId,inputSearch.getText().toString() + "",
                CatId,
                new retrofit.Callback<GetTrindingResponse>() {
                    @Override
                    public void success(GetTrindingResponse getTrendingResponse, Response response) {
                        int statusCode = response.getStatus();
                        List<GetTrending> gettrending = getTrendingResponse.getResults();
                        recyclerView.setAdapter(new TrendingAdapter(gettrending, getApplicationContext(), LoggedInUserId ,IsLoggedIn));
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(CategoryPage.this, "Opps! Something went wrong! It could be your Internet connection.", Toast.LENGTH_SHORT).show();
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
