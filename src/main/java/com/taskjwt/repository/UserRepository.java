package com.taskjwt.repository;
import com.taskjwt.model.UserDao;
import org.springframework.data.repository.CrudRepository;
public interface UserRepository extends CrudRepository<UserDao, Integer> {
    UserDao findByPhone(String phone);
}