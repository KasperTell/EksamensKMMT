package BE;

public class ProjectTechnician {
    private int id, technicianID, projectID;

    /**
     * Constructor for the class "ProjectTechnician".
     * @param id
     * @param technicianID
     * @param projectID
     */
    public ProjectTechnician(int id, int technicianID, int projectID) {
        this.id = id;
        this.technicianID = technicianID;
        this.projectID = projectID;
    }

    /**
     * Getters and setters for the entire class.
     */
    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public int getTechnicianID() { return technicianID;}

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }
}
