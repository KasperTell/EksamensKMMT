package PersonsTypes;

import java.util.HashMap;

public class ProjectManager implements IPersonTypes{

    @Override
    public String getViewString() {
        return "/GUI/View/ProjectManager/Mainwindow.fxml";
    }

    @Override
    public String getViewCss() {
        return "/GUI/View/ProjectManager/MainWindow.css";
    }

    @Override
    public HashMap<String, Boolean> turnButtonOnOrOff() {

        HashMap<String, Boolean> buttonOnOrOff = new HashMap<>();

        buttonOnOrOff.put("closeProjectButton", false);
        buttonOnOrOff.put("reOpenProjectButton", false);
        buttonOnOrOff.put("newProjectButton", false);
        buttonOnOrOff.put("newCustomerButton", false);
        buttonOnOrOff.put("openProjectWindowButton", false);
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
