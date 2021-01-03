package com.tuiyi.allin.utlis;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author liuhuijie
 * @date 12/22/20
 */
public class JsonUtils {

    public static String getParams(HashMap<String, String> paramsMap) {
        //String result = "";
        JSONObject jsonObject=new JSONObject();
        for (HashMap.Entry<String, String> entity : paramsMap.entrySet()) {
            //result += "&" + entity.getKey() + "=" + entity.getValue();
            try {
                jsonObject.put(entity.getKey(),entity.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toString();
    }
}
