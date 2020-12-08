package de.hsrm.mi.swtpro.pflamoehus.validation.ProductDatabase;

public enum RoomType {

    BATHROOM("Bad"), BEDROOM("Schlafzimmer"), LIVINGROOM("Küche/Wohnküche"), CHILDREN("Kinderzimmer"),BUREAU("Arbeitszimmer");
    
    private String type;

    RoomType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }

}
