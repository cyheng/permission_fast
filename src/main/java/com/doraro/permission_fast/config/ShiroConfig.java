package com.doraro.permission_fast.config;

import com.doraro.permission_fast.oauth2.OAuth2Filter;
import com.doraro.permission_fast.oauth2.OauthRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroWebFilterConfiguration;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition filterMap = new DefaultShiroFilterChainDefinition();
        filterMap.addPathDefinition("/webjars/**", "anon");
        filterMap.addPathDefinition("/druid/**", "anon");
        filterMap.addPathDefinition("/sys/login", "anon");
        filterMap.addPathDefinition("/swagger/**", "anon");
        filterMap.addPathDefinition("/v2/api-docs", "anon");
        filterMap.addPathDefinition("/swagger-ui.html", "anon");
        filterMap.addPathDefinition("/swagger-resources/**", "anon");
        filterMap.addPathDefinition("/captcha.jpg", "anon");
        filterMap.addPathDefinition("/**", "oauth2");
        return filterMap;
    }

    /**
     * AOP 需要使用
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager sessionManager, ShiroFilterChainDefinition definition) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(sessionManager);
        //oauth过滤
        Map<String, Filter> filters = new HashMap<>(1);
        filters.put("oauth2", new OAuth2Filter());
        shiroFilter.setFilters(filters);
        shiroFilter.setFilterChainDefinitionMap(definition.getFilterChainMap());
        return shiroFilter;
    }
    @Bean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }
}
