package cn.tuyucheng.taketoday.jsondateformat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlainContactWithJavaUtilDate {
    private String name;
    private String address;
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdate;
}