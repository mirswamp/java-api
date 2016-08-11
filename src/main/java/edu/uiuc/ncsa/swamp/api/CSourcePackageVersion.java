package edu.uiuc.ncsa.swamp.api;

import edu.uiuc.ncsa.swamp.session.Session;

/**
 * A package version for C source code.
 * <p>Created by Jeff Gaynor<br>
 * on 9/2/15 at  3:59 PM
 */
public class CSourcePackageVersion extends PackageVersion {
    public CSourcePackageVersion(Session session) {
        super(session);
    }

    @Override
    protected SwampThing getNewInstance() {
        return new CSourcePackageVersion(getSession());
    }
}
