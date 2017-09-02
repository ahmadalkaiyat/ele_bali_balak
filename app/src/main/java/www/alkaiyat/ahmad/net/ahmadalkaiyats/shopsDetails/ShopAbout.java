package www.alkaiyat.ahmad.net.ahmadalkaiyats.shopsDetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import Retrofit.AhmadApi;
import retrofit.RetrofitError;
import retrofit.client.Response;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.AppMenuePages.ContactUs;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.AppMenuePages.PrivacyAndPolicy;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.HomePage;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.MainActivity;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetGeneralresponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopContactDetails;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.R;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.UserLocalStore;

public class ShopAbout extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    String ShopId,ShopName,IsOnMyList,LoggedInUserId,loggedUser;
    UserLocalStore userLocalStore;
    TextView ShopDetailTV;
    boolean IsLoggedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        userLocalStore = new UserLocalStore(this);
        loggedUser = userLocalStore.getLoggedUser();
        IsLoggedin = userLocalStore.getUserLoggedIn();
        LoggedInUserId=userLocalStore.getUser_id();


        ShopDetailTV = (TextView) findViewById(R.id.shopDetailTV);
          /* getting the {atameteres from Categories Fragmenet / Categories Adapter */
        ShopId = getIntent().getStringExtra("EXTRA_ShopId");
        ShopName = getIntent().getStringExtra("EXTRA_ShopName");
        IsOnMyList = getIntent().getStringExtra("EXTRA_IsOnMyList")+"";
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
        getSupportActionBar().setTitle("About "+ShopName);

       ShopDetailTV.setText(" ");
        get_shop_about();
    }


    private void get_shop_about(){
        AhmadApi.getActivitiesRestClient().getshopAbout(ShopId+"",
                new retrofit.Callback<GetShopContactDetails>() {
                    @Override
                    public void success(GetShopContactDetails getShopContactDetails, Response response) {
                        ShopDetailTV.setText(getShopContactDetails.getShopAbout()+" ");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ShopDetailTV.setText("Cant About Details! :(");

                    }
                });
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
                new AlertDialog.Builder(ShopAbout.this)
                        .setTitle("Please Login!")
                        .setMessage("You need to Login to rate this shop")
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                userLocalStore.setWanLogIn(true);
                                finish();
                                startActivity(new Intent(ShopAbout.this, MainActivity.class));
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
                Intent intent = new Intent(ShopAbout.this, ShopRating.class);
                intent.putExtra("EXTRA_ShopId", ShopId + "");
                intent.putExtra("EXTRA_ShopName", ShopName + "");
                intent.putExtra("EXTRA_IsOnMyList", IsOnMyList+"");
                startActivity(intent);
                return true;
            }
        }
        if (id == R.id.action_BranchesAndLocation) {
            Intent intent = new Intent(this,ShopBranchLocation.class);
            intent.putExtra("EXTRA_ShopId", ShopId+"");
            intent.putExtra("EXTRA_ShopName", ShopName+"");
            intent.putExtra("EXTRA_IsOnMyList", IsOnMyList+"");
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_Report) {
            Intent intent = new Intent(this,ShopReport.class);
            intent.putExtra("EXTRA_ShopId", ShopId+"");
            intent.putExtra("EXTRA_ShopName", ShopName+"");
            intent.putExtra("EXTRA_IsOnMyList", IsOnMyList+"");
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
            Intent intent = new Intent(ShopAbout.this,HomePage.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.nav_contactus) {
            Intent intent = new Intent(ShopAbout.this,ContactUs.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.navPrivacy) {
            Intent intent = new Intent(ShopAbout.this,PrivacyAndPolicy.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_setting) {


        }else if (id == R.id.nav_logout) {
            userLocalStore.clearUserData();
            userLocalStore.setUserLoggedIn(false);
            userLocalStore.setWanLogIn(true);
            finish();
            startActivity(new Intent(ShopAbout.this, MainActivity.class));
            // System.exit(1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                                Toast.makeText(ShopAbout.this,getGeneralresponse.getmessage()+"",Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(ShopAbout.this,getGeneralresponse.getmessage()+"",Toast.LENGTH_SHORT).show();
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

