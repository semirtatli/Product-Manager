package com.staj2023backend.ws.unique_username;




import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
//Bu annotationlar belirtilen programın sadece fieldlara(variable lara) Runtime süresince ve sadece belirtilen class
//constraintinde yapılacağını belirtiyor.
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {UniqueUsernameValidator.class} )
public @interface UniqueUsername {
    String message() default "{staj2023backend.constraint.username.UniqueUsername.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}
