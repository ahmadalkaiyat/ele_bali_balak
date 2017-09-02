package www.alkaiyat.ahmad.net.ahmadalkaiyats;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Retrofit.AhmadApi;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragement extends Fragment implements View.OnClickListener {

    TextView tvLogin, tvSignup;
    Button bSignup;
    EditText etFirstName, etLastName, etUserName, etEmailAddress, etPassword, etPhoneNumber, etCountry;
    UserLocalStore userlocalstore;
    private View myFragmentView;
    public SignupFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_signup, container, false);
        bSignup = (Button) myFragmentView.findViewById(R.id.bSignup);
        etFirstName = (EditText) myFragmentView.findViewById(R.id.etFirstName);
        etLastName = (EditText) myFragmentView.findViewById(R.id.etLastName);
        etUserName = (EditText) myFragmentView.findViewById(R.id.etUsername);
        etEmailAddress = (EditText) myFragmentView.findViewById(R.id.etEmailAddress);
        etPassword = (EditText) myFragmentView.findViewById(R.id.etPassword);
        etPhoneNumber = (EditText) myFragmentView.findViewById(R.id.etPhoneNumber);
       etCountry = (EditText) myFragmentView.findViewById(R.id.etCountry);
        userlocalstore = new UserLocalStore(this.getContext());
        bSignup.setOnClickListener(this);
        return myFragmentView;
    }

    @Override
    public void onClick(View v) {
        boolean CheckInsertion = false;
        switch (v.getId()) {
            case R.id.bSignup:
                if (etFirstName.getText().toString().trim().equalsIgnoreCase("")) {
                    etFirstName.setError("Enter First Name");
                    CheckInsertion = true;
                }
                if (etLastName.getText().toString().trim().equalsIgnoreCase("")) {
                    etLastName.setError("Enter Last Name");
                    CheckInsertion = true;
                }
                if (etUserName.getText().toString().trim().equalsIgnoreCase("")) {
                    etUserName.setError("Enter USerName");
                    CheckInsertion = true;
                }
                if (etEmailAddress.getText().toString().trim().equalsIgnoreCase("")) {
                    etEmailAddress.setError("Enter EmailAddress");
                    CheckInsertion = true;
                }
                if (etPassword.getText().toString().trim().equalsIgnoreCase("")) {
                    etPassword.setError("Enter Password");
                    CheckInsertion = true;
                }
                if (etPhoneNumber.getText().toString().trim().equalsIgnoreCase("")) {
                    etPhoneNumber.setError("Enter PhoneNumber");
                    CheckInsertion = true;
                }
                if (etCountry.getText().toString().trim().equalsIgnoreCase("")) {
                    etCountry.setError("Enter Country");
                    CheckInsertion = true;
                }

                if (CheckInsertion) {
                    return;
                } else {
                    String FirstName = etFirstName.getText().toString();
                    String LastName = etLastName.getText().toString();
                    String UserName = etUserName.getText().toString();
                    String EmailAddress = etEmailAddress.getText().toString();
                    String Password = etPassword.getText().toString();
                    String Phonenumber = etPhoneNumber.getText().toString();
                    String Country = etCountry.getText().toString();
                    User user = new User(UserName, Password,FirstName,LastName,EmailAddress,Phonenumber+"",Country,1);
                    authinticate_user(user,userlocalstore);
                }
                break;
        }
    }


    private void authinticate_user(User user , final UserLocalStore userlocalstore)
    {
        AhmadApi.getActivitiesRestClient().register(user.username, user.password,
                user.first_name,user.last_name,user.email_address,user.phone_number,user.country,user.status
                ,new retrofit.Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        Toast.makeText(getActivity(), user.message, Toast.LENGTH_SHORT).show();
                        if (user.success == 1) {

                            userlocalstore.storeUserData(user);
                            userlocalstore.setUserLoggedIn(true);
                            getActivity().finish();
                            startActivity(new Intent(getActivity(), HomePage.class));
                            getActivity().finish();
                        } else {

                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {


                    }
                });

    }
}
