package cn.tuyucheng.taketoday.autowire.sample;

import org.springframework.stereotype.Component;

@Component
@FormatterType("Bar")
public class BarFormatter implements Formatter {

    public String format() {
        return "bar";
    }
}