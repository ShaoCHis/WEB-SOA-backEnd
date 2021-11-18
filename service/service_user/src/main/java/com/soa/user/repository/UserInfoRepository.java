package com.soa.user.repository;

import com.soa.user.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<User,String> {


}
