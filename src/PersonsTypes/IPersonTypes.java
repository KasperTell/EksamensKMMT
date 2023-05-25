package PersonsTypes;

import java.util.HashMap;
import java.util.List;

public interface IPersonTypes {



    String getViewCss();
    boolean setListInfo();
    Boolean enableTab();
     HashMap<ButtonType, Boolean> turnButtonOnOrOff();
     public HashMap<ButtonType, Boolean> closeProjectButtonOnOrOff();
    public HashMap<ButtonType, Boolean> openProjectButtonOnOrOff();

    }

