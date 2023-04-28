package PersonsTypes;

public class PersonTypes {

    private IPersonTypes iPersonTypes;



    public PersonTypes(IPersonTypes iPersonTypes)
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
