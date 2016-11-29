package com.BYSJ;

import java.io.PrintWriter;

public class Print {
	public static void printArray(int[] arr, PrintWriter pw) {
		if(arr==null){//null ֱ�ӷ���
			return;
		}
		for(int i=0; i<arr.length; i++){
			pw.print(arr[i]);
			pw.print(", ");
		}
	}
	
	public static void printArray(int[][] arr, PrintWriter pw) {
		if(arr==null){//null ֱ�ӷ���
			return;
		}
		for(int i=0; i<arr.length; i++){
			if(arr[i]==null){//null����
				continue;
			}
			for(int j=0; j<arr[i].length; j++){
			   pw.print(arr[i][j]);
			   pw.print(", ");
			}
			  pw.println();
		}
	}
	
	public static void printArray(int[][][] arr, PrintWriter pw) {
		if(arr==null){//null ֱ�ӷ���
			return;
		}
		for(int i=0; i<arr.length; i++){
			pw.println("�����е�"+(i)+"������");
			if(arr[i]==null){//null����
				continue;
			}
			for (int j = 0; j < arr[i].length; j++) {
				for(int k=0; k< arr[i][j].length; k++){
				   pw.print(arr[i][j][k]);
				   pw.print(", ");
				}
				  pw.println(' ');
			}			
		}
	}
}
