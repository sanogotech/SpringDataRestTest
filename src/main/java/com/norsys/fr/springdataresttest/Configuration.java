package com.norsys.fr.springdataresttest;

import com.norsys.fr.springdataresttest.validator.HouseValidator;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@org.springframework.context.annotation.Configuration
public class Configuration implements RepositoryRestConfigurer {

    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener v) {
        v.addValidator("beforeCreate", new HouseValidator());
    }
}
