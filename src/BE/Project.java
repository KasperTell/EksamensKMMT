package BE;

import java.time.LocalDate;

public class Project {
    private int id, customerID;
    private String title,note, companyName;
    private LocalDate date;
    private boolean open;




    /**
     * Constructor for the class "Project".
     *
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Getters and setters for the entire class.
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open_close) {
        this.open = open_close;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }



}
