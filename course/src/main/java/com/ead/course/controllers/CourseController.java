package com.ead.course.controllers;

import com.ead.course.dtos.CourseDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.SpecificationTemplate;
import com.ead.course.validations.CourseValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseValidator validator;

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody CourseDto courseDto, Errors errors) {

        validator.validate(courseDto, errors);

        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }

        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseDto, courseModel);

        courseModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseModel));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable("courseId") UUID courseId) {
        Optional<CourseModel> course = courseService.findById(courseId);

        if (!course.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }
        courseService.delete(course.get());

        return ResponseEntity.ok("Course deleted successfully.");

    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable("courseId") UUID courseId, @RequestBody @Validated CourseDto courseDto) {
        Optional<CourseModel> course = courseService.findById(courseId);

        if (!course.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }
        var courseModel = course.get();
        courseModel.setName(courseDto.getName());
        courseModel.setDescription(courseDto.getDescription());
        courseModel.setImageUrl(courseDto.getImageUrl());
        courseModel.setCourseStatus(courseDto.getCourseStatus());
        courseModel.setCourseLevel(courseDto.getCourseLevel());
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.ok(courseService.save(courseModel));
    }

    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAll(SpecificationTemplate.CourseSpec spec, @PageableDefault(page = 0,
            size = 10,
            sort = "courseId",
            direction = Sort.Direction.ASC) Pageable pageable,
                                                    @RequestParam(required = false) UUID userId){

        return ResponseEntity.ok(courseService.findAll(spec, pageable));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getById(@PathVariable("courseId") UUID courseId){
        Optional<CourseModel> course = courseService.findById(courseId);

        if (!course.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }
        return  ResponseEntity.ok(course.get());
    }
}
