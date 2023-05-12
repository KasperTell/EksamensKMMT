package PersonsTypes;

public class ProjectManager implements IPersonTypes{

    @Override
    public String getViewString() {
        return "/GUI/View/ProjectManager/NytVindue1.fxml";
    }

    @Override
    public String getViewCss() {
        return "/GUI/View/ProjectManager/managerView.css";
    }

    @Override
    public Boolean[] turnButtonOnOrOff() {


        Boolean[] buttonOnOrOff={false,false,false,false,false,false,false,false,false,false,false};

        return buttonOnOrOff;
    }
}
