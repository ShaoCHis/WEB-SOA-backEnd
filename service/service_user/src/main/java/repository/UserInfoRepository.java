package repository;

import model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<User,String> {


}
