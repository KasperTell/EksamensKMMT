package DAL;

import BE.ProjectFiles;

import java.util.List;

public interface iFileDataAccess {

    List<ProjectFiles> loadFilesFromAProject(int projectID) throws Exception;
    void updateUsedInDoc(Boolean usedInDoc, int id) throws Exception;
}
