package PersonsTypes;

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

    public String viewString()
    {
        return iPersonTypes.getViewString();
    }

    public String getCSS()
    {
        return iPersonTypes.getViewCss();
    }


    public Boolean[] turnButtonOnOrOff() {


        return iPersonTypes.turnButtonOnOrOff();
    }


}