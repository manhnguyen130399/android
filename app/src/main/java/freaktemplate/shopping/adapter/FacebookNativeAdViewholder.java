package freaktemplate.shopping.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;

import java.util.ArrayList;
import java.util.List;

import freaktemplate.shopping.R;


public class FacebookNativeAdViewholder extends RecyclerView.ViewHolder {

    private final View viu;
    private NativeAd nativeAd;
    private LinearLayout nativeAdContainer;
    private LinearLayout adView;

    public FacebookNativeAdViewholder(View itemView) {
        super(itemView);
        viu = itemView;
        nativeAdContainer = viu.findViewById(R.id.native_ad_container);
        showNativeAd();
    }

    private void showNativeAd() {
        nativeAd = new NativeAd(viu.getContext(), viu.getContext().getString(R.string.facebook_native_id));
        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

                Log.e("", "onError: " + adError.getErrorMessage());

            }

            @Override
            public void onAdLoaded(Ad ad) {


                LayoutInflater inflater = LayoutInflater.from(viu.getContext());
                // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
                adView = (LinearLayout) inflater.inflate(R.layout.custom_native_ad_layout, nativeAdContainer, false);
                nativeAdContainer.addView(adView);

                // Create native UI using the ad metadata.
                AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
                TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
                MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
                TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
                TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
                TextView sponsoredLabel = adView.findViewById(R.id.sponsored_label);
                Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

                // Set the Text.
                nativeAdTitle.setText(nativeAd.getAdvertiserName());
                nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                nativeAdBody.setText(nativeAd.getAdBodyText());
                nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
                nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
                sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

                // Download and display the ad icon.


                // Add the AdChoices icon
                LinearLayout adChoicesContainer = viu.findViewById(R.id.ad_choices_container);
                AdChoicesView adChoicesView = new AdChoicesView(viu.getContext(), nativeAd, true);
                adChoicesContainer.addView(adChoicesView);

                // Register the Title and CTA button to listen for clicks.
                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(nativeAdTitle);
                clickableViews.add(nativeAdCallToAction);

                // Register the Title and CTA button to listen for clicks.
                nativeAd.registerViewForInteraction(
                        adView,
                        nativeAdMedia,
                        nativeAdIcon,
                        clickableViews);

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        nativeAd.loadAd();
    }


}

