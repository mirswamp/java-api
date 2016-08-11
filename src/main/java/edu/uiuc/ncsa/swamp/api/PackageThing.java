package edu.uiuc.ncsa.swamp.api;

import edu.uiuc.ncsa.swamp.session.Session;

import static edu.uiuc.ncsa.security.core.util.BeanUtils.checkEquals;
import static edu.uiuc.ncsa.swamp.session.handlers.PackageHandler.*;

/**
 * This models a SWAMP package. Note that the name is <b>PackageThing</b> since the name "Package" is already
 * in use in Java (for reflection of classes). Supported properties are
 * <ul>
 *     <li>name = the name given to this package</li>
 *     <li>description = a human readable description of this package</li>
 *     <li>type = the type of this package. These are strings that are server dependent, e.g. "Java bytecode"</li>
 *     <li>external uri - The external uri for the source of this package.</li>
 *     <li>package sharing status - the access of the package: public, private</li>
 *     <li>package type id - the <i>internal</i> numeric code this instance of the SWAMP uses. Generally not portable between instances</li>
 * </ul>
 * <p>Created by Jeff Gaynor<br>
 * on 11/18/14 at  3:06 PM
 */
public class PackageThing extends SwampThing {

    public PackageThing(Session session) {
        super(session);
    }

    @Override
    public String toString() {
        String out = "Package[uuid=" + getIdentifier() + ", name=" + getName() + ", description=" + getDescription() + ",type=" + getType() + "]";
        return out;
    }

    @Override
    protected SwampThing getNewInstance() {
        return new PackageThing(getSession());
    }

    @Override
    public String getIDKey() {
        return PACKAGE_UUID_KEY;
    }

    public String getName() {
        return getString(PACKAGE_NAME_KEY);
    }

    public void setName(String name) {
        put(PACKAGE_NAME_KEY, name);
    }

    public String getDescription() {
        return getString(PACKAGE_DESCRIPTION_KEY);
    }

    public void setDescription(String description) {
        put(PACKAGE_DESCRIPTION_KEY, description);
    }

    public String getType() {
        return getString(PACKAGE_TYPE_KEY);
    }

    public void setType(String type) {
        put(PACKAGE_TYPE_KEY, type);
    }

    public String getVersions(){
    	return getConversionMap().getString("version_strings");
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof PackageThing)) return false;
        PackageThing pt = (PackageThing) object;
        if (!checkEquals(getIdentifier(), pt.getIdentifier())) return false;
        //if(!checkEquals(getOwnerUUID(), pt.getOwnerUUID())) return false;
        if (!checkEquals(getType(), pt.getType())) return false;
        if (!checkEquals(getIdentifier(), pt.getIdentifier())) return false;
        return true;
    }
}
