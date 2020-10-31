package manhnguyen.shopping.utils;

import android.content.Context;

public class SaveValues {

    public static String basUrl;
    public static String headerPass;

    public static void adbaseConfig(Context context, String baseLink, String headerText) {

        SPmanager.saveValue(context, "baseUrl", baseLink);
        basUrl = SPmanager.getPreference(context, "baseUrl");
        SPmanager.saveValue(context, "headerText", headerText);
        headerPass = SPmanager.getPreference(context, "headerText");

    }
}
