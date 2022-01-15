package com.ead.course.services;

import com.ead.course.models.UserModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {

    Object findAll(Specification<UserModel> and, Pageable pageable);
}
