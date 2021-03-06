package com.ead.course.services.impl;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.ModuleService;
import com.ead.course.specifications.SpecificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(ModuleModel module) {
        List<LessonModel> lessons = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
        if (!lessons.isEmpty()) {
            lessonRepository.deleteAll(lessons);
        }
        moduleRepository.delete(module);
    }

    @Override
    public ModuleModel save(ModuleModel module) {
        return moduleRepository.save(module);
    }

    @Override
    public Optional<ModuleModel> findById(UUID moduleId) {
        return moduleRepository.findById(moduleId);
    }

    @Override
    public Optional<ModuleModel> findModuleIntoCourse(UUID moduleId, UUID courseId) {
        return moduleRepository.findModuleIntoCourse(moduleId, courseId);
    }

    @Override
    public Page<ModuleModel> findAllByCourse(Specification spec, Pageable pageable) {
        return moduleRepository.findAll(spec, pageable);
    }
}
