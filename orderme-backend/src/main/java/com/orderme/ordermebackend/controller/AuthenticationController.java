package com.orderme.ordermebackend.controller;

import com.orderme.ordermebackend.controller.utils.PathRoutes;
import com.orderme.ordermebackend.model.dto.security.AuthenticationRequest;
import com.orderme.ordermebackend.model.entity.security.AuthenticationResponse;
import com.orderme.ordermebackend.utils.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathRoutes.PATH_AUTH)
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(value = "/admin", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody()
    public String home() {
        //TODO tbd
        return "Hello";
    }

    @PostMapping("/authenticate") //ОТРИМАТИ ТОКЕН, АЛЯ ЛОГІН
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest credentials) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
        } catch (BadCredentialsException e) {
            System.err.println("Error: ");
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getEmail());
        String jwt = JwtUtils.generateJWTToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
