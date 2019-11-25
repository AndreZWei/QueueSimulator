/** 
 * Implementation of a queue simulator using DTA/DTB scheduling
 *
 **/

import org.apache.commons.math3.distribution.*;
import java.util.*;

public class simulate{

	// An example usage of the simulator
	public static void main(String[] args) throws Exception {
		AbstractRealDistribution mu = new ExponentialDistribution(1.0);
		AbstractRealDistribution lambda = new ExponentialDistribution(1.25);
		
		List<Double> arrivals = new ArrayList<>();
		List<Double> sizes = new ArrayList<>();
		for (int i = 0; i < 1000000; i++) {
			arrivals.add((lambda.sample()));
			sizes.add(mu.sample());
		}

		QueueSimulator fcfs = new FCFSQueueSimulator();
		fcfs.init();
		fcfs.setCutOff(10.0);
		fcfs.simulate(arrivals, sizes);
		System.out.println("Drop rate = " + fcfs.getDropRate());
		System.out.println("mrt = " + fcfs.getMeanResponseTime());

		QueueSimulator dta = new DTAQueueSimulator();
		dta.init();
		dta.setCutOff(10.0);
		dta.simulate(arrivals, sizes);
		System.out.println("Drop rate = " + dta.getDropRate());
		System.out.println("mrt = " + dta.getMeanResponseTime());

		QueueSimulator dtb = new DTBQueueSimulator();
		dtb.init();
		dtb.setCutOff(10.0);
		dtb.simulate(arrivals, sizes);
		System.out.println("Drop rate = " + dtb.getDropRate());
		System.out.println("mrt = " + dtb.getMeanResponseTime());
	}
}