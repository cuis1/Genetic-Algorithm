package genetic;
/** 
 * @author ��ƽ
 * @date 2016/11/27
 */

import java.util.Random;


public class SpeciesGroup {
	
	/**
	 * @param size             ��Ⱥ��С 
	 * @param member[]         ��Ⱥ��Ա
	 * @param fitness[]        ��Ա��Ӧ��
	 * @param generation       ����
	 * @param VProbability     ��������
	 * @param IProbability     ��������
	 * @param lengthOfCode     Ⱦɫ��������  || �볤
	 * @param problemThings[]  ������ص� ����
	 * @param bestOneEnode      ���Ÿ������
	 * @param bestOneFit       ���Ÿ�����Ӧ��
	 * @param bestOneNum       ���Ÿ�����Ӧ��
	 * @return  
	 */
	
	
	/**
	 * ������	
	 */
	static int size = 100;//��Ⱥ��С��
	String bestOneEncode ="";
	double bestOneFit = 0;
	double bestOneNum[] = new double[2];
	String[] member = new  String[SpeciesGroup.size];//��Ⱥ��Ա��
	double[] fitness = new  double[SpeciesGroup.size];//��Ա��Ӧ��
	
	static int generation = 0;//����
	double VProbability = 0.1;//�������
	double IProbability = 0.9;//�������
	static int lengthOfCode = 33;//Ⱦɫ��������  || �볤
	static int lengthOfX_1 = 18;//X1Ⱦɫ��������  || �볤
	static int lengthOfX_2 = 15;//X2Ⱦɫ��������  || �볤
	static int MJX_1 = (int) Math.pow(2, 18)-1;//2^X1
	static int MJX_2 = (int) Math.pow(2,15)-1;//2^X2
	/**
	 * ��������	
	 */
	Random randomNumber=new Random();
	public double[][] problemThings = new double[SpeciesGroup.size][2];//������ص� ����
	
	/**
	 * ����ʵ��
	 * @param firstGeneration
	 */
	SpeciesGroup(String[] firstGeneration){
		for(int i = 0;i<size;i++){
			member[i] = firstGeneration[i];
		}
		decode();
	}
	
	/**
	 * ���δ��뱾����в�δʹ��
	 * 
	 * ����
	 * �������һ��ֻ����0/1��33λ������
	 * ����������Ǹ�λ���Ҷ��ǵ�λ
	 * ����ǰ18λ��X1�ı��룬��15λ��X2�ı���
	 */
	public void encode(){
		int size_index = 0; 
		while(size_index < SpeciesGroup.size){
			Random rand = new Random();
			int[] int_gen = new int[lengthOfCode];//ʮ������ʽ ��Ա��
			for(int i = 0;i < lengthOfCode;i++){
				int_gen[i] = rand.nextInt(2);//�������һ��ֻ����0/1��33λ������
			}
			String sb = new String();
			for(int i=1;i<int_gen.length;i++){
				sb += Integer.toString(int_gen[i]);
			}
			member[size_index] = sb;
			size_index += 1;
		}
	}
	
	/**
	 * ��һ�����ƽ����Ӧ�ȣ��ڶ�����������Ӧ��
	 * @return
	 */
	public double[] getValues(){
		double[] values = new double[2] ;
		double sum = 0 ;
		for(int i = 0 ; i < fitness.length ; i ++){
			sum += fitness[i] ;
		}
		values[0] = sum / fitness.length ;
		values[1] = bestOneFit ;
		return values ;
	}
	
	/**
	 * ����
	 */
	public void decode(){
		int k=0,m=0;//ʮ������ʽ ��Ա��
		int mum_index = 0;
		
		while (mum_index < SpeciesGroup.size) {
			String sub_X1 = member[mum_index].substring(0, lengthOfX_1);
			k = Integer.parseInt(sub_X1, 2);
			problemThings[mum_index][0] = CoreControl.X1_A + CoreControl.interalX1/MJX_1*k;
			
			String sub_X2 = member[mum_index].substring(lengthOfX_1, lengthOfCode);
			m = Integer.parseInt(sub_X2, 2);
			problemThings[mum_index][1] = CoreControl.X2_A + CoreControl.interalX2/MJX_2*m;
			m = 0;
			k = 0;
			mum_index += 1;
		}
	}
	
	/**
	 * ���� 
	 */
	public void variation(){
		for(int i=0;i<size;i++){
			if(randomNumber.nextDouble()<=VProbability){

				int changePlace=randomNumber.nextInt(lengthOfCode);  //���ѡ��һλȡ��
				StringBuffer sb=new StringBuffer(member[i]);
				
				if(sb.charAt(changePlace)=='0'){     
					sb.setCharAt(changePlace, '1');			//ȡ��	 						
				}
				else if(sb.charAt(changePlace)=='1'){		
					sb.setCharAt(changePlace, '0');         //ȡ��
				}
				else{
					System.out.println("��������");
				}
				member[i]=sb.toString();

			}
			else{				
			}
		}
	}
	
