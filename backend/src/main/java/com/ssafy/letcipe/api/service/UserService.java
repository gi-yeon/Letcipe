package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.user.ReqPostUserDto;
import com.ssafy.letcipe.api.dto.user.ReqPutUserDto;
import com.ssafy.letcipe.api.dto.user.ResGetUserDto;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.domain.user.UserRepository;
import com.ssafy.letcipe.domain.user.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public void createUser(ReqPostUserDto requestDto){
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
                .password(requestDto.getPassword())
                .phone(requestDto.getPhone())
                .build();
        userRepository.save(user);
    }

    @Transactional
    public void createAdmin(ReqPostUserDto requestDto){
        // 유저 엔티티 생성
        User admin=User.builder()
                .userId(requestDto.getUserId())
                .userType(UserType.ADMIN)
                .birth(requestDto.getBirth())
                .email(requestDto.getEmail())
                .family(requestDto.getFamily())
                .gender(requestDto.getGender())
                .name(requestDto.getName())
                .job(requestDto.getJob())
                .nickname(requestDto.getNickname())
                .password(requestDto.getPassword())
                .phone(requestDto.getPhone())
                .build();
        userRepository.save(admin);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user=userRepository
                .findById(userId)
                .orElseThrow(()->new NullPointerException("유저 정보가 없습니다."));
        user.delete();
    }

    @Transactional
    public void updateUser(ReqPutUserDto requestDto,Long userId) {
        User user=userRepository
                .findById(userId)
                .orElseThrow(()->new NullPointerException("유저 정보가 없습니다."));
        user.update(requestDto);
    }
}
