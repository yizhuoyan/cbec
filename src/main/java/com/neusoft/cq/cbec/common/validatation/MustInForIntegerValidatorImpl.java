package com.neusoft.cq.cbec.common.validatation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 */
public class MustInForIntegerValidatorImpl implements ConstraintValidator<MustIn, Integer> {
    private Set<Integer> valueSet;
    @Override
    public void initialize(MustIn constraintAnnotation) {
        String[] inValueArr=constraintAnnotation.value().split("/");

        valueSet=new HashSet<>(inValueArr.length);
        for (String v:inValueArr){
            valueSet.add(Integer.parseInt(v));
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value==null){
            return true;
        }
        if(valueSet.contains(value)){
            return true;
        }
        return false;
    }
}
