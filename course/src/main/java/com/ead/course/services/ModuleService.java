package com.ead.course.services;

import com.ead.course.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface ModuleService {

    void delete(ModuleModel module);

    ModuleModel save(ModuleModel module);

    Optional<ModuleModel> findById(UUID moduleId);

    Optional<ModuleModel> findModuleIntoCourse(UUID moduleId, UUID courseId);

    Page<ModuleModel> findAllByCourse(Specification<ModuleModel> spec, Pageable pageable);
}
