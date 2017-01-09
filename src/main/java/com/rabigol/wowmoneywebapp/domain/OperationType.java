package com.rabigol.wowmoneywebapp.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="operationTypes")
public class OperationType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int operationType_id;
    private String operationType_name;
}
