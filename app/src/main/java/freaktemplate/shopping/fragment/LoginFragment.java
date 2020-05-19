package freaktemplate.shopping.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.skoumal.fragmentback.BackFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Objects;

import freaktemplate.shopping.R;
import freaktemplate.shopping.activity.ForgetPasswordActivity;
import freaktemplate.shopping.activity.HomeActivity;
import freaktemplate.shopping.activity.RegisterActivity;
import freaktemplate.shopping.getset.login.Data;
import freaktemplate.shopping.getset.login.Data_;
import freaktemplate.shopping.getset.login.Loginpojjo;
import freaktemplate.shopping.utils.SPmanager;
import freaktemplate.shopping.utils.UtilHelper;

import static freaktemplate.shopping.activity.HomeActivity.txt_cartitem;
import static freaktemplate.shopping.activity.HomeActivity.txt_wishitem;


public class LoginFragment extends Fragment implements BackFragment, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LoginFragment";
    private static final int RC_SIGN_IN = 0;
    private View rootview;
    private Context context;
    private CallbackManager callbackManager;
    private String name;
    private String email;
    private String ppic;
    private String imagefb;
    private String login_type;
    private String regId;
    private Gson gson;
    private GoogleSignInClient mGoogleSignInClient;
    private EditText edt_password, mail;
    private GoogleApiClient mGoogleApiClient;

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_login, container, false);
        context = rootview.getContext();
        callbackManager = CallbackManager.Factory.create();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .enableAutoManage(Objects.requireNonNull(getActivity()), this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API)
                    .build();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        } catch (Exception e) {
            e.printStackTrace();
        }

        inite();
        return rootview;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
    }

    private void inite() {
        TextView txt_register = rootview.findViewById(R.id.txt_register);
        TextView txt_login = rootview.findViewById(R.id.txt_login);
        ImageView ic_facebook = rootview.findViewById(R.id.ic_facebook);
        ImageView ic_gmail = rootview.findViewById(R.id.ic_gmail);
        TextView txtForget = rootview.findViewById(R.id.txtForget);
        mail = rootview.findViewById(R.id.mail);
        edt_password = rootview.findViewById(R.id.edt_password);
        ic_facebook.setOnClickListener(this);
        ic_gmail.setOnClickListener(this);
        txt_register.setOnClickListener(this);
        txt_login.setOnClickListener(this);
        txtForget.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {
        assert getFragmentManager() != null;
        getFragmentManager().popBackStack();
        return false;
    }

    @Override
    public int getBackPriority() {
        return NORMAL_BACK_PRIORITY;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_facebook:
                login_type = "2";
                facebooklogin();
                break;
            case R.id.ic_gmail:
                login_type = "3";
                gmaillogin();
                break;
            case R.id.txt_login:
                login_type = "1";
                validation();
                break;
            case R.id.txtForget:
                Intent intent = new Intent(context, ForgetPasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.txt_register:
                intent = new Intent(rootview.getContext(), RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }


    private void validation() {
        String email_ = mail.getText().toString().trim();
        String password_ = edt_password.getText().toString().trim();
        if (isValidEmail(email_)) {
            if (!password_.isEmpty()) {
                getlogin(email_, password_);
            } else {
                edt_password.setError(getString(R.string.enter_password));
            }

        } else {
            if (email_.isEmpty()) {
                mail.setError(getString(R.string.enter_email));
            } else {
                mail.setError(getString(R.string.invalid_email));
            }
        }
    }

    private void gmaillogin() {
        try {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void facebooklogin() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getUserDetail(loginResult);
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("check1", exception.getMessage());
                        if (exception instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }

                    }
                });
        LoginManager.getInstance().logInWithReadPermissions(LoginFragment.this, Arrays.asList("email"));

    }

    @Override
    public void onResume() {
        super.onResume();
        rootview.setFocusableInTouchMode(true);
        rootview.requestFocus();
        rootview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getActivity().startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            Log.e(TAG, "display name: " + acct);
            try {
                name = acct.getDisplayName();
                String query = URLEncoder.encode(String.valueOf(acct.getPhotoUrl()), "utf-8");
                imagefb = query;
                email = acct.getEmail();
                regId = acct.getId();
                mGoogleApiClient.clearDefaultAccountAndReconnect();
                getlogin(email, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ApiException e) {
            Log.e(TAG, "signInResult:failed code=" + e.getLocalizedMessage());
        }
    }


    private void getUserDetail(LoginResult loginResult) {
        UtilHelper.showdialog(context);
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {
                        Log.e("User Data", json_object.toString());
                        String json = json_object.toString();
                        try {
                            JSONObject profile = new JSONObject(json);
                            name = profile.getString("name");
                            name = name.replace("%20", " ");
                            regId = profile.getString("id");
                            email = profile.getString("email");
                            JSONObject picture = profile.getJSONObject("picture");
                            JSONObject data = picture.getJSONObject("data");
                            ppic = data.getString("url");
                            if (name != null) {
                                try {
                                    String query = URLEncoder.encode(ppic, "utf-8");
                                    imagefb = String.valueOf(query);
                                    UtilHelper.hidedialog();
                                    getlogin(email, "");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                    UtilHelper.hidedialog();
                                }
                                Log.d("fbimage", "" + imagefb);

                            }
                        } catch (JSONException e) {
                            UtilHelper.hidedialog();
                            Toast.makeText(context, getString(R.string.email_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture");
        data_request.setParameters(permission_param);
        data_request.executeAsync();
    }

    private void getlogin(String email_, String password_) {
        UtilHelper.showdialog(context);
        String url = getString(R.string.link) + "login";
        Log.e(TAG, "getlogin: " + url);
        String token = SPmanager.getPreference(context, "token");
        if (token == null) {
            token = "abc";
        }
        ANRequest.GetRequestBuilder getRequestBuilder = new ANRequest.GetRequestBuilder(url);
        getRequestBuilder.addQueryParameter("login_type", login_type);
        getRequestBuilder.addQueryParameter("token", token);
        getRequestBuilder.addQueryParameter("token_type", "1");
        getRequestBuilder.addQueryParameter("email", email_);
        getRequestBuilder.addQueryParameter("name", name);
        if (!login_type.equals("1")) {
            if (!imagefb.equals("null")) {
                getRequestBuilder.addQueryParameter("Image", imagefb);
            }
            getRequestBuilder.addQueryParameter("soical_id", regId);
        } else {
            getRequestBuilder.addQueryParameter("password", password_);
        }
        ANRequest anRequest = getRequestBuilder.build();
        anRequest.getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "onResponse: " + response);
                try {
                    Loginpojjo loginpojjo = gson.fromJson(response, Loginpojjo.class);
                    Data data = loginpojjo.getData();
                    if (data.getStatus().equals(1)) {
                        Data_ data_ = data.getData();
                        SPmanager.saveValue(context, "userID", String.valueOf(data_.getId()));
                        SPmanager.saveValue(context, "email", data_.getEmail());
                        SPmanager.saveValue(context, "user_name", data_.getFirstName());
                        SPmanager.saveValue(context, "first_name", data_.getFirstName());
                        SPmanager.saveValue(context, "user_phone", data_.getPhone());
                        SPmanager.saveValue(context, "user_address", data_.getAddress());
                        SPmanager.saveValue(context, "profile", data_.getProfilePic());
                        txt_cartitem.setText(String.valueOf(data_.getCart()));
                        txt_wishitem.setText(String.valueOf(data_.getTotalwish()));
                        Fragment fragment;
                        fragment = new UserFragment();
                        if (getActivity().getSupportFragmentManager().findFragmentById(R.id.container) != null) {
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container)).commit();
                        }
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();

                        UtilHelper.hidedialog();
                    } else {
                        UtilHelper.hidedialog();
                        Toast.makeText(context, data.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    UtilHelper.hidedialog();
                    Log.e(TAG, "onResponse: " + e.getMessage());
                    Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.e(TAG, "onError: " + anError.getErrorDetail());
                Log.e(TAG, "onError: " + anError.getErrorBody());
                Log.e(TAG, "onError: " + anError.getLocalizedMessage());
                Log.e(TAG, "onError: " + anError.getMessage());
                UtilHelper.hidedialog();
            }
        });
/*
        anRequest.getAsString(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "onResponse: " + response);

                try {
                    Loginpojjo loginpojjo = gson.fromJson(String.valueOf(response), Loginpojjo.class);
                    Data data = loginpojjo.getData();
                    if (data.getStatus().equals(1)) {
                        Data_ data_ = data.getData();
                        SPmanager.saveValue(context, "userID", String.valueOf(data_.getId()));
                        SPmanager.saveValue(context, "email", data_.getEmail());
                        SPmanager.saveValue(context, "user_name", data_.getFirstName());
                        SPmanager.saveValue(context, "first_name", data_.getFirstName());
                        SPmanager.saveValue(context, "user_phone", data_.getPhone());
                        SPmanager.saveValue(context, "profile", data_.getProfilePic());
                        txt_cartitem.setText(String.valueOf(data_.getCart()));
                        txt_wishitem.setText(String.valueOf(data_.getTotalwish()));
                        Fragment fragment;
                        fragment = new UserFragment();
                        if (getActivity().getSupportFragmentManager().findFragmentById(R.id.container) != null) {
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container)).commit();
                        }
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();

                        UtilHelper.hidedialog();
                    } else {
                        UtilHelper.hidedialog();
                        Toast.makeText(context, data.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    UtilHelper.hidedialog();
                    Log.e(TAG, "onResponse: " + e.getMessage());
                    Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.e(TAG, "onError: " + anError.getErrorDetail());
                Log.e(TAG, "onError: " + anError.getErrorBody());
                Log.e(TAG, "onError: " + anError.getLocalizedMessage());
                Log.e(TAG, "onError: " + anError.getMessage());
                UtilHelper.hidedialog();
                Toast.makeText(context, R.string.something_wrong, Toast.LENGTH_SHORT).show();
            }
        });
*/
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}