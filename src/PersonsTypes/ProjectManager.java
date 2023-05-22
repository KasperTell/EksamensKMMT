package PersonsTypes;

import java.util.HashMap;

public class ProjectManager implements IPersonTypes{

    @Override
    public String getViewString() {
        return "/GUI/View/MainWindow.fxml";
    }

    @Override
    public String getViewCss() {
        return "/GUI/View/MainWindow.css";
    }

    @Override
    public HashMap<String, Boolean> turnButtonOnOrOff() {

        HashMap<String, Boolean> buttonOnOrOff = new HashMap<>();

        buttonOnOrOff.put("closeProjectButton", false);
        buttonOnOrOff.put("reOpenProjectButton", false);
        buttonOnOrOff.put("newProjectButton", false);
        buttonOnOrOff.put("newCustomerButton", false);
        buttonOnOrOff.put("openProjectWindowButton", true);
        buttonOnOrOff.put("openUserWindowButton", false);
        buttonOnOrOff.put("closedProjectsTable", false);
        buttonOnOrOff.put("searchBoxTextField", false);

        return buttonOnOrOff;
    }

    public Boolean enableTab() {
        return false;
    }

    @Override
    public boolean setListInfo() {
        return false;
    }


}
