package test;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.HttpClientUtil;

public class testOKHttp {

	public static void main(String[] args) {
		JSONObject jsonParam = JSONObject.parseObject("{\"content\": \"23\",\"id\": 120,\"name\": \"22\"}");
		HttpClientUtil.HttpPostAsyn("http://localhost:8899/session/loginOne", jsonParam, new Callback() {

			@Override
			public void onFailure(Call call, IOException e) {
				// TODO Auto-generated method stub
				System.out.println(e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				System.out.println(JSONObject.parseObject(response.body().string()));
				System.out.println(response);
				
			}});

	}

}
