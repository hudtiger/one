package test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class testSSH {
	public static void main(String[] args) throws IOException, JSchException {
		// TODO Auto-generated method stub
		  String host = "192.168.56.102";
		    int port = 22;
		    String user = "root";
		    String password = "123456";
		    String command = "docker images";
		new SSHWapper(host,port,user,password).connect()
		.exec(command, msg->System.out.println(msg))
		.exec("ls", msg->System.out.println(msg))
		.close();
	}
}

class SSHWapper{
	Session session;
	public SSHWapper(String host, int port, String user, String password) throws JSchException {
		JSch jsch = new JSch();
		session = jsch.getSession(user, host, port);
		session.setConfig("StrictHostKeyChecking", "no");
		// java.util.Properties config = new java.util.Properties();
		// config.put("StrictHostKeyChecking", "no");
		session.setPassword(password);
	}
	public SSHWapper connect() throws JSchException {
		session.connect();
		return this;
	}
	public SSHWapper exec(String command,tracert tracert) throws JSchException, IOException {
		ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
		InputStream in = channelExec.getInputStream();
		channelExec.setCommand(command);
		channelExec.setErrStream(System.err);
		channelExec.connect();

		String out = IOUtils.toString(in, "UTF-8");
		channelExec.disconnect();
		if(tracert!=null) {
			tracert.trace(out);
		}
		return this;
	}
	public void close() {
		this.session.disconnect();
	}
}

interface tracert{
	void trace(String message);
}