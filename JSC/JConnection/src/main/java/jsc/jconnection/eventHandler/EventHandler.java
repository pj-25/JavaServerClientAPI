package jsc.jconnection.eventHandler;

public abstract class EventHandler implements CustomEvent {

    private boolean isEnabled = true;
    private Object eventLock = new Object();

    public EventHandler(boolean enableStatus){
        this.isEnabled = enableStatus;
    }

    public boolean isEnabled(){
        return isEnabled;
    }

    public void setEnabled(boolean enableStatus){
        this.isEnabled = enableStatus;
    }

    public void run() {
        synchronized (eventLock) {
            while (isEnabled) {
                try {
                    eventLock.wait();
                    onEvent();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
