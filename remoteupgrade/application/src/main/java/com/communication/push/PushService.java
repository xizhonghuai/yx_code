/**
 * 
 */
package com.communication.push;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author xizhonghuai
 * @date 2018��7��23��
 * @readme
 */
public class PushService extends Thread {

	private String url;
	private String msg;

	/**
	 * @param url
	 * @param msg
	 */
	public PushService(String url, String msg) {
		super();
		this.url = url;
		this.msg = msg;
	}

	private String postCall() throws IOException {

		URL url = new URL(this.url);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Encoding", "UTF-8");
		connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		connection.connect();
		// POST����
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.write(this.msg.getBytes("UTF-8"));

		out.flush();
		out.close();
		// ��ȡ��Ӧ
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String lines;
		StringBuffer sb = new StringBuffer("");
		while ((lines = reader.readLine()) != null) {
			lines = new String(lines.getBytes(), "utf-8");
			sb.append(lines);
		}

		reader.close();
		// �Ͽ�����
		connection.disconnect();

		return sb.toString();
	}

	public void run() {

		try {

			postCall();

		} catch (IOException e) {
			// TODO Auto-generated catch block

			System.out.println(e.toString());
		}
	}

}
