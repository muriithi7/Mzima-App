package com.FarmProduce.FarmApp.service;

import com.FarmProduce.FarmApp.ErrorHandling.UserNotFoundException;
import com.FarmProduce.FarmApp.model.UserModel;
import com.FarmProduce.FarmApp.model.rolesModel;
import com.FarmProduce.FarmApp.repository.RoleRepo;
import com.FarmProduce.FarmApp.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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


    private final PasswordEncoder passwordEncoder;

    @Value("${app.upload.dir}")
    private String uploadDir;

// adding users to the DB
    @Override
    public UserModel addUser(UserModel usermodel) {
        log.info("Adding user {} to the db",usermodel.getName());
      // this.passwordEncoder =new BCryptPasswordEncoder();
        //String encodedPassword = this.passwordEncoder.encode(usermodel.getPassword());
       // usermodel.setPassword(encodedPassword);
       // return userrepo.save(usermodel);

        String encodedPassword = passwordEncoder.encode(usermodel.getPassword());
        usermodel.setPassword(encodedPassword);

        return userrepo.save(usermodel);
    }
// Adding roles to the db
    @Override
    public rolesModel addrole(rolesModel rolemodel) {
        log.info("Adding role {} to the db",rolemodel.getName());
        return rolerepo.save(rolemodel);
    }
//adding roles to the users added in the db
    @Override
    public void addroleToUser(String username, String roleName) {
        log.info("Adding a specific role  to a user");
        UserModel usermodel = userrepo.findByUsername(username).get();
        rolesModel rolemodel = rolerepo.findByName(roleName);
        usermodel.getRoles().add(rolemodel);

    }
// getting a specific user from the database
    @Override
    public UserModel getUser(String username) {
        log.info("Getting a specific user from the db");
        return userrepo.findByUsername(username).get();
    }
//getting all the users from the db
    @Override
    public List<UserModel> getUsers() {
        log.info("Getting all users from the db");
        return userrepo.findAll();
    }
// Checking all the roles from the db
    @Override
    public List<rolesModel> getRoles() {
        log.info("Getting all roles from the db");
        return rolerepo.findAll();
    }
 @Override
 public void delUser(Long id) {
     Optional<UserModel> user = userrepo.findById(id);
     if (user.isPresent()) {
         userrepo.deleteById(id);
     }
     else {
         throw new UserNotFoundException( id );
     }
 }

 //updating user details

    @Override

    public UserModel updateUser(UserModel usermodel, MultipartFile profilePicture) {
        Optional<UserModel> optionalUser = userrepo.findById(usermodel.getId());


        if (optionalUser.isPresent()) {
            UserModel existingUser = optionalUser.get();
            // Update the user's name and username
            existingUser.setName(usermodel.getName());
            existingUser.setUsername(usermodel.getUsername());
            // Update user roles (assuming UserModel has a @ManyToMany relationship with rolesModel)
            List<rolesModel> updatedRoles = new ArrayList<>();
            for (rolesModel role : usermodel.getRoles()) {
                rolesModel existingRole = rolerepo.findByName(role.getName());
                if (existingRole != null) {
                    updatedRoles.add(existingRole);
                }
            }
            existingUser.setRoles(updatedRoles);
            // Update the profile picture
            try {
                // Handle profile picture upload
                if (profilePicture != null && !profilePicture.isEmpty()) {
                    // Save the profile picture and update the path in the database
                    String profilePicturePath = saveProfilePicture(profilePicture);
                    existingUser.setProfileImagePath(profilePicturePath);

                }
            } catch (IOException e) {
                // Handle the exception, you might want to log it
                throw new RuntimeException("Error updating user profile picture", e);
            }

            // Check if the password is updated
            if (!passwordEncoder.matches(usermodel.getPassword(), existingUser.getPassword())) {
                String encodedPassword = passwordEncoder.encode(usermodel.getPassword());
                existingUser.setPassword(encodedPassword);
            }

            return userrepo.save(existingUser);
        } else {
            throw new UserNotFoundException(usermodel.getId());
        }
    }



    // Save the profile picture to the specified directory
    private String saveProfilePicture(MultipartFile file) throws IOException {
        Path uploadPath = Path.of(uploadDir);

        // Create a unique filename
        String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Save the file to the specified folder using uploadPath
        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return the file path to save in the database
        return filePath.toString();
    }

    @Override
    public String uploadProfilePicture(MultipartFile file) {
        try {
            // Save the profile picture to the specified directory
            String profilePicturePath = saveProfilePicture(file);
            return profilePicturePath;
        } catch (IOException e) {
            // Handle the exception, you might want to log it
            throw new RuntimeException("Error uploading profile picture", e);
        }
    }


    @Override
    public UserModel getUserById(Long id) {
        Optional<UserModel> optionalUser = userrepo.findById(id);
        return optionalUser.orElseThrow(() -> new UserNotFoundException(id));
    }


}
