package com.ssafy.letcipe.domain.detailCode;

import com.ssafy.letcipe.domain.headerCode.HeaderCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class DetailCode {
    @Id
    @Column(name = "id", unique = true, length = 10)
    private String id;

    @ManyToOne(targetEntity = HeaderCode.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "header_id", referencedColumnName = "id")
    private HeaderCode header;

    @Column(name = "name", length = 20)
    private String name;
}
