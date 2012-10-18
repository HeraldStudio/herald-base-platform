/*
 * Copyright (C) 2012 Herald, Southeast University
 */

package cn.edu.seu.herald.session;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionCacheAccessException extends Exception {
    
    public SessionCacheAccessException() {
        super();
    }
    
    public SessionCacheAccessException(Throwable cause) {
        super(cause);
    }
    
    public SessionCacheAccessException(String msg) {
        super(msg);
    }
    
    public SessionCacheAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
