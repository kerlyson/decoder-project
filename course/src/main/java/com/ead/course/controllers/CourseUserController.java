package com.ead.course.controllers;

import com.ead.course.dtos.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @GetMapping("course/{courseId}/users")
    public ResponseEntity<Page<UserDto>> getAll(@PageableDefault(page = 0,
            size = 10,
            sort = "userId",
            direction = Sort.Direction.ASC) Pageable pageable,
                                                @PathParam("courseId") UUID courseId) {

        return ResponseEntity.ok().build();
    }

}
