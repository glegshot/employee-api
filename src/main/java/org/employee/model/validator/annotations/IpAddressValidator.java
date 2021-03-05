package org.employee.model.validator.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpAddressValidator implements ConstraintValidator<IpAddress, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern =
                Pattern.compile("^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$");
        Matcher matcher = pattern.matcher(value);
        if(matcher.matches()){
            return false;
        }else{
            for(int i = 0;i<4;i++){
                int octect = Integer.parseInt(matcher.group(i));
                if(octect >  255){
                    return false;
                }
            }
        }
        return true;
    }
}
