package com.soa.LoginRegister.repository;

import com.soa.LoginRegister.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,String> {
}
