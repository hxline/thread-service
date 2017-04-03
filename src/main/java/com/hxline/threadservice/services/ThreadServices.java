/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hxline.threadservice.services;

import com.hxline.threadservice.domain.Thread;
import com.hxline.threadservice.domain.Thumb;
import com.hxline.threadservice.dto.ThreadDTO;
import com.hxline.threadservice.hibernate.interfaces.ThreadHibernateInterface;
import com.hxline.threadservice.services.interfaces.ThreadServicesInterface;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Handoyo
 */
@Service
public class ThreadServices implements ThreadServicesInterface {

//    @Value("${info.gateway}")
    private String gateway_url = "https://hxline-gateway.herokuapp.com";
    
    private ThreadHibernateInterface threadHibernate;

    public void setThreadHibernate(ThreadHibernateInterface threadHibernate) {
        this.threadHibernate = threadHibernate;
    }
    
    @Override
    public void save(Thread thread) {
        if (this.get(thread.getId()) == null) {
            threadHibernate.save(thread);
        } else {
            throw new RuntimeException("Thread Has Been Existed");
        }
    }

    @Override
    public List<ThreadDTO> getAll() {
        List<Thread> threads = threadHibernate.getAll();
        if (threads.size() > 0) {
            List<ThreadDTO> threadDTOs = new ArrayList();
            
            for (Thread thread : threads) {
                threadDTOs.add(new ThreadDTO(
                        thread.getId(),
                        thread.getThreadTopic(),
                        thread.getThreadDescription(),
                        thread.getComments(),
                        this.getThumb(thread.getId())));
            }
            
            return threadDTOs;
        } else {
            return null;
        }
    }

    @Override
    public Thread get(String id) {
        Thread thread = threadHibernate.get(id);
        if (thread == null) {
            return null;
        } else {
            return thread;
        }
    }

    @Override
    public Thumb getThumb(String id) {
        RestTemplate restTemplate = new RestTemplate();
        Thumb response = new Thumb();
        try {
            response = restTemplate.getForObject(gateway_url + "/thumb-service/get/" + id, Thumb.class);

        } catch (Exception e) {
            System.out.println(e);
            response = new Thumb();
        }
        
        return response;
    }
}