package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.ingredient.ResGetIngredientDto;
import com.ssafy.letcipe.domain.headerCode.HeaderCode;
import com.ssafy.letcipe.domain.headerCode.HeaderCodeRepository;
import com.ssafy.letcipe.domain.ingredient.Ingredient;
import com.ssafy.letcipe.domain.ingredient.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final HeaderCodeRepository headerCodeRepository;
    private final IngredientRepository ingredientRepository;

    /**
     * 디테일 코드를 카테고리 이름으로 변환한 응답용 객체를 반환하는 함수
     *
     * @param ingredient 변환하려는 엔티티
     * @return 응답용 dto
     * @throws NullPointerException
     */
    public ResGetIngredientDto getIngredientResponse(Ingredient ingredient) throws NullPointerException {
        HeaderCode code = headerCodeRepository.findById(ingredient.getCategory().substring(0, 3)).orElseThrow(() -> new NullPointerException("카테고리를 찾을 수 없습니다."));
        return new ResGetIngredientDto(ingredient, code.getName());
    }

    public Ingredient getIngredient(long id) throws NullPointerException {
        return ingredientRepository.findById(id).orElseThrow(() -> new NullPointerException("재료를 찾을 수 없습니다."));
    }

    public Map<String,Object> searchIngredient(String keyword) {
        List<Ingredient> searched = ingredientRepository.searchIngredient(keyword);

        // 검색 결과 리스트
        List<ResGetIngredientDto> dtos = new ArrayList<>();

        // 응답용 Dto 객체로 변환
        for (Ingredient ing : searched)
            dtos.add(getIngredientResponse(ing));

        // 유사도가 높은 순으로 정렬
        Collections.sort(dtos, (o1,o2)-> {
            int similarity1 = o1.getName().length()-keyword.length();
            int similarity2 = o2.getName().length()-keyword.length();
            if (similarity1 == similarity2)
                return o1.getName().indexOf(keyword) - o2.getName().indexOf(keyword);
            else
                return similarity1-similarity2;
        });
        Map<String,Object> map = new LinkedHashMap<>();
        for (int i = 0; i<dtos.size() && map.size() <= 10; i++) {
            map.putIfAbsent(dtos.get(i).getName(),dtos.get(i));
        }
        return map;
    }
}
