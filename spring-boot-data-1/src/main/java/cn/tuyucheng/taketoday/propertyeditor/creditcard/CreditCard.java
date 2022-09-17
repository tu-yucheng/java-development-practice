package cn.tuyucheng.taketoday.propertyeditor.creditcard;

import lombok.Data;

@Data
public class CreditCard {
    private String rawCardNumber;
    private Integer bankIdNo;
    private Integer accountNo;
    private Integer checkCode;
}