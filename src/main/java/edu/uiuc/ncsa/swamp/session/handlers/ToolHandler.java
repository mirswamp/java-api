package edu.uiuc.ncsa.swamp.session.handlers;

import edu.uiuc.ncsa.swamp.api.PackageThing;
import edu.uiuc.ncsa.swamp.api.Project;
import edu.uiuc.ncsa.swamp.api.Tool;
import edu.uiuc.ncsa.swamp.session.MyResponse;
import edu.uiuc.ncsa.swamp.session.Session;
import edu.uiuc.ncsa.swamp.session.util.ConversionMapImpl;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 12/10/14 at  11:01 AM
 */
public class ToolHandler<T extends Tool> extends AbstractHandler<T> {
    public ToolHandler(Session session) {
        super(session);
    }

    public static final String TOOL_UUID_KEY = "tool_uuid";
    public static final String NAME_KEY = "name";
    public static final String TOOL_SHARING_STATUS_KEY = "tool_sharing_status";
    public static final String IS_BUILD_NEEDED_KEY = "is_build_needed";
    public static final String POLICY_CODE_KEY = "policy_code";
    public static final String CREATE_DATE_KEY = "create_date";
    public static final String UPDATE_DATE_KEY = "update_date";
    public static final String IS_OWNED_KEY = "is_owned";
    public static final String POLICY_KEY = "policy";
    public static final String PACKAGE_TYPE_NAMES = "package_type_names";
    public static final String PLATFORM_NAMES = "platform_names";
    

    @Override
    public List<T> getAll() {
        String url = createURL("tools/public");
        MyResponse mr = getClient().rawGet(url, null);
        ArrayList<T> tools = new ArrayList<>();
        if(mr.jsonArray == null){
            return tools;
        }
        for (int i = 0; i < mr.jsonArray.size(); i++) {
            JSONObject json = mr.jsonArray.getJSONObject(i);
            tools.add(fromJSON(json));
        }
        return tools;
    }

    /*
     * Gets Private Tools for the Project
     */
    public List<T> getAll(Project project) {
        String url = createURL("tools/protected/" + project.getUUIDString());
        MyResponse mr = getClient().rawGet(url, null);
        ArrayList<T> tools = new ArrayList<>();
        if(mr.jsonArray == null){
            return tools;
        }
        for (int i = 0; i < mr.jsonArray.size(); i++) {
            JSONObject json = mr.jsonArray.getJSONObject(i);
            tools.add(fromJSON(json));
        }
        return tools;
    }

    public boolean hasPermission(Tool tool, Project project, PackageThing package_thing) {
    	String url = createURL("tools/" + tool.getUUIDString() + "/permission");
    	HashMap<String, Object> map = new <String, Object>HashMap();
    	map.put("package_uuid", package_thing.getUUIDString());
    	map.put("project_uid", project.getUUIDString());
        MyResponse mr = getClient().rawPost(url, map);
        if(mr.jsonArray == null){
            return false;
        }else{
        	return mr.jsonArray.getString(0).equals("granted");
        }
    }
    
    protected T fromJSON(JSONObject json) {
        T tool = (T) new Tool(getSession());
        ConversionMapImpl map = new ConversionMapImpl();
        String[] sAttrib = {NAME_KEY,TOOL_SHARING_STATUS_KEY,POLICY_CODE_KEY,POLICY_KEY};
        String[] uAttrib = {TOOL_UUID_KEY};
        String[] dAttrib = {CREATE_DATE_KEY,UPDATE_DATE_KEY};
        String[] bAttrib = {IS_BUILD_NEEDED_KEY,IS_OWNED_KEY};
        String[] aAttrib = {PACKAGE_TYPE_NAMES, PLATFORM_NAMES};
        
        setAttributes(map, sAttrib, json, DATA_TYPE_STRING);
        setAttributes(map, dAttrib, json, DATA_TYPE_DATE);
        setAttributes(map, uAttrib, json, DATA_TYPE_IDENTIFIER);
        setAttributes(map, bAttrib, json, DATA_TYPE_BOOLEAN);
        setAttributes(map, aAttrib, json, DATA_TYPE_ARRAY);
        
/*
        tool.setUUID(UUID.fromString(json.getString(TOOL_UUID_KEY)));
        tool.setName(json.getString(NAME_KEY));
        tool.setToolSharingStatus(json.getString(TOOL_SHARING_STATUS_KEY));
        tool.setBuildNeeded(json.getInt(IS_BUILD_NEEDED_KEY) != 0);
        tool.setPolicyCode(json.getString(POLICY_CODE_KEY));
        tool.setCreateDate(toSWAMPDate(json, CREATE_DATE_KEY));
        tool.setUpdateDate(toSWAMPDate(json, UPDATE_DATE_KEY));
        tool.setOwned(json.getBoolean(IS_OWNED_KEY));
        tool.setPolicy(json.getString(POLICY_KEY));
*/
        tool.setConversionMap(map);
        return tool;
    }

    public Tool find(String name) {
        for (Tool tool : getAll()) {
            if (tool.getName().equals(name)) {
                return tool;
            }
        }
        return null;
    }

    @Override
    public String getURL() {
        return createURL("tools");
    }
}
