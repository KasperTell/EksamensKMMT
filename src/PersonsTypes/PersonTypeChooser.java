package PersonsTypes;


public class PersonTypeChooser {

    public static PersonTypesKlient personTypes;


    public void chooseType(PersonType personType)
    {


        switch (personType)
        {

            case Salesman:
                personTypes=new PersonTypesKlient(new Salesman());
                break;

            case ProjectManager:
                personTypes=new PersonTypesKlient(new ProjectManager());
                break;

            case Technician:
                personTypes=new PersonTypesKlient(new Technician());
                break;

        }

    }

    public String getViewString()
    {
        return personTypes.viewString();

    }

    public String getCSS()
    {
        return personTypes.getCSS();

    }

    public Boolean[] turnButtonOnOrOff()
    {
        return personTypes.turnButtonOnOrOff();

    }



}
