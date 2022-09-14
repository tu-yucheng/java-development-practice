package cn.tuyucheng.taketoday.applicationcontext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int no;
    private String name;

    public void destroy() {
        log.info("Student(no: {}) is destroyed", no);
    }
}