package system.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
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
