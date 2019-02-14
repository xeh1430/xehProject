package com.xeh.security.dao;


import com.xeh.security.model.Permission;
import com.xeh.security.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    User findByUserName(String username);
    List<Permission> findAllPermission();
    List<Permission> findByAdminUserId(int userId);

   /* @Select("select u.*,r.name from user u left join user_role sru on u.id= sru.user_id\n" +
            "        left join role r on sru.role_id = r.id  where username=#{name}")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(column="roles", property="roles", javaType= List.class,
                    many=@Many(select="com.xeh.security.dao.UsersMapper.selectRoleByName")),
    })
    User findByUserName(String name);


    @Select("select * from role r where name=#{name}")
    @Results(id = "roleList",value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
    })
    List<Role> selectRoleByName(String name);*/
}
