package com.ssafy.letcipe.domain.headerCode;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class HeaderCode {
    @Id
    @Column(name = "id", unique = true, length = 10)
    private String id;

    @Column(name = "name", length = 20)
    private String name;
}
