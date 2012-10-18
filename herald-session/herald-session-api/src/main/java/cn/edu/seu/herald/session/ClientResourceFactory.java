/*
 * Copyright (C) 2012 Herald, Southeast University
 */

package cn.edu.seu.herald.session;

import org.restlet.resource.ClientResource;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class ClientResourceFactory {
    
    private String resourceUri;
    
    public ClientResourceFactory(String resourceUri) {
        
    }
    
    public ClientResource newClientResource() {
        return new ClientResource(resourceUri);
    }

}
