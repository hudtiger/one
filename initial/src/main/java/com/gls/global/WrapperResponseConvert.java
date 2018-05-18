package com.gls.global;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.MethodInvocationInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class WrapperResponseConvert {
	@Autowired
	WebApplicationContext applicationContext;
	
	@Bean // 自定义转换器
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new RestfulWapperConverter();
		List<MediaType> supportedMediaTypes = new ArrayList<>(); 
	       supportedMediaTypes.add(MediaType.APPLICATION_JSON);
	       supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
	       supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
	       supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
	       supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
	       supportedMediaTypes.add(MediaType.APPLICATION_PDF);
	       supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
	       supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
	       supportedMediaTypes.add(MediaType.APPLICATION_XML);
	       supportedMediaTypes.add(MediaType.IMAGE_GIF);
	       supportedMediaTypes.add(MediaType.IMAGE_JPEG);
	       supportedMediaTypes.add(MediaType.IMAGE_PNG);
	       supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
	       supportedMediaTypes.add(MediaType.TEXT_HTML);
	       supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
	       supportedMediaTypes.add(MediaType.TEXT_PLAIN);
	       supportedMediaTypes.add(MediaType.TEXT_XML); 
	       
	       fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
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
				||type==DefaultException.class
				||type ==RtnResult.class){
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
