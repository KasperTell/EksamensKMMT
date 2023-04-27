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



}
