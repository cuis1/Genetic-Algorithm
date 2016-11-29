package com.BYSJ;

import java.util.Random;

import org.omg.CORBA.FloatSeqHelper;

public class Chromosome {
    protected int chromosomeLength = 7; //染色体长度
    protected int chromosomeHigh = 3;  //染色体高度
    protected int inputLength = (int) Math.pow(2,chromosomeHigh);//输入个数
    protected int[][] gene = {{1, 1, 1, 0}, {2, 3, 4, 5}};//基因编码库
    protected boolean USE_5 = false; //非门使用与否的标志位
    Random random = new Random();//随机数生成对象的定义
    Range range = new Range();//range函数对象实例化
  //选着组成染色体的基因
	protected int [] chooseGene(){
		int randomElement = random.nextInt(gene[0].length);
		if (!USE_5) {
			while (randomElement == 3) {
				randomElement = random.nextInt(gene[0].length);
			}
		}
		int randomPosition1 = random.nextInt(chromosomeHigh);
		int randomPosition2 = random.nextInt(chromosomeHigh);
		while (randomPosition1 == randomPosition2) {
			randomPosition2 = random.nextInt(chromosomeHigh);
		}
		int [] choose = {randomElement, randomPosition1, randomPosition2};
		return choose;		
	}
	//使用被选择的基因构造染色体
	protected int [][] createChromosome(){
		int [] position = new int[3];
		int [][] tempChromosome = new int[chromosomeHigh][chromosomeLength];
		for (int chromosomeLengthIndex : range.range(chromosomeLength)) {
			position = chooseGene();
			for (int chromosomeHighIndex : range.range(chromosomeHigh)) {
				if (position[1] == chromosomeHighIndex)
                    tempChromosome[chromosomeHighIndex][chromosomeLengthIndex] = gene[0][position[0]];
                else if (position[2] == chromosomeHighIndex)
                    tempChromosome[chromosomeHighIndex][chromosomeLengthIndex] = gene[1][position[0]];
			}
		}
		return tempChromosome;		
	}
}
