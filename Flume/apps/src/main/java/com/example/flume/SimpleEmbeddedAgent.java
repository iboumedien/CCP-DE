import org.apache.flume.agent.embedded.EmbeddedAgent;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.EventDeliveryException;
import java.nio.charset.Charset;
import com.google.common.collect.Lists;
import java.util.*;
public class SimpleEmbeddedAgent {

	public static void main(String[] args) throws EventDeliveryException {

		Map<String, String> properties = new HashMap<String, String>();
		//properties for Embedded Agent
		properties.put("channel.type", "memory");
		properties.put("channel.capacity", "100");
		properties.put("sinks", "sink1");
		properties.put("sink1.type", "avro");
		properties.put("sink1.hostname", "localhost");
		properties.put("sink1.port", "41414");

		EmbeddedAgent ea = new EmbeddedAgent("SimpleEmbeddedAgent");

		//Configure and start agent
		ea.configure(properties);
		System.out.println("******* Starting Agent");
		ea.start();

		List<Event> events = Lists.newArrayList();

		String data = "Hello Flume!!";

		for (int i = 0; i < 10 ; i++ ) {
			Event event = EventBuilder.withBody(data, Charset.forName("UTF-8"));
			events.add(event);
		}
		//Send Data
		System.out.println("Sending data ...");
		ea.putAll(events);
		//Stop Agent
		System.out.println("******* Stopping Agent");
		ea.stop();
	}
}
