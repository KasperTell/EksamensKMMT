package PersonsTypes;

import java.util.HashMap;

public class PersonTypesKlient {

    private IPersonTypes iPersonTypes;



    public PersonTypesKlient(IPersonTypes iPersonTypes)
    {
        this.iPersonTypes=iPersonTypes;
    }

    public void setViewString(IPersonTypes iPersonTypes)
    {
        this.iPersonTypes=iPersonTypes;
    }



    public String getCSS()
    {
        return iPersonTypes.getViewCss();
    }

    public HashMap<ButtonType, Boolean> turnButtonOnOrOff() {
        return iPersonTypes.turnButtonOnOrOff();
    }

    public boolean setListInfo(){
        return iPersonTypes.setListInfo();
    }

    public Boolean enableTab()
    {
        return iPersonTypes.enableTab();

    }


    public HashMap<ButtonType, Boolean> closeProjectButtonOnOrOff() {
        return iPersonTypes.closeProjectButtonOnOrOff();
    }


    public HashMap<ButtonType, Boolean> openProjectButtonOnOrOff() {
        return iPersonTypes.openProjectButtonOnOrOff();
    }




}
