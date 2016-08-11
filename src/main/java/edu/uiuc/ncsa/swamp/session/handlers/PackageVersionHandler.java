package edu.uiuc.ncsa.swamp.session.handlers;

import edu.uiuc.ncsa.security.core.exceptions.GeneralException;
import edu.uiuc.ncsa.security.core.exceptions.NotImplementedException;
import edu.uiuc.ncsa.swamp.api.FileHandle;
import edu.uiuc.ncsa.swamp.api.PackageThing;
import edu.uiuc.ncsa.swamp.api.PackageVersion;
import edu.uiuc.ncsa.swamp.api.SwampThing;
import edu.uiuc.ncsa.swamp.exceptions.NoJSONReturnedException;
import edu.uiuc.ncsa.swamp.session.MyResponse;
import edu.uiuc.ncsa.swamp.session.Session;
import edu.uiuc.ncsa.swamp.session.util.ConversionMapImpl;
import edu.uiuc.ncsa.swamp.session.util.SWAMPIdentifiers;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.*;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 1/13/15 at  11:16 AM
 */
public class PackageVersionHandler<T extends PackageVersion> extends AbstractHandler<T> {
    protected static final int JAVA_SOURCE_PACKAGE_TYPE = 10;
    protected static final int JAVA_BYTECODE_PACKAGE_TYPE = 20;
    protected static final int C_SOURCE_PACKAGE_TYPE = 30;

    public static final String PACKAGE_VERSION_UUID = "package_version_uuid";
    public static final String PACKAGE_UUID = "package_uuid";
    public static final String PLATFORM_UUID = "platform_uuid";
    public static final String VERSION_STRING = "version_string";
    public static final String VERSION_SHARING_STATUS = "version_sharing_status";


    public static final String RELEASE_DATE = "release_date";
    public static final String RETIRE_DATE = "retire_date";
    public static final String NOTES = "notes";


    public static final String PACKAGE_PATH = "package_path";
    public static final String SOURCE_PATH = "source_path";

    public static final String CONFIG_DIR = "config_dir";
    public static final String CONFIG_CMD = "config_cmd";
    public static final String CONFIG_OPT = "config_opt";

    public static final String BUILD_FILE = "build_file";
    public static final String BUILD_SYSTEM = "build_system";
    public static final String BUILD_TARGET = "build_target";


    public static final String BYTECODE_CLASS_PATH = "bytecode_class_path";

    public static final String BYTECODE_AUX_CLASS_PATH = "bytecode_aux_class_path";
    public static final String BYTECODE_SOURCE_PATH = "bytecode_source_path";


    public static final String BUILD_DIR = "build_dir";
    public static final String BUILD_CMD = "build_cmd";
    public static final String BUILD_OPT = "build_opt";

    public final static String FILE_UPLOAD_NAME = "uploaded_file";
    public final static String FILE_UPLOAD_PATH = "path";
    public final static String FILE_UPLOAD_EXTENSION = "extension";
    public final static String FILE_UPLOAD_MIME_TYPE = "mime";
    public final static String FILE_UPLOAD_SIZE = "size";
    public final static String FILE_UPLOAD_ID = "destination_path";

    public final static String FILENAME = "filename";

    /**
     * This is the key in the conversion map for the create command.
     */
    public final static String FILE_UPLOAD_PATH_KEY = "urn:file:path";


    public PackageVersionHandler(Session session) {
        super(session);
    }

    protected T fromJSON(JSONObject json) {
        T packageThing = (T) new PackageVersion(getSession());
        ConversionMapImpl map = new ConversionMapImpl();
        String[] sAttrib = {FILENAME, FILE_UPLOAD_NAME, FILE_UPLOAD_PATH, FILE_UPLOAD_EXTENSION, FILE_UPLOAD_MIME_TYPE, FILE_UPLOAD_ID,
                BUILD_CMD, BUILD_DIR, BUILD_OPT,
                BUILD_FILE, BUILD_SYSTEM, BUILD_TARGET,
                CONFIG_CMD, CONFIG_DIR, CONFIG_OPT,
                PACKAGE_PATH, SOURCE_PATH,
                NOTES, VERSION_SHARING_STATUS, VERSION_STRING,
                BYTECODE_AUX_CLASS_PATH, BYTECODE_CLASS_PATH, BYTECODE_SOURCE_PATH};
        String[] uAttrib = {PACKAGE_UUID, PACKAGE_VERSION_UUID, PLATFORM_UUID};
        String[] dAttrib = {RELEASE_DATE, RETIRE_DATE};

        setAttributes(map, sAttrib, json, DATA_TYPE_STRING);
        setAttributes(map, uAttrib, json, DATA_TYPE_IDENTIFIER);
        setAttributes(map, dAttrib, json, DATA_TYPE_DATE);
        packageThing.setConversionMap(map);
        return packageThing;
    }

