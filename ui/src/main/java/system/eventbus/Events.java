package system.eventbus;

import com.google.common.eventbus.EventBus;
import org.springframework.stereotype.Service;

@Service
public class Events {

    //<editor-fold defaultstate="collapsed" desc="singleton infra">
    private final EventBus eventBus = new EventBus();

    public void register(Object listener) {
        eventBus.register(listener);
    }

    public void post(Object event) {
        eventBus.post(event);
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

    public static final class LoginSuccessEvent {
    }

    public static final class MainMenuClickEvent {
    }
    //</editor-fold>
}
