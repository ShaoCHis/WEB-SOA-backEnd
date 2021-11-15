package com.soa.LoginRegister.repository;

import com.soa.LoginRegister.model.Administrator;
import org.springframework.data.repository.CrudRepository;

public interface AdministratorRepository extends CrudRepository<Administrator,String> {
}
