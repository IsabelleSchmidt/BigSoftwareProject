package de.hsrm.mi.swtpro.pflamoehus.product;

/**
 * Enum for the different available producttypes.
 * 
 * @author Svenja Schenk, Marie Scharhag
 * @version 1
 */
public enum ProductType {

    /**
     * type 'Stuhl' 
     * */
    CHAIR("Chair"),
    /**
     * type 'Pflanze' 
     * */
    PLANT("Plant"), 
    /**
     * type 'Tisch' 
     * */
    TABLE("Table"), 
    /**
     * type 'Bett' 
     * */
    BED("Bed"), 
    /**
     * type 'Dekoration' 
     * */
    DECORATION("Decoration"),
    /**
     * type 'Schrank/Kommode' 
     * */ 
    CLOSET("Closet"),
    /**
     * type 'Sofa/Couch' 
     * */
    COUCH("Couch");

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
