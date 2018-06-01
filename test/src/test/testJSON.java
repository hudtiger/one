package test;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;

import test.model.UserInfo;
import test.model.UserVO;

public class testJSON {
	static List<UserInfo> models;
	static {
		if (models == null) {
			models = new ArrayList<>();
			for (int i = 0; i < 11; i++) {
				models.add(new UserInfo(i, i, "chen"));
			}
		}
	}

	static void exampleHorizontal() {
		PropertyFilter profilter = new PropertyFilter() {
			@Override
			public boolean apply(Object object, String name, Object value) {
				if (name.equalsIgnoreCase("fage") && value.equals(5)) {
					return false;
				}
				return true;
			}
		};
		System.out.println(JSON.toJSONString(models, profilter));
	}

	static void exampleVirtual(List<UserInfo> models) {
		PropertyFilter profilter = new PropertyFilter() {
			@Override
			public boolean apply(Object object, String name, Object value) {
				System.out.println(name);

				if (name.equalsIgnoreCase("fage")) {
					return false;
				}
				return true;
			}
		};
		System.out.println(JSON.toJSONString(models, profilter));
	}

	

	public static void main(String[] args) {
		// exampleHorizontal();
		// exampleVirtual(models);
		// exampleVO(Orientation.HORIZONTAL);
	//	JSONUtil.parseClass(JSONANNO.class, new JSONANNO().exampleVO(new UserVO(models.get(0), "Micheal detail")));
		
		System.out.println(JSONUtil.getJSONString(new JSONANNO(),"exampleVO",new UserVO(models.get(0), "Micheal detail")));
		
		
	}
}

enum Orientation {
	HORIZONTAL(1), VERTICAL(2);

	private Integer code;

	Orientation(Integer code) {
		this.code = code;
	}

	public Integer code() {
		return this.code;
	}
}

