package com.ssafy.letcipe.api.dto.user;

import com.ssafy.letcipe.domain.user.GenderType;
import com.ssafy.letcipe.domain.user.JobType;
import com.ssafy.letcipe.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
public class LogUserDto {
    private Long id;
    private String nickname;
    private LocalDate birth;
    private GenderType gender;
    private JobType job;
    private Integer family;

    public LogUserDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.birth = user.getBirth();
        this.gender = user.getGender();
        this.job = user.getJob();
        this.family = user.getFamily();
    }

    public Map<String, Object> toJsonMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("nickname", nickname);
        map.put("birth", birth);
        map.put("gender", gender);
        map.put("job", job);
        map.put("family", family);
        return map;
    }
}
