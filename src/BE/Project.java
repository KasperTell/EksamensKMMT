package BE;

import java.time.LocalDate;

public class Project {
    private int id, customerID;
    private String title,note, companyName;
    private LocalDate date;
    private boolean open;

    /**
     * Constructor for the class "Project".
     * @param id
     * @param title
     * @param customerID
     * @param date
     * @param open
     * @param note
     * @param companyName
     */
    public Project(int id, String title, int customerID, LocalDate date, boolean open, String note, String companyName) {
        this.companyName=companyName;
        this.id = id;
        this.title = title;
        this.customerID = customerID;
        this.date = date;
        this.open = open;
        this.note=note;
    }

    /**
     * Getters and setter for the entire class.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() { return companyName;}

    public String getTitle() {
        return title;
    }

    public int getCustomerID() {
        return customerID;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isOpen() {
        return open;
    }

    public String getNote() {
        return note;
    }
}