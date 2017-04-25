package org.continuousassurance.swamp.api;

import org.continuousassurance.swamp.session.Session;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 3/4/15 at  12:39 PM
 */
public class PlatformVersion extends SwampThing {
    public static final String NAME_KEY ="full_name";
    public static final String PLATFORM_UUID_KEY="platform_uuid";  
    public static final String PLATFORM_VERSION_UUID_KEY ="platform_version_uuid";
    public static final String VERSION_STRING ="version_string";

    protected Platform platform;
	
    public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public PlatformVersion(Session session) {
        super(session);
    }

	public String getFullName() {
		return this.getConversionMap().getString(NAME_KEY);
	}

	public String getVersionString() {
		return this.getConversionMap().getString(VERSION_STRING);
	}

	public String getPlatformVersionUuid() {
		return this.getConversionMap().getString(PLATFORM_VERSION_UUID_KEY);
	}

	public String getName() {
		return getFullName();
	}
	
    @Override
    public String getIDKey() {
        return PLATFORM_VERSION_UUID_KEY;
    }

    @Override
    protected SwampThing getNewInstance() {
        return new PlatformVersion(getSession());
    }
}
