package com.faison.tryouts;

import org.springframework.stereotype.Component;

import javax.ejb.Stateless;

@Stateless
@Component
public class MyEjbImpl implements MyEjbLocal, MyEjbRemote {

    public String sendMessage() {
        return "hello world!";
    }
}
