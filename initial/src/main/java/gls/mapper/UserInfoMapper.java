package gls.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import gls.entity.User;


@Mapper
public interface UserInfoMapper {

	@Select("select id,name,content from user where Id= #{id}")
	@Results({
		@Result(property="name",column="name"),
		@Result(property="content",column="content")
	})
	User getUserById(Integer id);

	@Select("select id,name,content from user")
	public List<User> getUserList();

	@Insert("insert into user(id,name,content) values(#{id}, #{name}, #{content})")
	public int add(User user);

	@Update("UPDATE user SET name = #{user.name} , content = #{user.content} WHERE id = #{id}")
	public int update(@Param("id") Integer id, @Param("user") User user);

	@Delete("DELETE from user where id = #{id} ")
	public int delete(Integer id);
}