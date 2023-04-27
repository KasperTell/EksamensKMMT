package PersonsTypes;

public class Salesman implements IPersonTypes{
    @Override
    public String getViewString() {
        return "/GUI/View/Salesman/SalesView.fxml";
    }
}
