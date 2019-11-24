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
	protected Double dropRate;
	protected Double meanResponseTime;
	protected Double cutOffTime;

	public abstract void simulate(int n);
	public abstract void simulate(List<Double> arrivalTimes, List<Double> jobSizes);

	public double getDropRate() throws Exception {
		if (dropRate == null) {
			throw new Exception("Simulation not run yet");
		}
		return dropRate;
	}

	public double getMeanResponseTime() throws Exception {
		if (meanResponseTime == null) {
			throw new Exception("Simulation not run yet");
		}
		return meanResponseTime;
	}

	public void setCutOff(double t) {
		this.cutOffTime = t;
	}

}