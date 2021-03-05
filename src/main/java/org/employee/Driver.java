package org.employee;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@interface DummyAnnotation{
    String message() default "my name is hello";
    int value();
}

public class Driver {

    @DummyAnnotation(33)
    public String name;

    public void dummyMethod() throws NoSuchFieldException, ClassNotFoundException {
        Class clazz = this.getClass();
        System.out.println(clazz.getField("name").isAnnotationPresent(DummyAnnotation.class));
        DummyAnnotation annotation = clazz.getField("name").getAnnotation(DummyAnnotation.class);
        name = annotation.message();

        System.out.println("value of "+name);
    }

    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException {
        Driver driver = new Driver();
        driver.dummyMethod();
    }

}
