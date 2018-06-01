package gls.global;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class RouteFactory {
	@Autowired
	WebApplicationContext applicationContext;

	@Bean
	public List<MapInfo> getAllMaps() {
		RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);

		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
		List<MapInfo> list = new ArrayList<>();
		Set<String> patterns;
		HandlerMethod method;
		String comment;
		for (Map.Entry<RequestMappingInfo, HandlerMethod> item : map.entrySet()) {
			patterns = item.getKey().getPatternsCondition().getPatterns();
			method = item.getValue();
			Comment cmt = method.getMethodAnnotation(Comment.class); // 注解
			for (String url : patterns) {
				list.add(new MapInfo(url, method.getBean().toString(), method.getMethod().getName(),
						cmt == null ? "" : cmt.value()));
			}
		}
		list.forEach(mapInfo -> System.out.println(mapInfo.toString()));
		return list;
	}
}

class MapInfo{
	String url;
	String controller;
	String method;
	String comment;
	
	public MapInfo() {
		
	}

	public MapInfo(String url, String controller, String method, String comment) {
		super();
		this.url = url;
		this.controller = controller;
		this.method = method;
		this.comment = comment;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getController() {
		return controller;
	}
	public void setController(String controller) {
		this.controller = controller;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "MapInfo [url=" + url + ", controller=" + controller + ", method=" + method + ", comment=" + comment
				+ "]";
	}
}