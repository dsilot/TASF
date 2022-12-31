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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tasf.mx.ctrlventas.payload.checklogin;
import tasf.mx.ctrlventas.payload.login;

@Controller
public class indexController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/login")
    public String tologin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestBody login l) {
        try {
            String url = "http://localhost:8080/user/check";
            checklogin response = restTemplate.getForObject(url, checklogin.class);
            
            return response.getIslogin() ? "consulta" : "login";
        } catch (Exception e) {
            return "login";
        }
    }
    
}
