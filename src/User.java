import java.io.DataOutputStream;
import java.net.Socket;

public class User {

    Socket userSocket;
    String to_authNum;

    DataOutputStream output;

    public User(Socket userSocket,String to_authNum, DataOutputStream output) {
        this.userSocket = userSocket;
        this.to_authNum = to_authNum;
        this.output = output;
    }

    public Socket getUserSocket() {
        return userSocket;
    }

    public void setUserSocket(Socket userSocket) {
        this.userSocket = userSocket;
    }

    public String getTo_authNum() {
        return to_authNum;
    }

    public void setTo_authNum(String to_authNum) {
        this.to_authNum = to_authNum;
    }


    public DataOutputStream getOutput() {
        return output;
    }

    public void setOutput(DataOutputStream output) {
        this.output = output;
    }
}
