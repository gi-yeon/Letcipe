package com.ssafy.letcipe.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CartReportRepository extends JpaRepository<CartReport,Long> {
    @Query("select cr from CartReport cr where cr.attributes=:attributes and cr.date >= :beginDate and cr.date <= :endDate")
    public List<CartReport> findAllByAttr(String attributes, LocalDate beginDate, LocalDate endDate);
}
