package delightex.client.model;

public class Message {
  private final User myUser;
  private final long myStamp;
  private final String myText;

  public Message(User user, String text) {
    myUser = user;
    myText = text;
    myStamp = System.currentTimeMillis();
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
