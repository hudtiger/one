package test.Netty;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class ReplayDefender{
	String clientId;
	Object content;
	int counter;
	final static int MAXSIZE = 3; //最大重播次数
	static Map<String,ReplayDefender> monitor = null;
	static {
		if(monitor == null) {
			monitor = new HashMap<>();
		}
	}
	static Boolean checkAllow(Channel channel, Object msg) {
		String clientId = channel.id().asShortText();
		if(!monitor.containsKey(clientId)) {
			monitor.put(clientId, new ReplayDefender(clientId,msg,1));
			return true;
		} else {
			ReplayDefender currDefender = monitor.get(clientId);
			if(!msg.equals(currDefender.getContent())) {
				currDefender.setContent(msg);
				currDefender.setCounter(1);
				return true;
			}else if(currDefender.getCounter()<MAXSIZE){
				currDefender.setCounter(currDefender.getCounter()+1);
				return true;
			}else {
				return false;
			}
		}
	}
}
