package de.hsrm.mi.swtpro.pflamoehus.validation.order_db;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidStatusValidator implements ConstraintValidator<ValidStatus,String > {

    private enum Status{
        INCOMING("INCOMING"), IN_PROGESS("IN PROGRESS"), PARTIALLY_READY("PARTIAL"), READY_FOR_SHIPPING("READY"), SHIPPED("SHIPPED"); 

        private String value;

        Status(String value){
            this.value = value;
        }

        @Override
        public String toString(){
            return this.value;
        }
    };

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for(Status status: Status.values()){
            if(status.value.equals(value)){
                return true;
            }
        }
        return false;
    }

   
    
}
