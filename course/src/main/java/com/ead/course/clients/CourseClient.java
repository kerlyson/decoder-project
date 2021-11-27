package com.ead.course.clients;

import com.ead.course.dtos.ResponsePageDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.services.UtilService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class CourseClient {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilService utilService;

    public Page<UserDto> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        List<UserDto> result = null;
        String url = utilService.createUrl(courseId, pageable);

        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);

        try {
            ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType =
                    new ParameterizedTypeReference<ResponsePageDto<UserDto>>() {

                    };
            ResponseEntity<ResponsePageDto<UserDto>> resultRest = restTemplate.exchange(
                    url, HttpMethod.GET, null, responseType
            );

            result = resultRest.getBody().getContent();

            log.debug("Response Number of Elements: {}", result.size());
        } catch (Exception e) {
            log.error("Error request /user: {}", e);
        }
        log.info("Endind request /user courseId: {}", courseId);
        return new PageImpl<UserDto>(result);
    }
}
