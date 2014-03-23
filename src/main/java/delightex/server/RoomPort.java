package delightex.server;

import delightex.client.model.Message;
import delightex.client.model.User;

public interface RoomPort {
    void send(Message message);

    User getUser();
}
