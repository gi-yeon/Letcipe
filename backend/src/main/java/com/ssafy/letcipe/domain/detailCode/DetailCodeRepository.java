package com.ssafy.letcipe.domain.detailCode;

import com.ssafy.letcipe.domain.headerCode.HeaderCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailCodeRepository extends JpaRepository<DetailCode, String> {
    List<DetailCode> findAllByHeader(HeaderCode headerCode);
}
