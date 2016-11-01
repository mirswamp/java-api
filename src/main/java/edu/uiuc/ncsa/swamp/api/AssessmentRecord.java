package edu.uiuc.ncsa.swamp.api;

import edu.uiuc.ncsa.swamp.session.Session;
import edu.uiuc.ncsa.swamp.session.handlers.AssessmentRecordHandler;

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
public class AssessmentRecord extends SwampThing{

	Project project;
    PackageThing pkg;
    Platform platform;
    Tool tool;
    String status;

    public AssessmentRecord(Session session) {
        super(session);
    }

    @Override
    protected SwampThing getNewInstance() {
        return new AssessmentRecord(getSession());
    }

    @Override
    public String getIDKey() {
        return AssessmentRecordHandler.EXECUTION_RECORD_UUID;
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

    public String getAssessmentRunUUID() {
        return getConversionMap().getString(AssessmentRecordHandler.ASSESSMENT_RUN_UUID);
    }

    public String getAssessmentResultUUID() {
        return getConversionMap().getString(AssessmentRecordHandler.ASSESSMENT_RESULT_UUID);
    }

    public String getStatus() {
        return getConversionMap().getString(AssessmentRecordHandler.STATUS_STRING);
    }

    public int getWeaknessCount() {
        return (Integer)getConversionMap().get(AssessmentRecordHandler.WEAKNESS_COUNT);
    }
    
    @Override
    public String toString() {
        return "AssessmentRecord[" +
                "uuid=" + getIdentifier() +
                ", project=" + (project==null?"none":getProject().getFullName())+
                ", pkg=" + (pkg==null?"none":getPkg().getName()) +
                ", platform=" + (platform==null?"none":getPlatform().getName()) +
                ", tool=" + (tool==null?"none":getTool().getName()) +
                "]";
    }
}
