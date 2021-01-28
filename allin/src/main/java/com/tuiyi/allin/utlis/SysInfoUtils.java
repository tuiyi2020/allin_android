package com.tuiyi.allin.utlis;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.tuiyi.allin.oaid.OAIdManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;



import static android.content.Context.USAGE_STATS_SERVICE;
import static android.content.Context.WIFI_SERVICE;

public class SysInfoUtils {
    public static HashMap<String, Double> gps=new HashMap<>();

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }

                } catch (SocketException e) {
                    e.printStackTrace();
                }


            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                return intIP2StringIP(wifiInfo.getIpAddress());
            }
        }  //当前无网络连接]

        return null;
    }


    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        String appName = getAppName2(context);
        if (!TextUtils.isEmpty(appName)) {
            return appName;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getAppName2(Context context) {
        if (context == null) {
            return null;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            return String.valueOf(packageManager.getApplicationLabel(context.getApplicationInfo()));
        } catch (Exception ignored) {

        } catch (Throwable e) {
            // Log.i(“chwn”,"getAppName >> e:" + e.toString());
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String getMac(Context context) {
        String mac = "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(context);
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMachineHardwareAddress();
            if (TextUtils.isEmpty(mac)) {
                mac = getMacAddress();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMachineHardwareAddress();
        }
        return mac;

    }

    /**
     * Android 6.0 之前（不包括6.0）获取mac地址
     * 必须的权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     *
     * @param context * @return
     */
    public static String getMacDefault(Context context) {
        String mac = "";
        if (context == null) {
            return mac;
        }
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }

    /**
     * Android 6.0-Android 7.0 获取mac地址
     */
    public static String getMacAddress() {
        String macSerial = null;
        String str = "";

        try {
            Process pp = Runtime.getRuntime().exec("cat/sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            while (null != str) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();//去空格
                    break;
                }
            }
        } catch (Exception ex) {
            // 赋予默认值
            ex.printStackTrace();
        }

        return macSerial;
    }
    /*  *//*
    public static String getMacFromHardware() {
        try {
            Enumeration<NetworkInterface> all = (Enumeration<NetworkInterface>) Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.name.equals("wlan0", ignoreCase = true))
                    continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) return "";
                StringBuilder res1 = new StringBuilder();
                for (Byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (!TextUtils.isEmpty(res1)) {
                    res1.deleteCharAt(res1.length - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }*/

    /**
     * 获取设备HardwareAddress地址
     *
     * @return
     */
    public static String getMachineHardwareAddress() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String hardWareAddress = null;
        NetworkInterface iF;
        if (interfaces == null) {
            return null;
        }
        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();
            try {
                hardWareAddress = bytesToString(iF.getHardwareAddress());
                if (hardWareAddress != null)
                    break;
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return hardWareAddress;
    }

    /***
     * byte转为String
     *
     * @param bytes
     * @return
     */
    private static String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    public static boolean checkPermission(String permissionname, Context act) {
        PackageManager pm = act.getPackageManager();

        return (PackageManager.PERMISSION_GRANTED == pm.checkPermission(permissionname, act.getPackageName()));
    }

    public static String getImei(Context context) {
        String imeiRes = null;

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, context))
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                imeiRes = telephonyManager.getDeviceId();
            } catch (Exception e) {
//                e.printStackTrace();
//                WLog.d(e.toString());
            }
            if (imeiRes == null || imeiRes.trim().length() == 0 || imeiRes.matches("0+")) {
                imeiRes = "";//+ (new Random(System.currentTimeMillis())).nextLong();
            }
        }
        if (imeiRes == null) {
            imeiRes = "";
        }
