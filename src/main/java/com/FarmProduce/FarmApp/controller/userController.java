package com.FarmProduce.FarmApp.controller;

import com.FarmProduce.FarmApp.ErrorHandling.UserNotFoundException;
import com.FarmProduce.FarmApp.auth.AuthenticationRequest;
import com.FarmProduce.FarmApp.auth.AuthenticationResponse;
import com.FarmProduce.FarmApp.auth.ResponseModel;
import com.FarmProduce.FarmApp.model.UserModel;
import com.FarmProduce.FarmApp.model.rolesModel;
import com.FarmProduce.FarmApp.repository.UserRepo;
import com.FarmProduce.FarmApp.service.AuthenticationService;
import com.FarmProduce.FarmApp.service.userService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/")

public class userController {

    private final userService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/users")

    public ResponseEntity<List<UserModel>>getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }
    @PostMapping ("/addusers")
    public ResponseEntity<UserModel>addUser(@RequestBody UserModel usermodel){
        return ResponseEntity.ok().body(userService.addUser(usermodel));
    }
    @PostMapping ("/addroles")
    public ResponseEntity<rolesModel>addrole(@RequestBody rolesModel role){
        return ResponseEntity.ok().body(userService.addrole(role));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<rolesModel>>getRoles(){
        return ResponseEntity.ok().body(userService.getRoles());
    }

    @PostMapping ("/addrolestousers")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form){
        userService.addroleToUser(form.getUsername(),form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/userlogin")
    public ResponseEntity<AuthenticationResponse> userlogin(@RequestBody AuthenticationRequest  authenticationRequest){

      return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));

    }
    /*Delete the specific bot using ID*/
    @DeleteMapping("/user/{id}")
    public void delUser(@PathVariable Long id) {
        userService.delUser(id);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture, @RequestBody UserModel usermodel) {
        UserModel existingUser = userService.updateUser(usermodel, profilePicture);
        if (existingUser != null) {
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        try {
            UserModel user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/upload-profile-picture")
    public ResponseEntity<Map<String, String>> uploadProfilePicture(
            @RequestParam("profilePicture") MultipartFile file
    ) {
        String filePath = userService.uploadProfilePicture(file);
        Map<String, String> response = new HashMap<>();
        response.put("filePath", filePath);
        return ResponseEntity.ok(response);
    }


}
@Data
class RoleToUserForm{
    private String username;
    private String roleName;
}
