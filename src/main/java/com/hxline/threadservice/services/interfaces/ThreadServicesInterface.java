package com.hxline.threadservice.services.interfaces;

import java.util.List;
import com.hxline.threadservice.domain.Thread;
import com.hxline.threadservice.domain.Thumb;
import com.hxline.threadservice.dto.ThreadDTO;

/**
 *
 * @author Handoyo
 */
public interface ThreadServicesInterface {

    public void save(Thread thread);

    public void saveQueue(Thread thread);

    public List<ThreadDTO> getAll();

    public Thread get(String id);

    public Thumb getThumb(String id);
}
