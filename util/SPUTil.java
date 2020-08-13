package com.yk.silence.customnode.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUTil {

    private static SharedPreferences settings;

    private SPUTil() {
        throw new AssertionError();
    }

    private static void init(Context context) {
        settings = context.getApplicationContext()
                .getSharedPreferences("Student" +
                        AppUtil.getPackageName(context.getApplicationContext()), Context.MODE_PRIVATE);
    }


    /**
     * 写入String数据（同步操作）
     */
    public static boolean putSyncString(Context context, String key, String value) {
        init(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 写入String数据（异步操作）
     */
    public static void putAsyncString(Context context, String key, String value) {
        init(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 获取String数据（不带默认值）
     */
    public static String getString(Context context, String key) {
        init(context);
        return getString(context,key, null);
    }

    /**
     * 获取String数据（带默认值）
     */
    public static String getString(Context context, String key, String defaultValue) {
        init(context);
        return settings.getString(key, defaultValue);
    }

    /**
     * 写入Int数据（同步操作）
     */
    public static boolean putSyncInt(Context context, String key, int value) {
        init(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * 写入Int数据（异步操作）
     */
    public static void putAsyncInt(Context context, String key, int value) {
        init(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 获取String数据（不带默认值）
     */
    public static int getInt(Context context, String key) {
        init(context);
        return getInt(context,key, -1);
    }

    /**
     * 获取String数据（带默认值）
     */
    public static int getInt(Context context, String key, int defaultValue) {
        init(context);
        return settings.getInt(key, defaultValue);
    }

    /**
     * 写入long数据（同步操作）
     */
    public static boolean putSyncLong(Context context, String key, long value) {
        init(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * 写入long数据（同步操作）
     */
    public static void putAsyncLong(Context context, String key, long value) {
        init(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 获取long数据（不带默认值）
     */
    public static long getLong(Context context, String key) {
        init(context);
        return getLong(context,key, -1);
    }

    /**
     * 获取long数据（带默认值）
     */
    public static long getLong(Context context, String key, long defaultValue) {
        init(context);
        return settings.getLong(key, defaultValue);
    }

    /**
     * 写入float数据（同步操作）
     */
    public static boolean putSyncFloat(Context context, String key, float value) {
        init(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 写入float数据（异步操作）
     */
    public static void putAsyncFloat(Context context, String key, float value) {
        init(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 获取float数据（不带默认值）
     */
    public static float getFloat(Context context, String key) {
        init(context);
        return getFloat(context,key, -1);
    }

    /**
     * 获取long数据（带默认值）
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        init(context);
        return settings.getFloat(key, defaultValue);
    }


    /**
     * 写入boolean数据（同步操作）
     */
    public static boolean putSyncBoolean(Context context, String key, boolean value) {
        init(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 写入boolean数据（异步操作）
     */
    public static void putAsyncBoolean(Context context, String key, boolean value) {
        init(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 获取boolean数据（不带默认值）
     */
    public static boolean getBoolean(Context context, String key) {
        init(context);
        return getBoolean(context,key, false);
    }

    /**
     * 获取boolean数据（不带默认值）
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        init(context);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * 异步移除数据
     */
    public static void removeAsync(Context context, String key) {
        init(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 同步移除数据
     */
    public static boolean removeSync(Context context, String key) {
        init(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 异步清除所有
     */
    public static void clearSync(Context context) {
        init(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear().apply();
    }

    /**
     * 同步清除所有
     */
    public static boolean clearAsync(Context context) {
        init(context);
        SharedPreferences.Editor editor = settings.edit();
        return editor.clear().commit();
    }
}
