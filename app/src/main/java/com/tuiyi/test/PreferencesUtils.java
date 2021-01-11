package com.tuiyi.test;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * sp缓存
 *
 * @author liuhuijie
 * @date 1/11/21
 */
public class PreferencesUtils {

    public static String FILE_NAME = "allin_local";

    public static final String PID = "pid";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String TYPE = "type";

    public static final String BANNER_PID = "banner_pid";
    public static final String BANNER_WIDTH = "banner_width";
    public static final String BANNER_HEIGHT = "banner_height";

    public static final String SPLASH_PID = "splash_pid";
    public static final String SPLASH_WIDTH = "splash_width";
    public static final String SPLASH_HEIGHT = "splash_height";

    public static final String NATIVE_PID = "native_pid";
    public static final String NATIVE_WIDTH = "native_width";
    public static final String NATIVE_HEIGHT = "native_height";

    public static final String INSERT_PID = "insert_pid";
    public static final String INSERT_WIDTH = "insert_width";
    public static final String INSERT_HEIGHT = "insert_height";


    private static SharedPreferences sSharedPreferences;

    public static void init(Context context) {
        sSharedPreferences = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
    }

    public static void put(String key, Object object) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    public static Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sSharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sSharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sSharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sSharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sSharedPreferences.getLong(key, (Long) defaultObject);
        }
        return null;
    }


    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        return sSharedPreferences.contains(key);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clearAll() {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

}  
