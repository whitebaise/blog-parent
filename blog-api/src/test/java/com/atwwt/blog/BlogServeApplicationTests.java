package com.atwwt.blog;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogServeApplicationTests {

    @Test
    void contextLoads() {
        String password;

        password= DigestUtils.md5Hex("adminwwt!@#");
        System.out.println(password);
    }

}
