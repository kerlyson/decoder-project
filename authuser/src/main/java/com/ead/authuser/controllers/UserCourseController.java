package com.ead.authuser.controllers;

import com.ead.authuser.clients.UserClient;
import com.ead.authuser.dtos.CourseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    private UserClient userClient;

    @GetMapping("user/{userId}/courses")
    public ResponseEntity<Page<CourseDto>> getAll(@PageableDefault(page = 0,
            size = 10,
            sort = "courseId",
            direction = Sort.Direction.ASC) Pageable pageable,
                                                  @PathParam("userId") UUID userId) {

        return ResponseEntity.ok(userClient.getAllCoursesByUser(userId, pageable));
    }
}
