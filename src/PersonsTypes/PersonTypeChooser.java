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

    public HashMap<ButtonType, Boolean> turnButtonOnOrOff()
    {



        return personTypes.turnButtonOnOrOff();
    }

    public boolean setListInfo(){
        return personTypes.setListInfo();
    }

    public Boolean enableTab()
    {
        return personTypes.enableTab();

    }

    public HashMap<ButtonType, Boolean> closeProjectButtonOnOrOff()
    {



        return personTypes.closeProjectButtonOnOrOff();
    }


    public HashMap<ButtonType, Boolean> openProjectButtonOnOrOff()
    {


        return personTypes.openProjectButtonOnOrOff();
    }




}
