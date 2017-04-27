package org.continuousassurance.swamp.api;

import org.continuousassurance.swamp.session.Session;

import java.util.Map;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 3/4/15 at  12:39 PM
 */
public class ToolVersion extends SwampThing{
    public ToolVersion(Session session) {
        super(session);
    }
    public ToolVersion(Session session, Map map) {
        super(session, map);
    }

    @Override
    public String getIDKey() {
        return null;
    }

    @Override
    protected SwampThing getNewInstance() {
        return new ToolVersion(getSession());
    }
}
