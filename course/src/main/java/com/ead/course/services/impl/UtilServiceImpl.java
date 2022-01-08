package com.ead.course.services.impl;

import com.ead.course.services.UtilService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilServiceImpl implements UtilService {

    @Value("${ead.api.url.authuser}")
    private String REQUEST_URI_AUTHUSER;

    public String createUrlGetAllUsersByCourseId(UUID courseId, Pageable pageable){
        return REQUEST_URI_AUTHUSER + "/users?courseId=" + courseId
                + "&page=" + pageable.getPageNumber()
                + "&size=" + pageable.getPageSize()
                + "&sort=" + pageable.getSort().toString().replace(": ", ",");
    }
}
