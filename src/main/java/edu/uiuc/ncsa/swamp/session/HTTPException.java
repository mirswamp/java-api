package edu.uiuc.ncsa.swamp.session;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 10/7/14 at  3:08 PM
 */
public class HTTPException extends RuntimeException {
    int statusCode;

    public HTTPException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
