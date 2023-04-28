package PersonsTypes;

public class Salesman implements IPersonTypes{
    @Override
    public String getViewString() {
        return "/GUI/View/Salesman/SalesView.fxml";
    }

    @Override
    public String getViewCss() {
        return "/GUI/View/Salesman/SalesView.css";
    }

    public Boolean[] turnButtonOnOrOff() {


        Boolean[] buttonOnOrOff={false,false,false,false,false,false,false,false,false,false,false};

        return buttonOnOrOff;
    }

}
