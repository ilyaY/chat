package delightex.client.model;

public class Room {
    private final String myName;
    private volatile long myLastActiveStamp;

    public Room(String name) {
        myName = name;
        activate();
    }

    public void activate() {
        myLastActiveStamp = System.currentTimeMillis();
    }

    public String getName() {
        return myName;
    }

    public long getStamp() {
        return myLastActiveStamp;
    }
}
