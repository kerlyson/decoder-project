package com.ead.notification.services.impl;

import com.ead.notification.models.NotificationModel;
import com.ead.notification.repositories.NotificationRespository;
import com.ead.notification.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    final NotificationRespository notificationRespository;


    @Autowired
    public NotificationServiceImpl(NotificationRespository notificationRespository) {
        this.notificationRespository = notificationRespository;
    }

    @Override
    public void saveNotification(NotificationModel notificationModel) {
        notificationRespository.save(notificationModel);
    }
}
