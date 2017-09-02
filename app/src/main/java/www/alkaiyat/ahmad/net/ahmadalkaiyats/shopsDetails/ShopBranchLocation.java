package www.alkaiyat.ahmad.net.ahmadalkaiyats.shopsDetails;


import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import java.util.List;

import Retrofit.AhmadApi;
import retrofit.RetrofitError;
import retrofit.client.Response;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.AppMenuePages.ContactUs;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.AppMenuePages.PrivacyAndPolicy;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.HomePage;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.MainActivity;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetGeneralresponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopBranches;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopBreanchesResponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetTrending;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetTrindingResponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.R;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.UserLocalStore;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.adapter.MyListAdapter;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.adapter.ShopBranchesAdapter;

public class ShopBranchLocation extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener ,
        View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener{
    String ShopId,ShopName,user_Latitude,user_Longitude,loggedUser,LoggedInUserId,IsOnMyList;
    UserLocalStore userLocalStore;
    private SwipeRefreshLayout swipeRefreshLayout;
    boolean IsLoggedin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_branch_location);

        userLocalStore = new UserLocalStore(this);
        loggedUser = userLocalStore.getLoggedUser();
        IsLoggedin = userLocalStore.getUserLoggedIn();
        LoggedInUserId=userLocalStore.getUser_id();



        /* get the Page element Details*/




          /* getting the {atameteres from Categories Fragmenet / Categories Adapter */
        ShopId = getIntent().getStringExtra("EXTRA_ShopId");
        ShopName = getIntent().getStringExtra("EXTRA_ShopName");
        IsOnMyList = getIntent().getStringExtra("EXTRA_IsOnMyList")+"";
        /*

        /* get user location */
        user_Latitude=userLocalStore.getUserLat();
        user_Longitude=userLocalStore.getUserLong();
        /* end of paraeter getting*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

         /* Navigation Drawer Code*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();
        // do the same for other MenuItems
        MenuItem nav_logout = menu.findItem(R.id.nav_logout);
        if (userLocalStore.getUserLoggedIn()){// user Loggin in
            nav_logout.setTitle("Log Out");
        }else  // user not logged in
        {
            nav_logout.setTitle("LogIn");
        }
        navigationView.setNavigationItemSelectedListener(this);
        /* End Navigation Drawer Code*/
        getSupportActionBar().setTitle("Branches of "+ShopName);
        /* swipe thing*/
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_myList);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        get_shop_branches();
                                    }
                                }
        );


    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_About) {
            Intent intent = new Intent(this,ShopAbout.class);
            intent.putExtra("EXTRA_ShopId", ShopId+"");
            intent.putExtra("EXTRA_ShopName", ShopName+"");
            intent.putExtra("EXTRA_IsOnMyList", IsOnMyList+"");
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_Contact) {
            Intent intent = new Intent(this,ShopContactUs.class);
            intent.putExtra("EXTRA_ShopId", ShopId+"");
            intent.putExtra("EXTRA_ShopName", ShopName+"");
            intent.putExtra("EXTRA_IsOnMyList", IsOnMyList+"");
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_Rate) {
            /*  check if user is logged in to check his list*/
            if (!userLocalStore.getUserLoggedIn()) {
                new AlertDialog.Builder(ShopBranchLocation.this)
                        .setTitle("Please Login!")
                        .setMessage("You need to Login to rate this shop")
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                userLocalStore.setWanLogIn(true);
                                finish();
                                startActivity(new Intent(ShopBranchLocation.this, MainActivity.class));
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
/*  End check if user is logged in to check his list*/
            if (userLocalStore.getUserLoggedIn()) {
                Intent intent = new Intent(ShopBranchLocation.this, ShopRating.class);
                intent.putExtra("EXTRA_ShopId", ShopId + "");
                intent.putExtra("EXTRA_ShopName", ShopName + "");
                intent.putExtra("EXTRA_IsOnMyList", IsOnMyList+"");
                startActivity(intent);
                return true;
            }
        }
        if (id == R.id.action_Report) {
            Intent intent = new Intent(this,ShopReport.class);
            intent.putExtra("EXTRA_ShopId", ShopId+"");
            intent.putExtra("EXTRA_ShopName", ShopName+"");
            intent.putExtra("EXTRA_IsOnMyList", IsOnMyList+"");
            startActivity(intent);
            return true;

        }
        if (id == R.id.action_AddDelete) {
            AddDelete_MyList();
            return true;

        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation v;iew item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            // Handle the camera action
        }
        else if (id == R.id.nav_home) {
            finish();
            Intent intent = new Intent(ShopBranchLocation.this,HomePage.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.nav_contactus) {
            Intent intent = new Intent(ShopBranchLocation.this,ContactUs.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.navPrivacy) {
            Intent intent = new Intent(ShopBranchLocation.this,PrivacyAndPolicy.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_setting) {


        }else if (id == R.id.nav_logout) {
            userLocalStore.clearUserData();
            userLocalStore.setUserLoggedIn(false);
            userLocalStore.setWanLogIn(true);
            finish();
            startActivity(new Intent(ShopBranchLocation.this, MainActivity.class));
            // System.exit(1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        get_shop_branches();
    }

    private void get_shop_branches() {
        swipeRefreshLayout.setRefreshing(true);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.shop_branch_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        AhmadApi.getActivitiesRestClient().get_shop_branches(ShopId+ "",
                new retrofit.Callback<GetShopBreanchesResponse>() {
                    @Override
                    public void success(GetShopBreanchesResponse getShopBreanchesResponse, Response response) {
                        int statusCode = response.getStatus();
                        List<GetShopBranches> getShopBranches = getShopBreanchesResponse.getResults();
                        recyclerView.setAdapter(new ShopBranchesAdapter(getShopBranches, getApplicationContext(),user_Latitude+"",user_Longitude+""));
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (userLocalStore.getUserLoggedIn()) {
                            Toast.makeText(ShopBranchLocation.this, "Opps! Something went wrong! It could be your Internet connection!", Toast.LENGTH_SHORT).show();
                        }
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

    }

    public void AddDelete_MyList()
    {
        if (!IsLoggedin) {
            new AlertDialog.Builder(this)
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
        else
        {
            if (IsOnMyList.matches(".*1.*"))
            {
                 /* retrofit part*/
                AhmadApi.getActivitiesRestClient().delete_from_my_list(LoggedInUserId,
                        ShopId,
                        new retrofit.Callback<GetGeneralresponse>() {
                            @Override
                            public void success(GetGeneralresponse getGeneralresponse, Response response) {
                                int statusCode = response.getStatus();
                                Toast.makeText(ShopBranchLocation.this,getGeneralresponse.getmessage()+"",Toast.LENGTH_SHORT).show();
                                IsOnMyList = "0";
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                /*end of retrofit*/
            }else
            {
                 /* retrofit part*/
                AhmadApi.getActivitiesRestClient().add_to_my_list(LoggedInUserId,
                        ShopId,
                        new retrofit.Callback<GetGeneralresponse>() {
                            @Override
                            public void success(GetGeneralresponse getGeneralresponse, Response response) {
                                int statusCode = response.getStatus();
                                Toast.makeText(ShopBranchLocation.this,getGeneralresponse.getmessage()+"",Toast.LENGTH_SHORT).show();
                                IsOnMyList = "1";
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                /*end of retrofit*/
            }

        }
    }

}

