package PersonsTypes;

import java.util.HashMap;
import java.util.List;

public interface IPersonTypes {

    String getViewString();


    String getViewCss();

    HashMap<ButtonType, Boolean> turnButtonOnOrOff();


     Boolean enableTab();
     boolean setListInfo();



    public HashMap<ButtonType, Boolean> closeProjectButtonOnOrOff();


    public HashMap<ButtonType, Boolean> openProjectButtonOnOrOff();
    }

