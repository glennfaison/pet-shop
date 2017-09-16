package com.faison.tryouts;

import org.springframework.stereotype.Service;

import javax.ejb.EJB;

@Service
public class MyService {

    private MyEjbLocal ejb;

    @EJB
    public void setMyEjbLocal(MyEjbLocal ejb){
        this.ejb = ejb;
    }

    public MyEjbLocal getMyEjbLocal(){
        return ejb;
    }
}