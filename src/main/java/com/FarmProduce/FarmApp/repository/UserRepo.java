package com.FarmProduce.FarmApp.repository;

import com.FarmProduce.FarmApp.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository <UserModel,Long>{
   Optional< UserModel> findByUsername(String username);
}
