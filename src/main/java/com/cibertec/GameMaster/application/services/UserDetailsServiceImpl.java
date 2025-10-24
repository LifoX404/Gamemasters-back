package com.cibertec.GameMaster.application.services;

import com.cibertec.GameMaster.application.port.UserPort;
import com.cibertec.GameMaster.infraestructure.database.entity.UserEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserPort userPort;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(
            UserPort userPort,
            PasswordEncoder passwordEncoder) {
        this.userPort = userPort;;
        this.passwordEncoder = passwordEncoder;;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userPort.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        if (!userEntity.isStatus()) {
            throw new UsernameNotFoundException("Usuario deshabilitado");
        }

        List<SimpleGrantedAuthority> authoritiesList = List.of(
                new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name())
        );


        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isStatus(),
                true,
                true,
                true,
                authoritiesList
        );
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }



}
