package main.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Restricts a controller action to clients whose session "role" matches one of the given roles.
 * The client must also be authenticated (session contains "nomUtilisateur").
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Role {
    String[] value();
}
