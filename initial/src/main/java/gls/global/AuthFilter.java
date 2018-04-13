package gls.global;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter  implements Filter {

	@Autowired
	List<MapInfo> mapInfoes;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("=======初始化过滤器=========");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		long start = System.currentTimeMillis();
		this.beforeDone(request, response);
		filterChain.doFilter(request, response);
		this.afterDone(request, response);
		System.out.println("filter 耗时：" + (System.currentTimeMillis() - start));
	}

	@Override
	public void destroy() {
		System.out.println("=======销毁过滤器=========");
	}

	// 配置系统级别无需校验的URI列表
	static List<String> excludeUri = null;
	static String loginRUI = "/demo/user/login";
	static String PermissionExceptionUri="/exception";
	static {
		if (excludeUri == null) {
			excludeUri = new ArrayList<>();
			excludeUri.add("/swagger-resources/configuration/security");
			excludeUri.add("/swagger-resources/configuration/ui");
			excludeUri.add("/swagger-resources");
			excludeUri.add("/swagger-ui.html");
			excludeUri.add("/v2/api-docs");
			excludeUri.add("/webjars/springfox-swagger-ui/favicon-32x32.png");
			excludeUri.add("/favicon.ico");
			excludeUri.add("/error");
			excludeUri.add("/");
			excludeUri.add(PermissionExceptionUri);
		}
	}
	Boolean checkIgnore(String url) {
		if (excludeUri.contains(url))
			return true;
		else
			return false;
	}
	/*
	 * 获取会话数据 1.Session方式 2.Token方式 3.其他
	 */
	Object getSession(ServletRequest request) {
		
		// ?????????????
		return new Object();
	}
	Boolean checkPermission(String url, Object obj) {

		return true;
	}

	/*
	 * Access Control 1.Encode 2.Login 3.Route access control
	 */
	void beforeDone(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		response.setCharacterEncoding("utf-8");
		HttpServletRequest req = (HttpServletRequest) request;
		String url = req.getRequestURI(); // 当前请求的url
		System.out.println(url);

		if (!checkIgnore(url)) {
			Object user = getSession(request);
			if (user == null) {
				request.getRequestDispatcher(loginRUI).forward(request, response);
			} else if (!checkPermission(url, user)) {
				request.getRequestDispatcher(PermissionExceptionUri).forward(request, response);
			}
		}
	}

	/*
	 * Output Control 1.Wrap Result
	 */
	void afterDone(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		//
	}
}

