package com.rabigol.wowmoneywebapp.utils;

/**
 * Created by Artur.Ziaev on 26.11.2016.
 */
public class Helper {
    public static long valueCheck(long l, String operationType){
        if (operationType.equals("Outcome")){
            if (l > 0) l = l * -1;
        } else l = Math.abs(l);
        return l;
    }
    //TODO: add valueCheck for transfer
}
