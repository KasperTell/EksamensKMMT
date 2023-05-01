package BE;

public class ProjectTechnician {
    private int id;
    private int technicianID;
    private int projectID;

    public ProjectTechnician(int id, int technicianID, int projectID) {
        this.id = id;
        this.technicianID = technicianID;
        this.projectID = projectID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTechnicianID() {
        return technicianID;
    }

    public void setTechnicianID(int technicianID) {
        this.technicianID = technicianID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }
}
