package com.oceancx;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by oceancx on 15/8/7.
 */
public class CreateReqMap {
    private static HashMap<String, String> map = new HashMap<String, String>();

    public static HashMap<String, String> req1100() {
        map.clear();
        map.put("type", "1100");
        map.put("pageid", "0");
        map.put("uid", "377");
        map.put("token", Global.TOKEN);
        return map;
    }

    public static HashMap<String, String> req1101() {
        map.clear();
        map.put("type", "1101");
        map.put("token", Global.TOKEN);
        map.put("id", "1825");
        map.put("uid", "377");
        return map;
    }


    public static HashMap<String, String> invent(Object obj, Class<?> cls) throws Exception {
        try {

            Field[] fields = cls.getDeclaredFields();
            HashMap<String, String> map = new HashMap<>();
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.getName().equals("token")) {

                    map.put(f.getName(), Global.TOKEN);
                } else {
                    if (f.getType().getSimpleName().equals("Integer"))
                        map.put(f.getName(), String.valueOf((Integer) f.get(obj)));
                    else if (f.getType().getSimpleName().equals("String"))
                        map.put(f.getName(), (String) f.get(obj));
                }
            }

            fields = cls.getFields();
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.getName().equals("token")) {
                    map.put(f.getName(), Global.TOKEN);
                } else {
                    if (f.getType().getSimpleName().equals("Integer"))
                        map.put(f.getName(), String.valueOf((Integer) f.get(obj)));
                    else if (f.getType().getSimpleName().equals("String"))
                        map.put(f.getName(), (String) f.get(obj));
                }
            }
            return map;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //Global.Log();
        return null;
    }


    public static HashMap<String, String> inventLogin(Object obj, Class<?> cls) {
        try {

            Field[] fields = cls.getDeclaredFields();
            HashMap<String, String> map = new HashMap<>();
            for (Field f : fields) {
                f.setAccessible(true);

                if (f.getType().getSimpleName().equals("Integer"))
                    map.put(f.getName(), String.valueOf((Integer) f.get(obj)));
                else if (f.getType().getSimpleName().equals("String"))
                    map.put(f.getName(), (String) f.get(obj));
            }

            fields = cls.getFields();
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.getType().getSimpleName().equals("Integer"))
                    map.put(f.getName(), String.valueOf((Integer) f.get(obj)));
                else if (f.getType().getSimpleName().equals("String"))
                    map.put(f.getName(), (String) f.get(obj));
            }
            return map;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}

