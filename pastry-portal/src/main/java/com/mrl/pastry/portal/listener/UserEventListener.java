package com.mrl.pastry.portal.listener;

import com.mrl.pastry.portal.event.user.UserFollowEvent;
import com.mrl.pastry.portal.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * User edit event listener
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/15
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventListener {

//    private final UserRepository userIndexRepository;

    private final UserMapper userMapper;

//    @Async
//    @EventListener
//    public void onApplicationEvent(UserNewEvent event) {
//        User user = event.getUser();
//        // save blog document
//        UserDocument doc = BeanUtils.copyProperties(user, UserDocument.class);
//        userIndexRepository.save(doc);
//        log.debug("es saved user document: [{}]", doc);
//    }

    @Async
    @EventListener
    @Transactional(rollbackFor = Exception.class)
    public void onApplicationEvent(UserFollowEvent event) {
        // 优化：冗余字段 代替count(*)
        // +关注：自己关注+1，对方粉丝+1; 取消关注：自己关注-1，对方粉丝-1
        int increase = event.getIncrease() ? 1 : -1;
        userMapper.increaseFollowCount(event.getUserId(), increase);
        userMapper.increaseFansCount(event.getBloggerId(), increase);
    }
}
