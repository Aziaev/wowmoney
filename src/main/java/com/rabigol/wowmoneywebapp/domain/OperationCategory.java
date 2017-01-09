package com.rabigol.wowmoneywebapp.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="operationCategories")
public class OperationCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int operationCategory_id;
    private String operationCategory_name;
    private String operationCategory_type;
}
