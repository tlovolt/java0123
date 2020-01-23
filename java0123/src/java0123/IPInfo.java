package java0123;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPInfo {

	public static void main(String[] args) {
		
		try {
			//�ڽ��� ��ǻ�� IP ���� Ȯ�� - �ڽ��� ��ǻ�� �̸�(������) �� IP �ּ�
			InetAddress local = InetAddress.getLocalHost();
			System.out.println(local);
			//������ IP ���� Ȯ��
			InetAddress [] googles = InetAddress.getAllByName("www.daum.net");
			for(InetAddress imsi : googles) {
				System.out.println(imsi);
			}
		} catch (UnknownHostException e) {
			System.out.println("����:" + e.getMessage());
		}

	}

}
