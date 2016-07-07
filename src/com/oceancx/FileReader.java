package com.oceancx;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by oceancx on 15/8/22.
 */
public class FileReader {
    public static final String pacakgeName="com.company.reqbean";
    public static final String genClassPackage = "package %1$s;\nimport java.io.*;\n";
    public static final String genClass = "public class %1$s {\n";
    public static final String genClassStrFormat = "    public String %1$s = \"%2$s\";\n";
    public static final String genClassIntFormat = "    public Integer %1$s = %2$d;\n";
    public static final String genClassStrGetter = "    public String get%1$s() {\n\t return %2$s;\n\t}\n";
    public static final String genClassStrSetter = "    public void set%1$s(String %2$s) {\n\t this.%2$s = %2$s; \n\t}\n";
    public static final String genClassIntSetter = "    public void set%1$s(Integer %2$s) {\n\t this.%2$s = %2$s; \n\t}\n";
    public static final String genClassIntGetter = "    public Integer get%1$s() {\n\t return %2$s;\n\t}\n";
    public static final String genClassEnd = "}\n";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String  type;

    public static String upperFirstLetter(String s){
        return Character.toUpperCase( s.charAt(0)  )+ s.substring(1,s.length());
    }

    ArrayList<String> stacks=new ArrayList<>();
    public static String readString(String filename) {
        File f = new File(filename);
        if (!f.exists()) {
            Global.Log("没有文件");
            return null;
        }
        try {
            java.io.FileReader fileReader = new java.io.FileReader(f);
            BufferedReader br = new BufferedReader(fileReader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                Global.Log(line);
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String genReqClass(File f) {
        Global.Log("filename:" + f.getName());
        String filename= f.getName();
        if(filename.split("_").length<3)return null;
        String simpleName= filename.split("_")[0] + filename.split("_")[1]+".java";
        if (!f.exists()) {
            Global.Log("没有文件");
            return null;
        }
        try {
            java.io.FileReader fileReader = new java.io.FileReader(f);
            BufferedReader br = new BufferedReader(fileReader);
            Global.Log("创建文件");
            File fOut=new File(upperFirstLetter( simpleName));
            if(!fOut.exists())fOut.createNewFile();

            FileWriter fos=new FileWriter(fOut);
            BufferedWriter pw=new BufferedWriter(fos);
            pw.write(String.format(genClassPackage, pacakgeName));
            pw.write(String.format(genClass,upperFirstLetter(  simpleName.split("\\.")[0])));
            String line;
            while ((line = br.readLine()) != null) {
                String key, value;
                String[] strs = line.split("\\t");
                key = strs[1];
                value = strs[2];
                boolean beNum = false;
                try {
                    int value2 = Integer.valueOf(value);
                    beNum = true;
                } catch (NumberFormatException e) {
                    beNum = false;
                    //   e.printStackTrace();
                }
                if (beNum) {
                    pw.write(String.format(genClassIntFormat, key, Integer.valueOf(value)));
                    pw.write(String.format(genClassIntSetter, upperFirstLetter(key), key));
                    pw.write(String.format(genClassIntGetter, upperFirstLetter(key), key));
                    pw.flush();
                } else {
                    pw.write(String.format(genClassStrFormat, key, value.replaceAll("\"", "\\\\\"")));
                    pw.write(String.format(genClassStrSetter, upperFirstLetter(key), key));
                    pw.write(String.format(genClassStrGetter, upperFirstLetter(key), key));
                    pw.flush();
                }

            }
            pw.write(String.format(genClassEnd));
            pw.close();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    public static void modifyFolder(String directoryName) {
        File root = new File(directoryName);
        File[] files = root.listFiles();
        for(File file:files){
            if(file.isDirectory()){
      /*
       * 递归调用
       */
             //   getFiles(file.getAbsolutePath());
             //   filelist.add(file.getAbsolutePath());
            //    System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
            }else{
             //   System.out.println("显示"+filePath+"下所有子目录"+file.getAbsolutePath());
                genReqClass(file);
            }
        }
    }

}
