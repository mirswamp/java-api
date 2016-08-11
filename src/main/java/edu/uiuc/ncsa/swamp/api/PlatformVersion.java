package edu.uiuc.ncsa.swamp.api;

import edu.uiuc.ncsa.swamp.session.Session;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 3/4/15 at  12:39 PM
 */
public class PlatformVersion extends SwampThing {
    public PlatformVersion(Session session) {
        super(session);
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
