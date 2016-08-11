package edu.uiuc.ncsa.swamp.api;

import edu.uiuc.ncsa.swamp.session.Session;

import java.util.Date;

import static edu.uiuc.ncsa.swamp.session.handlers.ToolHandler.*;

/**
 * Models one of the tools used by an assessment.
 * properties supported are
 * <ul>
 *     <li>Create date</li>
 *     <li>Name</li>
 *     <li>Policy</li>
 *     <li>Policy code</li>
 *     <li>Tool sharing status</li>
 *     <li>Update date</li>
 *     <li>is build needed?</li>
 *     <li>is owned?</li>
 * </ul>
 * <p>Created by Jeff Gaynor<br>
 * on 12/10/14 at  10:55 AM
 */
public class Tool extends SwampThing{
    public Tool(Session session) {
        super(session);
    }

    @Override
    protected SwampThing getNewInstance() {
        return new Tool(getSession());
    }

    @Override
    public String getIDKey() {
        return TOOL_UUID_KEY;
    }

    public String getName(){
        return  getString(NAME_KEY);
    }
    public void setName(String name){
         put(NAME_KEY, name);
    }

    public String getToolSharingStatus(){
        return  getString(TOOL_SHARING_STATUS_KEY);
    }
    public void setToolSharingStatus(String toolSharingStatus){
         put(TOOL_SHARING_STATUS_KEY, toolSharingStatus);
    }
    public boolean isBuildNeeded(){
        return  getBoolean(IS_BUILD_NEEDED_KEY);
    }
    public void setBuildNeeded(boolean buildNeeded){
         put(IS_BUILD_NEEDED_KEY, buildNeeded);
    }

    public String getPolicyCode(){
        return  getString(POLICY_CODE_KEY);
    }
    public void setPolicyCode(String policyCode){
         put(POLICY_CODE_KEY, policyCode);
    }
    public Date getCreateDate(){
        return getDate(CREATE_DATE_KEY);
    }
    public void setCreateDate(Date createDate){
        put(CREATE_DATE_KEY, createDate);
    }
    public String getPolicy(){
        return getString(POLICY_KEY);
    }
    public void setPolicy(String policy){
        put(POLICY_KEY, policy);
    }
    public boolean hasPolicy(){
        return getPolicy() != null;
    }

    public Date getUpdateDate(){
        return getDate(UPDATE_DATE_KEY);
    }
    public void setUpdateDate(Date updateDate){
        put(UPDATE_DATE_KEY, updateDate);
    }
    public boolean isOwned(){
        return getBoolean(IS_OWNED_KEY);
    }
    public void setOwned(boolean isOwned){
        put(IS_OWNED_KEY, isOwned);
    }

    @Override
    public String toString() {
        return "Tool[uuid=" + getIdentifier() + ", name=" + getName() + ", sharing status=" + getToolSharingStatus() + ", create date=" + getCreateDate() + "]";
    }


}
