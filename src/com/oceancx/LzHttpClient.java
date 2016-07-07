package com.oceancx;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;

import java.io.*;

/**
 * Created by oceancx on 15/8/7.
 */
public class LzHttpClient {

    private static LzHttpClient iLzHttpClient;
    private static HttpClient mHttpClient;

    public LzHttpClient() {
        mHttpClient = HttpClients.createDefault();
    }

    public static LzHttpClient get() {
        if (iLzHttpClient == null)
            iLzHttpClient = new LzHttpClient();
        return iLzHttpClient;
    }


    private HttpPost setPost(String url) {
        HttpPost post = new HttpPost(UrlUtils.HOST + url);
        post.setHeader("content-type", "application/x-www-form-urlencoded");
        return post;
    }

    public String getResult(String api, Object obj, Class<?> cls) {
        HttpPost post = setPost(api);

        try {
            if(api.equals(UrlUtils.api_Login)){
                post.setEntity(UrlUtils.convertSimplejsonToEntity(CreateReqMap.inventLogin(obj, cls)));
            }else {
                post.setEntity(UrlUtils.convertSimplejsonToEntity(CreateReqMap.invent(obj, cls)));
            }
            HttpResponse response = mHttpClient.execute(post);
            InputStream inputStream = response.getEntity().getContent();
            System.out.println("Output from Server .... \n");
            return writeToFile(inputStream);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getRequest(String api) {
        HttpGet httpGet=new HttpGet(api);
        httpGet.setHeader("content-type", "application/json");
        try {
            HttpResponse response = mHttpClient.execute(httpGet);
            InputStream inputStream = response.getEntity().getContent();
            System.out.println("Output from Server .... \n");
            return writeToFile(inputStream);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    public String writeToFile(InputStream is) {
        File f = new File("test.txt");
        if (!f.exists()) try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fos = new FileOutputStream(f);
            BufferedInputStream bis = new BufferedInputStream(is);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            int len = 0;
            int off = 0;

            while ((len = bis.read(buf, 0, 1024)) > 0) {
                bos.write(buf, 0, len);
                bos.flush();
            }


            String res = StringEscapeUtils.unescapeJava(bos.toString());
            Global.Log(res);
            byte[] source = res.getBytes();

            int offset = 0;
            int xlen = 1024;
            for (offset = 0; offset < source.length; ) {

                if (source.length - offset > 1024)
                    xlen = 1024;
                else
                    xlen = source.length - offset;


                fos.write(source, offset, xlen);

                fos.flush();
                offset += 1024;
            }
            fos.close();
            return res;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void testParser() {
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,
                HttpStatus.SC_OK, "OK");
        System.out.println(response.getProtocolVersion());
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getStatusLine().getReasonPhrase());
        System.out.println(response.getStatusLine().toString());


    }
}
