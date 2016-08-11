package edu.uiuc.ncsa.swamp.session.storage;

import edu.uiuc.ncsa.swamp.api.PackageThing;
import edu.uiuc.ncsa.swamp.session.handlers.AbstractHandler;
import edu.uiuc.ncsa.swamp.session.handlers.PackageHandler;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 4/19/16 at  11:02 AM
 */
public class PackageStore<V extends PackageThing> extends AbstractStore<V> {
    public PackageStore(AbstractHandler<? extends V> handler) {
        super(handler);
    }

    protected PackageHandler getPackageHandler(){
        return (PackageHandler)handler;
    }
    @Override
    public V create() {
        PackageThing p = getPackageHandler().create("Temp name", "Temp description", 0);
        p.setSession(handler.getSession());
        return (V)p;
    }
}
