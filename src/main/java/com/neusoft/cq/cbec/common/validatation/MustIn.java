package com.neusoft.cq.cbec.common.validatation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {MustInForIntegerValidatorImpl.class,MustInForStringValidatorImpl.class})
public @interface MustIn {
    String value();

    String message() default "must-in({value})";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