//        WLog.d("#"+imeiRes);
        if (TextUtils.isEmpty(imeiRes)) {
            imeiRes = getIMei(context);
        }
        return imeiRes;
    }


    /**
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getIMei(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei1 = (String) method.invoke(manager, 0);
            String imei2 = (String) method.invoke(manager, 1);
            if (TextUtils.isEmpty(imei2)) {
                if (imei1 == null) {
                    imei1 = "";
                }
                return imei1;
            }
            if (!TextUtils.isEmpty(imei1)) {
                //因为手机卡插在不同位置，获取到的imei1和imei2值会交换，所以取它们的最小值,保证拿到的imei都是同一个
                String imei = "";
                if (imei1.compareTo(imei2) <= 0) {
                    imei = imei1;
                } else {
                    imei = imei2;
                }
                if (imei == null) {
                    imei = "";
                }
                return imei;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取手机型号
     *
     * @return the model
     */
    public static String getModel() {
        return Build.MODEL;
    }


    /**
     * 获取手机品牌
     *
     * @return the vENDOR
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    //获取电话号码
    public static String getPhoneNumber(Context context) {
        String nativePhoneNumber = "";
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, context)) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                nativePhoneNumber = telephonyManager.getLine1Number();
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
        return nativePhoneNumber;
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static String isTablet(Context context) {

        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return "Y";
        } else {
            return "N";
        }
    }
    //获取手机安装应用列表信息

    public static JSONArray getLocalInstallAppList(PackageManager packageManager) {
        JSONArray array = new JSONArray();
        try {
            List packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = (PackageInfo) packageInfos.get(i);
                //过滤掉系统app
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                    continue;
                }
                JSONObject object = new JSONObject();
                object.put("versionName", packageInfo.versionName);
                object.put("versionCode", packageInfo.versionCode + "");
                object.put("firstInstallTime", packageInfo.firstInstallTime + "");
                object.put("lastUpdateTime", packageInfo.lastUpdateTime + "");
                object.put("packageName", packageInfo.packageName);
                object.put("appName", packageInfo.applicationInfo.loadLabel(packageManager).toString());
                array.put(object);
            }
        } catch (Exception ignored) {
        }

        return array;
    }

    //5.0之前
    private static JSONArray getNearUseApp(Context context) {
        JSONArray array = new JSONArray();
        PackageManager pm = context.getPackageManager();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RecentTaskInfo> appList4 = mActivityManager
                .getRecentTasks(10, ActivityManager.RECENT_WITH_EXCLUDED);//参数，前一个是你要取的最大数，后一个是状态
        for (ActivityManager.RecentTaskInfo running : appList4) {
            Intent intent = running.baseIntent;
            ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
            if (resolveInfo != null) {
                JSONObject object = new JSONObject();
                try {
                    object.put("packageName", resolveInfo.activityInfo.packageName);
                    object.put("appName", resolveInfo.loadLabel(pm).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        return array;
    }

    /*    //5.0之后
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private static String getNearUseApp2(Context context) {
            PackageManager pm = context.getPackageManager();
            ActivityManager mActivityManager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            // List<ActivityManager.RecentTaskInfo> appList4 = mActivityManager
            //      .getRecentTasks(10, ActivityManager.RECENT_WITH_EXCLUDED);//参数，前一个是你要取的最大数，后一个是状态
            List<ActivityManager.AppTask> tasks = mActivityManager.getAppTasks();
            for (ActivityManager.AppTask task : tasks) {
                ActivityManager.RecentTaskInfo taskInfo = task.getTaskInfo();
                Intent intent = taskInfo.baseIntent;
                ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
                if (resolveInfo != null) {
                    System.out.println(resolveInfo.activityInfo.packageName + "-hhhhhh");//获取应用包名
                    System.out.println(resolveInfo.loadLabel(pm).toString() + "-nhhhhh");//获取应用名
    // System.out.println(resolveInfo.loadIcon(pm) "n");//获取应用头标

                }
            }
            return "";
        }*/
    private static JSONArray getAppStatus(Context context) {
        Calendar beginCal = Calendar.getInstance();
        beginCal.set(2000, 1, 1);
        Calendar endCal = Calendar.getInstance();
        UsageStatsManager manager = (UsageStatsManager) context.getApplicationContext().getSystemService(USAGE_STATS_SERVICE);
        List<UsageStats> stats = manager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginCal.getTimeInMillis(), endCal.getTimeInMillis());

        JSONArray array = new JSONArray();
        for (UsageStats us : stats) {
            try {
                PackageManager pm = context.getApplicationContext().getPackageManager();
                ApplicationInfo applicationInfo = pm.getApplicationInfo(us.getPackageName(), PackageManager.GET_META_DATA);
                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                    //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    // String t = format.format(new Date(us.getLastTimeUsed()));
                    //System.out.println(pm.getApplicationLabel(applicationInfo) + "======" + us.getTotalTimeInForeground());
                    JSONObject object = new JSONObject();
                    object.put("packageName", us.getPackageName());
                    object.put("appName", pm.getApplicationLabel(applicationInfo));
                    object.put("lastTimeUsed", us.getLastTimeStamp());
                    object.put("time", us.getTotalTimeInForeground());
                    array.put(object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    public static boolean isNoOption(Context context) {
        PackageManager packageManager = context.getApplicationContext()
                .getPackageManager();
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        }
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean isNoSwitch(Context context) {
        long ts = System.currentTimeMillis();
        UsageStatsManager usageStatsManager = (UsageStatsManager)
                context.getApplicationContext().getSystemService("usagestats");
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST, 0, ts);
        return queryUsageStats != null && !queryUsageStats.isEmpty();
    }*/

    public static JSONArray getAppUseList(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return getNearUseApp(context);
        } else {
            return getAppStatus(context);
        }
    }

    public static void getGps(Context context) {
        double lat = 0.0;
        double lng = 0.0;
        if (gps == null) {
            // if (checkSelfPermission)

            gps = new HashMap<>();
        }
            if (checkPermission("android.permission.ACCESS_FINE_LOCATION", context)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //LocationListener locationListener = new JyLocationListener();

                    //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    //WLog.d("GPS_PROVIDER location2 %s", location);

                    if (location != null) {
                        lat = location.getLatitude(); // 经度
                        lng = location.getLongitude(); // 纬度
                    } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                        //  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            lat = location.getLatitude(); // 经度
                            lng = location.getLongitude(); // 纬度
                        }
                    }
                } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    // LocationListener locationListener = new JyLocationListener();

                    // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        lat = location.getLatitude(); // 经度
                        lng = location.getLongitude(); // 纬度
                    }
                }
            }

            gps.put("lat", lat);
            gps.put("lng", lng);


    }

    public static JSONObject getSysInfoJson(Context context) {
        getGps(context);
        //                WLog.d(json);
        return SysInfoUtils.getSysInfoJson(context, gps.get("lat"), gps.get("lng"));
    }

    public static JSONObject getDeviceInfo(Context context) {
        getGps(context);
        JSONObject sysInfoObj = new JSONObject();
        try {
            String platform = "ANDROID";
            String appname = SysInfoUtils.getAppName(context);
            String packagename = SysInfoUtils.getPackageName(context);
            String model = Build.MODEL;
            String ver = Build.VERSION.RELEASE;
            String ua = System.getProperty("http.agent");
            String androidid = SysInfoUtils.getAndroidId(context);
            String mac = SysInfoUtils.getMac(context);
            String imei = SysInfoUtils.getImei((Activity) context);
            String brand = SysInfoUtils.getBrand();
            String appver = SysInfoUtils.getVersionCode(context) + "";
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            String width = metrics.widthPixels + "";
            String height = metrics.heightPixels + "";
            String dpi = metrics.densityDpi + "";
            String istabledevice = SysInfoUtils.isTablet(context);
            //getGps((Activity) context);
            sysInfoObj.put("platform", platform);
            sysInfoObj.put("ver", ver);
            sysInfoObj.put("dpi", dpi);
            sysInfoObj.put("istabledevice", istabledevice);
            sysInfoObj.put("brand", brand);
            sysInfoObj.put("model", model);
            sysInfoObj.put("width", width);
            sysInfoObj.put("height", height);
            sysInfoObj.put("ua", ua);
            //td
            sysInfoObj.put("oaid", OAIdManager.getOAId(context));
            sysInfoObj.put("idfa", "");
            sysInfoObj.put("imei", imei);
            sysInfoObj.put("mac", mac);
            sysInfoObj.put("androidid", androidid);
            sysInfoObj.put("appname", appname);
            sysInfoObj.put("packagename", packagename);
            sysInfoObj.put("appver", appver);
            sysInfoObj.put("lat", gps.get("lat"));
            sysInfoObj.put("lng", gps.get("lng"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return sysInfoObj;

    }

    public static JSONObject getSysInfoJson(Context context, double lat, double lng) {
        JSONObject sysInfoObj = new JSONObject();
        try {
            String ip = SysInfoUtils.getIPAddress(context);
            String appname = SysInfoUtils.getAppName(context);
            String packagename = SysInfoUtils.getPackageName(context);
            String model = Build.MODEL;
            String platform = "ANDROID";
            String ver = Build.VERSION.RELEASE;
            String ua = System.getProperty("http.agent");
            String androidid = SysInfoUtils.getAndroidId(context);
            String mac = SysInfoUtils.getMac(context);
            String imei = SysInfoUtils.getImei(context);
            String brand = SysInfoUtils.getBrand();
            String appver = SysInfoUtils.getVersionCode(context) + "";
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            String width = metrics.widthPixels + "";
            String height = metrics.heightPixels + "";
            String dpi = metrics.densityDpi + "";
            String istabledevice = SysInfoUtils.isTablet(context);
            String phonenum = SysInfoUtils.getPhoneNumber(context);
            JSONArray applist = SysInfoUtils.getLocalInstallAppList(context.getPackageManager());
            JSONArray appusetime = SysInfoUtils.getAppUseList(context);
            //getGps((Activity) context);
            sysInfoObj.put("ip", ip);
            sysInfoObj.put("appname", appname);
            sysInfoObj.put("packagename", packagename);
            sysInfoObj.put("model", model);
            sysInfoObj.put("platform", platform);
            sysInfoObj.put("ver", ver);
            sysInfoObj.put("ua", ua);
            sysInfoObj.put("androidid", androidid);
            sysInfoObj.put("mac", mac);
            sysInfoObj.put("imei", imei);
            sysInfoObj.put("brand", brand);
            sysInfoObj.put("appver", appver);
            sysInfoObj.put("width", width);
            sysInfoObj.put("height", height);
            sysInfoObj.put("dpi", dpi);
            sysInfoObj.put("istabledevice", istabledevice);
            sysInfoObj.put("phonenum", phonenum);
            sysInfoObj.put("lat", lat);
            sysInfoObj.put("lng", lng);
            sysInfoObj.put("appusetime", appusetime);
            sysInfoObj.put("applist", applist);
            sysInfoObj.put("oaid", OAIdManager.getOAId(context));

            sysInfoObj.put("brightness", getSystemBrightness(context));
            sysInfoObj.put("systemvolume", getSystemVolume(context));
            sysInfoObj.put("isroot", isDeviceRooted());
            //电量加一下是否在充电
            sysInfoObj.put("electric", getElect(context));
            sysInfoObj.put("adbopen", isAdbOpen(context));
            //加一下具体的感应的值了

//            WLog.d(sysInfoObj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
//            return e.getMessage();
        }

        return sysInfoObj;
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }


    private static String gYValue = "";

    public static void getGy(Context context) {
        final SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor gyroSensor = sm
                .getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        final SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor != null && event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    try {
                        gYValue = " x:" + event.values[0] + " y:" + event.values[1] + " z:" + event.values[2];
                        // Log.e("hhhhh"," x:" + event.values[0] + " y:" + event.values[1] + " z:" + event.values[2]);
                    } catch (Exception e) {

                    } finally {
                        if (sm != null) {
                            sm.unregisterListener(this);
                        }
                    }

                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        if (gyroSensor != null) {
            sm.registerListener(sensorEventListener, gyroSensor
                    ,
                    SensorManager.SENSOR_DELAY_UI);
        }
    }


    /**
     * 获得系统亮度
     *
     * @return
     */
    public static int getSystemBrightness(Context context) {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return systemBrightness;
    }


    public static boolean isDeviceRooted() {

        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();

    }

    private static boolean checkRootMethod1() {

        String buildTags = Build.TAGS;

        return buildTags != null && buildTags.contains("test-keys");

    }

    private static boolean checkRootMethod2() {

        String[] paths = {

                "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"

        };

        for (String path : paths) {

            if (new File(path).exists())

                return true;

        }

        return false;

    }

    private static boolean checkRootMethod3() {

        Process process = null;
        try {

            process = Runtime.getRuntime().exec(new String[]{

                    "/system/xbin/which", "su"

            });

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            return in.readLine() != null;

        } catch (Throwable t) {

            return false;

        } finally {

            if (process != null) process.destroy();

        }

    }

    public static boolean isAdbOpen(Context context) {
        return (Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0);
    }

    /**
     * 执行shell 命令， 命令中不必再带 adb shell
     *
     * @param cmd
     * @return Sting  命令执行在控制台输出的结果
     */

    public static String execByRuntime(String cmd) {
        Process process = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            inputStreamReader = new InputStreamReader(process.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            int read;
            char[] buffer = new char[4096];
            StringBuilder output = new StringBuilder();
            while ((read = bufferedReader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != inputStreamReader) {
                try {
                    inputStreamReader.close();
                } catch (Throwable t) {

                }
            }
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (Throwable ignored) {

                }
            }
            if (null != process) {
                try {
                    process.destroy();
                } catch (Throwable ignored) {

                }
            }
        }
    }

    public static int getSystemVolume(Context context) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
    }

    public static String getElect(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent receiver = context.getApplicationContext().registerReceiver(null, filter);
        int level = receiver.getIntExtra("level", 0);
        int scale = receiver.getIntExtra("scale", 0);
        int status = receiver.getIntExtra("status", 0);
        int voltage = receiver.getIntExtra("voltage", 0);
        int temperature = receiver.getIntExtra("temperature", 0);
        double t = temperature / 10.0;
        return level + "%";
    }


}
