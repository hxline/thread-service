package com.hxline.threadservice.hibernate;

import com.hxline.threadservice.component.HibernateRepository;
import com.hxline.threadservice.messaging.publisher.ThreadPublisher;
import com.hxline.threadservice.hibernate.interfaces.ThreadHibernateInterface;
import com.hxline.threadservice.domain.Thread;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Handoyo
 */
@Repository
@Transactional
public class ThreadHibernate extends HibernateRepository implements ThreadHibernateInterface{
    
    private ThreadPublisher threadPublisher;

    public void setThreadPublisher(ThreadPublisher threadPublisher) {
        this.threadPublisher = threadPublisher;
    }
    
    @Override
    public void save(Thread thread, Boolean newThread){
        getSession().saveOrUpdate(thread);
        //send to another thread service
        threadPublisher.send(thread);
        if (newThread) {
            //send to all thumbservice to create thumb with this threadId
            threadPublisher.send(thread.getId());
        }
    }
    
    @Override
    public void saveQueue(Thread thread){
        getSession().saveOrUpdate(thread);
    }
    
    @Override
    public List<Thread> getAll(){
        return new ArrayList(getSession().createQuery("FROM Thread").list());
    }
    
    @Override
    public Thread get(String id){
        return getSession().get(Thread.class, id);
    }
}