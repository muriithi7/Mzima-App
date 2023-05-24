package com.FarmProduce.FarmApp.repository;

import com.FarmProduce.FarmApp.model.rolesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<rolesModel, Long>{
    rolesModel findByName(String name);
}
