package com.soa.hospital.repository;

import com.soa.hospital.model.Apply;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplyRepository extends CrudRepository<Apply,String> {

    @Query(value = "select * from apply where status=0 order by time" , nativeQuery = true)
    List<Apply> queryAllList();
}
