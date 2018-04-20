package com.gls.global;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class WrapperResponseConvert {
	@Bean // 自定义转换器
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new RestfulWapperConverter();

		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

		HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;

		return new HttpMessageConverters(converter);

	}
}

class RestfulWapperConverter extends FastJsonHttpMessageConverter{
	@Override
	public void write(Object t, //
            Type type, //
            MediaType contentType, //
            HttpOutputMessage outputMessage //
            ) throws HttpMessageNotWritableException, IOException{
		if(type.toString().indexOf("springfox")>-1
				||type==DefaultException.class){
			super.write(t, type, contentType, outputMessage);
		}else {
			Map<String,Object> map = new HashMap<String,Object>();
	        map.put("code", 200);
	        map.put("msg", String.format("%s is warpped", type));
	        map.put("data", t);
			super.write(map, type, contentType, outputMessage);
		}
	}
}
