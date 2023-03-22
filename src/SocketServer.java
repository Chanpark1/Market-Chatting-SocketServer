import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer {

    public static final int PORT = 4462;

    public static ArrayList<User> list;

    //       순서
//        에코서버 ( 자바 이클립스 )
//        1) 접속할 포트와 소켓을 열어준다.  ( 무한 반복 )
//        2) 접속한 소캣의 정보를 저장해둔다.
//        3) 저장 한 소캣의 입력 출력 스트림을 열어준다   ( 위의 무한 반복 안에 생성 )
//        ( 메세지 송 수신 관련 스트림입니다.  DataInputStream 과 DataOutputStream 를 사용합니다.    다른것도 사용할 수 있습니다.  )
//        4) 메세지를 수신할 스레드를 작성한다.
//        5) 수신한 메세지를 처리하는 로직을 짠다  ( ex 방나누기  1:1 채팅 그룹채팅 등을 구분할수 있는 구분자들 )
//        6) 보내고 싶은 상대방 쪽 스트림에다가 데이터를 전송한다.

    public static void main (String [] args) {
        DataOutputStream out;
        DataInputStream in;
        list = new ArrayList<User>();
        System.out.println("User List 생성됨");



        try {
            ServerSocket s_socket = new ServerSocket(PORT); // 서버 소켓 생성
            System.out.println("서버 소켓 생성 : 접속 대기중"); // -> 에코서버가 동작하면 생성된다.

            while(true) {
                // 채팅창에 들어갔을 때 호출됨.
                Socket c_socket = s_socket.accept();//1
                // Server Socket 이 클라이언트의 연결 요청을 accept 함으로서 새로 생성되는 소켓. 이 소켓을 통해 데이터를 클라이언트로 박아준다.
                System.out.println("socket accepted ");

                ClientManagerThread c_thread = new ClientManagerThread();

                c_thread.setSocket(c_socket);
                System.out.println("socket set");

                try {

                    c_thread.start();
                    System.out.println("thread start");


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch(IOException e) {
            e.printStackTrace();

        }
    }

}
