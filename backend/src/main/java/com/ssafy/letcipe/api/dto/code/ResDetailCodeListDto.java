package com.ssafy.letcipe.api.dto.code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ResDetailCodeListDto {
    List<ResDetailCodeDto> list;
}
