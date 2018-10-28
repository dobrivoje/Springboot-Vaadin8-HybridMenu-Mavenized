package system.eventbus;

import com.google.common.eventbus.EventBus;

@Deprecated
public class EventsSingleton {

    //<editor-fold defaultstate="collapsed" desc="singleton infra">
    private static final EventBus eventBus = new EventBus();

    public static EventsSingleton getInstance() {
        return EventsHolder.INSTANCE;
    }

    private static class EventsHolder {

        private static final EventsSingleton INSTANCE = new EventsSingleton();
    }
    //</editor-fold>

    public static void register(Object listener) {
        eventBus.register(listener);
    }

    public static void post(Object event) {
        eventBus.post(event);
    }

    //<editor-fold defaultstate="collapsed" desc="EventsSingleton">
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
