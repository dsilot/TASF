/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasf.mx.ctrlventas.controller;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tasf.mx.ctrlventas.model.user;
import tasf.mx.ctrlventas.service.userService;

/**
 *
 * @author silot
 */
@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private userService userService;

    @GetMapping("")
    public List<user> list() {
        return userService.listAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<user> get(@PathVariable Integer id) {
        try {
            user user = userService.getUser(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public void add(@RequestBody user user) {
        userService.saveUser(user);
    }

    @PostMapping("/check")
    public Boolean check(@RequestBody user user) {
        try {
            user u = userService.getUserByusername(user.getUsername());
            return u.getPassword().equals(user.getPassword());
        } catch (Exception e) {
            return false;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody user user, @PathVariable Integer id) {
        try {
            user existUser = userService.getUser(id);
            // user.setId(id);
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

}
