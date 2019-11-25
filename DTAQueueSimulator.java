/** DTA queue simulator
 *
 */

import org.apache.commons.math3.distribution.*;
import java.util.*;
import java.lang.*;

public class DTAQueueSimulator extends QueueSimulator {
	private double work;

	public DTAQueueSimulator(AbstractRealDistribution jobSize, AbstractRealDistribution arrivalRate) {
		this.jobSize = jobSize;
		this.arrivalRate = arrivalRate;
	}

	public DTAQueueSimulator(){}

	public void init() {
		this.work = 0.0;
		this.jobs = 0;
		this.drops = 0;
		this.totalResponseTime = 0.0;
	}

	public void simulateStep(Double arrivalTime, Double size) {
		jobs++;
		work = Math.max(work - arrivalTime, 0) + size;
		if (cutOffTime != null && work > cutOffTime) {
			// drop the new arrival
			work -= size;
			drops++;
		} else {
			totalResponseTime += work;
		}
	}
}