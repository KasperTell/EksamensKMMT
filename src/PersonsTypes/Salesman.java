package PersonsTypes;

import java.util.HashMap;

public class Salesman implements IPersonTypes{
    @Override
    public String getViewString() {
        return "/GUI/View/MainWindow.fxml";
    }

    @Override
    public String getViewCss() {
        return "GUI/View/Salesman.css";
    }

    public HashMap<ButtonType, Boolean> turnButtonOnOrOff() {

        HashMap<ButtonType, Boolean> buttonOnOrOff = new HashMap<>();



        buttonOnOrOff.put(ButtonType.CloseProjectButton, false);
        buttonOnOrOff.put(ButtonType.ReOpenProjectButton, true);
        buttonOnOrOff.put(ButtonType.NewProjectButton, true);
        buttonOnOrOff.put(ButtonType.NewCustomerButton, true);
        buttonOnOrOff.put(ButtonType.OpenProjectWindowButton, true);
        buttonOnOrOff.put(ButtonType.OpenUserWindowButton, true);
        buttonOnOrOff.put(ButtonType.ClosedProjectsTable, false);
        buttonOnOrOff.put(ButtonType.SearchBoxTextField, false);




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
