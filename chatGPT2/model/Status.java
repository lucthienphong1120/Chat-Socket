package chatGPT2.model;

public class Status {

    public final static int NOT_LOGIN = -1;
    public final static int PREPARE = 0;
    public final static int CONNECTED = 1;
    private int currentState = 0;

    public Status() {
        currentState = -1;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

}
