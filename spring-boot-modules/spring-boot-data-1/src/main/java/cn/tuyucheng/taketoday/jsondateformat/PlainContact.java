package cn.tuyucheng.taketoday.jsondateformat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlainContact {
    private String name;
    private String address;
    private String phone;
    private LocalDate birthday;
    private LocalDateTime lastUpdate;
}