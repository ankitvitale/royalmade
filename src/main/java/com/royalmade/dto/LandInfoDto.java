package com.royalmade.dto;

import com.royalmade.entity.Address;
import com.royalmade.entity.Land;
import com.royalmade.entity.Partner;
import com.royalmade.entity.Person;

public class LandInfoDto {
   public Land land;
    public Address address;
    public Partner partner;
    public Person person;


    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
