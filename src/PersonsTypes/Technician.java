package PersonsTypes;

public class Technician implements IPersonTypes{

    @Override
    public String getViewString() {
        return "/GUI/View/technician/TechView.fxml";
    }
}
