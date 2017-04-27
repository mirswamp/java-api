package org.continuousassurance.swamp.api;

import org.continuousassurance.swamp.session.Session;
import org.continuousassurance.swamp.session.handlers.AssessmentRunHandler;

import java.util.Map;

/**
 * This models an assessment. Assessments also point to other objects, in particular
 * each assessment will point to a corresponding<br/>
 * <ul>
 *     <li>project</li>
 *     <li>package</li>
 *     <li>platform</li>
 *     <li>tool</li>
 * </ul>
 * Each of these are first class objects.
 * <p>Created by Jeff Gaynor<br>
 * on 11/18/14 at  3:06 PM
 */
public class AssessmentRun extends SwampThing{
    public AssessmentRun(Session session) {
        super(session);
    }
    public AssessmentRun(Session session, Map map) {
        super(session, map);
    }

    @Override
    protected SwampThing getNewInstance() {
        return new AssessmentRun(getSession());
    }

    @Override
    public String getIDKey() {
        return AssessmentRunHandler.ASSESSMENT_RUN_UUID;
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public PackageThing getPkg() {
        return pkg;
    }

    public void setPkg(PackageThing pkg) {
        this.pkg = pkg;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    Project project;
    PackageThing pkg;
    Platform platform;
    Tool tool;

    @Override
    public String toString() {
        return "AssessmentRun[" +
                "uuid=" + getIdentifier() +
                ", project=" + (project==null?"none":getProject().getFullName())+
                ", pkg=" + (pkg==null?"none":getPkg().getName()) +
                ", platform=" + (platform==null?"none":getPlatform().getName()) +
                ", tool=" + (tool==null?"none":getTool().getName()) +
                "]";
    }
}
