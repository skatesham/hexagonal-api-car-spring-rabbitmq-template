package core.demo.app.adapters.config.messaging;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MessageSenderImpl implements MessageSender {

    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String queue, String message) {
        log.trace("action=sending status=start topic={} message={}", queue, message);
        rabbitTemplate.convertAndSend(queue, queue, message);
        log.trace("action=sending status=sent topic={} message={}", queue, message);
    }

}
