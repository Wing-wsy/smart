package com.yj.tech.utils.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wing
 * @create 2024/7/22
 */
public class JsonUtil {

    private static Gson gson = null;

    static{
        gson  = new Gson();
    }

    public static synchronized Gson newInstance(){
        if(gson == null){
            gson =  new Gson();
        }
        return gson;
    }

    public static String getJsonString(Object obj){
        return gson.toJson(obj);
    }

    public static <T> T toBean(String json,Class<T> clz){
        return gson.fromJson(json, clz);
    }

    public static <T> Map<String, T> readJson2MapObj(String json,Class<T> clz){
        Map<String, JsonObject> map = gson.fromJson(json, new TypeToken<Map<String,JsonObject>>(){}.getType());
        Map<String, T> result = new HashMap<>();
        for(String key:map.keySet()){
            result.put(key,gson.fromJson(map.get(key),clz) );
        }
        return result;
    }

    public static <T> T json2Obj(String json,Class<T> clz){
        return gson.fromJson(json,clz);
    }

    public static Map<String, Object> toMap(String json){
        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String,Object>>(){}.getType());
        return map;
    }

    public static Map<String,String> readJsonStrMap(String json) {
        Map<String, JsonObject> map = gson.fromJson(json, new TypeToken<Map<String,JsonObject>>(){}.getType());
        Map<String,String> result = new HashMap<>();
        for(String key:map.keySet()){
            result.put(key,gson.fromJson(map.get(key),String.class) );
        }
        return result;
    }

    public static  Map<byte[], byte[]> readJsonByteMap(String json) {
        Map<String, JsonPrimitive> map = gson.fromJson(json, new TypeToken<Map<String,JsonPrimitive>>(){}.getType());
        Map<byte[], byte[]> vmap = new HashMap<>();
        for(String key:map.keySet()){
            vmap.put(key.getBytes(),gson.fromJson(map.get(key),String.class).getBytes() );
        }
        return vmap;

    }


    public static <T> List<T> readJson2Array(String json, Class<T> clz){
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        List<T> list  = new ArrayList<>();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, (Type)clz));
        }
        return list;
    }

    public static void main(String[] args) {
        /** 1、对象转成json */
//        System.out.println("11");
////        Menu menuParent = new Menu();
////        menuParent.setId(0L);
////        menuParent.setName("顶层");
//        Menu menu = new Menu();
//        menu.setId(1L);
//        menu.setName("菜单");
//        //menu.setParent(menuParent);
//        String jsonString = JsonUtil.getJsonString(menu);
//        System.out.println(jsonString);
//
//        /** 2、json转成对象 */
//        Menu bean = JsonUtil.toBean(jsonString, Menu.class);
//        System.out.println(bean);
//
//        /** 3、json转成Map */
//        jsonString = "{\"1\":{\"id\":1,\"name\":\"菜单\",\"parent\":{\"id\":0,\"deleteStatus\":0,\"name\":\"顶层\"}},\"2\":{\"id\":2,\"name\":\"菜单\"}}";
//        Map<String, Menu> stringMenuMap = JsonUtil.readJson2MapObj(jsonString, Menu.class);
//        System.out.println(stringMenuMap);
//
//        /** 4、json转成对象 */
//        jsonString = "{\"id\":2,\"name\":\"菜单\"}";
//        Menu menu1 = JsonUtil.json2Obj(jsonString, Menu.class);
//        System.out.println(menu1);
//
//        /** 5、json转成Map */
//        Map<String, Object> map = JsonUtil.toMap(jsonString);
//        System.out.println(map);
//
//        /** 6、json转成对象 */
//        Map<String, String> map1 = JsonUtil.readJsonStrMap(jsonString);
//        System.out.println(map1);


    }

}