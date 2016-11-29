package com.BYSJ;

import java.util.ArrayList;
import java.util.Random;

public class CrossTwoArray {
	public static int[] crossArray(int[] firstArr, int[] secondArr, int position) {
		if (position >= firstArr.length) {
			 return firstArr;
		}
		else if (position <= 0) {
			return secondArr;
		}
		else {
			ArrayList<Object> first = new ArrayList<Object>();
			for (int pos = 0; pos < position; pos++) {
				first.add(firstArr[pos]);
			}
			for (int pos = position; pos <secondArr.length; pos++) {
				first.add(secondArr[pos]);
			}
			Object[] arr = first.toArray();
			int[] arrInt = new int[arr.length];
			for (int i = 0; i < arr.length; i++) {
				arrInt[i] = (int)arr[i];
	        }
			return arrInt;
		}		
	}
//	public static void main(String[] args) {
//		Random random = new Random();
//		Range range = new Range();
//		int chromosomeLength = 6,
//		chromosomeHigh = 3;
//		int [] a = {1,2,3,4,5,6};
//		int [] b = {34,35,36,37,38,39};
//		int[] c = CrossTwoArray.crossArray(a, b, 7);
//
//	    int[] tempLine1 = new int[chromosomeLength];
//	    int[] tempLine2 = new int[chromosomeLength];
//	    int[][] tempChromosome1 = new int[chromosomeHigh][chromosomeLength];
//	    int[][] tempChromosome2 = new int[chromosomeHigh][chromosomeLength];
//	    int crossPosition = 3; //random.nextInt(chromosomeLength - 1) + 1;
//	    for (int highIndex : range.range(chromosomeHigh)) {
//	    	tempLine1 = CrossTwoArray.crossArray(a, 
//	    			b, crossPosition);
//	    	tempLine2 = CrossTwoArray.crossArray(b,
//	    			a, crossPosition);
//            tempChromosome1[highIndex] = tempLine1;
//            tempChromosome2[highIndex] = tempLine2;
//		}
//	
//		Print.printArray(tempChromosome1);
//		Print.printArray(tempChromosome2);
//	}
}
