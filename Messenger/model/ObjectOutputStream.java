package Messenger.model;

import java.io.IOException;
import java.io.Serializable;
public class ObjectOutputStream implements Serializable {
    private transient ObjectOutputStream objectOutputStream;

    public ObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.objectOutputStream = new ObjectOutputStream(objectOutputStream);
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }
}
