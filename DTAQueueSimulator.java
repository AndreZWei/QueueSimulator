/** DTA queue simulator
 *
 */

import org.apache.commons.math3.distribution.*;
import java.util.*;
import java.lang.*;

public class DTAQueueSimulator extends QueueSimulator {
	public DTAQueueSimulator(AbstractRealDistribution jobSize, AbstractRealDistribution arrivalRate) {
		this.jobSize = jobSize;
		this.arrivalRate = arrivalRate;
	}

	public DTAQueueSimulator(){}

	public void simulate(int n) {
		double work = 0;
		double totalResponseTime = 0;
		int drops = 0;
		for (int i = 0; i < n; i++) {
			double arrivalTime = arrivalRate.sample();
			double size = jobSize.sample();
			work = Math.max(work - arrivalTime, 0) + size;
			if (cutOffTime != null && work > cutOffTime) {
				// drop the new arrival
				work -= size;
				drops++;
			} else {
				totalResponseTime += work;
			}
		}
		dropRate = drops * 1.0 / n;
		meanResponseTime = totalResponseTime / (n - drops);
	}

	public void simulate(List<Double> arrivalTimes, List<Double> jobSizes) {
		double work = 0;
		double totalResponseTime = 0;
		int drops = 0;
		int n = arrivalTimes.size();
		for (int i = 0; i < n; i++) {
			double arrivalTime = arrivalTimes.get(i);
			double size = jobSizes.get(i);
			work = Math.max(work - arrivalTime, 0) + size;
			if (cutOffTime != null && work > cutOffTime) {
				// drop the new arrival
				work -= size;
				drops++;
			} else {
				totalResponseTime += work;
			}
		}
		dropRate = drops * 1.0 / n;
		meanResponseTime = totalResponseTime / (n - drops);
	}
}