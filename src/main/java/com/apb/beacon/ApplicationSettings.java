package com.apb.beacon;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import com.google.gson.Gson;

import static com.apb.beacon.AppConstants.ALERT_FREQUENCY;

public class ApplicationSettings {
    public static final String FIRST_RUN = "FIRST_RUN";
    private static final String PASS_CODE = "PASS_CODE";
    private static final String IS_ALERT_ACTIVE = "IS_ALERT_ACTIVE";
    public static final String BEST_LOCATION = "BEST_LOCATION";
    private static final String LAUNCH_CODE = "LAUNCH_CODE";
    private static final String GUARDIAN_ACTIVE = "GUARDIAN_ACTIVE";
    public static final String GUARDIAN_TIME = "GUARDIAN_TIME";
    public static final Long DEFAULT_GUARDIAN_TIME = 5l * 60l; //five minutes

    public static void completeFirstRun(Context context) {
        saveBoolean(context, FIRST_RUN, false);
    }

    public static boolean isFirstRun(Context context) {
        return sharedPreferences(context).getBoolean(FIRST_RUN, true);
    }

    public static void savePassword(Context context, String password) {
        saveString(context, PASS_CODE, password);
    }

    public static void saveLaunchCode(Context context, String code) {
        saveString(context, LAUNCH_CODE, code);
    }

    public static void saveGuardianTime(Context context, Long time) {
        saveLong(context, GUARDIAN_TIME, time);
    }

    public static Long getGuardianTime(Context context) {
        Long time = sharedPreferences(context).getLong(GUARDIAN_TIME, DEFAULT_GUARDIAN_TIME);
        return time;
    }

    public static void setIsGuardianActive(Context context, boolean value) {
        saveBoolean(context, GUARDIAN_ACTIVE, value);
    }

    public static boolean isGuardianActive(Context context) {
        return sharedPreferences(context).getBoolean(GUARDIAN_ACTIVE, false);
    }


    public static boolean passwordMatches(Context context, String otherPassword) {
        String actualPassword = sharedPreferences(context).getString(PASS_CODE, "");
        return actualPassword.equals(otherPassword);
    }

    public static boolean isAlertActive(Context context) {
        return sharedPreferences(context).getBoolean(IS_ALERT_ACTIVE, false);
    }

    public static void setAlertActive(Context context, boolean isActive) {
        saveBoolean(context, IS_ALERT_ACTIVE , isActive);
    }

    public static Location getCurrentBestLocation(Context context) {
        String locationJson = sharedPreferences(context).getString(BEST_LOCATION, null);
        return (locationJson == null ) ? null : constructLocation(locationJson);
    }

    private static Location constructLocation(String locationJson) {
        Location location = new Gson().fromJson(locationJson, Location.class);
        long timeDelta = System.currentTimeMillis() - location.getTime();
        return (timeDelta <= ALERT_FREQUENCY) ? location : null;
    }

    public static void setCurrentBestLocation(Context context, Location location) {
        saveString(context, BEST_LOCATION, new Gson().toJson(location));
    }

    private static SharedPreferences sharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private static void saveString(Context context, String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static void saveLong(Context context, String key, Long value) {
        SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.putLong(key, value);
        editor.commit();
    }
}
