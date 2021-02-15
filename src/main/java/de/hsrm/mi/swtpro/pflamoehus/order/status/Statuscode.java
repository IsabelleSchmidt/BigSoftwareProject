package de.hsrm.mi.swtpro.pflamoehus.order.status;

/*
 * Statuscode is a enum, which defines the different types of stati, that orders can get.
 * 
 * @author Svenja Schenk
 * @version 1
 */
public enum Statuscode {
   
     /**
     * type 'Incoming' 
     * */
    INCOMING("INCOMING"), 
     /**
     * type 'In Progress' 
     * */
    INPROGRESS("INPROGRESS"), 
     /**
     * type 'Partial' 
     * */
    PARTIALLYREADY("PARTIALLYREADY"), 
     /**
     * type 'Ready' 
     * */
    READYFORSHIPPING("READYFORSHIPPING"), 
     /**
     * type 'Shipped' 
     * */
    SHIPPED("SHIPPED"); 

    private String value;

    /**
     * Constructor.
     * 
     * @param value given status
     */
    Statuscode(String value){
        this.value = value;
    }   

    /**
     * @return status as string
     */
    @Override
    public String toString(){
        return this.value;
    }
   
}
