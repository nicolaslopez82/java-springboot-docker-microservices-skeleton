package com.nicolaslopez82.sms.service;

import com.nicolaslopez82.sms.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.not;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService service;

    @Test
    public void signup() {
        Optional<User> user = service.signup("tontoUsername", "tontoPassword", "john", "doe");
        Assert.assertThat(user.get().getPassword(), not("tontoPassword"));
        System.out.println("Encoded Password Debug -> = " + user.get().getPassword());
    }
}
