package freaktemplate.shopping.utils;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import freaktemplate.shopping.R;


/**
 * Created by RedixbitUser on 6/21/2018.
 */

public class AdManager {
    private static InterstitialAd mInterstitialAd;
    private static com.facebook.ads.InterstitialAd interstitialAd;
    private static int adCount = 0;

    public static void setUpInterstial(Context activity) {
        if (activity.getString(R.string.show_admmob_ads).equals("yes")) {
            mInterstitialAd = new InterstitialAd(activity);
            mInterstitialAd.setAdUnitId(activity.getString(R.string.interstial_ad_unit_id));
            mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("CB0B43F9BA0C8838DF17C89C1F8CF4CE").build());

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Log.e("adloadfailed", "onAdFailedToLoad: " + i);
                }
            });

        } else if (activity.getString(R.string.show_facebook_ads).equals("yes") && adCount == activity.getResources().getInteger(R.integer.adCount)) {
            interstitialAd = new com.facebook.ads.InterstitialAd(activity, activity.getString(R.string.facebook_interstial));
            interstitialAd.loadAd();
        }

    }

    public static void ShowadmobBanner(AdView mAdView) {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("CB0B43F9BA0C8838DF17C89C1F8CF4CE").build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });
    }

    public static void showInterstial(Context activity) {
        if (activity.getString(R.string.show_admmob_ads).equals("yes") && adCount == activity.getResources().getInteger(R.integer.adCount)) {
            if (mInterstitialAd != null) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        } else if (activity.getString(R.string.show_facebook_ads).equals("yes") && adCount == activity.getResources().getInteger(R.integer.adCount)) {
            if (interstitialAd != null) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
            }
        }
    }

    public static void increaseCount(Context activity) {
        if (adCount > activity.getResources().getInteger(R.integer.adCount)) {
            adCount = 1;
        }
        adCount++;
    }
}
