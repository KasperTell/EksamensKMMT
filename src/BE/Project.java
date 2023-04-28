package BE;

import java.util.Date;

public class Project {
    private int id;
    private String title;
    private int customernumber;
    private Date date;
    private boolean open_close;

    public Project(int id, String title, int customernumber, Date date, boolean open_close) {
        this.id = id;
        this.title = title;
        this.customernumber = customernumber;
        this.date = date;
        this.open_close = open_close;
    }

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

    public int getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(int customernumber) {
        this.customernumber = customernumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isOpen_close() {
        return open_close;
    }

    public void setOpen_close(boolean open_close) {
        this.open_close = open_close;
    }
}
