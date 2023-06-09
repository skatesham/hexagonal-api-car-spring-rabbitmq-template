package core.demo.app.adapters.sender;

import core.demo.app.core.domain.VeiculoEntity;
import core.demo.app.core.port.outgoing.SendAsyncVeiculeCreationPort;
import core.demo.app.utils.JsonUtils;
import core.demo.app.adapters.config.messaging.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VeiculoRequestedSender implements SendAsyncVeiculeCreationPort {

    @Autowired
    private MessageSender messageSender;

    @Value("${queue.name}")
    private String queueMessage;

    @Override
    public void send(VeiculoEntity message) {
        messageSender.send(queueMessage, JsonUtils.convertToString(message));
    }

}