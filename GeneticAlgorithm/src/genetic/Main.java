package genetic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/** 
 * @author ��ƽ
 * @date 2016/11/27
 */

public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
//		PrintWriter pw1 = new PrintWriter(new File("F:"+File.separator+"averg.txt")) ;
//		PrintWriter pw2 = new PrintWriter(new File("F:"+File.separator+"maxfit.txt")) ;
		PrintWriter pw1 = new PrintWriter(new File("averg.txt")) ;
		PrintWriter pw2 = new PrintWriter(new File("maxfit.txt")) ;
		CoreControl control = new CoreControl();
		
		control.initialize();        //���г�ʼ��

		SpeciesGroup speciesGroup = new SpeciesGroup(control.getMembers());

		System.out.println("��ʼ����Ⱥ��ԱΪ��");
		speciesGroup.print();       //�����Ŵ��㷨֮ǰ��Ⱥ��Ա���
		double theBest;
		
		double[] values;
	
		int n = 0;
		do{
			speciesGroup.decode();
			System.out.println("��"+n+"����");
			theBest = speciesGroup.sufficiency();
			System.out.println("f="+theBest); //���ÿ�ε�����Ӧ������Ӧ�Ľ��ֵ
			System.out.println();
			
			speciesGroup.gambleWheel();        //���ö��̷�������ѡ��			          
			speciesGroup.intersect();          //����
			speciesGroup.variation();          //����

			values = speciesGroup.getValues() ;

			pw1.println(values[0]) ;
			pw2.println(values[1]) ;
			pw2.println("��"+n+"����") ;
			pw1.flush() ;
			pw2.flush() ;
			
			SpeciesGroup.generation++;
			n++;

		}while(!control.isEnded());           //����������û�ﵽʱ��������
			
//		System.out.println("����Ⱥ��ԱΪ��");
//		speciesGroup.print();      //�����Ŵ��㷨֮����Ⱥ��Ա���
	}

}
