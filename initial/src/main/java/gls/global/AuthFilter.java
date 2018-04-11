package gls.global;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        HttpServletRequest req = (HttpServletRequest)request;
        String url = req.getRequestURI(); //当前请求的url
        System.out.println(mapInfoes.size()); //check (url in mapInfoes) with permission
        
        System.out.println(url);
        response.setCharacterEncoding("utf-8");
        filterChain.doFilter(request, response);
        System.out.println("filter 耗时：" + (System.currentTimeMillis() - start));
        
        
    }

    @Override
    public void destroy() {
        System.out.println("=======销毁过滤器=========");
    }
}

