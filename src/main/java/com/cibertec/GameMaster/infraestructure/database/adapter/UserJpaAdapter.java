package com.cibertec.GameMaster.infraestructure.database.adapter;

import com.cibertec.GameMaster.application.port.UserPort;
import com.cibertec.GameMaster.infraestructure.database.entity.UserEntity;
import com.cibertec.GameMaster.infraestructure.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserJpaAdapter implements UserPort {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserEntity> getUsersActive() {
        return userRepository.findAll().
                stream().filter(UserEntity::isStatus).toList();
    }

    @Override
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public boolean logicDelete(Long id) {
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
