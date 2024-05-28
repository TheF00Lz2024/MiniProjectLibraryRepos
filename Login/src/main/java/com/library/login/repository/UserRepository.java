package com.library.login.repository;

import com.library.login.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    @Query(value = "SELECT * FROM user WHERE username=:username AND password=:password", nativeQuery = true)
    List<User> loginUser(@Param("username") String username, @Param("password") String password);

    @Query(value = "SELECT * FROM user WHERE username=:username AND roles=:roles", nativeQuery = true)
    List<User> findEmployee(@Param("username") String username, @Param("roles") String roles);

}