	/**
	 * ����
	 */
	public void intersect(){
		for(int i = 0;i <= size/2;i++){
			if(Math.random() <= IProbability){
				int one = randomNumber.nextInt(size);//���ѡ�� һ�� ����
				int theOther = randomNumber.nextInt(size);//���ѡ����һ������
				int intersection = randomNumber.nextInt(lengthOfCode);//������� �����
				String str_one = member[one].substring(intersection);
				String str_theOther = member[theOther].substring(intersection);				
				StringBuffer sb = new StringBuffer(member[one]);//������һ�� ����
				sb.replace(intersection, lengthOfCode,str_theOther);
				member[one] = sb.toString();
			    sb = new StringBuffer(member[theOther]);//�����ڶ��� ����
				sb.replace(intersection, lengthOfCode,str_one);
				member[theOther] = sb.toString();
 			}
			else{				
			}
		}
	}
	
	/**
	 * ����ѡ�� 
	 */
	public void gambleWheel(){	
		double sum=0;//��Ӧ��֮��
		for(int i=0;i<SpeciesGroup.size;i++){
			sum+=fitness[i];
		}
		
		double[] fProbability = new double[SpeciesGroup.size]; //��Ӧ�ȵĸ���
		for(int i=0;i<SpeciesGroup.size;i++){
			fProbability[i] = fitness[i]/sum;
		}
		
		double[] wheelBorder = new double[SpeciesGroup.size+1];//���� �߽�
		wheelBorder[0] = 0;
		for(int i = 0;i<SpeciesGroup.size;i++){
			for(int j = 0;j<=i;j++){
				wheelBorder[i+1] +=   fProbability[j];
			}
		}
		
		double pointer = 0;
		String[]tempMember = new  String[SpeciesGroup.size];//��һ�� ��ʱ��Ⱥ��Ա��
		for(int i = 0;i<SpeciesGroup.size;i++){			
			pointer = randomNumber.nextDouble();			 
			for(int j=0;j<wheelBorder.length-1;j++){		//���� ת�� �� ָ�� ��� ָ�� ���� �е�һ������				 
				if(pointer>=wheelBorder[j]&&pointer<wheelBorder[j+1]){//ȷ�����ǿ�����					 
					tempMember[i] = member[j];           //     �����ʱ����  ����Ӱ�� ��Ⱥ����					 
					break;				 
				}				 
				else {				 
				}			 			
			}		
		}
		for(int i = 0;i<SpeciesGroup.size;i++){//��ʱ ��Ա  ת�� Ϊ ��ʵ ��Ա			
			member[i] = tempMember[i];
		}	
	}
	
	/**
	 * ����������Ӧ��ֵ
	 * f==x1sin(4*pi*x1)+x2sin(13*pi*x2)+18
	 * f = 0.5-(sin(sqrt(x*x+y*y))-0.5)/((1.0+0.001(x*x+y*y))*(1.0+0.001(x*x+y*y)))
	 * f =x1sin(sqrt(4*abs(x1)))+x2sin(sqrt(3*abs(x2)))+18 
	 */
	public double sufficiency(){
		int max = 0;
		int min = 0;
		for(int i = 0;i<SpeciesGroup.size;i++){
//			fitness[i] = problemThings[i][0]*Math.sin(Math.PI*4*problemThings[i][0]) +
//					problemThings[i][1]*Math.sin(Math.PI*13*problemThings[i][1])+18;
			
//			double y1 = problemThings[i][0]*problemThings[i][0]+problemThings[i][1]*problemThings[i][1];
//			fitness[i] =0.5-(Math.sin(Math.sqrt(y1))*Math.sin(Math.sqrt(y1))-0.5)/((1.0+0.001*y1)*(1.0+0.001*y1));
			
			fitness[i] = problemThings[i][0]*Math.sin(Math.PI*4*Math.abs(problemThings[i][0])) +
					problemThings[i][1]*Math.sin(Math.PI*13*Math.abs(problemThings[i][1])) + 18;
			
			if(fitness[max]<fitness[i]){
				max = i;
			}
			if(fitness[min]>fitness[i]){
				max = i;
			}
		}
		if (fitness[max] >= bestOneFit) {
			bestOneFit = fitness[max];
			bestOneEncode = member[max];
			bestOneNum = problemThings[max];
			System.out.println("x1=��"+problemThings[max][0]);        //���ÿ�ε�����Ӧ������Ӧ��xֵ
			System.out.println("x2=��"+problemThings[max][1]);        //���ÿ�ε�����Ӧ������Ӧ��xֵ
			System.out.println("Encode=��" + member[max]);
			return fitness[max];
		}else{
			fitness[min] = bestOneFit;
			member[min] = bestOneEncode;
			problemThings[min] = bestOneNum;
			System.out.println("x1=��"+problemThings[min][0]);        //���ÿ�ε�����Ӧ������Ӧ��xֵ
			System.out.println("x2=��"+problemThings[min][1]);        //���ÿ�ε�����Ӧ������Ӧ��xֵ
			System.out.println("Encode=��" + member[min]);
			return fitness[min];
		}
	}
	
	/**
	 * ��һ��
	 */
	public void nextGeneration(){	
		gambleWheel();      //ѡ��		
		intersect();   		//����
		variation();   		//����
	}
	
	/**
	 * �����Ⱥ��Ա
	 */
	public void print(){
		for(int i=0;i< SpeciesGroup.size;i++){
			System.out.println(member[i]);
		}
	}
	
	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		CoreControl control = new CoreControl();
		control.initialize();        //���г�ʼ��
		SpeciesGroup speciesGroup = new SpeciesGroup(control.getMembers());
    }
}
