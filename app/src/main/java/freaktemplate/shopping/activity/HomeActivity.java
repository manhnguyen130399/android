package freaktemplate.shopping.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.gson.GsonBuilder;

import freaktemplate.shopping.R;
import freaktemplate.shopping.fragment.CartFragment;
import freaktemplate.shopping.fragment.HomeFragment;
import freaktemplate.shopping.fragment.LoginFragment;
import freaktemplate.shopping.fragment.UserFragment;
import freaktemplate.shopping.fragment.WishFragment;
import freaktemplate.shopping.utils.AdManager;
import freaktemplate.shopping.utils.SPmanager;
import freaktemplate.shopping.utils.SaveValues;
import freaktemplate.shopping.utils.SqliteHelper;

@SuppressLint("StaticFieldLeak")
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "HomeActivity";
    public static RelativeLayout rel_home;
    public static RelativeLayout rel_user;
    public static RelativeLayout rel_cart;
    public static RelativeLayout rel_favorite;
    public static ImageView ic_favrite;
    public static ImageView ic_cart;
    public static ImageView ic_user;
    public static ImageView ic_home;
    public static TextView txt_home;
    public static TextView txt_user;
    public static TextView txt_cart;
    public static TextView txt_wishitem;
    public static TextView txt_cartitem;
    public static TextView txt_favrite;
    private HomeActivity context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = HomeActivity.this;
        GsonBuilder gsonBuilder = new GsonBuilder();
        init();
//        SPmanager.saveValue(context, "baseLink", baseApiJNI());

        Homefrag();
    }


    private void Homefrag() {
        Fragment fragment;
        fragment = new HomeFragment();
        if (getSupportFragmentManager().findFragmentById(R.id.container) != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentById(R.id.container)).commit();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AdManager.setUpInterstial(this);
    }

    private void init() {
        SqliteHelper sqliteHelper = new SqliteHelper(this);
        SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
        rel_home = findViewById(R.id.rel_home);
        rel_user = findViewById(R.id.rel_user);
        rel_cart = findViewById(R.id.rel_cart);
        rel_favorite = findViewById(R.id.rel_favorite);
        ic_favrite = findViewById(R.id.ic_favrite);
        ic_cart = findViewById(R.id.ic_cart);
        ic_user = findViewById(R.id.ic_user);
        ic_home = findViewById(R.id.ic_home);
        txt_home = findViewById(R.id.txt_home);
        txt_user = findViewById(R.id.txt_user);
        txt_cart = findViewById(R.id.txt_cart);
        txt_favrite = findViewById(R.id.txt_favrite);
        txt_cartitem = findViewById(R.id.txt_cartitem);
        txt_wishitem = findViewById(R.id.txt_wishitem);

        rel_home.setOnClickListener(this);
        rel_user.setOnClickListener(this);
        rel_cart.setOnClickListener(this);
        rel_favorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        Fragment fragment;
        String userID = SPmanager.getPreference(context, "userID");
        switch (viewID) {
            case R.id.rel_cart:
                if (userID != null) {
                    fragment = new CartFragment();
                    openFragment(fragment);
                } else {
                    Showdialog();
                }
                break;
            case R.id.rel_home:
                fragment = new HomeFragment();
                openFragment(fragment);
                break;
            case R.id.rel_user:
                fragment = new UserFragment();
                openFragment(fragment);
                break;
            case R.id.rel_favorite:
                if (userID != null) {
                    fragment = new WishFragment();
                    openFragment(fragment);
                } else {
                    Showdialog();
                }
                break;
        }
    }

    private void Showdialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        MaterialButton rele_close = dialog.findViewById(R.id.rele_close);
        MaterialButton rele_submit = dialog.findViewById(R.id.rele_submit);
        TextView txt_settingtitle = dialog.findViewById(R.id.txt_settingtitle);
        TextView txt_desc = dialog.findViewById(R.id.txt_desc);
        txt_settingtitle.setText(getString(R.string.warning));
        txt_desc.setText(getString(R.string.need_login_for_wish));
        rele_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        rele_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                openFragment(fragment);
                dialog.dismiss();
            }
        });
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
