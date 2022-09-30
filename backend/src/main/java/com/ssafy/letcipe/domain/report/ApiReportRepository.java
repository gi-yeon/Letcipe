package com.ssafy.letcipe.domain.report;

import com.ssafy.letcipe.domain.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ApiReportRepository extends JpaRepository<ApiReport, Long> {
    @Query("select ar from ApiReport ar where ar.date >= :begin and ar.date <= :end")
    public List<ApiReport> rangeSearch(LocalDate begin, LocalDate end);
}
