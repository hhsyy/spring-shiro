package com.yiyuclub.springshiro.config;

import cn.hutool.core.util.IdUtil;
import com.yiyuclub.springshiro.models.IdeaRole;
import com.yiyuclub.springshiro.models.IdeaUser;
import com.yiyuclub.springshiro.service.UserService;
import lombok.SneakyThrows;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        IdeaUser principal = (IdeaUser)SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        IdeaRole role = userService.getRoleById(Integer.valueOf(principal.getRole()));

        Set<String> set = new HashSet<>();
        set.add(role.getRoleName());
        info.addRoles(set);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

        IdeaUser user = userService.getUserByName(token.getUsername());

        if(user == null){
            throw new AccountException();
        }

        return new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getSalt()),"yiyu_realm");
    }
}
