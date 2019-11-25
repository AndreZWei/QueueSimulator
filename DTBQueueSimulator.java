/** DTB queue simulator
 *
 */

import org.apache.commons.math3.distribution.*;
import java.util.*;
import java.lang.*;

public class DTBQueueSimulator extends QueueSimulator {
	private Deque<Job> queue;
	private double time;
	private int index;

	class Job {
		int id;
		double arrivalTime;
		double size;

		public Job(double arrivalTime, double size) {
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

	public void init() {
		this.queue = new LinkedList<Job>();
		this.time = 0.0;
		this.jobs = 0;
		this.drops = 0;
		this.totalResponseTime = 0.0;
	}

	public void simulateStep(Double arrivalTime, Double size) {
		jobs++;
		while (arrivalTime > 0) {
			if (queue.isEmpty()) {
				time += arrivalTime;
				arrivalTime = 0.0;
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
					arrivalTime = 0.0;
				}
			}
		}
		// Check total work in queue
		Job newJob = new Job(time, size);
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

	@Override
	public void simulate(int n) {
		for (int i = 0; i < n; i++) {
			simulateStep(arrivalRate.sample(), jobSize.sample());
		}
		while (!queue.isEmpty()) {
			Job job = queue.poll();
			time += job.size;
			totalResponseTime += (time - job.arrivalTime);
		}
	}

	@Override
	public void simulate(List<Double> arrivalTimes, List<Double> jobSizes) {
		int n = arrivalTimes.size();
		for (int i = 0; i < n; i++) {
			simulateStep(arrivalTimes.get(i), jobSizes.get(i));
		}
		while (!queue.isEmpty()) {
			Job job = queue.poll();
			time += job.size;
			totalResponseTime += (time - job.arrivalTime);
		}
	}
}