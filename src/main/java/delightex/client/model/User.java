package delightex.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User {
  private String myName;

  public String getName() {
    return myName;
  }

  public void setName(String name) {
    myName = name;
  }
}
