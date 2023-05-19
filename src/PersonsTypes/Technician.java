package PersonsTypes;


import java.util.HashMap;

public class Technician implements IPersonTypes{

    @Override
    public String getViewString() {
        return "/GUI/View/technician/TechView.fxml";
    }

    @Override
    public String getViewCss() {
        return "/GUI/View/technician/TechView.css";
    }

    public HashMap<String, Boolean> turnButtonOnOrOff() {

        HashMap<String, Boolean> buttonOnOrOff = new HashMap<>();

        buttonOnOrOff.put("closeProjectButton", false);
        buttonOnOrOff.put("reOpenProjectButton", true);
        buttonOnOrOff.put("newProjectButton", true);
        buttonOnOrOff.put("newCustomerButton", true);
        buttonOnOrOff.put("openProjectWindowButton", false);
        buttonOnOrOff.put("openUserWindowButton", true);
        buttonOnOrOff.put("closedProjectsTable", true);
        buttonOnOrOff.put("searchBoxTextField", true);

        return buttonOnOrOff;
    }

    public Boolean enableTab() {
        return true;
    }

    @Override
    public boolean setListInfo(){return true;}
    }



