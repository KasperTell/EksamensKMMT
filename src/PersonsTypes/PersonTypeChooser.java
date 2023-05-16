package PersonsTypes;


import java.util.HashMap;

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

    public HashMap<String, Boolean> turnButtonOnOrOff()
    {

        HashMap<String, Boolean> sss=personTypes.turnButtonOnOrOff();

        return personTypes.turnButtonOnOrOff();


    }
}
