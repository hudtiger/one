package test.Mapper;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class UserInfo{
	int id;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	Date dateS;
}
