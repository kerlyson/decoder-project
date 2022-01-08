package com.ead.authuser.dtos;

import com.ead.authuser.enums.CourseLevel;
import com.ead.authuser.enums.CourseStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CourseDto {
    private UUID courseId;
    private String name;
    private String description;
    private String imageUrl;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
    private CourseStatus courseStatus;
    private CourseLevel courseLevel;
}
