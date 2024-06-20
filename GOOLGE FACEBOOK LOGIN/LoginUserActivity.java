package com.ep.ai.hd.live.wallpaper.activity.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.eventopackage.epads.utils.LogUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ep.ai.hd.live.wallpaper.DataModel.LoginResponse;
import com.ep.ai.hd.live.wallpaper.Dialogs.NetworkDialog;
import com.ep.ai.hd.live.wallpaper.Dialogs.UpLoading;
import com.ep.ai.hd.live.wallpaper.R;
import com.ep.ai.hd.live.wallpaper.ViewModel.UserViewModel;
import com.ep.ai.hd.live.wallpaper.activity.Comman.ForgetPasswordActivity;
import com.ep.ai.hd.live.wallpaper.activity.DashboardActivity;
import com.ep.ai.hd.live.wallpaper.databinding.ActivitySigninBinding;
import com.ep.ai.hd.live.wallpaper.interfaces.DialogCallBack;
import com.ep.ai.hd.live.wallpaper.utils.MyAppClass;
import com.ep.ai.hd.live.wallpaper.utils.MyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginUserActivity extends AppCompatActivity {
    private static final String TAG = "SigninActivity";
    Activity activity;
    ActivitySigninBinding binding;
    UserViewModel userViewModel;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    UpLoading upLoading;
    NetworkDialog networkDialog;
    private boolean isPasswordVisiable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        todo();
    }

    private void todo() {
        upLoading = new UpLoading(activity, "Loading...");
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//        getGoogleSignIn();

        if (MyAppClass.appPrefrenceHelper.getRememberme()) {
            binding.edUsername.setText(MyAppClass.appPrefrenceHelper.getUserName());
            binding.edEmail.setText(MyAppClass.appPrefrenceHelper.getEmail());
            binding.edPassword.setText(MyAppClass.appPrefrenceHelper.getPassword());
        } else {
            binding.edUsername.setText(MyAppClass.appPrefrenceHelper.getUserName());
        }
        facebookLogin();


        binding.boxRemeber.setChecked(MyAppClass.appPrefrenceHelper.getRememberme());
        binding.boxRemeber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MyAppClass.appPrefrenceHelper.setRememberme(isChecked);
                } else {
                    MyAppClass.appPrefrenceHelper.setRememberme(isChecked);
                }
            }
        });
        binding.layoutSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyController.isOnline(activity)) {
                    if (!binding.edUsername.getText().equals("") && !binding.edEmail.getText().equals("") && !binding.edPassword.getText().equals("")) {
                        if (binding.edEmail.getText().toString().matches(emailPattern)) {
                            upLoading.show();
                            LoginUser();
                        } else {
                            upLoading.dismiss();
                            MyController.shoToast(activity, "Please fill Correct Details");
                        }
                    }
                } else {
                    networkDialog = new NetworkDialog(activity, new DialogCallBack() {
                        @Override
                        public void onButtonClicked() {
                            if (MyController.isOnline(activity)) {
                                binding.layoutSignin.performClick();
                            } else {
                                startActivity(new Intent(Settings.ACTION_SETTINGS));
                            }

                        }
                    });
                    networkDialog.show(getSupportFragmentManager(), "");
                }

            }
        });

        binding.tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, ForgetPasswordActivity.class));
            }
        });

        binding.layoutGoogle.setOnClickListener(v -> {
            loginwithgoogle();

        });


        binding.layoutFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.logInWithReadPermissions(activity, Arrays.asList("email", "public_profile", "user_brithday"));
            }
        });

        binding.tvSignUpAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, SignupActivity.class));
            }
        });

        binding.ivSeePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ivSeePassword.setVisibility(View.GONE);
                binding.ivSeeVPassword.setVisibility(View.VISIBLE);
                binding.edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.edPassword.setSelection(binding.edPassword.length());
            }
        });

        binding.ivSeeVPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ivSeePassword.setVisibility(View.VISIBLE);
                binding.ivSeeVPassword.setVisibility(View.GONE);
                binding.edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.edPassword.setSelection(binding.edPassword.length());
            }
        });

    }


    GoogleSignInClient mGoogleSignInClient;

    public void loginwithgoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestId()
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);

        try {
            LogUtils.logE(TAG, " signOut");
            mGoogleSignInClient.signOut();
        } catch (Exception e) {
            LogUtils.logE(TAG, "catch: getLastSignedInAccount");
        }

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);

        if (account != null) {
            LogUtils.logE(TAG, "loginwithgoogle: getLastSignedInAccount");
        } else {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            resultLauncher.launch(signInIntent);
        }
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    LogUtils.logE(TAG, "getAccount:---------> " + googleSignInAccount.getAccount());
                    LogUtils.logE(TAG, "getEmail:---------> " + googleSignInAccount.getEmail());
                    LogUtils.logE(TAG, "getGivenName:---------> " + googleSignInAccount.getGivenName());
                    LogUtils.logE(TAG, "getAccount:---------> " + googleSignInAccount.getAccount());
                    LogUtils.logE(TAG, "getId:---------> " + googleSignInAccount.getId());
                    LogUtils.logE(TAG, "getDisplayName:---------> " + googleSignInAccount.getDisplayName());
                    LogUtils.logE(TAG, "getPhotoUrl:---------> " + googleSignInAccount.getPhotoUrl());
                    LogUtils.logE(TAG, "getIdToken:---------> " + googleSignInAccount.getIdToken());
                    upLoading = new UpLoading(activity, "Please wait..");
                    upLoading.show();
                    veriFiedUserByBackend(googleSignInAccount.getEmail());
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });
    public void veriFiedUserByBackend(String vEmail) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("vEmail", vEmail.toString().trim());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        userViewModel.setGoogleLogin(requestBody).observe(this, new Observer<JsonElement>() {
            @Override
            public void onChanged(JsonElement jsonElement) {
                if (jsonElement != null) {
                    if (upLoading != null && upLoading.isShowing()) {
                        upLoading.dismiss();
                    }
                    if (jsonElement.isJsonObject()) {
                        JsonObject object = jsonElement.getAsJsonObject();
                        JsonObject dataObject = object.getAsJsonObject("data");
                        String vLoginToken = dataObject.get("vLoginToken").getAsString();
                        LogUtils.logE(TAG, "onChanged: -------> Login Token " + vLoginToken);
                        MyAppClass.appPrefrenceHelper.setLoginToken(vLoginToken);


                    }

                    Toast.makeText(activity, "Login Successfully", Toast.LENGTH_SHORT).show();
                    MyAppClass.appPrefrenceHelper.setSignUpSuccess(true);
                    MyAppClass.appPrefrenceHelper.setLoginSuccess(true);
                    MyAppClass.appPrefrenceHelper.setFromGmail(true);
                    startActivity(new Intent(activity, DashboardActivity.class));
                } else {
                    if (upLoading != null && upLoading.isShowing()) {
                        upLoading.dismiss();
                    }
                    Toast.makeText(activity, "Data not Fetch Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void facebookLogin() {
        FacebookSdk.setApplicationId(activity.getString(R.string.facebookappid));
        FacebookSdk.sdkInitialize(activity);
        FacebookSdk.setClientToken("2ab593f8a251b064feaaa1a8b3be26b7");
        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();
        FacebookSdk.setAutoLogAppEventsEnabled(false);

        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
        AppEventsLogger logger = AppEventsLogger.newLogger(activity);
        logger.getApplicationId();
        logger.logEvent(getClass().getSimpleName());

        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        if (object != null) {
                            try {
                                String name = object.getString("name");
                                String email = object.getString("email");
                                String fbUserID = object.getString("id");

                                disconnectFromFacebook();

                                // do action after Facebook login success
                                // or call your API
                            } catch (JSONException |
                                     NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString(
                        "fields",
                        "id, name, email, gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                LogUtils.logE("LoginScreen", "---onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                // here write code when get error
                LogUtils.logE("LoginScreen", "----onError: "
                        + error.getMessage());
            }
        });
    }

    public void disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return;
        }
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {
                        LoginManager.getInstance().logOut();
                    }
                })
                .executeAsync();
    }

    private void LoginUser() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("vEmail", Objects.requireNonNull(binding.edEmail.getText()).toString().trim());
            jsonObject.put("vPassword", Objects.requireNonNull(binding.edPassword.getText()).toString().trim());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        userViewModel.userLogin(requestBody).observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse signinResponse) {
                if (signinResponse != null) {
                    LogUtils.logE(TAG, "onChanged: ------> not null ");
                    if (signinResponse.getiStatusCode() == 200) {
                        MyAppClass.appPrefrenceHelper.setLoginToken(signinResponse.getData().getvLoginToken());
                        if (binding.boxRemeber.isChecked()) {
                            MyAppClass.appPrefrenceHelper.setEmail(binding.edEmail.getText().toString().trim());
                            MyAppClass.appPrefrenceHelper.setPassword(binding.edPassword.getText().toString().trim());
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                upLoading.dismiss();
                                MyController.shoToast(activity, "Login Successfully");
                                MyAppClass.appPrefrenceHelper.setLoginSuccess(true);
                                startActivity(new Intent(activity, DashboardActivity.class));
                            }
                        }, 1000);

                    } else if (signinResponse.getiStatusCode() == 400) {
                        upLoading.dismiss();
                        MyController.shoToast(activity, signinResponse.getvMessage());
                    }
                } else {
                    upLoading.dismiss();
                    MyController.shoToast(activity, "Invalid Credential");
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}