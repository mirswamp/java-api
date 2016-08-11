package edu.uiuc.ncsa.swamp.api;

import edu.uiuc.ncsa.swamp.session.Session;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 9/2/15 at  4:00 PM
 */
public class JavaBytecodePackageVersion extends PackageVersion {
    public JavaBytecodePackageVersion(Session session) {
        super(session);
    }

    @Override
    protected SwampThing getNewInstance() {
        return new JavaBytecodePackageVersion(getSession());
    }
}
