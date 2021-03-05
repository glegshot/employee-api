package org.employee.model.validator.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IpAddressValidator.class)
public @interface IpAddress {

    String message() default "ip address is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
