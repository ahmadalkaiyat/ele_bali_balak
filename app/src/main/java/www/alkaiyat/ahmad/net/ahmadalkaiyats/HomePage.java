package www.alkaiyat.ahmad.net.ahmadalkaiyats;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.alkaiyat.ahmad.net.ahmadalkaiyats.AppMenuePages.ContactUs;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.AppMenuePages.PrivacyAndPolicy;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.shopsDetails.ShopAbout;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener  {

    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 111;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    UserLocalStore userLocalStore;
    String loggedUser;
    ImageView imageViewhdr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        userLocalStore = new UserLocalStore(this);
        loggedUser = userLocalStore.getLoggedUser();
       imageViewhdr = (ImageView) findViewById(R.id.imageViewhdr) ;
        // imageViewhdr.setOnClickListener(this);
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

        /* Tabs Shit*/
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        /*end of tab shit*/
        getSupportActionBar().setTitle("footJai");
        ActivityCompat.requestPermissions(
                this, // Activity
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_FINE_LOCATION);
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
        //getMenuInflater().inflate(R.menu.shops2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation v;iew item clicks here.
        int id = item.getItemId();

        if(id== R.id.imageViewhdr){
        }
        if (id == R.id.nav_search) {
            // Handle the camera action
        } else if (id == R.id.nav_contactus) {
            Intent intent = new Intent(HomePage.this,ContactUs.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.navPrivacy) {
            Intent intent = new Intent(HomePage.this,PrivacyAndPolicy.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_setting) {


        }else if (id == R.id.nav_logout) {
            userLocalStore.clearUserData();
            userLocalStore.setUserLoggedIn(false);

            userLocalStore.setWanLogIn(true);
            finish();
            startActivity(new Intent(HomePage.this, MainActivity.class));
           // System.exit(1);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /*tabs shit*/
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TrendingFragement(), "Trending");
        adapter.addFragment(new CategoriesFragement(), "Categories");
        adapter.addFragment(new MyListFragement(), "My List");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Log.d("5ya6","5ya6");
       /* switch (v.getId()) {
            case R.id.imageViewhdr:
                Toast.makeText(this,"herllo",Toast.LENGTH_SHORT).show();
                break;
        }*/

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


}
