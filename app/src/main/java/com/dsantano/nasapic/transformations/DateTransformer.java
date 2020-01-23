package com.dsantano.nasapic.transformations;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DateTransformer {

    public String dateTransformation(String date){
        String result;
        String year = date.split("-")[0];
        String month = date.split("-")[1];
        String day = date.split("-")[2];
        result = day + "/" + month + "/" + year;
        return result;
    }
}
