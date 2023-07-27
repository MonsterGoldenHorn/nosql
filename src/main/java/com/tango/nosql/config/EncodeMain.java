package com.tango.nosql.config;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.tomcat.util.security.MD5Encoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date 2023/7/26 21:39
 */
public class EncodeMain {

    public static void main(String[] args) {
        String username = "fc_hy_admin";
        String password = "123456";
        //请求
        String url = "http://192.168.0.29:8081/0.0.1/auth";
        String loginStr = username+":"+password+":"+System.currentTimeMillis();

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json;charset=UTF-8");
        HttpResponse response = HttpRequest.post(url)
                .headerMap(headerMap, false)
                .body(encode(loginStr))
                .timeout(30 * 1000)
                .execute();
        System.out.println(response);
        //返回成功示例,其中data为身份校验码，需要添加到业务接口的header中
        String res = """
                {
                	"data": "eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbl91c2VyX3VuaXF1ZV9pZDoiOiI2NDBhYmJhMS1hOGM3LTRiN2QtOTI0My0zMDdkODJjZWI2NzUifQ.Kt86sQKQdtcfo4kGzOM1v5fXT3Dl8YegFjEtfbxuU4g",
                	"error": {
                		"code": 0,
                		"message": " "
                	},
                	"httpStatusCode": 200,
                	"timestamp": "2023-07-26 21:52:39"
                }
                """;
        JSONObject jsonObject = JSONObject.parseObject(response.body());
        String token = jsonObject.getString("data");
        //业务请求
        url = "http://192.168.0.29/medaxis-api/get_default_permission";

        headerMap.put("Authorization", token);
        HttpResponse response2 = HttpRequest.get(url)
                .headerMap(headerMap, false)
                .timeout(30 * 1000)
                .execute();
        System.out.println(response2);


//        //业务请求
//        url = "http://192.168.0.29:8081/0.0.1/req";
//        Map bizMap = new HashMap<String, String>();
//        bizMap.put("name","name");
//        bizMap.put("hosCode","123");
//        bizMap.put("totalFee",100);
//        String param = """
//                {
//                	"name": "1",
//                	"hosCode": "123",
//                	"totalFee":100
//                }
//                """;
//        String sign = sign(param);
//        bizMap.put("sign",sign);
//
//        headerMap.put("Authorization","登录接口请求获取到的token");
//        HttpResponse response2 = HttpRequest.post(url)
//                .headerMap(headerMap, false)
//                .body(JSONObject.toJSONString(bizMap))
//                .timeout(30 * 1000)
//                .execute();
//        System.out.println(response2);
    }

    /**
     * 参数签名，使用md5
     * @param param
     * @return
     */
    public static String sign(String param){
        String encode = MD5Encoder.encode(param.getBytes());
        return encode;
    }

    /**
     * 使用jdk自带base64加密
     * @param param
     * @return
     */
    public static String encode(String param){
        byte[] encode = Base64.getEncoder().encode(param.getBytes());
        return new String(encode);
    }

    public static String decode(String param){
        byte[] decode = Base64.getDecoder().decode(param.getBytes());
        return new String(decode);
    }

    public static String sendPost(String urlstr, String paramsStr) {
        String result = " ";
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlstr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("accept", "application/json, text/plain, */*");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Connection", "Keep-Alive");
            // 不使用缓存
            connection.setUseCaches(false);
            connection.connect();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
            out.print(paramsStr);
            out.flush();

            int resultCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resultCode) {
                StringBuffer stringBuffer = new StringBuffer();
                String readLine;
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                while ((readLine = responseReader.readLine()) != null) {
                    stringBuffer.append(readLine).append("\n");
                }
                responseReader.close();
                result = stringBuffer.toString();
            } else {
                result = "{'code':'" + resultCode + "'}";
            }
            out.close();
        } catch (Exception e) {
            return "{'code':500,'result':'x-www-form-urlencoded请求 " + urlstr + " 时出现异常'}";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }
}