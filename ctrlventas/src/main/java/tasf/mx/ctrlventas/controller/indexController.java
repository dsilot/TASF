/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasf.mx.ctrlventas.controller;

/**
 *
 * @author silot
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tasf.mx.ctrlventas.payload.login;

@Controller
public class indexController {

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String tologin() {
        return "login";
    }

    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        MediaType.APPLICATION_JSON_VALUE})
    public String login(login l) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(l.getUsername(), l.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return authentication.isAuthenticated() ? "redirect:/consulta" : "login";
        } catch (Exception e) {
            return "login?error=true";
        }
    }

    @GetMapping("/consulta")
    @PreAuthorize("hasRole('USER')")
    public String consulta() {
        return "consulta";
    }

}
