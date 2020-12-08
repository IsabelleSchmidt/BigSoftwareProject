package de.hsrm.mi.swtpro.pflamoehus.validation.ProductDatabase;

public enum ProductType {
    CHAIR("Stuhl"),PLANT("Pflanze"),TABLE("Tisch"),BED("Bett"),DECORATION("Dekoration"),CLOSET("Schrank/Kommode"),COUCH("Sofa/Couch");

    private String type;

    ProductType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }
    
}
