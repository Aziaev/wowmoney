package com.rabigol.wowmoneywebapp.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="operations")

public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long ownerId = 1; //Operation owner. Needed in server
    private long timestamp; //for date in operations
    private String operationType; // one of third types of operation: income, outcome, transfer
    private String operationCategory; // category of operation, for example: clothes, food, car etc
    private String operationPic; // pic for operation
    private String account; // name of account
    private long value; // operation value
    private String currency;
    private String description;

    public String getDoubleValue() {
        Long a = value/100;
        Long b = value%100;
        String decimal = "";
        if (Math.abs(b) > 10){
            decimal = "." + Math.abs(b);
        } else if (Math.abs(b) > 0 && Math.abs(b) < 10){
            decimal = "." + Math.abs(b) + "0";
        }
        String operationValue = a + decimal;
        return operationValue;
    }
}
