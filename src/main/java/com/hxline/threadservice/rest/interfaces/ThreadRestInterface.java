package com.hxline.threadservice.rest.interfaces;

import com.hxline.threadservice.dto.ThreadDTO;
import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Handoyo
 */
@FeignClient("thread-service")
public interface ThreadRestInterface {
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public ResponseEntity<List<ThreadDTO>> getAll();

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Void> addThread(@RequestBody com.hxline.threadservice.domain.Thread thread);

    @RequestMapping(value = "/comment/save", method = RequestMethod.POST)
    public ResponseEntity<Void> addComment(@RequestBody com.hxline.threadservice.domain.Thread thread);
}