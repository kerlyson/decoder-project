package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.ResponsePageDto;
import com.ead.authuser.services.UtilService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class CourseClient {

    @Autowired
    private UtilService utilService;

    @Autowired
    private RestTemplate restTemplate;


//    @Retry(name = "retryInstance", fallbackMethod = "retryFallback")
    @CircuitBreaker(name="circuitbreakerInstance")
    public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable, String token) {
        List<CourseDto> result = null;
        String url = utilService.createUrl(userId, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", headers);

        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);


            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType =
                    new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {};
            ResponseEntity<ResponsePageDto<CourseDto>> resultRest = restTemplate.exchange(
                    url, HttpMethod.GET, requestEntity, responseType
            );

            result = resultRest.getBody().getContent();

            log.debug("Response Number of Elements: {}", result.size());
     
        log.info("Ending request /course userId: {}", userId);
        return new PageImpl<CourseDto>(result);
    }

    public Page<CourseDto> retryFallback(UUID userId, Pageable pageable, Throwable t){
        log.error("Inside retryfallback, cause - {}", t.toString());
        List<CourseDto> searchResult = new ArrayList<>();
        return new PageImpl<>(searchResult);
    }

    public Page<CourseDto> circuitbreakerFallback(UUID userId, Pageable pageable, Throwable t){
        log.error("Inside circuitbreakerFallback, cause - {}", t.toString());
        List<CourseDto> searchResult = new ArrayList<>();
        return new PageImpl<>(searchResult);
    }
}