    @Override
    public List<T> getAll() {
        throw new NotImplementedException();
    }

    /**
     * Get all the versions of a package
     *
     * @param packageThing
     * @return
     */
    public List<T> getAll(PackageThing packageThing) {
        String url = createURL("packages/" + packageThing.getUUIDString() + "/versions");
        MyResponse mr = getClient().rawGet(url);
        ArrayList<T> versions = new ArrayList<>();
        if (mr.jsonArray == null || mr.jsonArray.isEmpty()) {
            return versions;
        }
        for (int i = 0; i < mr.jsonArray.size(); i++) {
            JSONObject json = mr.jsonArray.getJSONObject(i);
            versions.add(fromJSON(json));
        }

        return versions;
    }

    public File download(PackageVersion packageVersion, File targetDirectory) {
        String url = createURL("packages/versions/" + packageVersion.getUUIDString() + "/download");
        return getClient().getFile(url, targetDirectory, packageVersion.getFilename());
    }

    @Override
    public String getURL() {
        //return createURL("packages/versions");
    	return createURL("packages/versions/store");
    }

    /*
    curl 'https://swa-csaweb-pd-01.mir-swamp.org/packages/versions/upload' -X POST
    -H 'Accept: *//*' -H 'Accept-Encoding: gzip, deflate'
    -H 'Accept-Language: en-US,en;q=0.5'
    -H 'Content-Length: 4188829'
    -H 'Content-Type: multipart/form-data; boundary=---------------------------1978615270641118831918295646'
    -H 'Cookie: swamp_reg_session=eyJpdiI%3D;%3D%3D; swamp_csa_session3D;zJmOWY1ZjYifQ%3D%3D'
    -H 'Host: swa-csaweb-pd-01.mir-swamp.org'
    -H 'Origin: https://www.mir-swamp.org'
     -H 'Referer: https://www.mir-swamp.org/'
      -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:35.0) Gecko/20100101 Firefox/35.0'
     */
    protected FileHandle upload(PackageThing packageThing, File f) {
        String url = createURL("packages/versions/upload");
        Map<String, Object> map = new HashMap<>();
        map.put("user_uid", getSession().getUserUID());
        map.put("package_uuid", packageThing.getUUIDString());
        map.put("external_url", "");

        //DebugUtil.say(this, ".upload: url = " + url);
        List<File> files = new LinkedList<>();
        files.add(f);
        MyResponse mr = getClient().rawPost(url, map, files);
        //System.out.println(getClass().getSimpleName() + " json=" + mr.json);
        FileHandle fileHandle = new FileHandle(getSession());
        fileHandle.setIdentifier(SWAMPIdentifiers.toIdentifier(mr.json.getString(FILE_UPLOAD_ID)));
        fileHandle.setExtension(mr.json.getString(PackageVersionHandler.FILE_UPLOAD_EXTENSION));
        fileHandle.setName(f.getName());
        //fileHandle.setName(mr.json.getString(PackageVersionHandler.FILE_UPLOAD_NAME));
        fileHandle.setPath(mr.json.getString(PackageVersionHandler.FILE_UPLOAD_PATH));
        fileHandle.setSize(mr.json.getLong(PackageVersionHandler.FILE_UPLOAD_SIZE));
        fileHandle.setMimeType(mr.json.getString(PackageVersionHandler.FILE_UPLOAD_MIME_TYPE));
        return fileHandle;
    }

    protected String uploadNew(String pkgName, String description, String pkgUrl, int pkgTypeId) {
        String url = createURL("packages");
        HashMap<String, Object> map = new HashMap<>();
        map.put("package_sharing_status", "private");
        map.put("name", pkgName);
        map.put("description", description);
        map.put("external_url", pkgUrl);
        map.put("package_type_id", pkgTypeId);

        MyResponse mr = getClient().rawPost(url, map);
        if (mr.json == null) {
            if (mr.jsonArray == null) {
                return null;
            }
            if (mr.jsonArray.isEmpty()) return "";
            return mr.jsonArray.getString(0);
        } else {
            if (mr.json.containsKey("error")) {
                throw new GeneralException("Error posting to " + url + ". Message=" + mr.json);
            }
            if (mr.json.containsKey("package_uuid")) return mr.json.getString("package_uuid");
        }
        return "";
    }

