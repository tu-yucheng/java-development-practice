package cn.tuyucheng.taketoday.nullibility;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class Person {
    @NonNull
    private String fullName;
    @Nullable
    private String nickName;

    String getFullName() {
        return fullName;
    }

    void setFullName(String fullName) {
        if (fullName != null && fullName.isEmpty()) {
            fullName = null;
        }
        this.fullName = fullName;
    }

    String getNickName() {
        return nickName;
    }

    void setNickName(String nickName) {
        if (nickName != null && nickName.isEmpty()) {
            nickName = null;
        }
        this.nickName = nickName;
    }
}