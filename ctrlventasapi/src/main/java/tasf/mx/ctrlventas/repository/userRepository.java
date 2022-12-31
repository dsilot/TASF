/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasf.mx.ctrlventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tasf.mx.ctrlventas.model.user;

/**
 *
 * @author silot
 */
public interface userRepository extends JpaRepository<user, Integer> {
   user findByUsername(String username);
}

