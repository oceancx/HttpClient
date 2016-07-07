package com.oceancx;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by oceancx on 15/8/4.
 */
public class Global {

    public static String TOKEN = "android_377_1427087773_13b22f164eb94484750411b3d17b5241";
    public static void Log(String msg) {
        System.out.println(msg);
    }
    public static String createQueryStringComplex(Object obj, Class<?> cls) {
        try {
            boolean first = true;
            Field[] fields = cls.getDeclaredFields();
            StringBuffer sb = new StringBuffer();
            sb.append("{");

            for (Field f : fields) {
                f.setAccessible(true);
                if (first) {
                    sb.append(makeKeyValueStr(f.getType().getSimpleName(), f.getName(), f.get(obj)));
                    first = false;
                } else {
                    sb.append("," + makeKeyValueStr(f.getType().getSimpleName(), f.getName(), f.get(obj)));
                }

            }

            fields = cls.getFields();
            for (Field f : fields) {
                f.setAccessible(true);
                if (first) {
                    sb.append(makeKeyValueStr(f.getType().getSimpleName(), f.getName(), f.get(obj)));
                    first = false;
                } else {
                    sb.append("," + makeKeyValueStr(f.getType().getSimpleName(), f.getName(), f.get(obj)));
                }
            }
            sb.append("}");
            Global.Log(sb.toString());
            return sb.toString();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //Global.Log();
        return null;
    }
    public static String createQueryString(Object obj, Class<?> cls) {
        try {
            boolean first = true;
            Field[] fields = cls.getDeclaredFields();
            StringBuffer sb = new StringBuffer();
            sb.append("{");

            for (Field f : fields) {
                f.setAccessible(true);
                if (first) {
                    sb.append(makeKeyValueStr(f.getType().getSimpleName(), f.getName(), f.get(obj)));
                    first = false;
                } else {
                    sb.append("," + makeKeyValueStr(f.getType().getSimpleName(), f.getName(), f.get(obj)));
                }

            }

            fields = cls.getFields();
            for (Field f : fields) {
                f.setAccessible(true);
                if (first) {
                    sb.append(makeKeyValueStr(f.getType().getSimpleName(), f.getName(), f.get(obj)));
                    first = false;
                } else {
                    sb.append("," + makeKeyValueStr(f.getType().getSimpleName(), f.getName(), f.get(obj)));
                }
            }
            sb.append("}");
            Global.Log(sb.toString());
            return sb.toString();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //Global.Log();
        return null;
    }

    private static String makeKeyValueStr(String type, String key, Object value) throws IllegalAccessException {
        if (type.equals("Integer")) {
            //Global.Log("\"" + key + "\":" + value);
            return "\"" + key + "\":" + value;
        } else if (type.equals("String")) {
            //Global.Log("\"" + key + "\":\"" + value + "\"");
            return "\"" + key + "\":\"" + value + "\"";
        }else if(type.equals("List")){
            List<Object> list= (List<Object>) value;
            StringBuffer sb=new StringBuffer();
            sb.append("\""+key+"\":[");
            boolean first=true;
            for(Object o : list){
                if(first) {
                    sb.append(createQueryStringComplex(o, o.getClass()));
                    first=false;
                }
                else
                    sb.append(","+createQueryStringComplex(o,o.getClass()));
            }
            sb.append("]");
            return sb.toString();
        }

        return null;
    }



}
