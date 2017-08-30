package me.ianhe.dao;

import me.ianhe.db.entity.Authorities;
import me.ianhe.db.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByOpenId(String openId);

    User selectByPhone(String phone);

    List<User> listUserByCondition(Map<String, Object> res, RowBounds rowBounds);

    int listUserCount(Map<String, Object> res);

    User selectByUsername(String username);

}