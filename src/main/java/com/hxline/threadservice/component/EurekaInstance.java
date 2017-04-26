package com.hxline.threadservice.component;

import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;

/**
 *
 * @author Handoyo
 */
public class EurekaInstance extends EurekaInstanceConfigBean{
    
    public EurekaInstance(InetUtils inetUtils) {
        super(inetUtils);
    }
    
    public String getServiceId(){
        return getInstanceId();
    }
    
    public String getHost(){
        return getHostname();
    }
}