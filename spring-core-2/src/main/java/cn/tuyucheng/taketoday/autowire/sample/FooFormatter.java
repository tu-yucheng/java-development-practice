package cn.tuyucheng.taketoday.autowire.sample;

import org.springframework.stereotype.Component;

@Component
@FormatterType("Foo")
public class FooFormatter implements Formatter {

    public String format() {
        return "foo";
    }
}