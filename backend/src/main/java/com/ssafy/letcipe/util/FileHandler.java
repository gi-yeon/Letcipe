package com.ssafy.letcipe.util;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;


@Component
public class FileHandler {
    @Value("${file.upload.dir}")
    private String BASE_PATH;

    @Value("${file.upload.url}")
    private String BASE_URL;

    private final String sep = File.separator;

    /**
     * 이미지 파일을 업로드
     *
     * @param imgFile 업로드할 이미지
     * @return 저장된 이미지의 url
     * @throws FileNotFoundException 업로드 할 이미지가 빈 경우
     */
    public String uploadImage(MultipartFile imgFile) throws FileUploadException {
        // 유효성 검사
        System.out.println("와아아ㅏㅈ아ㅏㄹ;ㅈ얄;");

        System.out.println(imgFile.getOriginalFilename());

        if (imgFile == null || imgFile.isEmpty())
            throw new FileUploadException("업로드하려는 파일이 없습니다.");


        //확장자 명이 없는 파일은 처리 안함
        String contentType = imgFile.getContentType();
        if (ObjectUtils.isEmpty(contentType)) {
            throw new FileUploadException("확장자 명이 없습니다.");
        }

        if (!(contentType.contains("image/jpeg") || contentType.contains("image/png") || contentType.contains("image/gif")))
            throw new FileUploadException("이미지 파일 확장자가 아닙니다.");

        // 업로드 패스 설정
        File directory = new File(BASE_PATH);
        // 디렉토리가 존재하지 않는 경우 새로 생성
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // uuid 발급
        String uuid = UUID.randomUUID().toString();

        // 이미지 파일 업로드

        // 파일 확장자 추출
        String ext = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
        String filePath = new String(BASE_PATH + sep + uuid + ext);
        File file = new File(filePath);
        try {
            imgFile.transferTo(file);
        } catch (IOException e) {
            throw new FileUploadException("파일 업로드 서버 에러. : " + e.getMessage());
        }

        // 업로드한 이미지 url 리턴
        return new String(BASE_URL+"/"+uuid + ext);
    }

    public void deleteImageFile(String url) throws FileNotFoundException {
        String fileName = url.substring(url.lastIndexOf('/'));
        String path = new String(BASE_PATH + sep + fileName);
        File file;
        try {
            file = new File(path);
            file.delete();
        } catch (Exception e) {
            throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        }
    }

    public File getImageFile(String fileName) throws FileNotFoundException {
        String path = new String(BASE_PATH+sep+fileName);
        File file;
        try {
            file = new File(path);
            if (!file.exists()) throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        } catch (Exception e) {
            throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        }
        return file;
    }
}
