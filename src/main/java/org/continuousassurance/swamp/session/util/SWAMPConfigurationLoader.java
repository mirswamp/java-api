package org.continuousassurance.swamp.session.util;

import edu.uiuc.ncsa.security.core.exceptions.GeneralException;
import edu.uiuc.ncsa.security.core.util.LoggingConfigLoader;
import edu.uiuc.ncsa.security.core.util.MyLoggingFacade;
import edu.uiuc.ncsa.security.util.ssl.SSLConfiguration;
import edu.uiuc.ncsa.security.util.ssl.SSLConfigurationUtil;
import org.apache.commons.configuration.tree.ConfigurationNode;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.uiuc.ncsa.security.core.configuration.Configurations.getFirstAttribute;


/**
 * <p>Created by Jeff Gaynor<br>
 * on 4/11/16 at  9:41 AM
 */
public class SWAMPConfigurationLoader<T extends SWAMPServiceEnvironment> extends LoggingConfigLoader<T> implements SWAMPConfigTags {
    public SWAMPConfigurationLoader(ConfigurationNode node) {
        super(node);
    }

    public SWAMPConfigurationLoader(ConfigurationNode node, MyLoggingFacade logger) {
        super(node, logger);
    }

    @Override
    public T createInstance() {
        return (T) new SWAMPServiceEnvironment(getRWSServer(),
                getCSAServer(),
                getUsername(),
                getPassword(),
                getHeaders(),
                getSslConfiguration());
    }

    @Override
    public T load() {
        info("loading configuration.");
        // Can do extra processing here, if needed.
        return  createInstance();
    }

    @Override
    public String getVersionString() {
        return "1.0";
    }

    String username;
    String password;

    public String getUsername() {
        if (username == null) {
            List kids = cn.getChildren(USERNAME_TAG);
            if (kids.isEmpty()) {
                throw new GeneralException("Error: no username found.");
            }
            ConfigurationNode sn = (ConfigurationNode) kids.get(0);
            Object obj = sn.getValue();
            if (obj != null) {
                username = obj.toString();
            }

        }
        return username;
    }

    public String getPassword() {
        if (password == null) {
            List kids = cn.getChildren(PASSWORD_TAG);
            if (kids.isEmpty()) {
                throw new GeneralException("Error: no password found");
            }
            ConfigurationNode sn = (ConfigurationNode) kids.get(0);
            Object obj = sn.getValue();
            if (obj != null) {
                password = obj.toString();
            }

        }
        return password;
    }

    URI rwsServer;


    public URI getRWSServer() {
        if (rwsServer == null) {
            List kids = cn.getChildren(SERVER_ADDRESSES_TAG);
            if (kids.isEmpty()) {
                throw new GeneralException("Error: no servers configured");
            }
            ConfigurationNode sn = (ConfigurationNode) kids.get(0);
            String rws = getFirstAttribute(sn, RWS_ADDRESS_TAG);
            if (rws != null) {
                rwsServer = URI.create(rws);
            }
        }
        return rwsServer;

    }

    URI csaServer;

    public URI getCSAServer() {
        if (csaServer == null) {
            List kids = cn.getChildren(SERVER_ADDRESSES_TAG);
            if (kids.isEmpty()) {
                throw new GeneralException("Error: no servers configured");
            }
            ConfigurationNode sn = (ConfigurationNode) kids.get(0);
            String csa = getFirstAttribute(sn, CSA_ADDRESS_TAG);
            if (csa != null) {
                csaServer = URI.create(csa);
            }
        }
        return csaServer;
    }

    Map<String, String> headers = new HashMap<>();

    public Map<String, String> getHeaders() {
        if (headers.isEmpty()) {
            List kids = cn.getChildren(HEADERS_TAG);
            // Strictly speaking, there do not need to be any headers configured, since these
            // are peculiar to only certain instances of the SWAMP.
            if (kids.isEmpty()) {
                return headers;
            }
            ConfigurationNode sn = (ConfigurationNode) kids.get(0);
            String x = getFirstAttribute(sn, REFERER_HEADER_TAG);
            if (x != null) {
                headers.put(REFERER_HEADER_TAG, x);
            }
            x = getFirstAttribute(sn, ORIGIN_HEADER_TAG);
            if (x != null) {
                headers.put(ORIGIN_HEADER_TAG, x);
            }
            x = getFirstAttribute(sn, HOST_HEADER_TAG);
            if (x != null) {
                headers.put(HOST_HEADER_TAG, x);
            }
        }
        return headers;
    }

    SSLConfiguration sslConfiguration;
    public SSLConfiguration getSslConfiguration(){
        if(sslConfiguration == null) {
            sslConfiguration = SSLConfigurationUtil.getSSLConfiguration(myLogger, cn);
        }
        return sslConfiguration;
    }
}
