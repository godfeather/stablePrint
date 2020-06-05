package tableG.ganerator;

import java.util.ArrayList;

import javax.management.ConstructorParameters;

/**
 * �������ɱ��
 * */
public class TableFormat {
	private ArrayList<String> all=new ArrayList<>();
	@Deprecated
	public ArrayList<ArrayList<String >> comp(String resource){
		return comp(resource,false);
	}
	@Deprecated
	public ArrayList<ArrayList<String >> comp(ArrayList<String> resource){
		String re="";
		for(String st:resource) {
			re+=st+"\n";
		}
		return comp(re,false);
	}
	@Deprecated
	public ArrayList<ArrayList<String >> comp(ArrayList<String> resource,boolean index){
		String re="";
		for(String st:resource) {
			re+=st+"\n";
		}
		return comp(re,index);
	}
	@Deprecated
	public ArrayList<ArrayList<String>> comp(String resource,boolean index) {//��ȡ������������
		String[] row=resource.split("\n");
		ArrayList<ArrayList<String>> content=new ArrayList<>();				//���ݱ���
		int [] count=new int[row.length];
		for(int i=0;i<row.length;i++) {//������

			ArrayList<String> al=new ArrayList<String>();
			char[] singleRow=row[i].trim().toCharArray();
			String map=row[i].trim();//���е��ֶ�ӳ��
			int start=0;
			int end=0;		
			boolean have=false;							//�ҵ���"�����ŵı�ʾ
			boolean needComma=false;							//���ҵ�"����ʱ���ͻᴥ���ñ�־������־Ϊtureʱ���������ֱ���ҵ�Comma���������Ż��ߵ���β����β
			int endPoint=0;										//��һ��"���ų��ֵ�λ��
			for(int j=0;j<singleRow.length;j++) {			//ͳ���ֶθ���
				if(index) {
					if(j==0&&i!=0) {
						al.add(""+i);							//����ı��б��
						count[i]++;
					}else if(j==0&&i==0){
						al.add("NO");
						count[i]++;
					}
				}
													
				end=j;
				if((singleRow[j]=='"')&&have==false&&needComma==false) {
					
						start=j+1;
						have=true;
						if(j<singleRow.length-1) {								//���"���ž��Ǳ��е����һλ����ô����ҪѰ�ҽ����Ķ��ţ�����
							needComma=true;
						}
					
				}else if((singleRow[j]=='"')&&have) {
					have=false;
					if(j==singleRow.length-1) {
						end-=1;
					}
					endPoint=j;
				}
				if((singleRow[j]==','||singleRow[j]=='��')&&have==false) {	//�ԣ���Ϊ�зָͳ��������ͳ���ֶ����ֵ
					if(j==0||singleRow[j-1]!='\\'){
							end=j;
							
							if(needComma) {
								al.add(map.substring(start,endPoint).trim());
							}else {
								al.add(map.substring(start,end).trim());
							}
							needComma=false;								//ִ���˸�Case˵����""���ҵ��˶��ţ���˼����ַ�������׼�����Բ�����һ�����ţ�""��
								end=j;										//�����Ƕ���ĸ�ֵ�����ҵ�һ�����ţ�ǡ����Ҫһ������ʱ�����ϱߵĸ�ֵ�ᱻ�ı䣬����ĸ�ֵ��֤���۷���ʲô�������һ�񶺺�
								count[i]++;
							start=end+1;
						
					}else if(singleRow[j-1]=='\\'){
						
						String cEnd=map.substring(j+1,map.length());
						String cStart=map.substring(0,j-1)+",";
						map=cStart+cEnd;
						singleRow=map.toCharArray();
						j--;
						
						
						System.out.println("endPoint:"+endPoint);
						System.out.println("J:"+j);
						System.out.println("end:"+j);
						System.out.println("Len:"+map.length());
						System.out.println("start:"+start);
						if(j==singleRow.length-1) {												//��������ֱ�ע�͵��Ķ��ţ�ֱ�ӿ�ʼ�أ�����û�л����ٽأ�����ɸ���Ϊ�յ����
							al.add(map.substring(start,j+1));
						}
					}		
				}else if(j==singleRow.length-1){
					if(needComma&&have==false) {												//���ȱ�ٶ��ŵ��ַ������ߵ���ͷʱ����"���Ŵ��ضϣ����ಿ�ֲ�Ҫ����
						al.add(map.substring(start,endPoint));
					}else {
						al.add(map.substring(start,end+1));
					}
				}
			}
			content.add(al);
		}
		int max=0;
		for(int i=0;i<count.length;i++) {
			if(count[i]>max) {
				max=count[i];
			}
		}
		max++;
		for(int i=0;i<content.size();i++) {
			while(content.get(i).size()<max) {
				content.get(i).add("");
				
			}
		}
		return content;
	}
	private ArrayList<ArrayList<String>> format(ArrayList<ArrayList<String>> as) {	//���������������ˮƽ����
		ArrayList<ArrayList<String>> aas=as;
		
		int[] size=countMax(aas);
		ArrayList<ArrayList<String>> all=new ArrayList<>();
		
		for(int i=0;i<aas.size();i++) {
			ArrayList<String> al=aas.get(i);						//ÿ������				
			ArrayList<String>arrayli=new ArrayList<String>();		//cell
			for(int j=0;j<al.size();j++) {
				int spaceTotal=(size[j]-al.get(j).getBytes().length)+4;		//��ǰ�ֶ���Ҫ���ӵĿո�����	
				
				int spaceHalf=spaceTotal/2;										//��ǰ�ֶ����߸���ӿո�����
				String field="";
				for(int z=0;z<spaceTotal;z++) {
					
					if(z==spaceHalf){
						field+=al.get(j);
					}
						field+=" ";
					
				}
				
				arrayli.add(field);
				
			}
			all.add(arrayli);
		}
		return all;
	}
	private ArrayList<String> getTable(ArrayList<ArrayList<String>> aas){					//������ṹ�ļ��ϣ���ʹ�ü��ϱ�ɱ��
		return timelyPrint(aas, false);
	}
	public ArrayList<String> getTableFormat(ArrayList<ArrayList<String>> tableModel){
		all.clear();
		return getTable(format(tableModel));
	}
	public ArrayList<String> getTableFormat(ArrayList<ArrayList<String>> tableModel,boolean timelyPrint){
		all.clear();
		return timelyPrint(format(tableModel),timelyPrint);
	}
	public ArrayList<String> timelyPrint(ArrayList<ArrayList<String>> aas ,boolean timelyPrint) {				//���趨��ʱ��ʾģʽ��ÿ��ȡһ�оʹ�ӡһ��
		ArrayList<String>title=aas.get(0);
		String tit="";
		for(int i=0;i<title.size();i++) {										//ƴ���ֶ�
			tit+="+";
			for(int j=0;j<title.get(i).getBytes().length;j++) {
				tit+="=";
			}
		}
		tit+="+\r\n";
		for(int i=0;i<title.size();i++) {										
			tit+="#"+title.get(i);
		}
		tit+="#\r\n";
		for(int i=0;i<title.size();i++) {										
			tit+="+";
			for(int j=0;j<title.get(i).getBytes().length;j++) {
				tit+="=";
			}
		}			//ͷ�ֶ�ƴ�����
		tit+="+\r\n";
		if(timelyPrint) {
			System.out.print(tit);
		}
		all.add(tit);
		for(int z=1;z<aas.size();z++) {
			tit="";
			ArrayList<String>row=aas.get(z);
			for(int i=0;i<row.size();i++) {										
				tit+="|"+row.get(i);
			}
			tit+="|\r\n";
			for(int i=0;i<row.size();i++) {										//ƴ���ֶ�
				if(i==0) {
					tit+="|";
				}else {
					tit+="+";
				}
				for(int j=0;j<row.get(i).getBytes().length;j++) {
					tit+="-";
				}
			}
			tit+="|\r\n";
			if(timelyPrint) {
				System.out.print(tit);
			}
			all.add(tit);
		}
		return all;
	}
	public void showTable() {			//��ӡ���
		for(String al:all) {
			System.out.print(al);
		}
	}
	@Override
	public String toString() {
		String temp="";
		for(String al:all) {
			temp+=al;
		}
		return temp;
	}
	private int[] countMax(ArrayList<ArrayList<String>> aas) {
		int arrayLengh=0;
		for(ArrayList<String> row:aas) {
			if(row.size()>arrayLengh) {
				arrayLengh=row.size();
			}
		}
		int []size=new int[arrayLengh];						//ͳ���ֶ���
		for(int i=0;i<aas.size();i++) {					//��ѭ�����ڰ�ͳ��ÿ������ַ������ֽڽ���ͳ�ƣ���֤���и�ʽ
			for(int j=0;j<aas.get(i).size();j++) {
				if(size[j]<aas.get(i).get(j).getBytes().length) {
					size[j]=aas.get(i).get(j).getBytes().length;
				}
			}
		}
		return size;
	}
	
	
}
