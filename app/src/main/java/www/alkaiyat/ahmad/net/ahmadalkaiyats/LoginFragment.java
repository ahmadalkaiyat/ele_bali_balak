package www.alkaiyat.ahmad.net.ahmadalkaiyats;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Retrofit.AhmadApi;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import  com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    TextView tvLogin,tvGuest;
    EditText etUserName, etPassword;
    UserLocalStore userlocalstore;
    private View myFragmentView;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_login, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);  //  to avoid automatically appear android keyboard when activity start

        tvLogin = (TextView)  myFragmentView.findViewById(R.id.tvLogin);
        etUserName = (EditText) myFragmentView.findViewById(R.id.usernameET);
        etPassword = (EditText)myFragmentView.findViewById(R.id.passwordET);
        tvGuest = (TextView) myFragmentView.findViewById(R.id.tvGuest);
        tvGuest.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        userlocalstore = new UserLocalStore(this.getContext());

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)myFragmentView.findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d("5ya6","User ID: "
                        + loginResult.getAccessToken().getUserId()
                        + "\n" +
                        "Auth Token: "
                        + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

        /******the part to check if the user al already logged in*************/
        if (authinticate() == true) {
            getActivity().finish();
            startActivity(new Intent(getActivity(),HomePage.class));
        } else {
            // user can browse the app  even without logging in , but without some features
            if (userlocalstore.ifWantLogin()){
                // user want to Login dont Direct Him;
                userlocalstore.setWanLogIn(false);
            }
            else{
                //user dont want to Login  keep brwosing
                getActivity().finish();
                startActivity(new Intent(getActivity(),HomePage.class));
            }
        }
        /******End part to check if the user al already logged in*************/
        return myFragmentView;
    }

    private boolean authinticate() {
        return userlocalstore.getUserLoggedIn();
    }


    @Override
    public void onClick(View v) {
        boolean checkInsertion = false;
        switch (v.getId()) {
            case R.id.tvLogin:
                if (etUserName.getText().toString().trim().equalsIgnoreCase("")) {
                    etUserName.setError("Enter First Name");
                    checkInsertion = true;
                }
                if (etPassword.getText().toString().trim().equalsIgnoreCase("")) {
                    etPassword.setError("Enter PassWord");
                    checkInsertion = true;
                }
                if (checkInsertion) {
                    return;
                } else {
                    String UserName = etUserName.getText().toString();
                    String Password = etPassword.getText().toString();
                    User user = new User(UserName, Password);
                    authinticate_user(user,userlocalstore);
                }

                break;

            case R.id.tvGuest:
                getActivity().finish();
                startActivity(new Intent(getActivity(),HomePage.class));
                break;
        }
    }

    private void authinticate_user(User user , final UserLocalStore userlocalstore)
    {
        AhmadApi.getActivitiesRestClient().loginUser(user.username, user.password, new retrofit.Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Toast.makeText(getActivity(), user.message, Toast.LENGTH_SHORT).show();
                if (user.success == 1) {
                    userlocalstore.storeUserData(user);
                    userlocalstore.setUserLoggedIn(true);
                    getActivity().finish();
                    startActivity(new Intent(getActivity(),HomePage.class));
                } else {
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(),"Something went wrong!, it could be your internet connection!",Toast.LENGTH_SHORT).show();
            }
        });

    }


}
