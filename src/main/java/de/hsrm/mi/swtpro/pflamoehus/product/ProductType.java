package de.hsrm.mi.swtpro.pflamoehus.product;

/**
 * Enum for the different available producttypes.
 * 
 * @author Svenja Schenk, Marie Scharhag
 * @version 2
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
     * type 'Schrank' 
     * */ 
    CLOSET("Schrank"),
  
    /**
     * type 'waschbecken'
     */
    SINK("Waschbecken"),

    /**
     * type 'Küche' 
     */
    KITCHEN("Küche"),
    /**
     * type 'Sofa/Couch' 
     * */
    COUCH("Sofa");
    /**
     *  type null
     */

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
