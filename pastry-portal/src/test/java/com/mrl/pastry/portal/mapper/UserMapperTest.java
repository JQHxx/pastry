package com.mrl.pastry.portal.mapper;

import com.mrl.pastry.portal.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * User Mapper Test
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/20
 */
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insertTest(){
        User user = new User();
        //...
        userMapper.insert(user);
    }
}
