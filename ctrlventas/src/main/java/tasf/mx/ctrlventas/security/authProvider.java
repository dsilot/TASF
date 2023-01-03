/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasf.mx.ctrlventas.security;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tasf.mx.ctrlventas.payload.login;

/**
 *
 * @author silot
 */
@Component
public class authProvider implements AuthenticationProvider {

        @Autowired
    private RestTemplate restTemplate;
        
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        String url = "http://localhost:8080/user/check";
        login l = new login();
        l.setUsername(username);
        l.setPassword(pwd);
        ResponseEntity<Boolean> response = restTemplate.postForEntity(url, l, Boolean.class);
        Boolean res = response.getBody();
        if (res) {
            return new UsernamePasswordAuthenticationToken(username, pwd, Collections.emptyList());
        } else {
            throw new BadCredentialsException("Usuario y/o contraseña incorrecto");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

}
