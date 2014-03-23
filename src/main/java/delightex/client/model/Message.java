package delightex.client.model;

public class Message {
    private final User myUser;
    private final long myStamp;
    private final String myText;

    public Message(User user, String text) {
        this(user, text, System.currentTimeMillis());
    }

    public Message(User user, String text, long stamp) {
        myUser = user;
        myText = text;
        myStamp = stamp;
    }

    public User getUser() {
        return myUser;
    }

    public long getStamp() {
        return myStamp;
    }

    public String getText() {
        return myText;
    }
}
