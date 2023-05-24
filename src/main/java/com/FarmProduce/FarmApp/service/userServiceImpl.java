package com.FarmProduce.FarmApp.service;

import com.FarmProduce.FarmApp.model.UserModel;
import com.FarmProduce.FarmApp.model.rolesModel;
import com.FarmProduce.FarmApp.repository.RoleRepo;
import com.FarmProduce.FarmApp.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class userServiceImpl implements userService{
    //import the repos
    @Autowired
    private  RoleRepo rolerepo;
    @Autowired
    private  UserRepo userrepo;

    private PasswordEncoder passwordEncoder;


    @Override
    public UserModel addUser(UserModel usermodel) {
        log.info("Adding user {} to the db",usermodel.getName());
       this.passwordEncoder =new BCryptPasswordEncoder();
        String encodedPassword = this.passwordEncoder.encode(usermodel.getPassword());
        usermodel.setPassword(encodedPassword);
        return userrepo.save(usermodel);
    }

    @Override
    public rolesModel addrole(rolesModel rolemodel) {
        log.info("Adding role {} to the db",rolemodel.getName());
        return rolerepo.save(rolemodel);
    }

    @Override
    public void addroleToUser(String username, String roleName) {
        log.info("Adding a specific role  to a user");
        UserModel usermodel = userrepo.findByUsername(username).get();
        rolesModel rolemodel = rolerepo.findByName(roleName);
        usermodel.getRoles().add(rolemodel);

    }

    @Override
    public UserModel getUser(String username) {
        log.info("Getting a specific user from the db");
        return userrepo.findByUsername(username).get();
    }

    @Override
    public List<UserModel> getUsers() {
        log.info("Getting all users from the db");
        return userrepo.findAll();
    }

    @Override
    public List<rolesModel> getRoles() {
        log.info("Getting all roles from the db");
        return rolerepo.findAll();
    }



}
