package de.hsrm.mi.swtpro.pflamoehus.product;

/** 
 * Enum for the different available roomtypes.
 * 
 * @author Svenja Schenk, Marie Scharhag
 * @version 1
 */
public enum RoomType {

    /**
     * type 'Bad' 
     * */
    BATHROOM("Bathroom"), 
    /**
     * type 'Schlafzimmer' 
     * */
    BEDROOM("Bedroom"), 
    /**
     * type 'Küche/Wohnküche' 
     * */
    KITCHEN("Kitchen"), 
    /**
     * type 'Kinderzimmer' 
     * */
    CHILDREN("Children"),
    /**
     * type 'Arbeitszimmer' 
     * */
    BUREAU("Bureau"), 
    /**
     * type 'Wohnzimmer' 
     * */
    LIVINGROOM("Livingroom"),
   
    /**
     * type 'Esszimmer'
     */
    EATINGROOM("Eatingroom"),
    
    /**
     * type 'NULL'
     */
    NULL("null");

    private String type;

    /**
     * Constructor
     * 
     * @param type given roomtype
     */
    RoomType(String type){
        this.type = type;
    }

    /**
     * To create a string out of the producttypes.
     */
    @Override
    public String toString(){
        return type;
    }

}
