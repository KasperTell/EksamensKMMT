package GUI.Model;

import BE.ProjectFiles;
import BLL.ProjectFilesManager;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class ProjectFilesModel {


    private  ListProperty<ProjectFiles> projectFiles;

    private ProjectFilesManager projectFilesManager;
    private boolean isRunning = true;

    private int runs=0;
private int number=0 ;

    /**
     * Constructor for the class "ProjectFilesModel".
     * @throws Exception
     */

    public ProjectFilesModel() throws Exception {
        projectFilesManager = new ProjectFilesManager();
        projectFiles=new SimpleListProperty<>();
    }

    /**
     * get the list of ProjectFiles.
     * @param projectID
     * @return
     * @throws Exception
     */
    public ObservableList<ProjectFiles> getAllFilesFromProject(int projectID) throws Exception {

        projectFiles.clear();
        projectFiles.set(FXCollections.observableArrayList(projectFilesManager.loadFilesFromAProject(projectID)));
        return projectFiles;
    }

    /**
     *
     */
    public void observer()
    {

        boolean[] oldSelected= new boolean[projectFiles.getSize()];


        Thread t = new Thread(() ->
        {
            isRunning=true;

            if (projectFiles.getSize()!=0) //Ingen grund til gennemløb, hvis der er ingen filer.
                while (isRunning) {


                    for (ProjectFiles tjek : projectFiles) { //Her gennemløbes hele projectFiles linje for linje

                        if (tjek.getUsedBox().isSelected())

                        {
                            if (runs==0)                    //Vi vil sammenligne data fra projectfiles med et oldselection arkiv. I første omgang gemmes i oldselection til sammenligning senere.
                                oldSelected[number]=true;

                                if ( oldSelected[number]==false)        //Hvis gamle værdi er false og ny er true. Så er der sket en ændring som skal gemmes.
                                updateDataBase(true, tjek.getId()); //Kalder updateringsmetoden

                                oldSelected[number]=true;
                            }
                         else {

                            if (runs==0)
                                oldSelected[number]=false;

                            if ( oldSelected[number]==true)
                            updateDataBase(false, tjek.getId());

                            oldSelected[number]=false;
                            }

                            number++;
                         if (number==projectFiles.getSize())
                             number=0;
                             runs++;
                    }
                    try {
                        Thread.sleep(1000);


                    } catch (InterruptedException e) {
                        System.out.println("This is a treat exception");
                    }

               //     System.out.println("tæller "+idNew);

                    }

        });
        t.setDaemon(true); //I mark the thread as a daemon thread, so  its terminated when I exit the app.
        t.start();

        }

    private void updateDataBase(Boolean check, int id) {
        Platform.runLater(() -> {
            try {
                projectFilesManager.updateUsedInDoc(check, id);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


    }

    public void fileLoopStop()
    {
        isRunning=false;
    }



}

