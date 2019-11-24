/** FCFS queue simulator
 *
 */

import org.apache.commons.math3.distribution.*;
import java.util.*;
import java.lang.*;

public class FCFSQueueSimulator extends QueueSimulator {
	public FCFSQueueSimulator(AbstractRealDistribution jobSize, AbstractRealDistribution arrivalRate) {
		this.jobSize = jobSize;
		this.arrivalRate = arrivalRate;
	}

	public FCFSQueueSimulator(){}

	public void simulate(int n) {
		double work = 0;
		double totalResponseTime = 0;
		int drops = 0;
		for (int i = 0; i < n; i++) {
			double arrivalTime = arrivalRate.sample();
			double size = jobSize.sample();
			work = Math.max(work - arrivalTime, 0) + size;
			double responseTime = work;
			if (cutOffTime != null && responseTime > cutOffTime) {
				drops++;
			}
			totalResponseTime += responseTime;
		}
		dropRate = drops * 1.0 / n;
		meanResponseTime = totalResponseTime / n;
	}

	public void simulate(List<Double> arrivalTimes, List<Double> jobSizes) {
		double work = 0;
		double totalResponseTime = 0;
		int drops = 0;
		int n = arrivalTimes.size();
		for (int i = 0; i < n; i++) {
			work = Math.max(work - arrivalTimes.get(i), 0) + jobSizes.get(i);
			double responseTime = work;
			if (cutOffTime != null && responseTime > cutOffTime) {
				drops++;
			}
			totalResponseTime += responseTime;
		}
		dropRate = drops * 1.0 / n;
		meanResponseTime = totalResponseTime / n;
	}
}