package com.cibertec.GameMaster.application.services;

import com.cibertec.GameMaster.application.port.CustomerPort;
import com.cibertec.GameMaster.application.port.UserPort;
import com.cibertec.GameMaster.infraestructure.database.entity.Customer;
import com.cibertec.GameMaster.infraestructure.database.entity.RoleType;
import com.cibertec.GameMaster.infraestructure.database.entity.UserEntity;
import com.cibertec.GameMaster.infraestructure.mapper.UserMapper;
import com.cibertec.GameMaster.infraestructure.security.JwtUtils;
import com.cibertec.GameMaster.infraestructure.web.dto.UserDTO;
import com.cibertec.GameMaster.infraestructure.web.dto.auth.AuthResponse;
import com.cibertec.GameMaster.infraestructure.web.dto.auth.LoginRequest;
import com.cibertec.GameMaster.infraestructure.web.dto.auth.RegisterRequest;
import com.cibertec.GameMaster.infraestructure.web.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserDetailsServiceImpl security;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserPort userPort;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerPort customerPort;

    @Autowired
    private UserMapper mapper;


    public AuthResponse loginValidate(LoginRequest login) {
        String username = login.username();
        String password = login.password();

        Authentication authentication = security.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        return new AuthResponse(username,
                "User logged successfully",
                accessToken,
                true);
    }

    public AuthResponse createCustomer(RegisterRequest request){

        if (userPort.existsByUsername(request.username())) {
            throw new BadRequestException("Username is already taken");
        }

        if (userPort.existsByEmail(request.email())) {
            throw new BadRequestException("Email is already in use");
        }


        UserEntity userEntity = UserEntity.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(RoleType.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .build();

        userPort.save(userEntity);

        Customer customer = new Customer();
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());
        customer.setAddress(request.address());
        customer.setUser(userEntity);

        customerPort.save(customer);

        List<SimpleGrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userEntity, null, authorities);

        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponse(
                request.username(),
                "Customer created successfully",
                accessToken,
                true
        );
    }

    public RoleType getRole(String username){
        return mapper.toDTO(userPort.getUserByUsername(username).orElseThrow()).getRole();
    }

}
