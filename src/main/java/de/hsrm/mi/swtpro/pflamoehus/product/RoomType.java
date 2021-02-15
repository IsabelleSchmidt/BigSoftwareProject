package de.hsrm.mi.swtpro.pflamoehus.product;

/** 
 * Enum for the different available roomtypes.
 * 
 * @author Svenja Schenk, Marie Scharhag
 * @version 2
 */
public enum RoomType {

    /**
     * type 'Bad' 
     * */
    BATHROOM("Bad"), 
    /**
     * type 'Schlafzimmer' 
     * */
    BEDROOM("Schlafzimmer"), 
    /**
     * type 'Küche/Wohnküche' 
     * */
    KITCHEN("Küche"), 
    /**
     * type 'Kinderzimmer' 
     * */
    CHILDREN("Kinderzimmer"),
    /**
     * type 'Arbeitszimmer' 
     * */
    BUREAU("Arbeitszimmer"), 

    DININGROOM("Esszimmer"),
    /**
     * type 'Wohnzimmer' 
     * */
    LIVINGROOM("Wohnzimmer"),
    /**
     * type 'all' 
     * */
    ALL("Sonstiges");
    /**
     * type null
     */

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
