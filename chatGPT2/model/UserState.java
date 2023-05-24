package chatGPT2.model;

public class UserState {

    public final static int NOT_LOGIN = 0;
    public final static int LOGGED = 1;
    public final static int CONNECTED = 2;
    public int currentState = 0;

    public UserState() {
        currentState = 0;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

}
