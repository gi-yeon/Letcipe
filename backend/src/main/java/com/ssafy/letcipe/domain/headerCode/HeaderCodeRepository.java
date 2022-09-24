package com.ssafy.letcipe.domain.headerCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeaderCodeRepository extends JpaRepository<HeaderCode, String> {
}
