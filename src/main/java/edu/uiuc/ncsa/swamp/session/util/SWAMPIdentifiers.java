package edu.uiuc.ncsa.swamp.session.util;

import edu.uiuc.ncsa.security.core.Identifier;
import edu.uiuc.ncsa.security.core.util.BasicIdentifier;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 12/11/14 at  5:07 PM
 */
public class SWAMPIdentifiers {
    public static String IDENTIFIER_CAPUT = "urn:uuid:";

    public static Identifier toIdentifier(String x) {
    /*    if (!x.startsWith(IDENTIFIER_CAPUT)) {
            x = IDENTIFIER_CAPUT + x;
        }*/
        return BasicIdentifier.newID(x);
    }


    public static String fromIdentifier(Identifier identifier) {
        if (identifier == null) return null;
        String x = identifier.toString();
   /*     if (x.startsWith(IDENTIFIER_CAPUT)) {
            return x.substring(IDENTIFIER_CAPUT.length());
        }
   */     return x;
    }
}
