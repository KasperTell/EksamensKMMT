package PersonsTypes;

import GUI.Controller.NyController;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.HashMap;

public class Technician implements IPersonTypes{

    @Override
    public String getViewString() {
        return "/GUI/View/technician/TechView.fxml";
    }

    @Override
    public String getViewCss() {
        return "/GUI/View/technician/TechView1.css";
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

        return buttonOnOrOff;
    }

    public Boolean enableTab() {
        return true;
    }


    }



