package com.BYSJ;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Population extends Chromosome{
    private int scalePopulation = 600;//��Ⱥ��ģ
    private double crossProbability = 0.9;//�������
    private double mutationProbability = 0.7;//�������
    private int [][][] populationList = new int[scalePopulation][chromosomeHigh][chromosomeLength];//�洢�����ָ���
    private int [][][] nextPopulation = new int[scalePopulation][chromosomeHigh][chromosomeLength];//�洢��һ����Ⱥ����
    private int [][][] populationOutputList = new int[scalePopulation][chromosomeHigh][inputLength];//�洢�������
    private int [][][] populationOutputAsInputList = new int[scalePopulation][chromosomeHigh][inputLength];//�洢���巴�����
    private int [] populationFitnessList = new int[scalePopulation];//�洢������Ӧ��
    private int maxFit = 0;//�洢��ǰ��Ⱥ�����Ӧ��
    private int [][] inputList = new int[chromosomeHigh][inputLength];//�洢�ŵ�·������
    @SuppressWarnings("unchecked")
	Map maxFitnessList=new HashMap(){{put("Fitness",0);put("chromosome1",0);put("Index1",0);put("chromosome2",0);put("Index2",0);}};//�������Ÿ���
    Map minFitnessList=new HashMap();//����������
    //��ȡ�����Ӧ���Լ��ø����λ��
    public int[] max(int [] sourceArray) {
    	int[] temp = {0,0};
    	for (int sourceArrayIndex = 0; sourceArrayIndex < sourceArray.length; sourceArrayIndex++) {
			if(sourceArray[temp[0]] < sourceArray[sourceArrayIndex])
				temp[0] = sourceArrayIndex; 
		}
    	temp[1] =sourceArray[temp[0]];
    	return temp;
	}
    //��ȡ��С��Ӧ���Լ��ø����λ��
    public int[] min(int [] sourceArray) {
    	int[] temp = {0,0};
    	for (int sourceArrayIndex = 0; sourceArrayIndex < sourceArray.length; sourceArrayIndex++) {
			if(sourceArray[temp[0]] > sourceArray[sourceArrayIndex])
				temp[0] = sourceArrayIndex; 
		}
    	temp[1] =sourceArray[temp[0]];
    	return temp;
	}   
    //���ɿ����߼��ŵ�����
    private int[][] chromosomeInput() {
    	for (int inputLengthIndex : range.range(inputLength)) {
    		int temp = inputLengthIndex;
    		for (int chromosomeHighIndex : range.range(chromosomeHigh)) {
    			inputList[chromosomeHigh - chromosomeHighIndex - 1][inputLengthIndex] = temp % 2;
    			temp /=2;				
			}			
		} 
    	return inputList;
	}
    //������ʼ��Ⱥ
    private int[][][] creatPopulation() {
    	for (int scalePopulationIndex : range.range(scalePopulation)) {
    		populationList[scalePopulationIndex] = createChromosome();
		}
		return populationList;
	}
    //�����ŵ�·������Լ����ŵ�·������Ӧ�ı���ʹ���ڲ����������Ӿ���
    private int [][][] calculateOutput(int populationIndex, int [][] inputInNeed, boolean isInOrder){
    	int [][] output = new int[chromosomeHigh][inputLength];
    	int [] mid = new int[chromosomeHigh];
    	for (int inputIndex : range.range(inputInNeed[0].length)) {
            for (int chromosomeHighIndex : range.range(chromosomeHigh))
                mid[chromosomeHighIndex] = inputInNeed[chromosomeHighIndex][inputIndex];
            int chromosomeLengthIndex = -1;
            while (chromosomeLengthIndex < chromosomeLength - 1) {
            	chromosomeLengthIndex += 1;
                int control = -100;
                int ncv = -100;
                int directIndex = 0;
                int [] direct = new int [chromosomeHigh - 1];
                for (int indexDirect = 0; indexDirect < direct.length; indexDirect++) {
					direct[indexDirect] = -1;
				}
                for (int chromosomeHighIndex : range.range(chromosomeHigh)){
                    if (populationList[populationIndex][chromosomeHighIndex][chromosomeLengthIndex] == 1)
                        control = chromosomeHighIndex;
                    else if (populationList[populationIndex][chromosomeHighIndex][chromosomeLengthIndex] == 0){
                        //System.out.println(directIndex+ " " + chromosomeHighIndex);
                    	direct[directIndex] = chromosomeHighIndex;
                        directIndex++;
                    }
                    else
                        ncv = chromosomeHighIndex;
                }
                for (int directContent : direct)
                	if(directContent != -1){
                		output[directContent][inputIndex] = mid[directContent];
                	}
                if (control == -100){
                    if (populationList[populationIndex][ncv][chromosomeLengthIndex] == 5){
                        // 2��ʾV0��3��ʾV1
                        if (mid[ncv] == 0)
                            output[ncv][inputIndex] = 1;
                        else if (mid[ncv] == 1)
                            output[ncv][inputIndex] = 0;
                        else if (mid[ncv] == 2)
                            output[ncv][inputIndex] = 3;
                        else if (mid[ncv] == 3)
                            output[ncv][inputIndex] = 2;
                        mid[ncv] = output[ncv][inputIndex];
                    }
                    else{
                        System.out.println("���Ŵ��󣺿���λ = -100���ܿ�λ =" + populationList[populationIndex][ncv][chromosomeLengthIndex]);
                    }  
                }
                else if (-1 < control && control < chromosomeHigh) {
                	output[control][inputIndex] = mid[control];
                	if (mid[control] == 0)
                        output[ncv][inputIndex] = mid[ncv];
                	else if (mid[control] == 1){
                		if (populationList[populationIndex][ncv][chromosomeLengthIndex] == 2){
                            if (mid[ncv] == 0)
                                output[ncv][inputIndex] = 3;
                            else if (mid[ncv] == 1)
                                output[ncv][inputIndex] = 2;
                            else if (mid[ncv] == 2)
                                output[ncv][inputIndex] = 0;
                            else if (mid[ncv] == 3)
                                output[ncv][inputIndex] = 1;
                        }
                		else if (populationList[populationIndex][ncv][chromosomeLengthIndex] == 3){
                            if (mid[ncv] == 0)
                                output[ncv][inputIndex] = 2;
                            else if (mid[ncv] == 1)
                                output[ncv][inputIndex] = 3;
                            else if (mid[ncv] == 2)
                                output[ncv][inputIndex] = 1;
                            else if (mid[ncv] == 3)
                                output[ncv][inputIndex] = 0;
                        }
                		else if (populationList[populationIndex][ncv][chromosomeLengthIndex] == 4){
                            if (mid[ncv] == 0)
                                output[ncv][inputIndex] = 1;
                            else if (mid[ncv] == 1)
                                output[ncv][inputIndex] = 0;
                            else if (mid[ncv] == 2)
                                output[ncv][inputIndex] = 3;
                            else if (mid[ncv] == 3)
                                output[ncv][inputIndex] = 2;
                        }
                		else{
                            System.out.println("�ܿ�λ���󣺿���λ =" + 
                            		populationList[populationIndex][control][chromosomeLengthIndex] + 
                            		"�ܿ�λ =" + populationList[populationIndex][ncv][chromosomeLengthIndex]);
                            }
                		mid[ncv] = output[ncv][inputIndex];
                	}
                	else if (isInOrder && 1 < mid[control] && mid[control]< 4){
                		int[] position = chooseGene();
                        int newControl = populationList[populationIndex][control][chromosomeLengthIndex];
                        int newNcv = populationList[populationIndex][ncv][chromosomeLengthIndex];
                        populationList[populationIndex][control][chromosomeLengthIndex] = 0;
                        populationList[populationIndex][ncv][chromosomeLengthIndex] = 0;
                        populationList[populationIndex][position[1]][chromosomeLengthIndex] = newControl;
                        populationList[populationIndex][position[2]][chromosomeLengthIndex] = newNcv;
                        chromosomeLengthIndex = -1;
                        for (int chromosomeHighIndex : range.range(chromosomeHigh))
                            mid[chromosomeHighIndex] = inputInNeed[chromosomeHighIndex][inputIndex];
                    }
                	else if (!isInOrder)
                        output[ncv][inputIndex] = -1;
				}
			}
		}
    	if (isInOrder){
            populationOutputList[populationIndex] = output;
            return populationOutputList;
    	}
        else{
            populationOutputAsInputList[populationIndex] = output;
            return populationOutputAsInputList;
        }
    }
    //������Ӧ��
    private int[] calculateFitness(int scaleIndex,boolean useTarget){
    	//3λToffoli��
    	//Print.printArray(range.range(chromosomeHigh));
    	//int [][] target = {{0, 0, 0, 0, 1, 1, 1, 1}, {0, 0, 1, 1, 0, 0, 1, 1}, {0, 1, 0, 1, 0, 1, 1, 0}};//���Ʒ���
    	//int [][] target = {{0, 0, 0, 0, 1, 1, 1, 1}, {0, 0, 1, 1, 0, 1, 0, 1}, {0, 1, 0, 1, 0, 0, 1, 1}};//���ƽ�����
//    	int [][] target = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 
//    						{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1}, 
//    						{0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1}, 
//    						{0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1}, 
//    						{0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0}};
    	int [][] target = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, 
    			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, 
    			{0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1}, 
    			{0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1}, 
    			{0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,0,0}, 
    			{0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1}};
    	//int [][] target = {{1, 0, 0, 0, 1, 0, 1, 1}, {1, 0, 0, 1, 0, 1, 1, 0}, {1, 0, 1, 1, 0, 0, 0, 1}};
    	//for (int scaleIndex : range.range(0, scalePopulation)){
            int fit = 0;
            for (int longIndex : range.range(chromosomeLength - 1)){
                //�ŵ�·����������������������ȫ��ͬ���ų�
                int temp = 0;
                for (int highIndex : range.range(0, chromosomeHigh))
                    if (populationList[scaleIndex][highIndex][longIndex] == 
                    	populationList[scaleIndex][highIndex][longIndex + 1])
                        temp += 1;
                if (temp == chromosomeHigh)
                    fit -= chromosomeHigh;
            }
            int temp_5 = 0;
            for (int highIndex : range.range(chromosomeHigh)){
                // �ŵ�·��ĳһ��������ȫ��ͬ���ų�
                if (max(populationList[scaleIndex][highIndex])[1] == min(populationList[scaleIndex][highIndex])[1])
                    fit -= inputLength;
                int temp1 = 0;
                // �ŵ�·��ĳһ������ֻ��0��4��5�ų�
                for (int chromosomeLengthContent : populationList[scaleIndex][highIndex]){
                    if (chromosomeLengthContent == 0 || chromosomeLengthContent == 5 || chromosomeLengthContent == 4)
                        temp1 += 1;
                	if (chromosomeLengthContent == 5)
                		temp_5 += 1;
                }
                if (temp1 == chromosomeLength)
                    fit -= temp1;
                if (temp_5 > 0)
                    fit -= (temp_5 < 2 ? 0 : temp_5 * temp_5);
                // ʹ�üȶ�Ŀ���ŵ�·���������
                if (useTarget){
                    for (int targetLengthIndex : range.range(target[0].length)){
                        if (target[highIndex][targetLengthIndex] ==
                        		populationOutputList[scaleIndex][highIndex][targetLengthIndex])
                            fit += 1;
//                        if (populationOutputList[scaleIndex][highIndex][targetLengthIndex] == 1 && (chromosomeHigh-1 == highIndex )&&(inputLength-2 == targetLengthIndex)) {
//                        	fit += 100;
//    					}
//                        if (populationOutputList[scaleIndex][highIndex][targetLengthIndex] == 0 && (chromosomeHigh-1 == highIndex )&&(inputLength-1 == targetLengthIndex)) {
//                        	fit += 100;
//    					}
                    }
                }
                else if (! useTarget){
                    for (int inputLengthIndex : range.range(inputList[0].length)){
                        if (populationOutputList[scaleIndex][highIndex][inputLengthIndex] != 0 && 
                        		populationOutputList[scaleIndex][highIndex][inputLengthIndex] != 1){
//                        	System.out.println(-1);
                            fit -= 1;
                        }
                        if (inputList[highIndex][inputLengthIndex] == 
                        		populationOutputAsInputList[scaleIndex][highIndex][inputLengthIndex]){
                            fit += 1;
//                            System.out.println(1);
                        }
                    }
                }
            }
            populationFitnessList[scaleIndex] = fit;
            return populationFitnessList;	
        }
    	//return populationFitnessList;		
	//}
    //�������Ÿ���
    @SuppressWarnings("unchecked")
	private Map finMaxFitness() {
    	int [] temp = max(populationFitnessList);
    	maxFitnessList.put("Index", temp[0]);
    	maxFitnessList.put("Fitness", temp[1]);
    	maxFitnessList.put("chromosome1", populationList[temp[0]]);
    	return maxFitnessList;		
	}
    //����Ⱦɫ��֮��ġ���Բ��족
    @SuppressWarnings("unchecked")
	private Map distanceChromosome() {
    	ArrayList<Integer> listchomosome = new ArrayList<Integer>();
		int [] maxFitIndexAndNumber = max(populationFitnessList);
    	maxFit = maxFitIndexAndNumber[1];
    	if ((int)maxFitnessList.get("Fitness") < maxFit) {
    		maxFitnessList.put("Fitness", maxFit);
    		maxFitnessList.put("Index", maxFitIndexAndNumber[0]);
    		for (int fitnessIndex : range.range(scalePopulation)) {
				if (populationFitnessList[fitnessIndex] == (int)maxFitnessList.get("Fitness")) {
					listchomosome.add(fitnessIndex);
				}
			}
    		Object[] listChomosome = listchomosome.toArray();
			if (listChomosome.length > 1) {
				int maxDistance = 0;
				for (int startIndex1 = 0; startIndex1 < listChomosome.length; startIndex1++) {
					int distance = 0;
					for (int startIndex2 = startIndex1 + 1; startIndex2 < listChomosome.length; startIndex2++) {
						for (int contentLengthIndex = 0; contentLengthIndex < chromosomeLength; contentLengthIndex++) {
							for (int contentHighIndex = 0; contentHighIndex < chromosomeHigh; contentHighIndex++) {
								//System.out.println((int)listChomosome[startIndex1]+" " +(int) listChomosome[startIndex2]);
								distance += Math.pow(
								populationList[(int)listChomosome[startIndex1]][contentHighIndex][contentLengthIndex]-
							populationList[(int) listChomosome[startIndex2]][contentHighIndex][contentLengthIndex],2);								
							}							
						}
						if (distance >= maxDistance) {
							maxFitnessList.put("Index1", (int)listChomosome[startIndex1]);
							maxFitnessList.put("chromosome1", populationList[(int) listChomosome[startIndex1]]);
							maxFitnessList.put("Index2", (int)listChomosome[startIndex2]);
							maxFitnessList.put("chromosome2", populationList[(int) listChomosome[startIndex2]]);
							maxDistance = distance;
						}
					}
				}
			}
			else {
				maxFitnessList.put("chromosome1", populationList[(int) listChomosome[0]]);
				maxFitnessList.put("Index1", (int)listChomosome[0]);
				maxFitnessList.put("chromosome2", 0);
				maxFitnessList.put("Index2", -1);
			}
		}
    	else {
    		maxFitnessList.put("Index1", 0);
		}
		return maxFitnessList;
	}
	//ѡ������
    private int select() {
    	Map<Integer, Integer> individual = new HashMap<Integer, Integer>();
    	for (int i = 0; i < 6; i++) {
    		int tempIndex= random.nextInt(scalePopulation);
    		while (individual.containsKey(tempIndex)){
    			tempIndex= random.nextInt(scalePopulation);
    		}
			individual.put(tempIndex, populationFitnessList[tempIndex]);			
		}
    	int individualValue = -1000;
    	int result = 0;
    	for (int individualKey : individual.keySet()) {
			if (individualValue < individual.get(individualKey)) {
				individualValue = individual.get(individualKey);
				result = individualKey;
			}
		}
		return result;
	}
	//��������
    private int[][][] cross() {
		for (int populationIndex : range.range(0, scalePopulation, 2)) {
			int individual1 = select();
		    int individual2 = select();
		    while (individual1 == individual2) {
		    	individual2 = select();
			}
		    if (random.nextFloat() < crossProbability) {
			    int[] tempLine1 = new int[chromosomeLength];
			    int[] tempLine2 = new int[chromosomeLength];
			    int[][] tempChromosome1 = new int[chromosomeHigh][chromosomeLength];
			    int[][] tempChromosome2 = new int[chromosomeHigh][chromosomeLength];
			    int crossPosition = random.nextInt(chromosomeLength - 1) + 1;
			    //System.out.println(crossPosition);
			    for (int highIndex : range.range(chromosomeHigh)) {
			    	tempLine1 = CrossTwoArray.crossArray(populationList[individual1][highIndex], 
			    			populationList[individual2][highIndex], crossPosition);
			    	tempLine2 = CrossTwoArray.crossArray(populationList[individual2][highIndex],
			    			populationList[individual1][highIndex], crossPosition);
                    tempChromosome1[highIndex] = tempLine1;
                    tempChromosome2[highIndex] = tempLine2;
				}
			    nextPopulation[populationIndex] = tempChromosome1;
			    nextPopulation[populationIndex + 1] = tempChromosome2;
			}
		    else {
		    	nextPopulation[populationIndex] = populationList[individual1];
			    nextPopulation[populationIndex + 1] = populationList[individual2];
			}
		}
		return nextPopulation;
	}
    
    private int[][][] crossEnhance() {
		for (int populationIndex : range.range(scalePopulation)) {
			int individual1 = select();
		    int individual2 = select();
		    int individual3 = select();
		    //System.out.println(individual1+" "+individual2 + " "+individual3+" "+ populationIndex);
		    if (random.nextFloat() < crossProbability) {
			    int[] tempLine1 = new int[chromosomeLength];
			    //int[] tempLine2 = new int[chromosomeLength];
			    int[][] tempChromosome1 = new int[chromosomeHigh][chromosomeLength];
			    //int[][] tempChromosome2 = new int[chromosomeHigh][chromosomeLength];
			    int crossPosition1 = random.nextInt(chromosomeLength - 2)+1;
			    int crossPosition2 = random.nextInt(chromosomeLength - crossPosition1 -1)+1+crossPosition1;
			    //System.out.println(crossPosition);
			    for (int highIndex : range.range(chromosomeHigh)) {
			    	tempLine1 = CrossTwoArray.crossArray(populationList[individual1][highIndex], 
			    			populationList[individual2][highIndex], crossPosition1);
			    	tempLine1 = CrossTwoArray.crossArray(tempLine1,
			    			populationList[individual3][highIndex], crossPosition2);
                    tempChromosome1[highIndex] = tempLine1;
                    //tempChromosome2[highIndex] = tempLine2;
				}
			    nextPopulation[populationIndex] = tempChromosome1;
			    //nextPopulation[populationIndex + 1] = tempChromosome2;
			}
		    else {
		    	nextPopulation[populationIndex] = populationList[individual1];
			    //nextPopulation[populationIndex + 1] = populationList[individual2];
			}
		}
		return nextPopulation;
	}
    //��������
    private int[][][] mutation() {
    	for (int scaleIndex : range.range(scalePopulation)){
            if (random.nextFloat() < mutationProbability){
                if (random.nextFloat() < 0.5)
                    nextPopulation[scaleIndex] = createChromosome();
                else{
                    //int position = random.nextInt(chromosomeLength);
                    int position = chooseGene()[0];
                    int [] genIndexAndPosition = chooseGene();
                    for (int highIndex : range.range(chromosomeHigh)){
                        nextPopulation[scaleIndex][highIndex][position] = 0;
                    }   
                    nextPopulation[scaleIndex][genIndexAndPosition[1]][position] = gene[0][genIndexAndPosition[0]];
                    nextPopulation[scaleIndex][genIndexAndPosition[2]][position] = gene[1][genIndexAndPosition[0]];                    
                }
            }
    	}
        return nextPopulation;
    }
    
    private void optimalReserve() {
		nextPopulation[0] = (int[][]) maxFitnessList.get("chromosome1");
		if ((int)maxFitnessList.get("Index2") != -1) {
			nextPopulation[1] = (int[][]) maxFitnessList.get("chromosome2");
		}
		populationList = nextPopulation;		
	}
    			
 
    //�����������������
    public static void main(String[] args) throws IOException  {
    	boolean USE_TARGET = true;
    	Range range = new Range();
    	System.out.println("Function is running...");
    	//String filePath="F:\\java_workplace\\BYSJ";
    	String fileName = "text.txt";
    	
    	File file = new File(fileName);
    	
    	if (file.exists()) {
    		file.delete();
    		}
    	//file.createNewFile();
		FileWriter fw = new FileWriter(file,true);
		PrintWriter pw = new PrintWriter(fw);
		
		int size = 0;
		int fileNumber = 0;
		String newFileName;
		
    	int number = 0;
    	System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    	pw.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		Population a = new Population();
		a.inputList = a.chromosomeInput();
		Print.printArray(a.inputList, pw);
		a.creatPopulation();
		int fitnessMaxValue = a.chromosomeHigh * a.inputLength; //+ 200;
		while ((int)a.maxFitnessList.get("Fitness") < fitnessMaxValue ) {
			number += 1;
			//System.out.println(number);&& number < 100000
			for (int scaleIndex : range.range(a.scalePopulation))
	            a.calculateOutput(scaleIndex, a.inputList, true);
	        if (!USE_TARGET){
	            for (int scaleIndex : range.range(a.scalePopulation))
	                a.calculateOutput(scaleIndex, a.populationOutputList[scaleIndex], false);
	        }
	        for (int scaleIndex : range.range(a.scalePopulation))
	        	a.calculateFitness(scaleIndex, USE_TARGET);
	        a.distanceChromosome();
	        a.crossEnhance();
            a.mutation();
            a.optimalReserve();
            pw.println("_____________��" + number + "��________________");

            pw.println("Fitness = "+ a.maxFitnessList.get("Fitness"));
            pw.println("##########maxFitnessList[chromosome1]########");
            if ((int)a.maxFitnessList.get("Fitness") == fitnessMaxValue) {
            	a.calculateFitness((int)a.maxFitnessList.get("Index1"), USE_TARGET);
            	a.maxFitnessList.put("Fitness", a.populationFitnessList[(int)a.maxFitnessList.get("Index1")]);
			}
            Print.printArray((int[][])a.maxFitnessList.get("chromosome1"), pw);
            pw.println(a.maxFitnessList.get("Index1"));
            if ((int)a.maxFitnessList.get("Index2") > -1) {
				pw.println("##########maxFitnessList[chromosome2]########");
				pw.println(a.maxFitnessList.get("Index2"));
				Print.printArray((int[][])a.maxFitnessList.get("chromosome2"), pw);			
			}
            fw.flush();
            int times = 0;
            size = new FileInputStream(file).available() / 1024 / 1024;
            if (size > 70) {
				System.out.println("____________________________________________________");
				newFileName = "text" + fileNumber + ".txt";
				System.out.println("�ļ���СΪ��" + size +"\n��һ���ļ�������Ϊ��" + newFileName);
				fileNumber += 1;
				pw.close();
				fw.close();
				file = new File(newFileName);
				fw = new FileWriter(file,true);
				pw = new PrintWriter(fw);
//				System.out.println(newFileName);
//				File target = new File(newFileName);
//				  if (target.exists()) { //���ļ������ڣ���ɾ��
//				    target.delete();
//				  }
//				boolean sucessful = file.renameTo(target);
//				while (!sucessful) {
//					times+=1;
//					if (times%50000 == 0) {
//						System.out.println("����δ�ɹ�����ͻ��" + times + "!!!");
//					}
//					sucessful = file.renameTo(new File(newFileName));
//					//System.out.println(file.renameTo(new File(newFileName)));
//				}
//				System.out.println(sucessful);
//				System.out.println("____________________________________________________");
//				System.out.println(fileName);
//				file = new File(fileName);
//				fw = new FileWriter(file,true);
//				pw = new PrintWriter(fw);
			}
		}
//		for (int scaleIndex : range.range(a.scalePopulation)) {
//			pw.println("##########��"+ scaleIndex +"������########");
//			Print.printArray(a.populationList[scaleIndex], pw);
//		}
		pw.println("______****_______���Ž��_______****_________");
		Print.printArray((int[][])a.maxFitnessList.get("chromosome1"), pw);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		System.out.println("Function finished. Detail information: text.txt");
		pw.close();
		fw.close();
	}
}
