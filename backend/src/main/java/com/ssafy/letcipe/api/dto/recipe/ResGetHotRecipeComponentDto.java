package com.ssafy.letcipe.api.dto.recipe;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResGetHotRecipeComponentDto {
    String title;
    String attribute;

    public ResGetHotRecipeComponentDto(String title, String attribute) {
        this.title = title;
        this.attribute = attribute;
    }

    public ResGetHotRecipeComponentDto(String attribute) {
        this.attribute = attribute;
        StringBuilder title = new StringBuilder();
        String[] token = attribute.split(",");
        boolean[] check = new boolean[4];
        int cnt = 0;
        for (int i = 0; i < 4; i++) {
            if (token[i].equals("-"))
                check[i] = false;
            else {
                check[i] = true;
                cnt++;
            }
        }
        if (check[2]) {
            if (token[2].equals("1")) {
                title.append("혼자 사는 ");
            } else {
                title.append(token[2]).append("명이서 사는 ");
            }
            if (cnt == 1)
                title.append("사람이 ");
        }
        if (check[1])
            if (check[0] || check[3])
                title.append(token[1] + "대 ");
            else
                title.append(token[1] + "대가 ");
        if (check[0])
            if (check[3])
                title.append(token[0].equals("F") ? "여성 " : "남성 ");
            else
                title.append(token[0].equals("F") ? "여성이 " : "남성이 ");
        if (check[3]) {
            switch (token[3]) {
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
        this.title = title.toString();
    }
}
