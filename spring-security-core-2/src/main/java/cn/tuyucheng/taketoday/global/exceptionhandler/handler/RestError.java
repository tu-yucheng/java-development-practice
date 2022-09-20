package cn.tuyucheng.taketoday.global.exceptionhandler.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RestError {
    String errorCode;
    String errorMessage;
}