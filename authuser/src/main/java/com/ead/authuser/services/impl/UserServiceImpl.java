package com.ead.authuser.services.impl;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.UserEventDto;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.publishers.UserEventPublisher;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseClient client;

    @Autowired
    private UserEventPublisher userEventPublisher;

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    @Transactional
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Override
    public boolean existsByUsername(String userName) {
        return userRepository.existsByUsername(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable, Specification<UserModel> spec) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public UserModel saveUser(UserModel model){
        model = this.save(model);
        userEventPublisher.publishUserEvent(model.convertToUserEventDto(), ActionType.CREATE);
        return model;
    }

    @Override
    @Transactional
    public void deleteUser(UserModel model) {
        delete(model);
        userEventPublisher.publishUserEvent(model.convertToUserEventDto(), ActionType.DELETE);
    }

    @Override
    @Transactional
    public UserModel updateUser(UserModel model) {
        model = this.save(model);
        userEventPublisher.publishUserEvent(model.convertToUserEventDto(), ActionType.UPDATE);
        return model;
    }

    @Override
    public UserModel updatePassword(UserModel model) {
        return save(model);
    }
}
