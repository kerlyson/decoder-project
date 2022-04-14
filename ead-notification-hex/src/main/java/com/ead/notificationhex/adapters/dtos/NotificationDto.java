package com.ead.notificationhex.adapters.dtos;


import com.ead.notificationhex.core.domain.enums.NotificationStatus;

public class NotificationDto {
    private NotificationStatus notificationStatus;

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
