package com.ead.course.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUserDto {

    private UUID courseId, userId;

}
