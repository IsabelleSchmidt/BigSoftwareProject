package de.hsrm.mi.swtpro.pflamoehus.product;

/*
 * Enum for the different available producttypes.
 * 
 * @author Svenja Schenk
 * @version 1
 */
public enum ProductType {

    /**
     * type 'Stuhl' 
     * */
    CHAIR("Stuhl"),
    /**
     * type 'Pflanze' 
     * */
    PLANT("Pflanze"), 
    /**
     * type 'Tisch' 
     * */
    TABLE("Tisch"), 
    /**
     * type 'Bett' 
     * */
    BED("Bett"), 
    /**
     * type 'Dekoration' 
     * */
    DECORATION("Dekoration"),
    /**
     * type 'Schrank/Kommode' 
     * */ 
    CLOSET("Schrank/Kommode"),

    /**
     * type 'Waschbecken'
     */
    SINK("Waschbecken"),

    /**
     * type 'Küche' 
     */
    KITCHEN("Küche"),
    /**
     * type 'Sofa/Couch' 
     * */
    COUCH("Sofa/Couch");

    private String type;

    /**
     * Constructor
     * 
     * @param type given producttype
     */
    ProductType(String type) {
        this.type = type;
    }

    /**
     * To create a string out of the producttypes.
     */
    @Override
    public String toString() {
        return type;
    }

}
