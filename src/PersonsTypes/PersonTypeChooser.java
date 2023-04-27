package PersonsTypes;

public class PersonTypeChooser {

    public static PersonTypes personTypes;

    public void chooseType(String type)
    {

        switch (type)
        {

            case "Salesman":
                personTypes=new PersonTypes(new Salesman());
                break;

            case "ProjectManager":
                personTypes=new PersonTypes(new ProjectManager());
                break;

            case "Technician":
                personTypes=new PersonTypes(new Technician());
                break;

        }

    }

    public String getViewString()
    {
        return personTypes.viewString();

    }


}
