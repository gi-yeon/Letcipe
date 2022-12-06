package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipeTag.RecipeTag;
import com.ssafy.letcipe.domain.tag.Tag;
import com.ssafy.letcipe.domain.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    // 태그 찾기
    public Optional<Tag> getTag(String tagName) {
        return tagRepository.findByName(tagName);
    }

    // 태그 생성
    public Tag getOrCreateTag(String tagName) {
        // 이미 태그가 존재하는지 체크
        Optional<Tag> tag = getTag(tagName);
        if (tag.isPresent()) return tag.get();

        return tagRepository.save(Tag.builder()
                .name(tagName)
                .build()
        );
    }

}
