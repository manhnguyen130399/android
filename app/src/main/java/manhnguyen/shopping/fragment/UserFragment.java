package manhnguyen.shopping.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

import org.apache.commons.lang3.text.WordUtils;

import manhnguyen.shopping.R;
import manhnguyen.shopping.activity.AboutUs;
import manhnguyen.shopping.activity.AccountDetail;
import manhnguyen.shopping.activity.Categorylist;
import manhnguyen.shopping.activity.HelpCenter;
import manhnguyen.shopping.activity.HomeActivity;
import manhnguyen.shopping.activity.OrderList;
import manhnguyen.shopping.activity.ReportSpamActivity;
import manhnguyen.shopping.activity.TermsAndCondition;
import manhnguyen.shopping.utils.AdManager;
import manhnguyen.shopping.utils.SPmanager;

import static manhnguyen.shopping.activity.HomeActivity.ic_cart;
import static manhnguyen.shopping.activity.HomeActivity.ic_favrite;
import static manhnguyen.shopping.activity.HomeActivity.ic_home;
import static manhnguyen.shopping.activity.HomeActivity.ic_user;
import static manhnguyen.shopping.activity.HomeActivity.rel_cart;
import static manhnguyen.shopping.activity.HomeActivity.rel_favorite;
import static manhnguyen.shopping.activity.HomeActivity.rel_home;
import static manhnguyen.shopping.activity.HomeActivity.rel_user;
import static manhnguyen.shopping.activity.HomeActivity.txt_cart;
import static manhnguyen.shopping.activity.HomeActivity.txt_favrite;
import static manhnguyen.shopping.activity.HomeActivity.txt_home;
import static manhnguyen.shopping.activity.HomeActivity.txt_user;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Context context;
    private String userID;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        context = view.getContext();
        setview();
        init();
        return view;
    }

    private void setview() {
        rel_cart.setBackgroundResource(0);
        rel_favorite.setBackgroundResource(0);
        rel_user.setBackgroundResource(R.drawable.homerect);
        rel_home.setBackgroundResource(0);

        txt_home.setVisibility(View.GONE);
        txt_user.setVisibility(View.VISIBLE);
        txt_cart.setVisibility(View.GONE);
        txt_favrite.setVisibility(View.GONE);

        ic_favrite.setBackgroundResource(R.drawable.like_not);
        ic_cart.setBackgroundResource(R.drawable.cart_not);
        ic_user.setBackgroundResource(R.drawable.user);
        ic_home.setBackgroundResource(R.drawable.home_not);
    }

    private void init() {
        RoundedImageView chef_profile = view.findViewById(R.id.chef_profile);
        TextView txt_name = view.findViewById(R.id.txt_name);
        TextView txt_email = view.findViewById(R.id.txt_email);
        ImageView ic_edit = view.findViewById(R.id.ic_edit);

        RelativeLayout rel_cat = view.findViewById(R.id.rel_cat);
        RelativeLayout rel_orders = view.findViewById(R.id.rel_orders);
        RelativeLayout rel_about = view.findViewById(R.id.rel_about);
        RelativeLayout rel_terms = view.findViewById(R.id.rel_terms);
        RelativeLayout rel_help = view.findViewById(R.id.rel_help);
        RelativeLayout rel_report = view.findViewById(R.id.rel_report);
        RelativeLayout rel_logout = view.findViewById(R.id.rel_logout);
        RelativeLayout medi = view.findViewById(R.id.medi);

        userID = SPmanager.getPreference(context, "userID");
        String userName = SPmanager.getPreference(context, "user_name");
        String userEmail = SPmanager.getPreference(context, "email");
        String profile = SPmanager.getPreference(context, "profile");

        if (userID != null) {
            if (userID.equals("null")) {
                txt_name.setText(getString(R.string.sign_in));
                txt_email.setText(getString(R.string.set_yor_profile));
                ic_edit.setVisibility(View.GONE);
                rel_logout.setVisibility(View.GONE);
                medi.setClickable(true);
                medi.setOnClickListener(this);

            } else {
                rel_logout.setVisibility(View.VISIBLE);
                txt_email.setVisibility(View.VISIBLE);
                ic_edit.setVisibility(View.VISIBLE);
                txt_name.setText(WordUtils.capitalizeFully(userName));
                txt_email.setText(userEmail);
                medi.setClickable(false);
                medi.setOnClickListener(null);
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.user_icon);
                requestOptions.error(R.drawable.user_icon);
                Glide.with(context).load(getString(R.string.imagelink) + "public/upload/profile/" + profile).apply(requestOptions).into(chef_profile);
            }
        } else {
            rel_logout.setVisibility(View.GONE);
            txt_name.setText(getString(R.string.sign_in));
            txt_email.setText(getString(R.string.set_yor_profile));
            ic_edit.setVisibility(View.GONE);
            medi.setClickable(true);
            medi.setOnClickListener(this);
        }

        ic_edit.setOnClickListener(this);
        rel_logout.setOnClickListener(this);
        rel_cat.setOnClickListener(this);
        rel_orders.setOnClickListener(this);
        rel_about.setOnClickListener(this);
        rel_terms.setOnClickListener(this);
        rel_help.setOnClickListener(this);
        rel_report.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        AdManager.setUpInterstial(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.medi:
                AdManager.increaseCount(context);
                AdManager.showInterstial(context);
                Fragment fragment = new LoginFragment();
                Loginfrag(fragment);
                break;
            case R.id.rel_cat:
                Intent intent = new Intent(context, Categorylist.class);
                startActivity(intent);
                AdManager.increaseCount(context);
                AdManager.showInterstial(context);
                break;
            case R.id.ic_edit:
                intent = new Intent(context, AccountDetail.class);
                startActivity(intent);
                AdManager.increaseCount(context);
                AdManager.showInterstial(context);
                break;
            case R.id.rel_logout:
               Showdialog("2");
                /* SPmanager.saveValue(context, "userID", null);
                SPmanager.saveValue(context, "email", null);
                SPmanager.saveValue(context, "user_name", null);
                SPmanager.saveValue(context, "profile", null);
                SPmanager.saveValue(context, "user_phone", null);
                intent = new Intent(context, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                AdManager.increaseCount(context);
                AdManager.showInterstial(context);*/
                break;
            case R.id.rel_orders:
                if (userID == null) {
                    Showdialog("1");
                } else {
                    Intent intent1 = new Intent(context, OrderList.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    AdManager.increaseCount(context);
                    AdManager.showInterstial(context);
                }

                break;
            case R.id.rel_terms:
                Intent intent2 = new Intent(context, TermsAndCondition.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                AdManager.increaseCount(context);
                AdManager.showInterstial(context);
                break;
            case R.id.rel_about:
                Intent i = new Intent(context, AboutUs.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.rel_help:
                Intent j = new Intent(context, HelpCenter.class);
                j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(j);
                break;
            case R.id.rel_report:
                if (userID == null) {
                    Showdialog("1");
                } else {
                    Intent k = new Intent(context, ReportSpamActivity.class);
                    k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(k);
                    AdManager.increaseCount(context);
                    AdManager.showInterstial(context);
                }
                break;
        }
    }

    private void Showdialog(String type) {
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
        if (type.equals("1")){
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
                    Loginfrag(fragment);
                    dialog.dismiss();
                }
            });
        }else if (type.equals("2")){
            txt_settingtitle.setText(getString(R.string.logout));
            txt_desc.setText(getString(R.string.are_sure_logout));
            rele_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            rele_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SPmanager.saveValue(context, "userID", null);
                    SPmanager.saveValue(context, "email", null);
                    SPmanager.saveValue(context, "user_name", null);
                    SPmanager.saveValue(context, "profile", null);
                    SPmanager.saveValue(context, "user_phone", null);
                   Intent intent = new Intent(context, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    AdManager.increaseCount(context);
                    AdManager.showInterstial(context);
                }
            });
        }

    }

    private void Loginfrag(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}