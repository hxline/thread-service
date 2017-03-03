package com.hxline.threadservice.hibernate;

import com.hxline.threadservice.domain.Comment;
import com.hxline.threadservice.domain.Thread;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Handoyo
 */
@Transactional
public class ThreadHibernate {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public void save(Thread thread){
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(thread);
        transaction.commit();
        session.close();
        closeConnection();
    }
    
    public List<Thread> getAll(){
        Session session = this.sessionFactory.openSession();
        List<Thread> threads = session.createQuery("FROM Thread").list();
        session.close();
        closeConnection();
        return threads;
    }
    
    public Thread get(String id){
        Session session = this.sessionFactory.openSession();
        Thread thread = (Thread) session.get(Thread.class, id);
        session.close();
        closeConnection();
        return thread;
    }
    
    private void closeConnection() {
        sessionFactory.close();
    }
}