/** 
 * Implementation of a queue simulator using DTA/DTB scheduling
 *
 **/

import org.apache.commons.math3.distribution.*;
import java.util.*;

public class simulate{

	// An example usage of the simulator
	public static void main(String[] args) throws Exception {
		AbstractRealDistribution mu = new ParetoDistribution(1.0, 1.0);
		AbstractRealDistribution lambda = new ExponentialDistribution(1.25);
		List<Double> arrivals = new ArrayList<>();
		List<Double> sizes = new ArrayList<>();
		for (int i = 0; i < 10000000; i++) {
			arrivals.add((lambda.sample()));
			sizes.add(mu.sample());
		}

		QueueSimulator fcfs = new FCFSQueueSimulator();
		fcfs.setCutOff(10.0);

		QueueSimulator dta = new DTAQueueSimulator();
		dta.setCutOff(10.0);

		QueueSimulator dtb = new DTBQueueSimulator();
		dtb.setCutOff(10.0);
		
		fcfs.simulate(arrivals, sizes);
		System.out.println("FCFS Drop rate = " + fcfs.getDropRate());
		System.out.println("FCFS mrt = " + fcfs.getMeanResponseTime());

		dta.simulate(arrivals, sizes);
		System.out.println("DTA Drop rate = " + dta.getDropRate());
		System.out.println("DTA mrt = " + dta.getMeanResponseTime());

		dtb.simulate(arrivals, sizes);
		System.out.println("DTB Drop rate = " + dtb.getDropRate());
		System.out.println("DTB mrt = " + dtb.getMeanResponseTime());
	}
}