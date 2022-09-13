package cn.tuyucheng.taketoday.jsonexception;

import java.io.Serial;

public class CustomException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CustomException() {
        super("Custom exception message.");
    }
}