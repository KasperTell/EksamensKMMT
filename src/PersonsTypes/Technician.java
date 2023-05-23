package PersonsTypes;


import java.util.HashMap;

public class Technician implements IPersonTypes{

    @Override
    public String getViewString() {
        return "/GUI/View/MainWindow.fxml";
    }

    @Override
    public String getViewCss() {
        return "/GUI/View/Technician.css";
    }

    public HashMap<ButtonType, Boolean> turnButtonOnOrOff() {



        HashMap<ButtonType, Boolean> buttonOnOrOff = new HashMap<>();

        buttonOnOrOff.put(ButtonType.CloseProjectButton, false);
        buttonOnOrOff.put(ButtonType.ReOpenProjectButton, true);
        buttonOnOrOff.put(ButtonType.NewProjectButton, true);
        buttonOnOrOff.put(ButtonType.NewCustomerButton, true);
        buttonOnOrOff.put(ButtonType.OpenProjectWindowButton, false);
        buttonOnOrOff.put(ButtonType.OpenUserWindowButton, true);
        buttonOnOrOff.put(ButtonType.ClosedProjectsTable, true);
        buttonOnOrOff.put(ButtonType.SearchBoxTextField, true);


        return buttonOnOrOff;
    }

    public Boolean enableTab() {
        return true;
    }

    @Override
    public boolean setListInfo(){return true;}
    }



