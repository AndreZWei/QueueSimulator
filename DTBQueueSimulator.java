/** DTB queue simulator
 *
 */

import org.apache.commons.math3.distribution.*;
import java.util.*;
import java.lang.*;

public class DTBQueueSimulator extends QueueSimulator {
	class Job {
		int id;
		double arrivalTime;
		double size;

		public Job(int id, double arrivalTime, double size) {
			this.id = id;
			this.arrivalTime = arrivalTime;
			this.size = size;
		}

		public String toString() {
			return String.format("%d arrival: %f, size: %f\n", id, arrivalTime, size);
		}
	}

	public DTBQueueSimulator(AbstractRealDistribution jobSize, AbstractRealDistribution arrivalRate) {
		this.jobSize = jobSize;
		this.arrivalRate = arrivalRate;
	}

	public DTBQueueSimulator(){}

	public void simulate(int n) {
		Deque<Job> queue = new LinkedList<>();
		double time = 0;
		double totalResponseTime = 0;
		int drops = 0;
		for (int i = 0; i < n; i++) {
			double arrivalTime = arrivalRate.sample();
			double size = jobSize.sample();
			// Finish the jobs in the queue
			while (arrivalTime > 0) {
				if (queue.isEmpty()) {
					time += arrivalTime;
					arrivalTime = 0;
				} else {
					Job jobInService = queue.peek();
					if (jobInService.size < arrivalTime) {
						arrivalTime -= jobInService.size;
						time += jobInService.size;
						totalResponseTime += (time - jobInService.arrivalTime);
						queue.poll();
					} else {
						jobInService.size -= arrivalTime;
						time += arrivalTime;
						arrivalTime = 0;
					}
				}
			}
			// Check total work in queue
			Job newJob = new Job(i, time, size);
			queue.offer(newJob);
			double work = 0;
			Job maxJob = newJob;
			for (Job job: queue) {
				if (job.size > maxJob.size) {
					maxJob = job;
				}
				work += job.size;
			}

			if (cutOffTime != null && work > cutOffTime) {
				drops++;
				queue.remove(maxJob);
			}
		}
		while (!queue.isEmpty()) {
			Job job = queue.poll();
			time += job.size;
			totalResponseTime += (time - job.arrivalTime);
		}
		dropRate = drops * 1.0 / n;
		meanResponseTime = totalResponseTime / (n - drops);
	}

	public void simulate(List<Double> arrivalTimes, List<Double> jobSizes) {
		int n = arrivalTimes.size();
		Deque<Job> queue = new LinkedList<>();
		double time = 0;
		double totalResponseTime = 0;
		int drops = 0;
		for (int i = 0; i < n; i++) {
			double arrivalTime = arrivalTimes.get(i);
			double size = jobSizes.get(i);
			// Finish the jobs in the queue
			while (arrivalTime > 0) {
				if (queue.isEmpty()) {
					time += arrivalTime;
					arrivalTime = 0;
				} else {
					Job jobInService = queue.peek();
					if (jobInService.size < arrivalTime) {
						arrivalTime -= jobInService.size;
						time += jobInService.size;
						totalResponseTime += (time - jobInService.arrivalTime);
						queue.poll();
					} else {
						jobInService.size -= arrivalTime;
						time += arrivalTime;
						arrivalTime = 0;
					}
				}
			}
			// Check total work in queue
			Job newJob = new Job(i, time, size);
			queue.offer(newJob);
			double work = 0;
			Job maxJob = newJob;
			for (Job job: queue) {
				if (job.size > maxJob.size) {
					maxJob = job;
				}
				work += job.size;
			}

			if (cutOffTime != null && work > cutOffTime) {
				drops++;
				queue.remove(maxJob);
			}
		}
		while (!queue.isEmpty()) {
			Job job = queue.poll();
			time += job.size;
			totalResponseTime += (time - job.arrivalTime);
		}
		dropRate = drops * 1.0 / n;
		meanResponseTime = totalResponseTime / (n - drops);
	}
}