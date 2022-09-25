package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.user.*;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipe.RecipeRepository;
import com.ssafy.letcipe.domain.recipeBookmark.RecipeBookmark;
import com.ssafy.letcipe.domain.recipeBookmark.RecipeBookmarkRepository;
import com.ssafy.letcipe.domain.recipeList.RecipeList;
import com.ssafy.letcipe.domain.recipeList.RecipeListRepository;
import com.ssafy.letcipe.domain.recipeListBookmark.RecipeListBookmark;
import com.ssafy.letcipe.domain.recipeListBookmark.RecipeListBookmarkRepository;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.domain.user.UserRepository;
import com.ssafy.letcipe.domain.user.UserType;
import com.ssafy.letcipe.exception.AuthorityViolationException;
import com.ssafy.letcipe.util.EncryptUtils;
import com.ssafy.letcipe.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeListRepository recipeListRepository;
    private final RecipeBookmarkRepository recipeBookmarkRepository;
    private final RecipeListBookmarkRepository recipeListBookmarkRepository;
    private final EncryptUtils encryptUtils;
    private final StringUtils stringUtils;

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

    @Transactional
    public ResGetUserRecipesDto readUserRecipe(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException());
        List<Recipe> recipeList = recipeRepository.findAllByUser(pageable, user);
        List<ResGetUserRecipeDto> dtoList = new ArrayList<>();
        for(Recipe recipe: recipeList){
            dtoList.add(new ResGetUserRecipeDto(recipe));
        }
        return new ResGetUserRecipesDto(dtoList);
    }

    public ResGetUserRecipeListsDto readUserRecipeList(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException());
        List<RecipeList> recipeListList = recipeListRepository.findAllByUser(pageable, user);
        List<ResGetUserRecipeListDto> dtoList = new ArrayList<>();
        for(RecipeList recipeList: recipeListList){
            dtoList.add(new ResGetUserRecipeListDto(recipeList));
        }
        return new ResGetUserRecipeListsDto(dtoList);
    }

    public ResGetUserRecipesDto readRecipeBookmark(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException());
        List<RecipeBookmark> recipeBookmarkList = recipeBookmarkRepository.findAllByUser(pageable, user);
        List<ResGetUserRecipeDto> dtoList = new ArrayList<>();
        for(RecipeBookmark recipeBookmark: recipeBookmarkList){
            dtoList.add(new ResGetUserRecipeDto(recipeBookmark.getRecipe()));
        }
        return new ResGetUserRecipesDto(dtoList);
    }

    public ResGetUserRecipeListsDto readRecipeListBookmark(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException());
        List<RecipeListBookmark> recipeListBookmarkList = recipeListBookmarkRepository.findAllByUser(pageable, user);
        List<ResGetUserRecipeListDto> dtoList = new ArrayList<>();
        for(RecipeListBookmark recipeListBookmark: recipeListBookmarkList){
            dtoList.add(new ResGetUserRecipeListDto(recipeListBookmark.getRecipeList()));
        }
        return new ResGetUserRecipeListsDto(dtoList);
    }

    public String readUserId(ReqGetUserIdDto requestDto) {
        User user = userRepository.findByNameAndPhone(requestDto.getName(), requestDto.getPhone())
                .orElseThrow(() -> new NullPointerException());
        return user.getUserId();
    }

    @Transactional
    public String readPassword(ReqGetPasswordDto requestDto) throws NoSuchAlgorithmException {
        User user = userRepository.findByUserIdAndPhone(requestDto.getUserId(), requestDto.getPhone())
                .orElseThrow(() -> new NullPointerException());
        String newPassword = stringUtils.getRandomString(10);
        StringBuilder sb = new StringBuilder();

        String salt = encryptUtils.getSalt(user.getUserId());
        sb.append(salt).append(newPassword);
        user.updatePassword(encryptUtils.encrypt(sb.toString()));

        return newPassword;
    }

    @Transactional
    public void updatePassword(ReqUpdatePasswordDto requestDto, Long userId) throws NoSuchAlgorithmException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException());

        StringBuilder sb = new StringBuilder();
        String salt = encryptUtils.getSalt(user.getUserId());
        sb.append(salt).append(requestDto.getPassword());
        String password = encryptUtils.encrypt(sb.toString());

        if(password.equals(user.getPassword())) {
            sb = new StringBuilder();
            sb.append(salt).append(requestDto.getNewPassword());
            String newPassword = encryptUtils.encrypt(sb.toString());
            user.updatePassword(newPassword);
        } else {
            throw new AuthorityViolationException("비밀번호를 변경할 수 없습니다.");
        }
    }
}
