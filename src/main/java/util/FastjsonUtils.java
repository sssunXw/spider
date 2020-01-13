package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <pre>
 * 功能说明:
 * </pre>
 *
 * @author sxw
 * @date 2019/6/7
 */

public class FastjsonUtils {

    public static Object convertJsonToObject(String json) {

        Object object = JSON.parse(json);
        if (object instanceof JSONObject) {
            return convertJsonObjectToMap((JSONObject) object);
        } else if (object instanceof JSONArray) {
            return convertJsonObjectToList((JSONArray) object);
        }

        return null;
    }

    public static<T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    public static<T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    public static String convertObjectToJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    private static Map<String, Object> convertJsonObjectToMap(JSONObject jsonObject) {
        Map<String, Object> src = jsonObject.getInnerMap();
        for (Map.Entry<String, Object> entry: src.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof JSONObject) {
                Map<String, Object> valueMap = convertJsonObjectToMap((JSONObject) value);
                src.put(entry.getKey(), valueMap);
            } else if (value instanceof JSONArray) {
                List<Object> maps = convertJsonObjectToList((JSONArray) value);
                src.put(entry.getKey(), maps);
            }
        }
        return src;
    }

    private static List<Object> convertJsonObjectToList(JSONArray jsonArray) {
        List<Object> src = new ArrayList<>();
        for (Object Object: jsonArray) {
            if (Object instanceof JSONArray) {
                List<Object> list = convertJsonObjectToList((JSONArray) Object);
                src.add(list);
            } else if (Object instanceof JSONObject) {
                Map<String, Object> map = convertJsonObjectToMap((JSONObject) Object);
                src.add(map);
            } else {
                //基础类型 String Long Integer等等
                src.add(Object);
            }
        }
        return src;
    }
}
