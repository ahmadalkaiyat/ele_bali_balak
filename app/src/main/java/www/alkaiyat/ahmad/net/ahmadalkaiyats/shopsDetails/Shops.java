package www.alkaiyat.ahmad.net.ahmadalkaiyats.shopsDetails;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Retrofit.AhmadApi;
import retrofit.RetrofitError;
import retrofit.client.Response;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.AppMenuePages.ContactUs;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.AppMenuePages.PrivacyAndPolicy;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.HomePage;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.MainActivity;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetGeneralresponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopSections;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopSectionsResponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.R;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.UserLocalStore;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import static com.google.android.gms.common.api.GoogleApiClient.*;

public class Shops extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,ConnectionCallbacks, OnConnectionFailedListener, LocationListener{

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION =111 ;
    String ShopId,ShopName,IsOnMyList,LoggedInUserId;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    UserLocalStore userLocalStore;
    boolean IsLoggedin;

   private String loggedUser,     _latitude, _longitude;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        userLocalStore = new UserLocalStore(this);
        loggedUser = userLocalStore.getLoggedUser();
        IsLoggedin = userLocalStore.getUserLoggedIn();
        LoggedInUserId=userLocalStore.getUser_id();

          /* getting the {atameteres from Categories Fragmenet / Categories Adapter */
        ShopId = getIntent().getStringExtra("EXTRA_ShopId");
        ShopName = getIntent().getStringExtra("EXTRA_ShopName");
        IsOnMyList = getIntent().getStringExtra("Extra_isOnMyList");

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
        getSupportActionBar().setTitle(ShopName);
        fetchShopsSection();

        /*goole location thing*/
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else {
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();
        }

        /*end google location*/
    }

    /*Ending the updates for the location service*/
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }



    public void  fetchShopsSection(){
        AhmadApi.getActivitiesRestClient().getShopSections(ShopId+"",
                new retrofit.Callback<GetShopSectionsResponse>() {
                    @Override
                    public void success(GetShopSectionsResponse getshopsectionresponse, Response response) {
                        List<GetShopSections> getshopsections = getshopsectionresponse.getResults();
                        viewPager = (ViewPager) findViewById(R.id.viewpager);
                        setupViewPager(viewPager,getshopsections);
                        tabLayout = (TabLayout) findViewById(R.id.tabs);
                        tabLayout.setupWithViewPager(viewPager);
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
            Intent intent = new Intent(Shops.this,ShopAbout.class);
            intent.putExtra("EXTRA_ShopId", ShopId+"");
            intent.putExtra("EXTRA_ShopName", ShopName+"");
            intent.putExtra("EXTRA_IsOnMyList", IsOnMyList+"");
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_Contact) {
            Intent intent = new Intent(Shops.this,ShopContactUs.class);
            intent.putExtra("EXTRA_ShopId", ShopId+"");
            intent.putExtra("EXTRA_ShopName", ShopName+"");
            intent.putExtra("EXTRA_IsOnMyList", IsOnMyList+"");
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_Rate) {
            /*  check if user is logged in to check his list*/
            if (!userLocalStore.getUserLoggedIn()) {
                new AlertDialog.Builder(Shops.this)
                        .setTitle("Please Login!")
                        .setMessage("You need to Login to rate this shop")
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                userLocalStore.setWanLogIn(true);
                                finish();
                                startActivity(new Intent(Shops.this, MainActivity.class));
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
                Intent intent = new Intent(Shops.this, ShopRating.class);
                intent.putExtra("EXTRA_ShopId", ShopId + "");
                intent.putExtra("EXTRA_ShopName", ShopName + "");
                intent.putExtra("EXTRA_IsOnMyList", IsOnMyList+"");
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
        }
        if (id == R.id.action_BranchesAndLocation) {
            Intent intent = new Intent(Shops.this,ShopBranchLocation.class);
            intent.putExtra("EXTRA_ShopId", ShopId+"");
            intent.putExtra("EXTRA_ShopName", ShopName+"");
            intent.putExtra("EXTRA_IsOnMyList", IsOnMyList+"");
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_Report) {
            Intent intent = new Intent(Shops.this,ShopReport.class);
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
            Intent intent = new Intent(Shops.this,HomePage.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.nav_contactus) {
            Intent intent = new Intent(Shops.this,ContactUs.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.navPrivacy) {
            Intent intent = new Intent(Shops.this,PrivacyAndPolicy.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_setting) {


        }else if (id == R.id.nav_logout) {
            userLocalStore.clearUserData();
            userLocalStore.setUserLoggedIn(false);
            userLocalStore.setWanLogIn(true);
            finish();
            startActivity(new Intent(Shops.this, MainActivity.class));
            // System.exit(1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*tabs shit*/
    private void setupViewPager( ViewPager viewPager ,List<GetShopSections> getshopsections ) {
         ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i=0 ;i< getshopsections.size();i++) {
            adapter.addFragment(new ShopsFragement(getshopsections.get(i).getSectionDescription(),ShopId
            ,getshopsections.get(i).getSectionId()+"" ,ShopName+""), getshopsections.get(i).getSectionDescription());
        }
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        settingRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection Suspended!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed!", Toast.LENGTH_SHORT).show();
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, 90000);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Current Location", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }
    /*Method to get the enable location settings dialog*/
    public void settingRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);    // 10 seconds, in milliseconds
        mLocationRequest.setFastestInterval(1000);   // 1 second, in milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(Shops.this, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case 1000:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(this, "Location Service not Enabled", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if (mLastLocation != null) {
                //_progressBar.setVisibility(View.INVISIBLE);
                _latitude =("" + String.valueOf(mLastLocation.getLatitude()));
                _longitude =("" + String.valueOf(mLastLocation.getLongitude()));
                userLocalStore.setUserLocation(_latitude,_longitude);
                Log.d("5ya6","here");
            } else {
                /*if there is no last known location. Which means the device has no data for the loction currently.
                * So we will get the current location.
                * For this we'll implement Location Listener and override onLocationChanged*/
                Log.i("Current Location", "No data for location found");

                if (!mGoogleApiClient.isConnected())
                    mGoogleApiClient.connect();

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, Shops.this);
            }

        } else {
            /*Getting the location after aquiring location service*/
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if (mLastLocation != null) {
                //_progressBar.setVisibility(View.INVISIBLE);
                _latitude =("" + String.valueOf(mLastLocation.getLatitude()));
                _longitude =("" + String.valueOf(mLastLocation.getLongitude()));
                userLocalStore.setUserLocation(_latitude,_longitude);
            } else {
                /*if there is no last known location. Which means the device has no data for the loction currently.
                * So we will get the current location.
                * For this we'll implement Location Listener and override onLocationChanged*/
                Log.i("Current Location", "No data for location found");

                if (!mGoogleApiClient.isConnected())
                    mGoogleApiClient.connect();

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, Shops.this);
            }
        }
    }

    // Get permission result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted

                } else {
                    // permission was not granted
                }
                return;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
       // _progressBar.setVisibility(View.INVISIBLE);
        _latitude =("" + String.valueOf(mLastLocation.getLatitude()));
        _longitude =("" + String.valueOf(mLastLocation.getLongitude()));
        userLocalStore.setUserLocation(_latitude,_longitude);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


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
                                Toast.makeText(Shops.this,getGeneralresponse.getmessage()+"",Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Shops.this,getGeneralresponse.getmessage()+"",Toast.LENGTH_SHORT).show();
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
