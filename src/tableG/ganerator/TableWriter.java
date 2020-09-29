package tableG.ganerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.security.auth.Subject;

public class TableWriter{
	TableFormat tf;
	public TableWriter(String test) {
		this();
	}
	public static void main(String[] args) {
		new TableWriter();
	}
	public TableWriter(){
		tf=new TableFormat();
		boolean envCreate=env();
		File env=new File("tableGaneratorDir");
		File[] table=env.listFiles();
		System.out.println("�ҵ������ļ�:");
		for(int i=0;i<table.length;i++) {
			if(table[i].isFile()&&table[i].getName().indexOf(".")!=-1) {
				System.out.println("#["+i+"]---------["+table[i].getName()+"]");
			}
		}
		String operat=null;
		Scanner sc=new Scanner(System.in);
		while(true) {
			if(envCreate) {
				
				while(true) {
					System.out.println("��ѡ��������ͣ�\t�༭ģʽ(E)\t���ɱ��(G)\tȫ������(A)\t����ɨ��(R)\t�˳�(Q)");
					operat=sc.nextLine();
					if(!operat.equals("")) {
						break;
					}
				}
				if(operat.equalsIgnoreCase("g")||operat.equalsIgnoreCase("generate")) {
					while(true) {
						System.out.println("�������ļ���ţ�ȡ��(Q)");
						try {
							String d=sc.nextLine();
							if(d.equalsIgnoreCase("q")) {
								break;
							}
							int b=Integer.parseInt(d.trim());
							if(b<0||b>table.length-1) {
								System.out.println("��������ȷ���ļ���ţ�");
								continue;
							}
							File f=table[b];
							if(!f.isFile()) {
								System.out.println("�޷�����Ŀ¼���������ļ���Ӧ�ı�ţ�");
								continue;
							}
							FileReader fr=new FileReader(f);
							BufferedReader buf=new BufferedReader(fr);
							String temp="";
							String s=null;
							while((s=buf.readLine())!=null) {
								temp+=s+"\n";
							}
							buf.close();
							fr.close();
							ArrayList<String>al=tf.getTableFormat(tf.comp(temp));
							for(String a:al) {
								System.out.print(a);
							}
							System.out.println("ȷ�����뵽�ļ���:ȷ��(y) ȡ��(another key)�����ȷ����ʷ�汾�ѱ���");
							operat=sc.nextLine().trim();
							if(operat.equalsIgnoreCase("y")||operat.equalsIgnoreCase("ȷ��")) {
								FileWriter file=new FileWriter("tableGaneratorDir/compd/"+f.getName());
								for(String a:al) {
									file.write(a);
								}
								file.close();
								
								System.out.println("�������");
								break;
							}else {
								break;
							}
						}catch(Exception e) {
							sc=new Scanner(System.in);
							e.printStackTrace();
							System.out.println("�����ԣ�");
						}
					}
					
				}else if(operat.equalsIgnoreCase("a")||operat.equalsIgnoreCase("all")) {
					for(File f:table) {
						if(f.isFile()&&f.getName().indexOf(".")!=-1) {
							String temp="";
							try {
								FileReader fr=new FileReader(f);
								BufferedReader bff=new BufferedReader(fr);
								
								String curr=null;
								while((curr=bff.readLine())!=null) {
									temp+=curr+"\r\n";
								}
								bff.close();
								fr.close();
							} catch (Exception e) {}
							FileWriter fw=null;
							try {
								fw = new FileWriter("tableGaneratorDir/compd/"+f.getName());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ArrayList<String>al=tf.getTableFormat(tf.comp(temp));
							
							try {
								for(String a:al) {
									fw.write(a);
								}
								fw.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("�������");
						}
						
					}
				}else if(operat.equalsIgnoreCase("r")||operat.equalsIgnoreCase("refresh")) {
					table=env.listFiles();
					System.out.println("�ҵ������ļ�:");
					for(int i=0;i<table.length;i++) {
						if(table[i].isFile()&&table[i].getName().indexOf(".")!=-1) {
							System.out.println("#["+i+"]---------["+table[i].getName()+"]");
						}
					}
					continue;
				}else if(operat.equalsIgnoreCase("q")||operat.equalsIgnoreCase("quit")) {
					break;
				}else if(operat.equalsIgnoreCase("e")||operat.equalsIgnoreCase("edit")) {
					ArrayList<String> content=new ArrayList<>();
					System.out.println("���༭������\t\\help����");
					while(true) {
						String tem=sc.nextLine().trim();
						if(tem.equalsIgnoreCase("\\q")) {
							break;
						}else if(tem.equalsIgnoreCase("\\help")){
							System.out.println("\\q\t\t�˳�");
							System.out.println("\\s\t\t�鿴���");
							System.out.println("\\w\t\t��������");
							System.out.println("\\ww\t\t����Դ�ͱ��");
						}else if(tem.equalsIgnoreCase("\\s")) {
							tf.getTableFormat(tf.comp(content, true));
							tf.showTable();
							System.out.println("\n\n--------------------------------->>\n\n");
						}else if(tem.equalsIgnoreCase("\\w")) {
							save(sc,false,content);
						}else if(tem.equalsIgnoreCase("\\ww")) {
							save(sc,true,content);
						}else if(!tem.trim().equals("")){
							content.add(tem);
						}
					}
				}
			}
		}
	}
	public void save(Scanner sc,boolean saverec,ArrayList<String> content) {
		String name="";
		while(true) {
			System.out.println("�����뱣����ļ�����\tȡ����Q��");
			name=sc.nextLine().trim();
			if(name.equalsIgnoreCase("q")) {
				break;
			}else if(name.equalsIgnoreCase("")){
				continue;
			}
			System.out.println("�Ƿ����������\t��(Y)\t��(another key)");
			String index=sc.nextLine().trim();
			if(index.equalsIgnoreCase("y")) {
				tf.getTableFormat(tf.comp(content,true));
			}else {
				tf.getTableFormat(tf.comp(content));
			}
			
			FileWriter fo=null;
			FileWriter rec=null;
			try {
				fo=new FileWriter("tableGaneratorDir/compd/"+name);
				fo.write(tf.toString());
				System.out.println("д�����");
				if(saverec) {
					rec=new FileWriter("tableGaneratorDir/"+name);
					String re="";
					for(String ree:content) {
						re+=ree+"\r\n";
					}
					rec.write(re);
					rec.close();
				}
			} catch (Exception e) {
				System.out.println("�������������ԣ�");
			}finally {
				try {
					fo.close();
				} catch (IOException e) {}
			}
			break;
		}
	}
	public boolean env() {
		//���������ļ���
		File env=new File("tableGaneratorDir");
		if(env.exists()) {
			File access=new File(env,"this is flag file,don't remove it!");
			if(access.exists()) {
				return true;
			}else {
				System.out.print("�뱣֤��ǰĿ¼��û�������tableGaneratorDir,������ת�Ƶ�����Ŀ¼���²���");
				return false;
			}
		}else {
			env.mkdir();
			new File(env,"compd").mkdir();
			try {
				File access=new File(env,"this is flag file,don't remove it!");
				access.createNewFile();
				FileWriter fw=new FileWriter(access);
				fw.write("�벻Ҫɾ�����ļ�����ĸ��ļ���\r\ndon't remove this file or modify the file pleace!");
				fw.close();
			} catch (IOException e) {}
			
		}
		return true;
	}
	
}
