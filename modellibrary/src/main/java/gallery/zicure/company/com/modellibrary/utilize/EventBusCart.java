package gallery.zicure.company.com.modellibrary.utilize;

import com.squareup.otto.Bus;

/**
 * Created by BallOmO on 10/13/2016 AD.
 */
public class EventBusCart {
    private static EventBusCart me = null;
    private Bus eventBus;
    private EventBusCart(){
        eventBus = new Bus();
    }

    public static EventBusCart getInstance(){
        if (me == null){
            me = new EventBusCart();
        }
        return me;
    }

    public Bus getEventBus(){
        return eventBus;
    }
}
