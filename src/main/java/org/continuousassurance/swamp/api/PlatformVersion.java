package org.continuousassurance.swamp.api;

import org.continuousassurance.swamp.session.Session;

import java.util.Map;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 3/4/15 at  12:39 PM
 */
public class PlatformVersion extends SwampThing {
    public PlatformVersion(Session session) {
        super(session);
    }
    public PlatformVersion(Session session, Map map) {
        super(session, map);
    }

    @Override
    public String getIDKey() {
        return null;
    }

    @Override
    protected SwampThing getNewInstance() {
        return new PlatformVersion(getSession());
    }
}
