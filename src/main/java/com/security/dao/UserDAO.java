package com.security.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.security.entity.UserEntity;


@Dao
@ConfigAutowireable
public interface UserDAO {

	@Select
	List<UserEntity> getAllUser();

	@Select
	UserEntity getUserByUsername(String username);
}
