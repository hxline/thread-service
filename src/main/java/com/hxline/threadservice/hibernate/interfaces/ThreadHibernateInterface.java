package com.hxline.threadservice.hibernate.interfaces;

import java.util.List;
import com.hxline.threadservice.domain.Thread;

/**
 *
 * @author Handoyo
 */
public interface ThreadHibernateInterface {

    public void save(Thread thread);

    public void saveQueue(Thread thread);

    public List<Thread> getAll();

    public Thread get(String id);
}
