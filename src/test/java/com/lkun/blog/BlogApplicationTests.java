package com.lkun.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Test
    public void contextLoads() {
        int i = 10, j = 20, k =3;
        k *= i + j;
        System.out.println(k);
    }

    @Test
    public void test001(){
        Date date=new Date();
        System.out.printf("%tD%n",date);
    }

}
