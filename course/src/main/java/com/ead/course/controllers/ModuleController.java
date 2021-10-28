package com.ead.course.controllers;

import com.ead.course.dtos.ModuleDto;
import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> saveModule(@PathVariable("courseId") UUID courseId, @RequestBody @Validated ModuleDto moduleDto) {

        Optional<CourseModel> course = courseService.findById(courseId);
        if (!course.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }

        var module = new ModuleModel();
        BeanUtils.copyProperties(moduleDto, module);
        module.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        module.setCourse(course.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(module));
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> deleteModule(@PathVariable("courseId") UUID courseId, @PathVariable("moduleId") UUID moduleId) {

        Optional<ModuleModel> module = moduleService.findModuleIntoCourse(moduleId, courseId);
        if (!module.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
        }
        moduleService.delete(module.get());
        return ResponseEntity.ok("Module deleted successfully.");
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> updateCourse(@PathVariable("courseId") UUID courseId, @PathVariable("moduleId") UUID moduleId, @RequestBody @Validated ModuleDto moduloDto) {
        Optional<ModuleModel> moduleUpdate = moduleService.findModuleIntoCourse(moduleId, courseId);
        if (!moduleUpdate.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
        }

        var module = moduleUpdate.get();
        module.setTitle(moduloDto.getTitle());
        module.setDescription(moduloDto.getDescription());

        return ResponseEntity.ok(moduleService.save(module));
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<List<ModuleModel>> getAll(@PathVariable("courseId") UUID courseId) {
        return ResponseEntity.ok(moduleService.findAllByCourse(courseId));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> getById(@PathVariable("courseId") UUID courseId, @PathVariable("moduleId") UUID moduleId) {
        Optional<ModuleModel> module = moduleService.findModuleIntoCourse(moduleId, courseId);
        if (!module.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
        }
        return ResponseEntity.ok(module.get());
    }
}
