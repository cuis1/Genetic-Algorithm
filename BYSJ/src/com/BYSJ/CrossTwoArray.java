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
}
