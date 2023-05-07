package GUI.Model;

import BE.Project;
import BE.ProjectFiles;
import BLL.ProjectFilesManager;
import BLL.ProjectManager;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ProjectFilesModel {


    private  ListProperty<ProjectFiles> projectFiles, projectFilesRun;

    private ProjectFilesManager projectFilesManager;
    private boolean isRunning = true;
    int x=0, x1=0;
    public ProjectFilesModel() throws Exception {
        projectFilesManager = new ProjectFilesManager();
        projectFiles=new SimpleListProperty<>();

    }


    public ObservableList<ProjectFiles> getAllFilesFromProject(int projectID) throws Exception {

        projectFiles.clear();
        projectFiles.set(FXCollections.observableArrayList(projectFilesManager.loadFilesFromAProject(projectID)));

         return projectFiles;
    }


    public void observer()
    {
        Boolean[] tjek1= new Boolean[projectFiles.size()];

        Thread t = new Thread(() ->
        {

                while (isRunning) {

                    for (ProjectFiles tjek : projectFiles) {


                        if (tjek.getUsedBox().isSelected()) {

                                {

                                    Platform.runLater(() -> {


                                        try {
                                            projectFilesManager.updateUsedInDoc(true, tjek.getId());
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }

                                    });
                                }

                            }
                         else {

                            {
                                Platform.runLater(() -> {

                                    try {
                                        projectFilesManager.updateUsedInDoc(false, tjek.getId());
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                            }

                            }
                        }

                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        System.out.println("This is a treat exception");
                        ;
                    }

                    }



        });
        t.setDaemon(true); //I mark the thread as a daemon thread, so  its terminated when I exit the app.
        t.start();

        }




}
