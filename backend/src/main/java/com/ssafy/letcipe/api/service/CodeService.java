package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.code.ResDetailCodeDto;
import com.ssafy.letcipe.api.dto.code.ResDetailCodeListDto;
import com.ssafy.letcipe.domain.detailCode.DetailCode;
import com.ssafy.letcipe.domain.detailCode.DetailCodeRepository;
import com.ssafy.letcipe.domain.headerCode.HeaderCode;
import com.ssafy.letcipe.domain.headerCode.HeaderCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CodeService {

    private final DetailCodeRepository detailCodeRepository;
    private final HeaderCodeRepository headerCodeRepository;

    @Transactional
    public ResDetailCodeListDto readRecipeDetailCode() {
        HeaderCode headerCode = headerCodeRepository.findById("R00").orElseThrow(() -> new NullPointerException("코드를 찾을 수 없습니다."));
        List<DetailCode> detailCodes = detailCodeRepository.findAllByHeader(headerCode);
        System.out.println(detailCodes);
        List<ResDetailCodeDto> codes = new ArrayList<>();

        for(DetailCode detailCode: detailCodes){
            codes.add(ResDetailCodeDto.builder()
                            .id(detailCode.getId())
                            .name(detailCode.getName())
                            .build());
        }

        return ResDetailCodeListDto.builder()
                .list(codes)
                .build();
    }
}
