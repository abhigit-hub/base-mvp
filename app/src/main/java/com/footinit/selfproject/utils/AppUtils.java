package com.footinit.selfproject.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.footinit.selfproject.R;

/**
 * Created by Abhijit on 08-11-2017.
 */

public final class AppUtils {

    private AppUtils() {
        //PC
    }

    public static void openPlayStoreForApp(Context context) {
        String packageName = context.getPackageName();

        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                    .getResources()
                    .getString(R.string.app_market_link) + packageName)));
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                    .getResources()
                    .getString(R.string.app_google_playstore_link) + packageName)));
        }
    }
}
