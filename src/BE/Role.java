package BE;

public class Role {
    private int id;
    private String role;

    /**
     * Constructor for the class "Role".
     * @param id
     * @param role
     */
    public Role(int id, String role){
        this.id = id;
        this.role = role;
    }

    /**
     *Getters and setter for the entire class.
     */
    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getRole() { return role;}

    /**
     * Overriding the default toString method, so it returns the name of the role.
     * @return
     */
    @Override
    public String toString() {
        return role;
    }
}