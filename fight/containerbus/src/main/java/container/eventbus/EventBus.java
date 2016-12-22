package container.eventbus;

import java.lang.reflect.Method;

/**
 * Created by 58 on 2016/12/20.
 */

public class EventBus {
    static
    public void register(Object subsciber) {
        //从subscriber获取以onEvent开头的method
        Class subscriberClass = subsciber.getClass();
       Method[] subscriberMethod= subscriberClass.getMethods();
        for(Method method:subscriberMethod){

        }
    }
//    subscribe(Object ){
//
//    }

}
