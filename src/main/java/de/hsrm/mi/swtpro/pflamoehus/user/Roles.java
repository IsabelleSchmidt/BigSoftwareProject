package de.hsrm.mi.swtpro.pflamoehus.user;

public enum Roles {
    
    ADMIN("ADMIN"), USER("USER"), SERVICE("SERVICE"), WAREHOUSE("WAREHOUSE"), STAFF("STAFF");

    private String role;

    Roles(final String role){
        this.role = role;
    }

    @Override
    public String toString(){
        return role;
    }
   
    
}
