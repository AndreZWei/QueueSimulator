/** A stub class for a queue simulator
 *
 */

import org.apache.commons.math3.distribution.*;
import java.util.*;
import java.lang.*;

public abstract class QueueSimulator {
	protected AbstractRealDistribution jobSize;
	protected AbstractRealDistribution arrivalRate;
	// The fraction of jobs with response time greater than cut off
	protected Integer jobs;
	protected Integer drops;
	protected Double totalResponseTime;
	protected Double cutOffTime;

	public abstract void init();
	public abstract void simulateStep(Double arrivalTime, Double size);
	
	public void simulate(int n) {
		for (int i = 0; i < n; i++) {
			simulateStep(arrivalRate.sample(), jobSize.sample());
		}
	}

	public void simulate(List<Double> arrivalTimes, List<Double> jobSizes) {
		int n = arrivalTimes.size();
		for (int i = 0; i < n; i++) {
			simulateStep(arrivalTimes.get(i), jobSizes.get(i));
		}
	}

	public double getDropRate() throws Exception {
		if (drops == null) {
			throw new Exception("Simulation not run yet");
		}
		return drops * 1.0 / jobs;
	}

	public double getMeanResponseTime() throws Exception {
		if (totalResponseTime == null) {
			throw new Exception("Simulation not run yet");
		}
		if (this instanceof FCFSQueueSimulator) {
			return totalResponseTime / jobs;
		}
		return totalResponseTime / (jobs - drops);
	}

	public void setCutOff(double t) {
		this.cutOffTime = t;
	}

}