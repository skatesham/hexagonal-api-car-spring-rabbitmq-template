package core.demo.app.adapters.config.messaging;

public interface MessageSender {

    void send(String queue, String message);

}
