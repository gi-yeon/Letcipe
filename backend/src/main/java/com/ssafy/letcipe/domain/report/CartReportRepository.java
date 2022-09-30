package com.ssafy.letcipe.domain.report;

import com.ssafy.letcipe.api.dto.report.JPQLCartReportDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CartReportRepository extends JpaRepository<CartReport,Long> {
    @Query("select new com.ssafy.letcipe.api.dto.report.JPQLCartReportDto(cr.recipe, sum(cr.count)) from CartReport cr where cr.attributes=:attributes and cr.date >= :beginDate and cr.date <= :endDate group by cr.recipe")
    public List<JPQLCartReportDto> findAllByAttr(String attributes, LocalDate beginDate, LocalDate endDate, Pageable pageable);
}
