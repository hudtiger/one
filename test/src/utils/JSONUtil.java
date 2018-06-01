package utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;

import filters.UPropertyFilter;

public class JSONUtil {
	public static <T> T getParamFilter(Method method) {
		T filter = null;
		Annotation[][] objAn = method.getParameterAnnotations();// .getAnnotation(UPropertyFilter.class);
		for (int i = 0; i < objAn.length; i++) {
			for (int j = 0; j < objAn[i].length; j++) {
				filter = (T) objAn[i][j];
				if (filter != null) {
					System.out.println(filter);
					break;
				}
			}
		}
		return filter;
	}

	public static void parseClass(Class<?> clazz, Object vo) {
		try {
			UPropertyFilter filter = getParamFilter(clazz.getDeclaredMethod("exampleVO", Object.class));
			if(filter==null)
				return;
			PropertyFilter profilter = new PropertyFilter() {
				@Override
				public boolean apply(Object object, String name, Object value) {
					for (String fname : filter.include()) {
						if (name.equalsIgnoreCase("fage")) {
							return false;
						}
					}
					return true;
				}
			};
			System.out.println(JSON.toJSONString(vo, profilter));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static <T extends Annotation> T getResultFilter(Method method,Class<T> annotationClass) {
		return  method.getAnnotation(annotationClass);
	}
	
	
	public static String getJSONString(Object obj, String methodName,Object parms) {
		String res = "";
		try {
			Method method = obj.getClass().getDeclaredMethod(methodName,Object.class);
			UPropertyFilter filter = getResultFilter(method,UPropertyFilter.class);
			if (filter == null)
				res = JSON.toJSONString(parms);
			else {	
				PropertyFilter profilter = new PropertyFilter() {
					@Override
					public boolean apply(Object object, String name, Object value) {
						for (String field : filter.include()) {					
							if (name.equalsIgnoreCase(field)) {
								return false;
							}
						}
						return true;
					}
				};
				res= JSON.toJSONString(method.invoke(obj, parms), profilter);
			}
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			res= JSON.toJSONString(e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
}
