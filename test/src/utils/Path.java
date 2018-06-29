package utils;

import org.apache.commons.io.FileUtils;

public class Path{
	public static String Combile(final String basePath,final String... names) {
		return FileUtils.getFile(basePath,FileUtils.getFile(names).getPath()).getPath();
	}
}