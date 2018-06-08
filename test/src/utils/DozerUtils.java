package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.dozer.DozerBeanMapper;

public class DozerUtils {
	private static DozerBeanMapper dozer;

	static {
		if (dozer == null) {
			dozer = new DozerBeanMapper();
			 List<String> mappingFileUrls = Arrays.asList("dozer-date.xml");
			 dozer.setMappingFiles(mappingFileUrls);
		}
	}

	public static <T> T map(Object source, Class<T> destinationClass) {
		return dozer.map(source, destinationClass);
	}

	@SuppressWarnings("rawtypes")
	public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
		List<T> destinationList = new ArrayList<T>();
		for (Object sourceObject : sourceList) {
			T destinationObject = dozer.map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}

		return destinationList;
	}

}
