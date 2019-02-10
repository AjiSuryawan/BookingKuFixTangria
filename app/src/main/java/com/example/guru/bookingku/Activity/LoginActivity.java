package com.example.guru.bookingku.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.guru.bookingku.Activity.Main.MainActivity;
import com.example.guru.bookingku.Model.BookingResponse;
import com.example.guru.bookingku.Network.BookingClient;
import com.example.guru.bookingku.Network.BookingService;
import com.example.guru.bookingku.R;
import com.example.guru.bookingku.Service.FirebaseMessagingService;
import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    private LoginButton btnFacebook;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    EditText txtusername;
    EditText txtpassword;
    CallbackManager callbackManager;
    private AccessToken accessToken;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cetakhash();
        TextView tvregister = (TextView) findViewById(R.id.txtregister);
        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        dialog = new ProgressDialog(LoginActivity.this);
        pref = getSharedPreferences("login", MODE_PRIVATE);
        callbackManager = CallbackManager.Factory.create();
        btnFacebook = findViewById(R.id.facebookSigninBtn);
        //cetakhash();
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, signInOptions);
        SignInButton googleSignButton = findViewById(R.id.sign_in_button);
        googleSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("lala3", "onActivityResult: ");
                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent, 997);
            }
        });
        txtusername = (EditText) findViewById(R.id.txtusername);
        txtpassword = (EditText) findViewById(R.id.txtpassword);
        Button btnlogin = (Button) findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = txtusername.getText().toString().trim();
                final String password = txtpassword.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "please fill my heart first to send a request :(", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.setMessage("Login process");
                    dialog.show();
                    String token = getSharedPreferences("firebase_token", MODE_PRIVATE).getString("firebase_token", "");
                    BookingService bookingService = BookingClient.getRetrofit().create(BookingService.class);
                    Call<BookingResponse> call = bookingService.login(username, password, token);
                    call.enqueue(new Callback<BookingResponse>() {
                        @Override
                        public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                            try {
                                boolean success = response.body().getSuccess();
                                if (response.isSuccessful()) {
                                    if (success) {
                                        editor = pref.edit();
                                        editor.putInt("userid", response.body().getUserId());
                                        editor.apply();
                                        Log.d("iduser", "onResponse: " + response.body().getUserId());
                                        Intent in = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(in);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Something wrong is happen", Toast.LENGTH_SHORT).show();
                                    }
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                }
                                Log.e("Tag", "onResponse: " + response.code());
                            }
                            catch (Exception e){

                            }
                        }

                        @Override
                        public void onFailure(Call<BookingResponse> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Cannot connect to server", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        btnFacebook.setReadPermissions(Arrays.asList(EMAIL));
        if (accessToken != null) {
            accessToken = com.facebook.AccessToken.getCurrentAccessToken();
            LoginManager.getInstance().logOut();
        }
        btnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String userDetil = response.getRawResponse();
                        try {
                            JSONObject jsonObject = new JSONObject(userDetil);
                            String fbId = jsonObject.getString("id");
                            String realName = jsonObject.optString("name", "");
                            //String username = jsonObject.optString("first_name", "") + jsonObject.optString("last_name", "") + jsonObject.getString("id");
                            String email = jsonObject.optString("email", "Email doesn't exist");
                            String avatar = "https://graph.facebook.com/" + fbId + "/picture?type=large";

                            Log.d("gambar", "onCompleted: "+avatar);
                            //proses input service
                            String token = getSharedPreferences("firebase_token", MODE_PRIVATE).getString("firebase_token", "");
                            BookingService service = BookingClient.getRetrofit().create(BookingService.class);
                            Call<BookingResponse> call = service.loginMedsos(realName, email, "facebook", avatar, token);
                            call.enqueue(new Callback<BookingResponse>() {
                                @Override
                                public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                                    try {
                                        boolean success = response.body().getSuccess();
                                        boolean isPhoneNull = response.body().getPhoneStatus(); // null = true
                                        int userId = response.body().getUserId();
                                        if (response.isSuccessful()) {
                                            if (success) {
                                                editor = pref.edit();
                                                editor.putInt("userid", userId);
                                                editor.apply();
                                                Log.e("isPhoneNull", "onResponse: " + isPhoneNull );
                                                if(!isPhoneNull) {
                                                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                                                    editor.putBoolean("phone", true);
                                                    editor.apply();
                                                    startActivity(in);
                                                    finish();
                                                } else {
                                                    Intent intent = new Intent(LoginActivity.this, InputPhone.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Something wrong is happen", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } catch (Exception e){

                                    }

                                }

                                @Override
                                public void onFailure(Call<BookingResponse> call, Throwable t) {
                                    Toast.makeText(LoginActivity.this, "Cannot connect to server", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (JSONException ignored) {
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 997) {
            Log.d("lala1", "onActivityResult: ");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            Log.d("lala2", "onActivityResult: ");
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String realName = account.getDisplayName();
            String username = account.getGivenName() + account.getId();
            String email = account.getEmail();
            String userId = account.getId();
            String avatar;
            if (account.getPhotoUrl() != null) {
                avatar = account.getPhotoUrl().toString();
            } else {
                avatar = "avatar";
            }
            Log.d("avatarku", "handleSignInResult: "+avatar);

            //intent
            String token = getSharedPreferences("firebase_token", MODE_PRIVATE).getString("firebase_token", "");
            BookingService service = BookingClient.getRetrofit().create(BookingService.class);
            Call<BookingResponse> call = service.loginMedsos(realName, email, "gmail", avatar, token);
            call.enqueue(new Callback<BookingResponse>() {
                @Override
                public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                    try {
                        Log.d("lala4", "onActivityResult: ");
                        boolean success = response.body().getSuccess();
                        boolean isPhoneNull = response.body().getPhoneStatus(); // null = true
                        int userId = response.body().getUserId();
                        if (response.isSuccessful()) {
                            if (success) {
                                editor = pref.edit();
                                editor.putInt("userid", userId);
                                editor.apply();
                                Log.e("isPhoneNull", "onResponse: " + isPhoneNull );
                                if(!isPhoneNull) {
                                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(in);
                                    finish();
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, InputPhone.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Something wrong is happen", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e){
                        Log.d("lala5", "onActivityResult: "+e.toString());
                    }
                }

                @Override
                public void onFailure(Call<BookingResponse> call, Throwable t) {
                    Log.d("lala6", "onActivityResult: "+t.toString());
                    t.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Cannot connect to server", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (ApiException ignored) {
            Log.d("lala7", "onActivityResult: ");
            ignored.printStackTrace();
        }
    }

    public void cetakhash(){
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.example.guru.bookingku", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }

}
