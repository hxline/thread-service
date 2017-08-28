package com.hxline.threadservice.rest;

import com.hxline.threadservice.domain.Comment;
import com.hxline.threadservice.domain.Thread;
import com.hxline.threadservice.dto.ThreadDTO;
import com.hxline.threadservice.services.interfaces.ThreadServicesInterface;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.hxline.threadservice.rest.interfaces.ThreadRestInterface;

/**
 *
 * @author Handoyo
 */
@RestController
public class ThreadRest implements ThreadRestInterface{

    @Autowired
    private ThreadServicesInterface threadServices;
    
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    @HystrixCommand(
            groupKey = "MainThreadService",
            fallbackMethod = "getAllFallback",
            commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000"),
            threadPoolProperties = {
                        @HystrixProperty(name = "coreSize", value = "5"),
                        @HystrixProperty(name = "maxQueueSize", value = "10"),
                        @HystrixProperty(name = "queueSizeRejectionThreshold", value = "10"),
            }
        )
    public ResponseEntity<List<ThreadDTO>> getAll() {
        List<ThreadDTO> threadDTOs = threadServices.getAll();
        if (threadDTOs != null) {
            return new ResponseEntity(threadDTOs, HttpStatus.FOUND);
        } else {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @HystrixCommand(
            groupKey = "FallbackThreadService"
        )
    public ResponseEntity<List<ThreadDTO>> getAllFallback() {
        return new ResponseEntity(null, HttpStatus.REQUEST_TIMEOUT);
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @HystrixCommand(
            groupKey = "MainThreadService",
            fallbackMethod = "addThreadFallback",
            commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000")
        )
    public ResponseEntity<Void> addThread(@RequestBody Thread thread) {
        if (threadServices.get(thread.getId()) != null) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        } else {
            thread.setComments(null);
            threadServices.save(new Thread(UUID.randomUUID().toString(), thread.getThreadTopic(), thread.getThreadDescription(), thread.getComments()), true);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }
    
    @HystrixCommand(
            groupKey = "FallbackThreadService"
        )
    public ResponseEntity<Void> addThreadFallback(@RequestBody Thread thread) {
        return new ResponseEntity(HttpStatus.REQUEST_TIMEOUT);
    }

    @RequestMapping(value = "/comment/save", method = RequestMethod.POST)
    @HystrixCommand(
            groupKey = "MainThreadService",
            fallbackMethod = "addCommentFallback",
            commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000")
        )
    public ResponseEntity<Void> addComment(@RequestBody Thread thread) {
        Thread addThread = threadServices.get(thread.getId());
        if (addThread == null) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        } else {
            for (Comment comment : thread.getComments()) {
                comment.setId(UUID.randomUUID().toString());
                addThread.getComments().add(comment);
            }
            threadServices.save(addThread, false);

            return new ResponseEntity(HttpStatus.CREATED);
        }
    }
    
    @HystrixCommand(
            groupKey = "FallbackThreadService"
        )
    public ResponseEntity<Void> addCommentFallback(@RequestBody Thread thread) {
        return new ResponseEntity(HttpStatus.REQUEST_TIMEOUT);
    }

//    private String getAddress() {
//        int i = 0;
//        String address = "";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
//        HttpEntity<String> entity = new HttpEntity<String>("", headers);
//        try {
//            ResponseEntity<Object> response = restTemplate.exchange("http://localhost:1111/eureka/apps/thumb-service", HttpMethod.GET, entity, Object.class);
//            LinkedHashMap<String, Object> body = (LinkedHashMap<String, Object>) response.getBody();
//
//            LinkedHashMap<String, Object> value = new LinkedHashMap();
//            for (Map.Entry<String, Object> entry : body.entrySet()) {
//                if (i == 1) {
//                    value = (LinkedHashMap<String, Object>) entry.getValue();
//                }
//                i++;
//            }
//
//            for (Map.Entry<String, Object> entry : value.entrySet()) {
//                if (entry.getKey() == "homePageUrl") {
//                    address = entry.getValue().toString();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//            address = e.getMessage();
//        }
//
//        System.out.println("Thumb Service Address : " + address);
//        return address;
//    }

//    private String getAddressSecure() {
//        int i = 0;
//        String address = "";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.set("Authorization", "Bearer " + getToken());
//        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
//        try {
//            ResponseEntity<Object> response = restTemplate.exchange(gateway_url + "/api/gateway/routes", HttpMethod.GET, entity, Object.class);
//            LinkedHashMap<String, Object> body = (LinkedHashMap<String, Object>) response.getBody();
//
//            LinkedHashMap<String, Object> value = new LinkedHashMap();
//            for (Map.Entry<String, Object> entry : body.entrySet()) {
//                if (i == 1) {
//                    value = (LinkedHashMap<String, Object>) entry.getValue();
//                }
//                i++;
//            }
//
//            for (Map.Entry<String, Object> entry : value.entrySet()) {
//                if (entry.getKey() == "homePageUrl") {
//                    address = entry.getValue().toString();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//            address = e.getMessage();
//        }
//
//        System.out.println("Thumb Service Address : " + address);
//        return address;
//    }

//    private String getToken() {
//        RestTemplate restTemplate = new RestTemplate();
//        String plainCreds = "web_app:";
//        String gateway = gateway_url + "/uaa/oauth/token?grant_type=password&username=admin&password=admin";
//        byte[] plainCredsBytes = plainCreds.getBytes();
//        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
//        String base64Creds = new String(base64CredsBytes);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Basic " + base64Creds);
//        HttpEntity<String> request = new HttpEntity<String>(headers);
//        ResponseEntity<Object> response = restTemplate.exchange(gateway, HttpMethod.POST, request, Object.class);
//        LinkedHashMap<String, Object> body = (LinkedHashMap<String, Object>) response.getBody();
//
//        for (Map.Entry<String, Object> entry : body.entrySet()) {
//            if (entry.getKey() == "access_token") {
//                return entry.getValue().toString();
//            }
//        }
//
//        return null;
//    }
}