package com.example.tomek.uberallesdriver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tomek.uberallesdriver.FirebaseCouldMessaging.InstanceIdService;
import com.example.tomek.uberallesdriver.api.ApiClient;
import com.example.tomek.uberallesdriver.api.UserService;
import com.example.tomek.uberallesdriver.api.pojo.CreateAccount;
import com.example.tomek.uberallesdriver.api.pojo.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tomek.uberallesdriver.FirebaseCouldMessaging.InstanceIdService.getRegistrationToken;
import static com.example.tomek.uberallesdriver.LogedUserData.USER_NAME;
import static com.example.tomek.uberallesdriver.LogedUserData.USER_PASSWORD;
import static com.example.tomek.uberallesdriver.LogedUserData.USER_PHONE;
import static com.example.tomek.uberallesdriver.LogedUserData.USER_SURNAME;
import static com.example.tomek.uberallesdriver.LogedUserData.saveCredentials;

public class SignUpActivity extends AppCompatActivity {

    private static Context context;

    @BindView(R.id.edit_text_create_first_name)
    EditText editTextCreateFirstName;
    @BindView(R.id.edit_text_create_last_name)
    EditText editTextCreateLastName;
    @BindView(R.id.edit_text_create_password)
    EditText editTextCreatePassword;
    @BindView(R.id.edit_text_create_confirm_password)
    EditText editTextCreateConfirmPassword;
    @BindView(R.id.edit_text_create_phone_number)
    EditText editTextCreatePhoneNumber;

    @OnClick(R.id.btn_signup)
    public void onSignUpButtonClick(View v) {
        Toast.makeText(this, "Tego sie nie spodziewales", Toast.LENGTH_SHORT).show();
        String password = editTextCreatePassword.getText().toString();
        String phoneNumber = editTextCreatePhoneNumber.getText().toString();
        String firstName = editTextCreateFirstName.getText().toString();
        String lastName = editTextCreateLastName.getText().toString();
        new InstanceIdService().onTokenRefresh();
        if (password.equals(editTextCreateConfirmPassword.getText().toString())) {
            if (phoneNumber.length() > 0 &&
                    firstName.length() > 0 &&
                    lastName.length() > 0 &&
                    password.length() > 0) {
                CreateAccount account = new CreateAccount(phoneNumber, firstName, lastName, password, getRegistrationToken());
                createAccount(account);
            } else {
                Toast.makeText(this, "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        SignUpActivity.context = getApplicationContext();
        ButterKnife.bind(this);
    }

    public void createAccount(final CreateAccount account) {
        UserService loginService = ApiClient.createService(UserService.class);
        Call<User> call = loginService.createAccount(account);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    USER_NAME = response.body().firstName;
                    USER_SURNAME = response.body().lastName;
                    USER_PHONE = response.body().phoneNumber;
                    USER_PASSWORD = account.password;
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    Log.d("OK", "udalo sie " + USER_PHONE + USER_PASSWORD);
                    startActivity(intent);
                    finish();
                } else {
                    Log.d("Error", "cos pposzlo nie tak");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
            }
        });
    }
}
