package com.ead.notification.services.impl;

import com.ead.notification.enums.NotificationStatus;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.repositories.NotificationRespository;
import com.ead.notification.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Page<NotificationModel> findAllNotificationByUser(UUID userId, Pageable pageable) {
        return notificationRespository.findAllByUserIdAndNotificationStatus(
                userId,
                NotificationStatus.CREATED,
                pageable
        );
    }

    @Override
    public Optional<NotificationModel> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        return notificationRespository.findByNotificationIdAndUserId(notificationId, userId);
    }
}
