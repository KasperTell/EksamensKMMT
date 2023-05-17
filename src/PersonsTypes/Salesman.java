package PersonsTypes;

import GUI.Controller.NyController;

import java.util.HashMap;

public class Salesman implements IPersonTypes{
    @Override
    public String getViewString() {
        return "/GUI/View/Salesman/SalesView.fxml";
    }

    @Override
    public String getViewCss() {
        return "/GUI/View/Salesman/SalesView.css";
    }

    public HashMap<String, Boolean> turnButtonOnOrOff() {

        HashMap<String, Boolean> buttonOnOrOff = new HashMap<>();

        buttonOnOrOff.put("closeProjectButton", true);
        buttonOnOrOff.put("reOpenProjectButton", true);
        buttonOnOrOff.put("newProjectButton", true);
        buttonOnOrOff.put("newCustomerButton", true);
        buttonOnOrOff.put("openProjectWindowButton", true);
        buttonOnOrOff.put("openUserWindowButton", true);
        buttonOnOrOff.put("closedProjectsTable", false);


        return buttonOnOrOff;
    }

    public Boolean enableTab() {
        return true;
    }

    @Override
    public boolean setListInfo() {
        return false;
    }


}
