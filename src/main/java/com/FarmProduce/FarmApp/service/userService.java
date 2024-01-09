package com.FarmProduce.FarmApp.service;

import com.FarmProduce.FarmApp.model.UserModel;
import com.FarmProduce.FarmApp.model.rolesModel;

import java.util.List;

public interface userService {

    //add a new user
    UserModel addUser(UserModel usermodel);
    //add new roles
    rolesModel addrole(rolesModel rolemodel);
    //add roles to a user
    void addroleToUser(String username, String roleName);

    //get specific user
    UserModel getUser(String username);

    //get all users
    List<UserModel> getUsers();

    //get all roles available
    List<rolesModel>getRoles();

    //delete a specific user

   void delUser(Long id);

   //update user details
   UserModel updateUser(UserModel usermodel);
//get user by id
    UserModel getUserById(Long id);
}
