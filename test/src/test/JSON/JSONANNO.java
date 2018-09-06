package test.JSON;

import filters.UPropertyFilter;

public class JSONANNO{
	@UPropertyFilter(include= "fage")
	public Object exampleVO(Object obj) {
		return obj;
	}
}

