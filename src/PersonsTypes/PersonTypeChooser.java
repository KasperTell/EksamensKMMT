package PersonsTypes;


public class PersonTypeChooser {

    public static PersonTypes personTypes;


    public void chooseType(PersonType personType)
    {


        switch (personType)
        {


            case Salesman:
                personTypes=new PersonTypes(new Salesman());
                break;

            case ProjectManager:
                personTypes=new PersonTypes(new ProjectManager());
                break;

            case Technician:
                personTypes=new PersonTypes(new Technician());
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
