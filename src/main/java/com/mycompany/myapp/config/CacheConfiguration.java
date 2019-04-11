package com.mycompany.myapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.UserApp.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.UserApp.class.getName() + ".assigmentUsers", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.UserApp.class.getName() + ".incidentClients", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.UserThirdpartyMembership.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.UserThirdpartyMembership.class.getName() + ".userMemberShips", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Thirdparty.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Thirdparty.class.getName() + ".thirdpartyMemberShips", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Thirdparty.class.getName() + ".thirdpartyLicences", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Licence.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Incident.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Incident.class.getName() + ".assigmentIncidents", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.UserIncidentAssigment.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
