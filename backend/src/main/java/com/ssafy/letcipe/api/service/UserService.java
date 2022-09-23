package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.user.ReqLoginUserDto;
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
        sb.append(salt).append(requestDto.getPassword());
        String password = encryptUtils.encrypt(sb.toString());

        // 유저 엔티티 생성
        User user = User.builder()
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

    public User loginUser(ReqLoginUserDto requestDto) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();

        String salt = encryptUtils.getSalt(requestDto.getUserId());
        sb.append(salt).append(requestDto.getPassword());
        String password = encryptUtils.encrypt(sb.toString());

        User user = userRepository.findByUserIdAndPassword(requestDto.getUserId(), password)
                .orElseThrow(() -> new NullPointerException());

        return user;
    }

    public ResGetUserDto readUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException());
        return new ResGetUserDto(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException());
        user.delete();
    }

    @Transactional
    public void updateUser(Long userId, ReqPutUserDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException());
        user.update(requestDto);
    }

    public User findUser(long userId) throws NullPointerException {
        return userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));
    }
}
