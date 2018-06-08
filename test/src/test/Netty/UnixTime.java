package test.Netty;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class UnixTime implements Serializable {

    private final long value;
    private final int locale;
    
    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L,2046);
    }
        
    @Override
	public String toString() {
		return "UnixTime [value=" + value + ", locale=" + locale + "]";
	}
}