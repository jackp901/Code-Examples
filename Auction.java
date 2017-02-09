import java.util.*;
import java.util.Map.Entry;

public class Auction {

	/**
	 * auction determines an optimal assignment of agents to objects such that the
	 * sum of the values of agent-object pairs is maximized.  The algorithm does this
	 * by having agents "bid" on objects until all agents are paired. This ensures
	 * an optimal solution within epsilon. (if values are integers, solution will be 
	 * globally optimal)
	 * @param arr: Two dimensional array of values for each agent-object pair
	 * @return Map of agent-object pairs in optimal solution
	 */
	public static Map<Integer, Integer> auction(int[][] arr) {
		int size = arr[0].length;
		double epsilon = 1.0/(size + 1);
		double[] prices = new double[size];
		boolean[] assigned = new boolean[size];
		Map<Integer, Integer> pairs = new HashMap<Integer, Integer>();
		int currentAgent = -1;
		
		//find an unpaired agent
		while(pairs.size() < size) {
			for(int i = 0; i < size; i++) {
				if(!assigned[i]) {
					currentAgent = i;
					break;
				}
			}
			double[][] max = getMax(prices, arr[currentAgent]);
			//change the price of the object the agent bid on (add epsilon to avoid issues with ties)
			prices[(int) max[0][0]] = prices[(int) max[0][0]] + (max[1][0] - max[1][1]) + epsilon;
			Iterator it = pairs.entrySet().iterator();
			//delete any agent-object pairing that is already paired with the selected object
			while(it.hasNext()) {
				Map.Entry<Integer, Integer> pair = (Map.Entry)it.next();
				if(pair.getValue() == max[0][0]) {
					pairs.remove(pair.getKey());
					assigned[pair.getKey()] = false;
					break;
				}
			}
			
			assigned[currentAgent] = true;
			pairs.put(currentAgent, (int) max[0][0]);
		}
		
		return pairs;
	}
	
	
	/**
	 * getMax finds the values and indices of the largest and second largest benefits
	 * to the current agent. A benefit is calculated by subtracting the value of that
	 * object from the price
	 * @param prices the current prices of each object
	 * @param values the values the current agent holds for each object
	 * @return a two-d array holding the indices and values of the max and second largest values
	 */
	public static double[][] getMax(double[] prices, int[] values) {
		double max = Integer.MIN_VALUE;
		double secondMax = Integer.MIN_VALUE;
		int maxIndex = -1;
		int secondMaxIndex = -1;
		for(int i = 0; i < prices.length; i++) {
			double benefit = values[i] - prices[i];
			if(benefit > max) {
				secondMax = max;
				secondMaxIndex = maxIndex;
				max = benefit;
				maxIndex = i;
			} else {
				if(benefit > secondMax) {
					secondMax = benefit;
					secondMaxIndex = i;
				}
			}
		}
		return new double[][] {
			{maxIndex, secondMaxIndex}, 
			{max, secondMax}
		};
	}
	
	
	/**
	 * generate a random assignment problem
	 * @param n: the number of agents/objects in the problem
	 * @param m: the maximum value of each pairing (values range from 0 through m-1)
	 * @return the two dimensional array of the random assignment problem
	 */
	public static int[][] generateRandomAssignment(int n, int m) {
		int[][] rand = new int[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				rand[i][j] = (int)(Math.random()*m);
			}
		}
		return rand;
	}

}
