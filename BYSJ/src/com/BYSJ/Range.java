package com.BYSJ;

public class Range {
	public int[] range(int startNum, int endNum) {
		int [] listNeed = new int[endNum - startNum];
		for (int i = 0; i < endNum - startNum; i++) {
			listNeed[i] = i + startNum;
		}
		return listNeed;
	}
	
	public int[] range(int endNum) {
		int [] listNeed = new int[endNum];
		for (int i = 0; i < endNum; i++) {
			listNeed[i] = i;
		}
		return listNeed;
	}
	
	public int[] range(int startNum, int endNum, int step) {
		int arrayLong;
		if ((endNum - startNum)%step == 0) {
			arrayLong = (endNum - startNum)/step;
		} 
		else{
			arrayLong = (endNum - startNum)/step + 1;
		}
		int [] listNeed = new int[arrayLong];
		for (int i = 0; i < arrayLong; i++) {
			listNeed[i] = startNum + i * step;
		}
		return listNeed;
	}
//	public static void main(String[] args) {
//		Range range = new Range();
//		for (int i : range.range(1,5,5)) {
//			System.out.println(i);
//		}
//	}
}