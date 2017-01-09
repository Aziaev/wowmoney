package com.rabigol.wowmoneywebapp.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int currency_id;
    private String currency_name;
    private String currency_img;
}