    protected String uploadNewOld(FileHandle fileHandle) {
        String url = createURL("packages/versions/new/file-tree");
        HashMap<String, Object> map = new HashMap<>();
        map.put(PACKAGE_PATH, fileHandle.getUUIDString() + "/" + fileHandle.getName() );
        map.put("dirname", ".");
        MyResponse mr = getClient().rawGet(url, map);
        if (mr.json == null) {
            if (mr.jsonArray == null) {
                return null;
            }
            if (mr.jsonArray.isEmpty()) return "";
            return mr.jsonArray.getString(0);
        } else {
            if (mr.json.containsKey("error")) {
                throw new GeneralException("Error posting to " + url + ". Message=" + mr.json);
            }
            if (mr.json.containsKey("name")) return mr.json.getString("name");
        }
        return "";
    }

    @Override
    public SwampThing create(ConversionMapImpl map) {
        throw new NotImplementedException("This method is not supported for package versions. Use create(PackageThing, File, ConversionMapImpl");
    }

    /**
     * There are 4 (four) distinct steps that must be done in sequence to upload a file and create a package version.
     * This does them all. The map contains items (such as build options) that are augmented by the correct information
     * from the file itself. Note that you cannot actually create a package version until the file has been uploaded
     * and certain information is returned by the SWAMP. Think of the version as something that is attached to an uploaded
     * file.
     *
     * @param packageThing
     * @param f
     * @param map
     * @return
     */
    public PackageVersion create(PackageThing packageThing, File f, ConversionMapImpl map) {
        FileHandle fileHandle = upload(packageThing, f); //STEP 1
        //map.put(SOURCE_PATH, uploadNew(fileHandle)); // STEP 2
        //String package_uuid = uploadNew(packageThing.getName(), packageThing.getDescription(), "", 1);
        String package_uuid = packageThing.getUUIDString();
        String packagePath = fileHandle.getUUIDString() + "/" + fileHandle.getName();
        map.put(VERSION_SHARING_STATUS, "protected");
        map.put(PACKAGE_UUID, package_uuid);
        map.put(PACKAGE_PATH, packagePath);
        map.put(FILE_UPLOAD_NAME, fileHandle.getName());
        PackageVersion packageVersion = (PackageVersion) super.create(map); // STEP 3
        //String url = createURL("packages/versions/" + packageVersion.getUUIDString() + "/sharing"); // STEP 4
        HashMap<String, Object> addMap = new HashMap<>();
        addMap.put("projects[0][project_uid]", map.getString("project_uuid"));
        try {
            getClient().rawPut(createURL("packages/versions/" + packageVersion.getUUIDString() + "/sharing"), addMap);
        } catch (NoJSONReturnedException x) {
            // rock on. This one method does not return JSON.
        }
        packageVersion.setPackageThing(packageThing);
        packageVersion.setFileHandle(fileHandle);
        return packageVersion;
    }

    public PackageVersion createOld(PackageThing packageThing, File f, ConversionMapImpl map) {
        FileHandle fileHandle = upload(packageThing, f); //STEP 1
        //map.put(SOURCE_PATH, uploadNew(fileHandle)); // STEP 2
        String packagePath = fileHandle.getUUIDString() + "/" + fileHandle.getName();
        map.put(PACKAGE_PATH, packagePath);
        map.put(PACKAGE_UUID, packageThing.getUUIDString());
        map.put(FILE_UPLOAD_NAME, fileHandle.getName());
        PackageVersion packageVersion = (PackageVersion) super.create(map); // STEP 3
        String url = createURL("packages/versions/" + packageVersion.getUUIDString() + "/add"); // STEP 4
        HashMap<String, Object> addMap = new HashMap<>();
        addMap.put(PACKAGE_PATH, packagePath);
        try {
            getClient().rawPost(url, addMap);
        } catch (NoJSONReturnedException x) {
            // rock on. This one method does not return JSON.
        }
        packageVersion.setPackageThing(packageThing);
        packageVersion.setFileHandle(fileHandle);
        return packageVersion;
    }

    //public JavaSourcePackageVersion createJavaSourcePackageVersion(P)
}
