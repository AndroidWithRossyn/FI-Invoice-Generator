package com.invoice.generator.ads;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.invoice.generator.R;

public class MyNativeAds {
    AdLoader adLoader;

    @SuppressLint("MissingPermission")
    public void initNativeAds(Context context, TemplateView template) {
        MobileAds.initialize(context);
        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
        adLoader = new AdLoader.Builder(context, context.getResources().getString(R.string.native_ad_unit_id))
                .forNativeAd(nativeAd -> {
                    // Show the ad.
                    if (adLoader.isLoading()) {
                        template.setVisibility(View.VISIBLE);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    } else {
                    }
                })
                .build();

        adLoader.loadAds(new AdRequest.Builder().build(), 5);
    }

}
