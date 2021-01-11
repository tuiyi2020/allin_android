package com.tuiyi.allin.core.net;

import android.os.AsyncTask;

import com.tuiyi.allin.utlis.AllInLog;
import com.tuiyi.allin.utlis.JsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class NetManager {

    private NetListener mListener;

    public NetManager() {

    }

    public NetManager(NetListener listener) {
        this.mListener = listener;
    }

    public void doGet(String urlPath) {

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setReadTimeout(5000);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    int code = urlConnection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String readerLine;
                        StringBuilder buffer = new StringBuilder();
                        while ((readerLine = reader.readLine()) != null) {
                            buffer.append(readerLine);

                        }
                        String str = buffer.toString();
                        if (str == null) {
                            str = "";
                        }
                        return str;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

                return null;

            }

            protected void onPostExecute(String result) {
                parseResult(result);
            }


        }.execute(urlPath);//urlpath为网址
    }

    public void doPostJson(String urlPath, HashMap<String, String> paramsMap) {

        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {
                    URL url = new URL((String) params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Charset", "UTF-8");
                    //--------------------------------
                    HashMap<String, String> map = (HashMap<String, String>) params[1];
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(JsonUtils.getParams(map).getBytes());
                    //--------------------------------
                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                        String result = reader.readLine();
                        if (result == null) {
                            result = "";
                        }
                        return result;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return null;
            }

            protected void onPostExecute(String result) {
                parseResult(result);
            }


        }.execute(urlPath, paramsMap);//urlpath为网址
    }

    public void doPost(String urlPath, String json) {

        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {
                    URL url = new URL((String) params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Charset", "UTF-8");
                    //--------------------------------
                    //HashMap<String, String> map= (HashMap<String, String>) params[1];
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(json.getBytes());
                    //--------------------------------
                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                        String result = reader.readLine();
                        if (result == null) {
                            result = "";
                        }
                        return result;

                    }
                    AllInLog.e("code" + conn.getResponseCode() + conn.getResponseMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    AllInLog.e(e.getMessage());
                    return null;
                }
                return null;
            }

            protected void onPostExecute(String result) {
                parseResult(result);
            }


        }.execute(urlPath, json);//urlpath为网址
    }


    private void parseResult(String result) {
        if (mListener != null) {
            if (result == null) {
                mListener.onFail("网络错误");
            } else {
                mListener.onSuccess(result);
            }
        }
    }

}
