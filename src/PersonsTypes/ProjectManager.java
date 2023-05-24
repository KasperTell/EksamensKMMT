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
    public HashMap<ButtonType, Boolean> turnButtonOnOrOff() {

        HashMap<ButtonType, Boolean> buttonOnOrOff = new HashMap<>();

        buttonOnOrOff.put(ButtonType.CloseProjectButton, false);
        buttonOnOrOff.put(ButtonType.ReOpenProjectButton, false);
        buttonOnOrOff.put(ButtonType.NewProjectButton, false);
        buttonOnOrOff.put(ButtonType.NewCustomerButton, false);
        buttonOnOrOff.put(ButtonType.OpenProjectWindowButton, true);
        buttonOnOrOff.put(ButtonType.OpenUserWindowButton, false);
        buttonOnOrOff.put(ButtonType.ClosedProjectsTable, false);
        buttonOnOrOff.put(ButtonType.SearchBoxTextField, false);

        return buttonOnOrOff;
    }

    public Boolean enableTab() {
        return false;
    }

    @Override
    public boolean setListInfo() {
        return false;
    }

    @Override
    public HashMap<ButtonType, Boolean> closeProjectButtonOnOrOff() {
        HashMap<ButtonType, Boolean> closeProjectButtonOnOrOff = new HashMap<>();

        closeProjectButtonOnOrOff.put(ButtonType.CloseProjectButton, true);
        closeProjectButtonOnOrOff.put(ButtonType.ReOpenProjectButton, false);
        closeProjectButtonOnOrOff.put(ButtonType.OpenProjectWindowButton, false);

        return closeProjectButtonOnOrOff;

    }

    @Override
    public HashMap<ButtonType, Boolean> openProjectButtonOnOrOff() {
        HashMap<ButtonType, Boolean> openProjectButtonOnOrOff = new HashMap<>();

        openProjectButtonOnOrOff.put(ButtonType.CloseProjectButton, false);
        openProjectButtonOnOrOff.put(ButtonType.ReOpenProjectButton, true);
        openProjectButtonOnOrOff.put(ButtonType.OpenProjectWindowButton, false);

        return openProjectButtonOnOrOff;


    }


}
