package utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.Authenticator;
import okhttp3.Callback;
import okhttp3.Credentials;

public class HttpClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);    //日志记录
    public final static MediaType JSON = MediaType.parse("application/json;charset=UTF-8");
	public static final OkHttpClient client;

	static {
		OkHttpClient.Builder builder = new OkHttpClient.Builder() //
				.connectTimeout(60, TimeUnit.SECONDS)      //设置连接超时
		        .readTimeout(60, TimeUnit.SECONDS)         //设置读超时
		        .writeTimeout(60, TimeUnit.SECONDS)        //设置写超时
		        .retryOnConnectionFailure(true)            //是否自动重连
//		     	.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("1.1.1.1", 80)))
//				.proxyAuthenticator(new Authenticator() {
//                    @Override
//                    public Request authenticate(Route route, Response response) throws IOException {
//                        if (response.request().header("Proxy-Authorization") != null) {
//                            return null;
//                        }
//
//                        String credential = Credentials.basic("userName", "password");
//                        return response.request().newBuilder()
//                                .header("Proxy-Authorization", credential)
//                                .build();
//                    }
//                })
				.readTimeout(20, TimeUnit.SECONDS); //
		client = builder.build();
	}
    
    /**
     * httpPost
     * @param url  路径
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url,JSONObject jsonParam){
        return httpPost(url, jsonParam, false);
    }
 
    public static JSONObject httpPost(String url,JSONObject jsonParam, boolean noNeedResponse) {
   		RequestBody requestBody = RequestBody.create(JSON, jsonParam.toJSONString());
   		Request request = new Request.Builder() //
   				.url(url) //
   				.post(requestBody) //
   				.build(); //
   		Response response = null;
   		JSONObject jsonResult = null;
   		Map<String, String> map = new HashMap<>();
   		try {
   			response = client.newCall(request).execute();
   			if (!response.isSuccessful())
   				throw new IOException("Unexpected code " + response);
   			jsonResult = JSONObject.parseObject(response.body().string());
   			logger.info("send post method request success, url:{}", url);
   		} catch (Exception e) {
   			logger.error("send post method request error, url:{}", url, e);
   		} finally {
   			Util.closeQuietly(response);
   		}
   		return noNeedResponse?null:jsonResult;
   	}
    
    /**
     * 发送get请求
     * @param url    路径
     * @return
     */

    public static JSONObject httpGet(String url) {
   	//	RequestBody requestBody = RequestBody.create(JSONFMT, "");
   		Request request = new Request.Builder() //
   				.url(url) //
   				.build(); //
   		Response response = null;
   		JSONObject jsonResult = null;
   		try {
   			response = client.newCall(request).execute();
   			if (!response.isSuccessful())
   				throw new IOException("Unexpected code " + response);
   			jsonResult = JSONObject.parseObject(response.body().string());
   			logger.info("send get method request success, url:{}", url);
   		} catch (Exception e) {
   			logger.error("send get method request error, url:{}", url, e);
   		} finally {
   			Util.closeQuietly(response);
   		}
    	return jsonResult;
    }
    
    public static void HttpPostAsyn(String url,JSONObject jsonParam,Callback responseCallback) {
   		RequestBody requestBody = RequestBody.create(JSON, jsonParam.toJSONString());
   		Request request = new Request.Builder() 
   				.url(url) 
   				.post(requestBody) //
   				.build(); 
   		client.newCall(request).enqueue(responseCallback);
   	}
}
