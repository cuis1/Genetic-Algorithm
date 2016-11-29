package com.BYSJ;

import java.util.Random;

import org.omg.CORBA.FloatSeqHelper;

public class Chromosome {
    protected int chromosomeLength = 7; //Ⱦɫ�峤��
    protected int chromosomeHigh = 3;  //Ⱦɫ��߶�
    protected int inputLength = (int) Math.pow(2,chromosomeHigh);//�������
    protected int[][] gene = {{1, 1, 1, 0}, {2, 3, 4, 5}};//��������
    protected boolean USE_5 = false; //����ʹ�����ı�־λ
    Random random = new Random();//��������ɶ���Ķ���
    Range range = new Range();//range��������ʵ����
  //ѡ�����Ⱦɫ��Ļ���
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
	//ʹ�ñ�ѡ��Ļ�����Ⱦɫ��
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
