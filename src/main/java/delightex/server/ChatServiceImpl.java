package delightex.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import delightex.client.rpc.ChatService;
import delightex.client.model.User;
import delightex.server.model.Model;

import java.util.Set;

public class ChatServiceImpl extends RemoteServiceServlet implements ChatService {

    @Override
    public Set<String> getRooms() {
        Model model = (Model) getServletContext().getAttribute(ChatWebSocketServlet.MODEL_KEY);
        return model.getRoomNames();
    }

    @Override
    public void login(String name) {
        User user = new User(name);
        getThreadLocalRequest().getSession().setAttribute(ChatWebSocketServlet.USER_KEY, user);
    }

    @Override
    public String addRoom(String name) {
        Model model = (Model) getServletContext().getAttribute(ChatWebSocketServlet.MODEL_KEY);
        if (model.getRoom(name) != null) return "Duplicate chat name";
        model.addRoom(name);
        return null;
    }
}