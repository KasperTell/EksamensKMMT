package PersonsTypes;

public class Technician implements IPersonTypes{

    @Override
    public String getViewString() {
        return "/GUI/View/technician/TechView.fxml";
    }

    @Override
    public String getViewCss() {
        return "/GUI/View/technician/TechView.css";
    }

    public Boolean[] turnButtonOnOrOff() {


        Boolean[] buttonOnOrOff={true,true,true,true,true,true,true,true,true,true,true};

        return buttonOnOrOff;
    }


}
