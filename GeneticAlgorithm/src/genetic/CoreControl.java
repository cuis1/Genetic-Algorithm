package genetic;
/** 
 * @author ��ƽ
 * @date 2016/11/27
 */
import java.util.Random;

public class CoreControl {     // ���Ŀ�����
	
	/**
	 * ��Ҫ����
	 * @param A                �����½�
	 * @param B                �����Ͻ�
	 * @param interalX1        X1 ���� 
	 * @param interalX2        X2 ���� 
	 * @param problemThings[]  ������ص� ����
	 * @param members[]        ��Ⱥ�ĳ�Ա
	 * @param LENGTH           ���볤��
	 * @param stop             ֹͣ�Ĵ���
	 * @return  
	 */
	
	public static final double X1_A=-3;//�����½�
	public static final double X1_B=12.1;//�����Ͻ�
	public static final double interalX1 = X1_B-X1_A;//����
	public static final double X2_A=4.1;//�����½�
	public static final double X2_B=5.8;//�����Ͻ�
	public static final double interalX2 = X2_B-X2_A;//����
	public double[] problemThings = new double[SpeciesGroup.size];//������ص� ����
	public String[] members = new String[SpeciesGroup.size];
	
	public static final int LENGTH=33;//���볤�ȣ���ΪҪ��ȷ��С�������λ�����Ա�ΪLENGTHλ������һ��ʽ�ɲο�
	
	public static final int stop = 100;//ֹͣ�Ĵ���
	
	
	/**
	 * ����
	 */
	public void encode(){
		int size_index = 0; 
		while(size_index < SpeciesGroup.size){
			Random rand = new Random();
			int[] int_gen = new int[LENGTH];//ʮ������ʽ ��Ա��
			for(int i = 0;i < LENGTH;i++){
				int_gen[i] = rand.nextInt(2);//�������һ��ֻ����0/1��33λ������
//				System.out.print(int_gen[i]);
			}
//			System.out.println();
			String sb = new String("");
			for(int i = 0; i < LENGTH; i++){
				sb += Integer.toString(int_gen[i]);
			}
			members[size_index] = sb;
			size_index += 1;
		}
	}
	
	/**
	 * �ж��Ƿ����
	 */
	boolean isEnded(){
		if(SpeciesGroup.generation < stop)
			return false;
		else
			return true;
	}
	
	/**
	 * ��ʼ��
	 * @param args
	 */
	Random randomNumber=new Random();
	public void initialize(){
		encode();
	}
	
	public String[] getMembers(){
		return members;
	}
	public static void main(String[] args) {
		CoreControl a = new CoreControl();
		a.initialize();
		for (int i = 0; i < a.members.length; i++) {
			System.out.println(a.members[i]);
		}
	}
}
