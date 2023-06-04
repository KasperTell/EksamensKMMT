package GUI.Model;

import BE.ProjectFiles;
import BLL.ProjectFilesManager;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectFilesModel {
    private  ListProperty<ProjectFiles> projectFiles;
    private ProjectFilesManager projectFilesManager;
    private boolean isRunning = true;
    private ProjectFiles createdFile;
    private int runs=0;
    private int number=0 ;

    /**
     * Constructor for the class "ProjectFilesModel".
     * @throws Exception
     */

    public ProjectFilesModel() throws IOException {
        projectFilesManager = new ProjectFilesManager();
        projectFiles=new SimpleListProperty<>();
    }

    /**
     * get the list of ProjectFiles.
     * @param projectID
     * @return
     * @throws SQLException, FileNotFoundException
     */
    public ObservableList<ProjectFiles> getAllFilesFromProject(int projectID) throws SQLException, FileNotFoundException {

        projectFiles.clear();
        projectFiles.set(FXCollections.observableArrayList(projectFilesManager.loadFilesFromAProject(projectID)));
        return projectFiles;
    }

    /**
     * As part of the observer pattern. This is the observer part. It observes if a checkbox has been checked/Unchecked.
     *
     */
    public void observer()
    {
         ArrayList<Boolean> oldSelected= new ArrayList<>();
        number=0; //Tæller gennemkørsler indeni løkken


        Thread t = new Thread(() ->
        {
            isRunning=true;

            if (projectFiles.getSize()!=0) //Ingen grund til gennemløb, hvis der er ingen filer.
                while (isRunning) {  //Denne løkke kan kun standses udefra


                    if (projectFiles.size()!=oldSelected.size())    //autooptimeret løkke har anden længde end ikke auto generet løkke
                        {
                            oldSelected.clear();        //Slettes ArrayList
                           runs=0;                      //Første gennemkørsel.
                        }


                    for (ProjectFiles tjek : projectFiles) { //Her gennemløbes hele projectFiles linje for linje


                        if (tjek.getUsedBox().isSelected())     //Undersøges om tjek altså projectFiles er selected
                        {


                            if (runs==0)                    //Vi vil sammenligne data fra projectfiles med et oldselection arkiv. I første omgang gemmes i oldselection til sammenligning senere.
                            {
                                oldSelected.add(number,true); //Der er en counter kaldt number

                            }

                                if ( oldSelected.get(number)==false)        //Hvis gamle værdi er false og ny er true. Så er der sket en ændring som skal gemmes.
                                {
                                    updateDataBase(true, tjek.getId()); //Kalder updateringsmetoden
                                    oldSelected.set(number,true);
                                }

                            }

                        else {

                            if (runs==0)

                            {
                                oldSelected.add(number,false);

                            }

                            if ( oldSelected.get(number)==true)
                            {

                                updateDataBase(false, tjek.getId());
                                oldSelected.set(number,false);
                            }

                            }

                            number++;


                         if (number==projectFiles.getSize())
                             number = 0;


                    }
                        runs++;

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

    /**
     * Used in observer method. This is the update part of the observer pattern
     *@param check
     */
    private void updateDataBase(Boolean check, int id) {
        Platform.runLater(() -> {
            try {
                projectFilesManager.updateUsedInDoc(check, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }

    /**
     * This method stop the observer loop.
     */
    public void fileLoopStop() throws InterruptedException {

        isRunning=false;


    }



    public void createNewFile(ProjectFiles file) throws SQLException, FileNotFoundException {
        createdFile = projectFilesManager.createNewFile(file);
        projectFiles.clear();
        projectFiles.set(FXCollections.observableArrayList(projectFilesManager.loadFilesFromAProject(createdFile.getProjectID())));
    }






    public void deleteFile(ProjectFiles file) throws SQLException, FileNotFoundException {

        projectFilesManager.deleteFile(file);
        projectFiles.clear();
        projectFiles.set(FXCollections.observableArrayList(projectFilesManager.loadFilesFromAProject(file.getProjectID())));
        
    }

    /**
     * Sending the filepath through the model.
     * @param filepath
     * @return
     * @throws Exception
     */
    public boolean doesFileExist(String filepath) throws SQLException {return projectFilesManager.doesFileExist(filepath);}
}

