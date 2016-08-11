package edu.uiuc.ncsa.swamp.session.handlers;

import edu.uiuc.ncsa.swamp.api.PackageThing;
import edu.uiuc.ncsa.swamp.session.MyResponse;
import edu.uiuc.ncsa.swamp.session.Session;
import edu.uiuc.ncsa.swamp.session.util.ConversionMapImpl;
import edu.uiuc.ncsa.swamp.util.DebugUtil;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 11/20/14 at  5:34 PM
 */
public class PackageHandler<T extends PackageThing> extends AbstractHandler<T> {


    public static final String PACKAGE_UUID_KEY = "package_uuid";
    public static final String PACKAGE_OWNER_UUID_KEY = "package_owner_uuid";
    public static final String PACKAGE_NAME_KEY = "name";
    public static final String PACKAGE_DESCRIPTION_KEY = "description";
    public static final String PACKAGE_TYPE_KEY = "package_type";
    public static final String PACKAGE_TYPE_ID_KEY = "package_type_id";
    public static final String PACKAGE_SHARING_STATUS_KEY = "package_sharing_status";
    public static final String EXTERNAL_URI_KEY = "external_uri";
    public static final String CREATE_DATE_KEY = "create_date";
    public static final String UPDATE_DATE_KEY = "update_date";
    public static final String IS_OWNED_KEY = "is_owned";
    public static final String VERSION_STRINGS = "version_strings";

    public static final int PACKAGE_TYPE_C_SOURCE = 1;
    public static final int PACKAGE_TYPE_JAVA_SOURCE = 2;
    public static final int PACKAGE_TYPE_JAVA_BYTECODE = 3;
    public static final int PACKAGE_TYPE_PYTHON2 = 4;
    public static final int PACKAGE_TYPE_PYTHON3 = 5;
    public static final String PACKAGE_SHARING_STATUS_PRIVATE = "private";
    public static final String PACKAGE_SHARING_STATUS_PUBLIC = "public";
    public static final String PACKAGE_SHARING_STATUS_SHARED = "shared";


    public PackageHandler(Session session) {
        super(session);
    }

    public static final String ENDPOINT_LIST = "packages/users/";
    // public static final String ENDPOINT_LIST = "packages/";


    public PackageThing create(String name,
                               String description,

                               int type) {
        return create(name, description, null, type);
    }

    public PackageThing create(String name,
                               String description,
                               String externalUri,
                               int type) {
        ConversionMapImpl map = new ConversionMapImpl();
        map.put(PACKAGE_OWNER_UUID_KEY, getSession().getUserUID());
        // map.put(PACKAGE_OWNER_UUID_KEY, SWAMPIdentifiers.IDENTIFIER_CAPUT + "deadbeef-cafe-cafe-cafe-deadbeefdeadbeef");
        map.put(PACKAGE_SHARING_STATUS_KEY, PACKAGE_SHARING_STATUS_PRIVATE);
        map.put(PACKAGE_NAME_KEY, name);
        if (externalUri != null) {
            map.put(EXTERNAL_URI_KEY, externalUri);
        }
        map.put(PACKAGE_DESCRIPTION_KEY, description);
        map.put(PACKAGE_TYPE_ID_KEY, Integer.toString(type));
        return (PackageThing) super.create(map);
    }


    protected T fromJSON(JSONObject json) {
        T packageThing = (T) new PackageThing(getSession());
        ConversionMapImpl map = new ConversionMapImpl();
        String[] sAttrib = {PACKAGE_NAME_KEY, PACKAGE_DESCRIPTION_KEY, PACKAGE_TYPE_KEY, PACKAGE_TYPE_ID_KEY, PACKAGE_SHARING_STATUS_KEY, EXTERNAL_URI_KEY};
        String[] uAttrib = {PACKAGE_UUID_KEY};
        String[] bAttrib = {IS_OWNED_KEY};
        String[] dAttrib = {CREATE_DATE_KEY, UPDATE_DATE_KEY};
        String[] aAttrib = {VERSION_STRINGS};

        setAttributes(map, sAttrib, json, DATA_TYPE_STRING);
        setAttributes(map, uAttrib, json, DATA_TYPE_IDENTIFIER);
        setAttributes(map, bAttrib, json, DATA_TYPE_BOOLEAN);
        setAttributes(map, dAttrib, json, DATA_TYPE_DATE);
        setAttributes(map, aAttrib, json, DATA_TYPE_ARRAY);
        packageThing.setConversionMap(map);
        return packageThing;
    }

    public List<T> getAll() {
        MyResponse mr = null;
        mr = getClient().rawGet(createURL(ENDPOINT_LIST + getSession().getUserUID()), null);
        ArrayList<T> pkgs = new ArrayList<>();
        // For packages, the first call gets all the packages and individual calls to a specific url get
        // the rest of the information.
        for (int i = 0; i < mr.jsonArray.size(); i++) {
            JSONObject json = mr.jsonArray.getJSONObject(i);
            String uuid = json.getString(PACKAGE_UUID_KEY);
            String url = createURL("packages/" + uuid);
            MyResponse mr2 = getClient().rawGet(url, null);

            pkgs.add(fromJSON(mr2.json));
        }
        return pkgs;
    }

    @Override
    public String getURL() {
        return createURL("packages");
    }


    public void getTypes() {
        String url = createURL("packages/types");
        MyResponse mr = getClient().rawGet(url, null);
        DebugUtil.say(this, ".getTypes = " + mr.json);
        DebugUtil.say(this, ".getTypes = " + mr.jsonArray);


    }
}
