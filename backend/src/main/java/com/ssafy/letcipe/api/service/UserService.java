package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.user.ReqPostUserDto;
import com.ssafy.letcipe.api.dto.user.ReqPutUserDto;
import com.ssafy.letcipe.api.dto.user.ResGetUserDto;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.domain.user.UserRepository;
import com.ssafy.letcipe.domain.user.UserType;
import com.ssafy.letcipe.util.EncryptUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final EncryptUtils encryptUtils;
    @Transactional
    public void createUser(ReqPostUserDto requestDto) throws NoSuchAlgorithmException {

        StringBuilder sb = new StringBuilder();

        String salt = encryptUtils.getSalt(requestDto.getUserId());
        sb.append(salt).append(requestDto.getUserId());
        String password = encryptUtils.encrypt(sb.toString());

        // 유저 엔티티 생성
        User user=User.builder()
                .userId(requestDto.getUserId())
                .userType(UserType.USER)
                .birth(requestDto.getBirth())
                .email(requestDto.getEmail())
                .family(requestDto.getFamily())
                .gender(requestDto.getGender())
                .name(requestDto.getName())
                .job(requestDto.getJob())
                .nickname(requestDto.getNickname())
                .password(password)
                .phone(requestDto.getPhone())
                .build();
        userRepository.save(user);
    }
}
