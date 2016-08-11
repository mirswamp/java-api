package edu.uiuc.ncsa.swamp.session.storage;

import edu.uiuc.ncsa.swamp.api.Project;
import edu.uiuc.ncsa.swamp.session.handlers.ProjectHandler;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 4/11/16 at  3:57 PM
 */
public class ProjectStore<V extends Project> extends AbstractStore<V> {
    ProjectHandler<V> projectHandler;

    public ProjectStore(ProjectHandler<V> projectHandler) {
        super(projectHandler);
    }

    @Override
    public V create() {
        //return null;
        return (V) new Project(projectHandler.getSession());

    }
}
