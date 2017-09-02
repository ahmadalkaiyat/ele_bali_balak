package www.alkaiyat.ahmad.net.ahmadalkaiyats.shopsDetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetUserShopRate;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.R;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.UserLocalStore;

import static www.alkaiyat.ahmad.net.ahmadalkaiyats.R.id.layoutForm;
import static www.alkaiyat.ahmad.net.ahmadalkaiyats.R.id.ratingBar;


public class ShopRating extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener , View.OnClickListener {
    String ShopId,ShopName,IsOnMyList,LoggedInUserId,loggedUser;
    UserLocalStore userLocalStore;
    TextView lblHowHappy ,lblThanksFeedback;
    RatingBar ratingBar;
    Button btnSubmit;
    EditText txtComments;
    boolean IsLoggedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_rating);

        userLocalStore = new UserLocalStore(this);
        loggedUser = userLocalStore.getLoggedUser();
        IsLoggedin = userLocalStore.getUserLoggedIn();
        LoggedInUserId=userLocalStore.getUser_id();

        /* getting the {atameteres from Categories Fragmenet / Categories Adapter */
        ShopId = getIntent().getStringExtra("EXTRA_ShopId");
        ShopName = getIntent().getStringExtra("EXTRA_ShopName");
        IsOnMyList = getIntent().getStringExtra("EXTRA_IsOnMyList")+"";
        /*


        /* get the Page element Details*/
        lblHowHappy = (TextView) findViewById(R.id.lblHowHappy);
        lblThanksFeedback = (TextView)findViewById(R.id.lblThanksFeedback);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar) ;
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        txtComments = (EditText)findViewById(R.id.txtComments);
        lblHowHappy.setText("How happy are you with "+ShopName+" ?");
        lblThanksFeedback.setText("");





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
        getSupportActionBar().setTitle("Rate "+ShopName);

        /* check if the user already rated the shop , and show his previous rate of exists*/
        get_user_rate();


    }

    public void get_user_rate()
    {
        AhmadApi.getActivitiesRestClient().get_user_shop_rate(ShopId+"",LoggedInUserId+"",
                new retrofit.Callback<GetUserShopRate>() {
                    @Override
                    public void success(GetUserShopRate getUserShopRate, Response response) {
                        ratingBar.setRating(Float.parseFloat(getUserShopRate.getrate()));
                        txtComments.setText(getUserShopRate.getrate_note());

                    }

                    @Override
                    public void failure(RetrofitError error) {


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
        if (id == R.id.action_BranchesAndLocation) {
            Intent intent = new Intent(this,ShopBranchLocation.class);
            intent.putExtra("EXTRA_ShopId", ShopId+"");
            intent.putExtra("EXTRA_ShopName", ShopName+"");
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
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
            Intent intent = new Intent(ShopRating.this,HomePage.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.nav_contactus) {
            Intent intent = new Intent(ShopRating.this,ContactUs.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.navPrivacy) {
            Intent intent = new Intent(ShopRating.this,PrivacyAndPolicy.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_setting) {


        }else if (id == R.id.nav_logout) {
            userLocalStore.clearUserData();
            userLocalStore.setUserLoggedIn(false);
            userLocalStore.setWanLogIn(true);
            finish();
            startActivity(new Intent(ShopRating.this, MainActivity.class));
            // System.exit(1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit:

                rate_shop_by_user();

        }
    }
 public  void rate_shop_by_user()
 {
     AhmadApi.getActivitiesRestClient().rate_shop_from_user(ShopId+"",LoggedInUserId+""
             ,ratingBar.getRating()+"",txtComments.getText()+"",new retrofit.Callback<GetGeneralresponse>() {
                 @Override
                 public void success(GetGeneralresponse getGeneralresponse, Response response) {

                   //  lblThanksFeedback.setText(getGeneralresponse.getmessage());
                     Toast.makeText(ShopRating.this,getGeneralresponse.getmessage(), Toast.LENGTH_SHORT).show();

                 }

                 @Override
                 public void failure(RetrofitError error) {


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
                                Toast.makeText(ShopRating.this,getGeneralresponse.getmessage()+"",Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(ShopRating.this,getGeneralresponse.getmessage()+"",Toast.LENGTH_SHORT).show();
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

