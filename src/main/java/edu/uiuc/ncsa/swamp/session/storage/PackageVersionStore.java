package edu.uiuc.ncsa.swamp.session.storage;

import edu.uiuc.ncsa.swamp.api.PackageVersion;
import edu.uiuc.ncsa.swamp.session.handlers.AbstractHandler;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 4/19/16 at  4:11 PM
 */
public class PackageVersionStore<V extends PackageVersion> extends AbstractStore<V> {
    public PackageVersionStore(AbstractHandler<? extends V> handler) {
        super(handler);
    }

    @Override
    public V create() {
        return (V) new PackageVersion(handler.getSession());
    }
}
