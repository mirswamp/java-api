package org.continuousassurance.swamp.session.util;

import java.io.Serializable;

/**
 * <p>Created by Jeff Gaynor<br>
 * on 2/8/18 at  10:02 AM
 */
public class Proxy implements Serializable{
    public int port = -1;
    public String host = "";
    public String scheme = "";
    public boolean configured = false;
	
    public Proxy() {
    		this(-1, "", "", false);
	}
    
    public Proxy(int port, String host, String scheme, boolean configured) {
		super();
		this.port = port;
		this.host = host;
		this.scheme = scheme;
		this.configured = configured;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public boolean isConfigured() {
		return configured;
	}

	public void setConfigured(boolean configured) {
		this.configured = configured;
	}

    
}
