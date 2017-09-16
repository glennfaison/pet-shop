package com.faison.tryouts;

import org.springframework.stereotype.Component;

import javax.ejb.Local;
import javax.ejb.LocalBean;

@Local
public interface MyEjbLocal {

    String sendMessage();
}
