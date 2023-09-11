package com.FarmProduce.FarmApp.controller;

import com.FarmProduce.FarmApp.auth.AuthenticationRequest;
import com.FarmProduce.FarmApp.auth.AuthenticationResponse;
import com.FarmProduce.FarmApp.auth.ResponseModel;
import com.FarmProduce.FarmApp.model.UserModel;
import com.FarmProduce.FarmApp.model.rolesModel;
import com.FarmProduce.FarmApp.service.AuthenticationService;
import com.FarmProduce.FarmApp.service.userService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
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



}
@Data
class RoleToUserForm{
    private String username;
    private String roleName;
}
