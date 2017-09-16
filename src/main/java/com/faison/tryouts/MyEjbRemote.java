package com.faison.tryouts;

import javax.ejb.Remote;

@Remote
public interface MyEjbRemote {

    String sendMessage();
}
