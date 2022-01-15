package com.ead.course.controllers;

import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService courseUserService;


    @GetMapping("course/{courseId}/users")
    public ResponseEntity<Object> getAll(@PageableDefault(page = 0,
            size = 10,
            sort = "userId",
            direction = Sort.Direction.ASC) Pageable pageable,
                                                @PathParam("courseId") UUID courseId) {

        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);

        if(!courseModelOptional.isPresent()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PostMapping("course/{courseId}/users/subscription")
    public ResponseEntity<Object> sabeSubscriptionUseInCourse(@PathParam("courseId") UUID courseId, @RequestBody @Valid SubscriptionDto subscriptionDto){

        ResponseEntity<UserDto> responseUser;

        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
        if(!courseModelOptional.isPresent()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully.");
    }

}
