package de.janka.capstonedrazen.rest;

import feign.RequestLine;

public interface ImmoScoutAPI {

    @RequestLine("GET gis/v1.0/country/276/region")
    Void getImmoScout();


}
