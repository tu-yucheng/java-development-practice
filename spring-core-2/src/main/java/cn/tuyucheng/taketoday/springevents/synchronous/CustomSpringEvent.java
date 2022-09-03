package cn.tuyucheng.taketoday.springevents.synchronous;

import org.springframework.context.ApplicationEvent;

import java.io.Serial;

public class CustomSpringEvent extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = -8053143381029977953L;

    private final String message;

    public CustomSpringEvent(final Object source, final String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}