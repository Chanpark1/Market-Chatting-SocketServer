import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientManagerThread extends Thread{

    private Socket m_socket;
    boolean is = true;
    @Override
    public void run(){
        super.run();
        try{
            DataInputStream in = new DataInputStream(m_socket.getInputStream());
            DataOutputStream out = new DataOutputStream(m_socket.getOutputStream());
            String text;

            while(true){
                text = in.readUTF();
                System.out.println("Thread에서 받은 텍스트 : " + text);
                // ^이게 클라이언트에서 보내준 값을 에코서버단에서 읽어주는 거
                String[] filt = text.split("@");
                // filt 문자열 배열을 만들어서 @ 단위로 스플릿 해줌. 이 배열이 식별자 역할을 수행하게 된다.

                if(text!=null) {

                    if(filt[1].equals("auth")) {
                            SocketServer.list.add(new User(m_socket,filt[0],out));
                            System.out.println("User List 추가됨");
                            System.out.println(filt[0] + " : to_auth 추가됨");
                            System.out.println("Total list Size 리스트 사이즈 0일때: " + SocketServer.list.size());
                    } else if (filt[2].equals("room_auth")) {
                        for(int i = 0; i < SocketServer.list.size(); i++) {
                            if(filt[0].equals(SocketServer.list.get(i).getTo_authNum())) {

                                try {
                                    OutputStream os = SocketServer.list.get(i).getOutput();

                                    DataOutputStream dos = new DataOutputStream(os);
                                    dos.writeUTF(text);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    } else {
                        for(int i=0;i<SocketServer.list.size();i++){

                                System.out.println("메시지 최초 전송 스레드 아웃풋 반복문 시작");
                                // 보내주는 유저의 수만큼 호출이 됨. 그니까 SocketServer.m_OutputList 의 인덱스 수 만큼 반복해서 보내준다.
                                // 방을 나누려면 여기서 조건문 걸어서 나눠줘야 한
                                try {
                                    System.out.println("보내주는 거임 : " + filt[0] + "-> " + SocketServer.list.get(i).getTo_authNum());

                                    OutputStream dos = SocketServer.list.get(i).getOutput();
                                    DataOutputStream outs = new DataOutputStream(dos);
                                    outs.writeUTF(text);
                                    System.out.println(filt[0]+ "에게 " + filt[1] + " 전송됨" );
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                        }
                    }


                }
            }

        }catch(IOException e){
            e.printStackTrace();
            System.out.println("스레드 IOException 발동");
        }
    }
    public void setSocket(Socket _socket){
        m_socket = _socket;
    }
}
