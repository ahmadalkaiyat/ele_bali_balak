package www.alkaiyat.ahmad.net.ahmadalkaiyats.AppMenuePages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Retrofit.AhmadApi;
import retrofit.RetrofitError;
import retrofit.client.Response;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.HomePage;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.MainActivity;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetContactUsDetails;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetGeneralresponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopContactDetails;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.R;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.UserLocalStore;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.shopsDetails.ShopAbout;

public class ContactUs extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener , View.OnClickListener {
    String ShopId,ShopName;
    UserLocalStore userLocalStore;
    String loggedUser;
    TextView ContactUsPhoneNumberTV,ContactUsEmailAddressTV,ContactUsSendButton;
    EditText ContactUsEmail,ContactUsFeedbackBody,ContactUsName,ContactPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        userLocalStore = new UserLocalStore(this);
        loggedUser = userLocalStore.getLoggedUser();


        /* get the Page element Details*/
        ContactUsPhoneNumberTV = (TextView) findViewById(R.id.ContactUsPhoneNumberTV);
        ContactUsPhoneNumberTV.setOnClickListener(this);
        ContactUsEmailAddressTV = (TextView) findViewById(R.id.ContactUsEmailAddressTV);
        ContactUsEmailAddressTV.setOnClickListener(this);
        ContactUsEmail = (EditText) findViewById(R.id.ContactUsEmail);
        ContactUsFeedbackBody = (EditText) findViewById(R.id.ContactUsFeedbackBody);
        ContactUsName = (EditText) findViewById(R.id.ContactUsName);
        ContactPhoneNumber = (EditText) findViewById(R.id.ContactPhoneNumber);
        ContactUsSendButton = (TextView) findViewById(R.id.ContactUsSendButton);
        ContactUsSendButton.setOnClickListener(this);

        /* end of paraeter getting*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

         /* Navigation Drawer Code*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        getSupportActionBar().setTitle("Contact Us");

        ContactUsPhoneNumberTV.setText("00962776801503");
        get_ContactUsDetails();
    }


    private void get_ContactUsDetails(){
        AhmadApi.getActivitiesRestClient().getContactUsDetails(
                new retrofit.Callback<GetContactUsDetails>() {
                    @Override
                    public void success(GetContactUsDetails getContactUsDetails, Response response) {
                        ContactUsPhoneNumberTV.setText(getContactUsDetails.getPhoneNumber()+"");
                        ContactUsEmailAddressTV.setText(getContactUsDetails.getEmailAddress()+"");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        // ContactUsPhoneNumber.setText("Cant About Details! :(");

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
       // getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(ContactUs.this,ShopAbout.class);
            intent.putExtra("EXTRA_ShopId", ShopId+"");
            intent.putExtra("EXTRA_ShopName", ShopName+"");
            startActivity(intent);
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
        } else if (id == R.id.nav_contactus) {

        }
        else if (id == R.id.nav_home) {
            finish();
            Intent intent = new Intent(ContactUs.this,HomePage.class);
            startActivity(intent);
            return true;

        }else if (id == R.id.navPrivacy) {
            Intent intent = new Intent(ContactUs.this,PrivacyAndPolicy.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_setting) {


        }else if (id == R.id.nav_logout) {
            userLocalStore.clearUserData();
            userLocalStore.setUserLoggedIn(false);
            userLocalStore.setWanLogIn(true);
            finish();
            startActivity(new Intent(ContactUs.this, MainActivity.class));
            // System.exit(1);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /*tabs shit*/
    private void setupViewPager( ViewPager viewPager ) {
        ContactUs.ViewPagerAdapter adapter =  new ContactUs.ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        boolean checkInsertion = false;
        switch (v.getId()) {
            case R.id.ContactUsSendButton:
                if (ContactUsName.getText().toString().trim().equalsIgnoreCase("")) {
                    ContactUsName.setError("enternter your name");
                    checkInsertion = true;
                }
                if (ContactUsEmail.getText().toString().trim().equalsIgnoreCase("")) {
                    ContactUsEmail.setError("Enter email address");
                    checkInsertion = true;
                }
                if (ContactUsFeedbackBody.getText().toString().trim().equalsIgnoreCase("")) {
                    ContactUsFeedbackBody.setError("Enter your notes");
                    checkInsertion = true;
                }
                if (ContactPhoneNumber.getText().toString().trim().equalsIgnoreCase("")) {
                    ContactPhoneNumber.setError("Enter your notes");
                    checkInsertion = true;
                }
                if (checkInsertion) {
                    return;
                } else {
                    String CName = ContactUsName.getText().toString();
                    String CEmail = ContactUsEmail.getText().toString();
                    String CBody = ContactUsFeedbackBody.getText().toString();
                    String CNumber = ContactPhoneNumber.getText().toString();
                    send_contact_us(CName,CEmail,CBody,CNumber);
                }

                break;


            case R.id.ContactUsPhoneNumberTV:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ContactUsPhoneNumberTV.getText()));
                startActivity(intent);
                break;
            case R.id.ContactUsEmailAddressTV:
                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + (ContactUsEmailAddressTV.getText()) +
                        "?subject=" + Uri.encode("") +
                        "&body=" + Uri.encode("");
                Uri uri = Uri.parse(uriText);

                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send mail..."));
                break;
        }
    }

    private void send_contact_us( String Cuser,String Cemail, String Cbody,String CNumber){
        AhmadApi.getActivitiesRestClient().ContactUs(Cuser,CNumber,Cemail,Cbody,
                new retrofit.Callback<GetGeneralresponse>() {
                    @Override
                    public void success(GetGeneralresponse getGeneralresponse, Response response) {
                        Toast.makeText(ContactUs.this,getGeneralresponse.getmessage()+"",Toast.LENGTH_SHORT).show();
                        Clear_data();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        // ContactUsPhoneNumber.setText("Cant About Details! :(");

                    }
                });
    }
    private void Clear_data (){
        ContactUsEmail.setText("");
        ContactUsFeedbackBody.setText("");
        ContactUsName.setText("");
        ContactPhoneNumber.setText("");
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


