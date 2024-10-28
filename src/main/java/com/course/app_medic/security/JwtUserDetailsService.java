package com.course.app_medic.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.course.app_medic.models.User;
import com.course.app_medic.repositories.IUserRepo;

import lombok.RequiredArgsConstructor;

// Clase: se encarga de devolver el usuario autenticado con su información y dejarlo disponible para la app 
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final IUserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findOneByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User not exists", username));
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        user.getRoles().forEach(rol -> {
            roles.add(new SimpleGrantedAuthority(rol.getName())); // generar la lista de roles
        });

        // Situarlo al contexto de spring security
        UserDetails ud = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                roles);

        return ud;

    }

}
