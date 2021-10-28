package com.ead.course.controllers;

import com.ead.course.dtos.LessonDto;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.LessonService;
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
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private ModuleService moduleService;

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Object> save(@PathVariable("moduleId") UUID moduleId, @RequestBody @Validated LessonDto lessonDto) {
        Optional<ModuleModel> module = moduleService.findById(moduleId);
        if (!module.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found.");
        }

        var lesson = new LessonModel();
        BeanUtils.copyProperties(lessonDto, lesson);
        lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lesson.setModule(module.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lesson));
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> delete(@PathVariable("moduleId") UUID moduleId, @PathVariable("lessonId") UUID lessonId) {

        Optional<LessonModel> lesson = lessonService.findLessonIntoModule(lessonId, moduleId);
        if (!lesson.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }
        lessonService.delete(lesson.get());
        return ResponseEntity.ok("Lesson deleted successfully.");
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> updateCourse(@PathVariable("moduleId") UUID moduleId, @PathVariable("lessonId") UUID lessonId, @RequestBody @Validated LessonDto lessonDto) {
        Optional<LessonModel> lesson = lessonService.findLessonIntoModule(lessonId, moduleId);
        if (!lesson.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }

        var lessonM = lesson.get();
        lessonM.setTitle(lessonDto.getTitle());
        lessonM.setDescription(lessonDto.getDescription());
        lessonM.setVideoUrl(lessonDto.getVideoUrl());
        
        return ResponseEntity.ok(lessonService.save(lessonM));
    }

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<List<LessonModel>> getAll(@PathVariable("moduleId") UUID moduleId) {
        return ResponseEntity.ok(lessonService.findAllByModule(moduleId));
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> getById(@PathVariable("moduleId") UUID moduleId, @PathVariable("lessonId") UUID lessonId) {
        Optional<LessonModel> lesson = lessonService.findLessonIntoModule(lessonId, moduleId);
        if (!lesson.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }
        return ResponseEntity.ok(lesson.get());
    }
}
