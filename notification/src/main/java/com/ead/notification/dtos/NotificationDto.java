package com.ead.notification.dtos;

import com.ead.notification.enums.NotificationStatus;

public class NotificationDto {
    private NotificationStatus notificationStatus;

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
