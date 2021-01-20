package de.hsrm.mi.swtpro.pflamoehus.user.roles;

/*
 * Roles is a enum, which defines the different types of visitors, that may come to the website.
 * 
 * @author Svenja Schenk
 * @version 1
 */
public enum ERoles {
    /**
     * type 'Admin' 
     * */
    ADMIN("ADMIN"), 
    /**
     * type 'User' 
     * */
    USER("USER"), 
    /**
     * type 'Service' 
     * */
    SERVICE("SERVICE"), 
    /**
     * type 'Warehouse' 
     * */
    WAREHOUSE("WAREHOUSE"), 
    /**
     * type 'Staff' 
     * */
    STAFF("STAFF");

    private String role;

    /**
     * Constructor.
     * 
     * @param role given role
     */
    ERoles(final String role) {
        this.role = role;
    }

    /**
     * @return role as string
     */
    @Override
    public String toString() {
        return role;
    }

}
