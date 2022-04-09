package com.ead.authuser.services.impl;

import com.ead.authuser.services.UtilService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilServiceImpl implements UtilService {

    @Value("${ead.api.url.course}")
    private String REQUEST_URI_COURSE;

    public String createUrl(UUID userId, Pageable pageable){
        return REQUEST_URI_COURSE + "/courses?userId=" + userId
                + "&page=" + pageable.getPageNumber()
                + "&size=" + pageable.getPageSize()
                + "&sort=" + pageable.getSort().toString().replace(": ", ",");
    }

    public String getUrl(){
        return REQUEST_URI_COURSE;
    }
}
