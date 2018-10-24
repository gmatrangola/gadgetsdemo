package com.matrangola.gadgets.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient("microcustomer")
public interface CustomerClient {
    @RequestMapping(method = RequestMethod.GET, path = "/customers/emailById/{id}")
    public String findEmailById(@PathVariable("id")Long id);
}
