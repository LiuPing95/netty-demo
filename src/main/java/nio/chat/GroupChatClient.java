package nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @Author liu ping
 * @Version 1.x.0
 * @Description
 * @Date 2020/1/10 8:34 PM
 **/
public class GroupChatClient {

	
	private String apple;
	private Selector selector;
	private SocketChannel socketChannel;
	private String ip = "127.0.0.1";
	private int port = 8081;
	private String username;

	public GroupChatClient() throws IOException {
//        selector      = Selector.open();
		socketChannel = SocketChannel.open(new InetSocketAddress(ip, port));
//        socketChannel.configureBlocking(false);
//        socketChannel.register(selector, SelectionKey.OP_READ);
		username = socketChannel.getLocalAddress().toString();
		System.out.println(username + "is OK ...");
	}

	public void sendMsg(String msg) throws IOException {
//        System.out.println(username + " 说：" + msg);
		socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
	}

	public void readMsg() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		int read = 0;
		try {
			read = socketChannel.read(byteBuffer);
		} catch (IOException e) {
			System.out.println("没有读到消息");
		}
		if (read > 0) {
			System.out.println(new String(byteBuffer.array()));
		}
		/*
		 * int count = selector.select(3000); if (count > 0) { Iterator<SelectionKey>
		 * iterator = selector.keys().iterator(); while (iterator.hasNext()) {
		 * SelectionKey key = iterator.next(); if (key.isReadable()) { SocketChannel
		 * socketChannel = (SocketChannel) key.channel(); ByteBuffer byteBuffer =
		 * ByteBuffer.allocate(1024); int read = socketChannel.read(byteBuffer); if
		 * (read > 0) { System.out.println(new String(byteBuffer.array())); } } } }
		 */
	}

	public static void main(String[] args) throws IOException {
		GroupChatClient client = new GroupChatClient();

		new Thread(() -> {

			while (true) {
				client.readMsg();
			}

		}).start();

		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String msg = scanner.nextLine();
			client.sendMsg(msg);
		}
	}
}
