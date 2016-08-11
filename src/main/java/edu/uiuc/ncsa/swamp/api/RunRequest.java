package edu.uiuc.ncsa.swamp.api;

import edu.uiuc.ncsa.swamp.session.Session;

import static edu.uiuc.ncsa.swamp.session.handlers.RunRequestHandler.*;

/**
 * This models a run request. Properties supported are
 * <ul>
 *     <li>Description</li>
 *     <li>Name</li>
 *     <li>Project</li>
 * </ul>
 * <p>Created by Jeff Gaynor<br>
 * on 12/22/14 at  11:37 AM
 */
public class RunRequest extends SwampThing{
    public RunRequest(Session session) {
        super(session);
    }

    @Override
    protected SwampThing getNewInstance() {
        return new RunRequest(getSession());
    }

    @Override
    public String getIDKey() {
        return RUN_REQUEST_UUID_KEY;
    }

    public String getName(){return getString(RUN_REQUEST_NAME_KEY);}
    public void setName(String name){put(RUN_REQUEST_NAME_KEY, name);}
    public String getDescription(){return getString(RUN_REQUEST_DESCRIPTION_KEY);}
    public void setDescription(String description){put(RUN_REQUEST_DESCRIPTION_KEY, description);}

    Project project = null;
    public Project getProject(){return project;}
    public void setProject(Project project){this.project= project;}

    @Override
    public String toString() {
        return "RunRequest[uuid=" + getUUIDString() + ", name=" + getName() + ", description=" + getDescription() + ", project uuid=" + project.getUUIDString() + "]";
    }
}
