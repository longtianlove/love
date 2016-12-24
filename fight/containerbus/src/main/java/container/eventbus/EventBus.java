package container.eventbus;

import java.lang.reflect.Method;

/**
 * Created by 58 on 2016/12/20.
 */

public class EventBus {
    public static volatile EventBus instance = null;

    private EventBus() {
    }

    public static EventBus getInstance() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    public void register(Object subsciber) {
        //从subscriber获取以onEvent开头的method
//        Class subscriberClass = subsciber.getClass();
//        Method[] subscriberMethod = subscriberClass.getMethods();
//        for (Method method : subscriberMethod) {
//
//        }
        subscribe(subsciber);
    }
    void subscribe(Object subscriber){
        Method[] methods=subscriber.getClass().getMethods();
        for(Method method:methods){

        }
    }

}
