package com.yiyuclub.springshiro.config;

import cn.hutool.core.util.IdUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class tools {
    public static String MD5Pwd(String password) {
        // 加密算法MD5
        // salt盐 username + salt
        // 迭代次数
        String salt = createSalt();
        String md5Pwd = new SimpleHash("MD5", password,
                ByteSource.Util.bytes(salt), 1024).toHex();
        System.out.println(md5Pwd);
        System.out.println("salt:"+salt);
        String md5Pwd1 = new SimpleHash("MD5", "111111",
                ByteSource.Util.bytes("37df06f3489049d8ba3fe02ae392748c"), 1024).toHex();
        System.out.println(md5Pwd1);
        return md5Pwd;
    }

    public static String createSalt() {
        return IdUtil.simpleUUID();
    }
}
