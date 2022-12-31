/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasf.mx.ctrlventas.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tasf.mx.ctrlventas.model.user;
import tasf.mx.ctrlventas.repository.userRepository;

/**
 *
 * @author silot
 */
@Service
@Transactional
public class userService {

    @Autowired
    private userRepository userRepository;

    public List<user> listAllUser() {
        return userRepository.findAll();
    }

    public void saveUser(user user) {
        userRepository.save(user);
    }

    public user getUser(Integer id) {
        return userRepository.findById(id).get();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public user getUserByusername(String username) {
        return userRepository.findByUsername(username);
    }
}
