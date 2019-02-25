package system.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class Events implements SubscriberExceptionHandler {

    private final EventBus BUS = new EventBus(this);

    public void register(Object listener) {
        BUS.register(listener);
    }

    public void post(Object event) {
        BUS.post(event);
    }

    @Override
    public void handleException(Throwable thrwbl, SubscriberExceptionContext sec) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //<editor-fold defaultstate="collapsed" desc="Events">
    public static final class LoginTryEvent {

        private String username;

        public LoginTryEvent(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static final class MainMenuClickEvent {
    }
    //</editor-fold>
}
