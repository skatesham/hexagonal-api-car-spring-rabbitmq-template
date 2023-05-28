package core.demo.app.extra.config;


import core.demo.app.adapters.config.messaging.MessageSender;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Primary
@Component
public class MessageSenderMock implements MessageSender {

    private final MultiValueMap<String, String> data = new LinkedMultiValueMap<>();

    @Override
    public void send(String queue, String message) {
        data.add(queue, message);
    }

    public List<String> getTopic(String queue) {
        return data.get(queue);
    }

    public void clean() {
        data.clear();
    }

}
