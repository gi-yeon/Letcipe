package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.recipe.ResGetHotRecipeComponentDto;
import com.ssafy.letcipe.api.dto.user.*;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipe.RecipeRepository;
import com.ssafy.letcipe.domain.recipeBookmark.RecipeBookmark;
import com.ssafy.letcipe.domain.recipeBookmark.RecipeBookmarkRepository;
import com.ssafy.letcipe.domain.recipeLike.RecipeLike;
import com.ssafy.letcipe.domain.recipeLike.RecipeLikeRepository;
import com.ssafy.letcipe.domain.recipeList.RecipeList;
import com.ssafy.letcipe.domain.recipeList.RecipeListRepository;
import com.ssafy.letcipe.domain.recipeListBookmark.RecipeListBookmark;
import com.ssafy.letcipe.domain.recipeListBookmark.RecipeListBookmarkRepository;
import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.domain.user.*;
import com.ssafy.letcipe.exception.AuthorityViolationException;
import com.ssafy.letcipe.exception.BadRequestException;
import com.ssafy.letcipe.util.EncryptUtils;
import com.ssafy.letcipe.util.FileHandler;
import com.ssafy.letcipe.util.StringUtils;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeListRepository recipeListRepository;
    private final RecipeBookmarkRepository recipeBookmarkRepository;

    private final RecipeLikeRepository recipeLikeRepository;
    private final RecipeListBookmarkRepository recipeListBookmarkRepository;
    private final EncryptUtils encryptUtils;
    private final StringUtils stringUtils;

    private final FileHandler fileHandler;

    @Transactional
    public ResGetHotRecipeComponentDto getAttribute(Long userId) {
        JobType job;
        GenderType gen;
        int fam;
        int age;
        List<String> str = new ArrayList<>();
        Random rnd = new Random();
        if (userId == -1L) {
            job = JobType.values()[rnd.nextInt(4)];
            gen = GenderType.values()[rnd.nextInt(2)];
            fam = rnd.nextInt(4) + 1;
            age = (rnd.nextInt(4) + 1) * 10;
        } else {
            User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException());
            job = user.getJob();
            gen = user.getGender();
            fam = user.getFamily();
            age = (LocalDate.now().getYear() - user.getBirth().getYear()) / 10 * 10;
        }
        str.add(gen.getDesc().substring(0, 1).toUpperCase());
        str.add("" + age);
        str.add("" + fam);
        str.add(job.getDesc().toUpperCase());
        int flag = rnd.nextInt(16);
        StringBuilder att = new StringBuilder();
        StringBuilder title = new StringBuilder();
        boolean[] check = new boolean[4];
        int cnt = 0;
        for (int i = 0; i < 4; i++) {
            if (flag % 2 == 0) {
                att.append(str.get(i));
                check[i] = true;
                cnt++;
            } else {
                att.append("-");
            }
            att.append(",");
            flag /= 2;
        }
        if (check[2]) {
            if (str.get(2).equals("1")) {
                title.append("혼자 사는 ");
            } else {
                title.append(str.get(2)).append("명이서 사는 ");
            }
            if (cnt == 1)
                title.append("사람이 ");
        }
        if (check[1])
            if (check[0] || check[3])
                title.append(str.get(1) + "대 ");
            else
                title.append(str.get(1) + "대가 ");
        if (check[0])
            if (check[3])
                title.append(str.get(0).equals("F") ? "여성 " : "남성 ");
            else
                title.append(str.get(0).equals("F") ? "여성이 " : "남성이 ");
        if (check[3]) {
            switch (str.get(3)) {
                case "STUDENT":
                    title.append("학생이 ");
                    break;
                case "JUBU":
                    title.append("주부가 ");
                    break;
                case "WORKER":
                    title.append("직장인이 ");
                    break;
                case "COOK":
                    title.append("요리사가 ");
                    break;
            }
        }
        if(cnt==0){
            title.append("모두가 ");
        }
        title.append("좋아하는 레시피들");
        att.setLength(att.length() - 1);
        ResGetHotRecipeComponentDto hotRecipeDto = ResGetHotRecipeComponentDto.builder()
                .title(title.toString())
                .attribute(att.toString())
                .build();
        return hotRecipeDto;
    }

    @Transactional
    public ResGetHotRecipeComponentDto getAttribute2(Long userId) {
        JobType job;
        GenderType gen;
        int fam;
        int age;
        List<String> str = new ArrayList<>();
        Random rnd = new Random();
        if (userId == -1L) {
            job = JobType.values()[rnd.nextInt(4)];
            gen = GenderType.values()[rnd.nextInt(2)];
            fam = rnd.nextInt(1) + 1;
            age = (rnd.nextInt(4) + 1) * 10;
        } else {
            User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException());
            job = user.getJob();
            gen = user.getGender();
            fam = user.getFamily();
            age = (LocalDate.now().getYear() - user.getBirth().getYear()) / 10 * 10;
        }
        str.add(gen.getDesc().substring(0, 1).toUpperCase());
        str.add("" + age);
        str.add("" + fam);
        str.add(job.getDesc().toUpperCase());
        int flag = rnd.nextInt(16);
        if (fam != 1) {
            flag |= 0b0100;
        }
        StringBuilder att = new StringBuilder();
        StringBuilder title = new StringBuilder();
        boolean[] check = new boolean[4];
        int cnt = 0;
        for (int i = 0; i < 4; i++) {
            if (flag % 2 == 0) {
                att.append(str.get(i));
                check[i] = true;
                cnt++;
            } else {
                att.append("-");
            }
            att.append(",");
            flag /= 2;
        }
        if (check[2]) {
            if (str.get(2).equals("1")) {
                title.append("혼자 사는 ");
            } else {
                title.append(str.get(2)).append("명이서 사는 ");
            }
            if (cnt == 1)
                title.append("사람이 ");
        }
        if (check[1])
            if (check[0] || check[3])
                title.append(str.get(1) + "대 ");
            else
                title.append(str.get(1) + "대가 ");
        if (check[0])
            if (check[3])
                title.append(str.get(0).equals("F") ? "여성 " : "남성 ");
            else
                title.append(str.get(0).equals("F") ? "여성이 " : "남성이 ");
        if (check[3]) {
            switch (str.get(3)) {
                case "STUDENT":
                    title.append("학생이 ");
                    break;
                case "JUBU":
                    title.append("주부가 ");
                    break;
                case "WORKER":
                    title.append("직장인이 ");
                    break;
                case "COOK":
                    title.append("요리사가 ");
                    break;
            }
        }
        if(cnt==0){
            title.append("모두가 ");
        }
        title.append("좋아하는 레시피들");
        att.setLength(att.length() - 1);
        ResGetHotRecipeComponentDto hotRecipeDto = ResGetHotRecipeComponentDto.builder()
                .title(title.toString())
                .attribute(att.toString())
                .build();
        return hotRecipeDto;
    }

    @Transactional
    public void createUser(ReqPostUserDto requestDto) throws NoSuchAlgorithmException, FileUploadException {

        StringBuilder sb = new StringBuilder();

        // 비밀번호 암호화
        String salt = encryptUtils.getSalt(requestDto.getUserId());
        sb.append(salt).append(requestDto.getPassword());
        String password = encryptUtils.encrypt(sb.toString());

        // 생일 처리
        LocalDate localDate = LocalDate.parse(requestDto.getBirth());

        String profileImgUrl = fileHandler.uploadImage(requestDto.getProfileImg());

        // 유저 엔티티 생성
        User user = User.builder()
                .userId(requestDto.getUserId())
                .userType(UserType.USER)
                .birth(localDate)
                .email(requestDto.getEmail())
                .family(requestDto.getFamily())
                .gender(requestDto.getGender())
                .name(requestDto.getName())
                .job(requestDto.getJob())
                .nickname(requestDto.getNickname())
                .password(password)
                .phone(requestDto.getPhone())
                .profileImage(profileImgUrl)
                .build();
        userRepository.save(user);
    }

    public ResLoginUserDto loginUser(ReqLoginUserDto requestDto) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();

        String salt = encryptUtils.getSalt(requestDto.getUserId());
        sb.append(salt).append(requestDto.getPassword());
        String password = encryptUtils.encrypt(sb.toString());

        User user = userRepository.findByUserIdAndPasswordAndStatusType(requestDto.getUserId(), password, StatusType.N)
                .orElseThrow(() -> new NullPointerException());

        String token = jwtService.createToken(user);
        String refreshToken = jwtService.createRefreshToken();
        user.updateRefreshToken(refreshToken);
        return new ResLoginUserDto().builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    public ResLoginUserDto loginAdmin(ReqLoginUserDto requestDto) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();

        String salt = encryptUtils.getSalt(requestDto.getUserId());
        sb.append(salt).append(requestDto.getPassword());
        String password = encryptUtils.encrypt(sb.toString());

        User user = userRepository.findByUserIdAndPasswordAndUserType(requestDto.getUserId(), password, UserType.ADMIN)
                .orElseThrow(() -> new NullPointerException());

        String token = jwtService.createToken(user);
        String refreshToken = jwtService.createRefreshToken();
        user.updateRefreshToken(refreshToken);
        return new ResLoginUserDto().builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    public ResGetUserDto readUser(Long userId) {
        User user = userRepository.findByIdAndStatusType(userId, StatusType.N).orElseThrow(() -> new NullPointerException());
        return ResGetUserDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .name(user.getName())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .phone(user.getPhone())
                .email(user.getEmail())
                .gender(user.getGender())
                .job(user.getJob())
                .family(user.getFamily())
                .birth(user.getBirth())
                .build();
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findByIdAndStatusType(userId, StatusType.N).orElseThrow(() -> new NullPointerException());
        user.delete();
    }

    @Transactional
    public void updateUser(Long userId, ReqPutUserDto requestDto) throws FileUploadException {
        User user = userRepository.findByIdAndStatusType(userId, StatusType.N).orElseThrow(() -> new NullPointerException());
        // 개인 정보 수정
        user.update(requestDto);
        // 프로필 이미지 따로 수정
        if(requestDto.getProfileImg() != null) {
            user.updateProfileImage(fileHandler.uploadImage(requestDto.getProfileImg()));
        }
    }

    public User findUser(long userId) throws NullPointerException {
        return userRepository.findByIdAndStatusType(userId, StatusType.N).orElseThrow(() -> new NullPointerException());
    }

    @Transactional
    public ResGetUserRecipesDto readUserRecipe(Long userId, Pageable pageable) {
        User user = userRepository.findByIdAndStatusType(userId, StatusType.N).orElseThrow(() -> new NullPointerException());
        List<Recipe> recipeList = recipeRepository.findAllByUser(pageable, user);
        List<ResGetUserRecipeDto> dtoList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            dtoList.add(new ResGetUserRecipeDto(recipe));
        }
        return new ResGetUserRecipesDto(dtoList);
    }

    public ResGetUserRecipeListsDto readUserRecipeList(Long userId, Pageable pageable) {
        User user = userRepository.findByIdAndStatusType(userId, StatusType.N).orElseThrow(() -> new NullPointerException());
        List<RecipeList> recipeListList = recipeListRepository.findAllByUser(pageable, user);
        List<ResGetUserRecipeListDto> dtoList = new ArrayList<>();
        for (RecipeList recipeList : recipeListList) {
            dtoList.add(new ResGetUserRecipeListDto(recipeList));
        }
        return new ResGetUserRecipeListsDto(dtoList);
    }

    public ResGetUserRecipesDto readRecipeBookmark(Long userId, Pageable pageable) {
        User user = userRepository.findByIdAndStatusType(userId, StatusType.N).orElseThrow(() -> new NullPointerException());
        List<RecipeBookmark> recipeBookmarkList = recipeBookmarkRepository.findAllByUser(pageable, user);
        List<ResGetUserRecipeDto> dtoList = new ArrayList<>();
        for (RecipeBookmark recipeBookmark : recipeBookmarkList) {
            dtoList.add(new ResGetUserRecipeDto(recipeBookmark.getRecipe()));
        }
        return new ResGetUserRecipesDto(dtoList);
    }

    public ResGetUserRecipeListsDto readRecipeListBookmark(Long userId, Pageable pageable) {
        User user = userRepository.findByIdAndStatusType(userId, StatusType.N).orElseThrow(() -> new NullPointerException());
        List<RecipeListBookmark> recipeListBookmarkList = recipeListBookmarkRepository.findAllByUser(pageable, user);
        List<ResGetUserRecipeListDto> dtoList = new ArrayList<>();
        for (RecipeListBookmark recipeListBookmark : recipeListBookmarkList) {
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

        if (password.equals(user.getPassword())) {
            sb = new StringBuilder();
            sb.append(salt).append(requestDto.getNewPassword());
            String newPassword = encryptUtils.encrypt(sb.toString());
            user.updatePassword(newPassword);
        } else {
            throw new AuthorityViolationException("비밀번호를 변경할 수 없습니다.");
        }
    }

    public ResLoginUserDto updateToken(String token, String refreshToken) {
        if (jwtService.checkJwtToken(token)) {
            throw new AuthorityViolationException("접근할 수 없음");
        }
        User user = userRepository.findById(jwtService.getUserId(token))
                .orElseThrow(() -> new NullPointerException());
        if (!jwtService.checkJwtToken(refreshToken) || !user.getRefreshToken().equals(refreshToken)) {
            throw new AuthorityViolationException("접근할 수 없음");
        }

        String newToken = jwtService.createToken(user);
        String newRefreshToken = jwtService.createRefreshToken();

        return new ResLoginUserDto().builder()
                .accessToken(newToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public void checkDuplicationId(String userId) {
        if (userRepository.existsByUserId(userId)) throw new BadRequestException("이미 사용중인 아이디 입니다.");
    }

    public void checkDuplicationNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) throw new BadRequestException("이미 사용중인 닉네임 입니다.");
    }

    @Transactional
    public ResGetUserRecipesDto readRecipeLike(Long userId, Pageable pageable) {
        User user = userRepository.findByIdAndStatusType(userId, StatusType.N).orElseThrow(() -> new NullPointerException());
        List<RecipeLike> recipeLikeList = recipeLikeRepository.findAllByUser(pageable, user);
        List<ResGetUserRecipeDto> dtoList = new ArrayList<>();
        for (RecipeLike recipelike : recipeLikeList) {
            dtoList.add(new ResGetUserRecipeDto(recipelike.getRecipe()));
        }
        return new ResGetUserRecipesDto(dtoList);
    }
}
