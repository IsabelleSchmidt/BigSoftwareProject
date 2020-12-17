package de.hsrm.mi.swtpro.pflamoehus.product;

public enum RoomType {

    DININGROOM("Esszimmer"),BATHROOM("Bad"), BEDROOM("Schlafzimmer"), KITCHEN("Küche/Wohnküche"), CHILDREN("Kinderzimmer"),BUREAU("Arbeitszimmer"), LIVINGROOM("Wohnzimmer");
    
    private String type;

    RoomType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }

}
