package com.ptcg.pokemon_api.service;

import com.ptcg.pokemon_api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserService userService;
    
    public User authenticate(String username, String password) {
        try {
            User user = userService.getUserByUsername(username);
            
            if (userService.validatePassword(password, user.getPassword())) {
                // Não retorne a senha no objeto de resposta
                user.setPassword(null);
                return user;
            } else {
                throw new BadCredentialsException("Credenciais inválidas");
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Credenciais inválidas");
        }
    }
}