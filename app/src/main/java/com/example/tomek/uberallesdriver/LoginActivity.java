package com.example.tomek.uberallesdriver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tomek.uberallesdriver.FirebaseCouldMessaging.InstanceIdService;
import com.example.tomek.uberallesdriver.api.ApiClient;
import com.example.tomek.uberallesdriver.api.UserService;
import com.example.tomek.uberallesdriver.api.pojo.User;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tomek.uberallesdriver.LogedUserData.USER_NAME;
import static com.example.tomek.uberallesdriver.LogedUserData.USER_PASSWORD;
import static com.example.tomek.uberallesdriver.LogedUserData.USER_PHONE;
import static com.example.tomek.uberallesdriver.LogedUserData.USER_SURNAME;
import static com.example.tomek.uberallesdriver.LogedUserData.saveCredentials;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_phone_number)
    EditText phoneNumberEditText;
    @BindView(R.id.edit_text_password)
    EditText passwordEditText;
    private static Context context;

    @OnClick(R.id.forgot_password_text)
    public void onForgotPasswordTextClick(View v) {
        Toast.makeText(this, "Forgot password implementation", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.sing_up_text)
    public void onSingupTextClick(View v) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClick(View v) {
        String phoneNumber = phoneNumberEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (phoneNumber.length() > 0 && password.length() > 0) {
            checkCredentials(phoneNumber, password);
        } else {
            Toast.makeText(this, "Podaj dane logowania", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences("com.uberalles", Context.MODE_PRIVATE);
        String auth_id = prefs.getString("Authentication_Id", "");
        String auth_pass = prefs.getString("Authentication_Password", "");
        if (auth_id.length() > 0 && auth_pass.length() > 0) {
            checkCredentials(auth_id, auth_pass);
        }
        // create layout after checking credentials
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();
        ButterKnife.bind(this);
    }

    public void makeToast(String text) {
        Toast.makeText(this, R.string.wrong_password, Toast.LENGTH_SHORT).show();
    }

    public void makeToast() {
        Toast.makeText(this, R.string.wrong_password, Toast.LENGTH_SHORT).show();
    }

    public void checkCredentials(String phoneNumber, final String password) {
        UserService userService =
                ApiClient.createService(UserService.class, phoneNumber, password);
        Call<User> call = userService.basicLogin();
        final ProgressDialog progress = new ProgressDialog(LoginActivity.this, R.style.SpinnerTheme);
        progress.setProgressStyle(android.R.style.Widget_Material_ProgressBar_Large);
        progress.show();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    USER_NAME = response.body().firstName;
                    USER_SURNAME = response.body().lastName;
                    USER_PHONE = response.body().phoneNumber;
                    USER_PASSWORD = password;
                    saveCredentials(USER_PHONE, USER_PASSWORD, USER_NAME, USER_SURNAME, context);
                    Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);
                    startActivity(intent);
                } else {
                    // error response, no access to resource?
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        makeToast(jObjError.getString("error"));
                    } catch (Exception e) {
                        makeToast(e.getMessage());
                    }
                    response.errorBody();
                    makeToast();
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (t.getMessage() != null) {
                    makeToast(t.getMessage());
                }
                progress.dismiss();
            }
        });
    }
}
