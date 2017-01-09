package com.rabigol.wowmoneywebapp.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int account_id;
    private String account_name;
    private String account_img;
}
