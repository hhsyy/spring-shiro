package com.yiyuclub.springshiro.config;

import com.yiyuclub.springshiro.filter.JwtTokenFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //    anon
    //    无参，开放权限，可以理解为匿名用户或游客
    //
    //    authc
    //    无参，需要认证
    //
    //    logout
    //    无参，注销，执行后会直接跳转到shiroFilterFactoryBean.setLoginUrl(); 设置的 url
    //
    //    authcBasic
    //    无参，表示 httpBasic 认证
    //
    //    user
    //    无参，表示必须存在用户，当登入操作时不做检查
    //
    //    ssl
    //    无参，表示安全的URL请求，协议为 https
    //
    //    perms[user]
    //    参数可写多个，表示需要某个或某些权限才能通过，多个参数时写 perms["user, admin"]，当有多个参数时必须每个参数都通过才算通过
    //    perms[user:delete]

    //    roles[admin]
    //    参数可写多个，表示是某个或某些角色才能通过，多个参数时写 roles["admin，user"]，当有多个参数时必须每个参数都通过才算通过
    //
    //    rest[user]
    //    根据请求的方法，相当于 perms[user:method]，其中 method 为 post，get，delete 等
    //
    //    port[8081]
    //    当请求的URL端口不是8081时，跳转到schemal://serverName:8081?queryString 其中 schmal 是协议 http 或 https 等等，serverName 是你访问的 Host，8081 是 Port 端口，queryString 是你访问的 URL 里的 ? 后面的参数


    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);


        /*重要，设置自定义拦截器，当访问某些自定义url时，使用这个filter进行验证*/
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        //如果map里面key值为authc,表示所有名为authc的过滤条件使用这个自定义的filter
        //map里面key值为myFilter,表示所有名为myFilter的过滤条件使用这个自定义的filter，具体见下方
        filters.put("myFilter", jwtTokenFilter());
        factoryBean.setFilters(filters);

        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        factoryBean.setLoginUrl("/login");
        // 设置无权限时跳转的 url;
        factoryBean.setUnauthorizedUrl("/notRole");

        // 拦截器
        Map<String, String> filterChain = new LinkedHashMap<>();
        //游客，开发权限
        filterChain.put("/guest/**", "anon");
        //用户，需要角色权限 “user”
        filterChain.put("/user/**", "roles[user_common]");
        //管理员，需要角色权限 “admin”
        filterChain.put("/admin/**", "roles[user_admin]");
        //开放登陆接口
        filterChain.put("/login", "anon");
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChain.put("/**", "authc");

        factoryBean.setFilterChainDefinitionMap(filterChain);

        return factoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(MyRealm());
        return securityManager;
    }

    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public MyRealm MyRealm() {
        MyRealm myrealm = new MyRealm();
        //添加加密
        myrealm.setCredentialsMatcher(hashedCredentialsMatcher());
        //关闭缓存
        myrealm.setCachingEnabled(false);

        return myrealm;
    }

    /**
     * 这里需要设置成与PasswordEncrypter类相同的加密规则
     * <p>
     * 在doGetAuthenticationInfo认证登陆返回SimpleAuthenticationInfo时会使用hashedCredentialsMatcher
     * 把用户填入密码加密后生成散列码与数据库对应的散列码进行对比
     * <p>
     * HashedCredentialsMatcher会自动根据AuthenticationInfo的类型是否是SaltedAuthenticationInfo来获取credentialsSalt盐
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");// 散列算法, 与注册时使用的散列算法相同
        hashedCredentialsMatcher.setHashIterations(1024);// 散列次数, 与注册时使用的散列册数相同
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);// 生成16进制, 与注册时的生成格式相同
        return hashedCredentialsMatcher;
    }



    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }
}
