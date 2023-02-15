package com.example.lc_print_sdk;

import androidx.annotation.NonNull;

import java.lang.reflect.Method;

public class SystemProperties {
    private static final String TAG = "MySystemProperties";
    private static Class<?> mClassType = null;
    private static Method mGetMethod = null;
    private static Method mGetIntMethod = null;
    private static Method mSetMethod = null;
    private static Method mSetIntMethod = null;

    private static void init() {
        try {
            if (mClassType == null) {
                mClassType = Class.forName("android.os.SystemProperties");

                mGetMethod = mClassType.getDeclaredMethod("get", String.class);
                mGetIntMethod = mClassType.getDeclaredMethod("getInt", String.class, int.class);
                mSetMethod = mClassType.getDeclaredMethod("set", String.class, String.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        init();

        String value = null;

        try {
            value = (String) mGetMethod.invoke(mClassType, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public static int getInt(String key, int def) {
        init();

        int value = def;
        try {
            Integer v = (Integer) mGetIntMethod.invoke(mClassType, key, def);
            value = v.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void set(String key, String value){
        init();
        try {
            mSetMethod.invoke(mClassType, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addChangeCallback(@NonNull Runnable callback) {

    }

    public static int getSdkVersion() {
        return getInt("ro.build.version.sdk", -1);
    }
}
