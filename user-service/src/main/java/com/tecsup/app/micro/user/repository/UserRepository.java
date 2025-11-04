package com.tecsup.app.micro.user.repository;

import com.tecsup.app.micro.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
