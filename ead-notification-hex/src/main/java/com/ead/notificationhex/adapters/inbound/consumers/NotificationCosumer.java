package com.ead.notificationhex.adapters.inbound.consumers;

import com.ead.notificationhex.adapters.dtos.NotificationCommandDto;
import com.ead.notificationhex.core.domain.NotificationDomain;
import com.ead.notificationhex.core.domain.enums.NotificationStatus;
import com.ead.notificationhex.core.ports.NotificationServicePort;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class NotificationCosumer {

    final NotificationServicePort notificationService;

    @Autowired
    public NotificationCosumer(NotificationServicePort notificationService) {
        this.notificationService = notificationService;
    }


    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${ead.broker.queue.notificationCommandQueue.name}", durable = "true"),
                    exchange = @Exchange(
                            value = "${ead.broker.exchange.notificationCommandExchange}",
                            type = ExchangeTypes.TOPIC,
                            ignoreDeclarationExceptions = "true"
                    ),
                    key = "${ead.broker.key.notificationCommandKey}"
            )
    )
    public void listen(@Payload NotificationCommandDto notificationCommandDto){
        var notificationModel = new NotificationDomain();

        BeanUtils.copyProperties(notificationCommandDto, notificationModel);

        notificationModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        notificationModel.setNotificationStatus(NotificationStatus.CREATED);

        notificationService.saveNotification(notificationModel);
    }
}
