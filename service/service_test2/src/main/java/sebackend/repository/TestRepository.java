package sebackend.repository;
/*
 ** Created By ShaoCHi
 ** Date 2021/11/11 8:54 上午
 ** Tongji University
 */

import org.springframework.data.repository.CrudRepository;
import sebackend.model.Test;

public interface TestRepository extends CrudRepository<Test,Integer> {

}
