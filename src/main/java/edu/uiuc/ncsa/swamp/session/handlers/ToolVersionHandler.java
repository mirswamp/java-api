package edu.uiuc.ncsa.swamp.session.handlers;

import edu.uiuc.ncsa.security.core.exceptions.NotImplementedException;
import edu.uiuc.ncsa.swamp.api.Tool;
import edu.uiuc.ncsa.swamp.api.ToolVersion;
import edu.uiuc.ncsa.swamp.session.MyResponse;
import edu.uiuc.ncsa.swamp.session.Session;
import edu.uiuc.ncsa.swamp.session.util.ConversionMapImpl;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 5/26/16 at  3:10 PM
 */
public class ToolVersionHandler<T extends ToolVersion> extends AbstractHandler<T> {
    public ToolVersionHandler(Session session) {
        super(session);
    }
    public static final String TOOL_VERSION_UUID_KEY = "tool_version_uuid";
    public static final String TOOL_UUID_KEY = "tool_uuid";
    public static final String VERSION_STRING_KEY = "version_string";
    public static final String RELEASE_DATE_KEY = "release_date";
    public static final String RETIRE_DATE_KEY = "retire_date";
    public static final String NOTES_KEY = "notes";
    public static final String TOOL_PATH_KEY = "tool_path";
    public static final String TOOL_EXECUTABLE_KEY = "tool_executable";
    public static final String TOOL_ARGUMENTS_KEY = "tool_arguments";
    public static final String TOOL_DIRECTORY_KEY = "tool_directory";


    @Override
    protected T fromJSON(JSONObject json) {
        T tv = (T) new ToolVersion(getSession());
            ConversionMapImpl map = new ConversionMapImpl();
            String[] sAttrib = {NOTES_KEY,VERSION_STRING_KEY,TOOL_PATH_KEY,TOOL_EXECUTABLE_KEY,TOOL_ARGUMENTS_KEY,TOOL_DIRECTORY_KEY};
            String[] uAttrib = {TOOL_UUID_KEY,TOOL_VERSION_UUID_KEY};
            String[] dAttrib = {RELEASE_DATE_KEY,RETIRE_DATE_KEY};
            setAttributes(map, sAttrib, json, DATA_TYPE_STRING);
            setAttributes(map, dAttrib, json, DATA_TYPE_DATE);
            setAttributes(map, uAttrib, json, DATA_TYPE_IDENTIFIER);
//            setAttributes(map, bAttrib, json, DATA_TYPE_BOOLEAN);/*
  /*
        		$toolVersion->tool_version_uuid = Input::get('tool_version_uuid');
		$toolVersion->tool_uuid = Input::get('tool_uuid');
		$toolVersion->version_string = Input::get('version_string');

		$toolVersion->release_date = Input::get('release_date');
		$toolVersion->retire_date = Input::get('retire_date');
		$toolVersion->notes = Input::get('notes');

		$toolVersion->tool_path = Input::get('tool_path');
		$toolVersion->tool_executable = Input::get('tool_executable');
		$toolVersion->tool_arguments = Input::get('tool_arguments');
		$toolVersion->tool_directory = Input::get('tool_directory');
         */
        tv.setConversionMap(map);
        return tv;
    }

    @Override
    public Collection<T> getAll() {
        throw new NotImplementedException("GetAll for tool versions is not implemented.");
    }

    @Override
    public String getURL() {
        return createURL("tools");
    }

    /**
     * Return all versions of the given tool.
     *
     * @param tool
     * @return
     */
    public Collection<T> getAll(Tool tool) {
        String x = createURL("tools/" + tool.getIdentifierString() + "/versions");
        MyResponse mr = getClient().rawGet(x, null);
        ArrayList<T> tools = new ArrayList<>();
        if (mr.jsonArray == null) {
            return tools;
        }
        for (int i = 0; i < mr.jsonArray.size(); i++) {
            JSONObject json = mr.jsonArray.getJSONObject(i);
            tools.add(fromJSON(json));
        }
        return tools;
    }
}
