package com.android.orc.ocrapplication.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dashboard.DashBoardActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by RUNGNUENG on 15/10/2560.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private LoginButton login_facebook;
    private CallbackManager callbackManager;

    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;

    TextView tv_name, tv_email, tv_id, login_guest;
    String first_name="", last_name="", email="", id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tv_id = findViewById(R.id.tv_id);
        login_guest = findViewById(R.id.login_guest);

        login_facebook = findViewById(R.id.login_facebook);
        login_facebook.setReadPermissions("public_profile");


        callbackManager = CallbackManager.Factory.create();
        setupTokenTracker();
        setupProfileTracker();

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        login_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                tv_name.setText(constructWelcomeMessage(profile));
                goMainScreen();



            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });
         login_guest.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(),DashBoardActivity.class);
                 intent.putExtra("login_guest",login_guest.getText().toString());
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             }
         });

    }

    private String constructWelcomeMessage(Profile profile) {
        StringBuffer stringBuffer = new StringBuffer();
        if (profile != null) {
            stringBuffer.append(profile.getName());
        }
        return stringBuffer.toString();
    }

    public void displayUserInfo(JSONObject object) {
        try {
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            email = object.getString("email");
            id = object.getString("id");
//            stringFacebook.setFirst_name(object.getString("first_name"));
//            stringFacebook.setLast_name(object.getString("last_name"));
//            stringFacebook.setEmail(object.getString("email"));
//            stringFacebook.setId(object.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv_name.setText(first_name + " " + last_name);
        tv_email.setText("Email : " + email);
        tv_id.setText("ID : " + id);
        Toast.makeText(this,tv_name.getText(),Toast.LENGTH_SHORT).show();
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, DashBoardActivity.class);
        intent.putExtra("name",tv_name.getText().toString());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setupTokenTracker() {
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                Log.d("VIVZ", "" + currentAccessToken);
            }
        };
    }

    private void setupProfileTracker() {
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
//                Log.d("VIVZ", "" + currentProfile);
                tv_name.setText(constructWelcomeMessage(currentProfile));
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        tv_name.setText(constructWelcomeMessage(profile));

    }



    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}
