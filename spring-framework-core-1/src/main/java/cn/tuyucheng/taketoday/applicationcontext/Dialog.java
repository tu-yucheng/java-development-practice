package cn.tuyucheng.taketoday.applicationcontext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dialog {
    private Locale locale;
    private String hello;
    private String thanks;
}