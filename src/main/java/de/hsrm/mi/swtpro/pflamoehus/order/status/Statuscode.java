package de.hsrm.mi.swtpro.pflamoehus.order.status;

public enum Statuscode {
   
    INCOMING("INCOMING"), IN_PROGESS("IN PROGRESS"), PARTIALLY_READY("PARTIAL"), READY_FOR_SHIPPING("READY"), SHIPPED("SHIPPED"); 

    private String value;

    Statuscode(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return this.value;
    }
   
}
