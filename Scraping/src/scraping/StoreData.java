package scraping;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class StoreData {

	public static void outputCsv(String FileName, double Data[][]){
		if(FileName.indexOf(".csv") != -1){
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(FileName, false));

				for(int i=0;i<Data.length;i++){
					pw.print(i+1);
					pw.print(",");
					for(int j=0;j<Data[i].length;j++){
						pw.print(Data[i][j]);
						pw.print(",");
					}
					pw.println();
				}
				pw.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}else{
			System.out.println("拡張子を確認してください");
		}
	}

	public static void outputCsv(String FileName, String Data[][]){
		if(FileName.indexOf(".csv") != -1){
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(FileName, false));

				for(int i=0;i<Data.length;i++){
					pw.print(i+1);
					pw.print(",");
					for(int j=0;j<Data[i].length;j++){
						pw.print(Data[i][j]);
						pw.print(",");
					}
					pw.println();
				}
				pw.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}else{
			System.out.println("拡張子を確認してください");
		}
	}


	void insertColumn(PrintWriter PW, String clumn[]){
		for(int i=0; i<clumn.length;i++){
			PW.print(clumn[i]);
			PW.print(",");
		}
		PW.println();
	}

		// connect array
	public static double[][] connectArray(double[][] data1, double[][] data2){
		double[][]returnData =null;
		if(data1[0].length== data2[0].length){//配列１と配列２の要素数を比較
			int len = data1.length+data2.length;
			returnData = new double[len][data1[0].length];

			//配列１を挿入
			for(int i=0; i<data1.length;i++){
				for(int j=0;j<data1[i].length;j++){
					returnData[i][j] = data1[i][j];
				}
			}

			//配列２を挿入
			for(int i=0;i<data2.length;i++){
				for(int j=0;j<data2[i].length;j++){
					returnData[i+data1.length][j] = data2[i][j];
				}
			}
		}else{
			System.out.println("differ array amount.");
		}
		return returnData;
	}

	// connect array
public static String[][] connectArray(String[][] data1, String[][] data2){
	String[][]returnData =null;
	if(data1[0].length== data2[0].length){//配列１と配列２の要素数を比較
		int len = data1.length+data2.length;
		returnData = new String[len][data1[0].length];

		//配列１を挿入
		for(int i=0; i<data1.length;i++){
			for(int j=0;j<data1[i].length;j++){
				returnData[i][j] = data1[i][j];
			}
		}

		//配列２を挿入
		for(int i=0;i<data2.length;i++){
			for(int j=0;j<data2[i].length;j++){
				returnData[i+data1.length][j] = data2[i][j];
			}
		}
	}else{
		System.out.println("differ array amount.");
	}
	return returnData;
}

	public static List<String[]> toArrayConnectedList(List<String> list[]){

		//size error check
		for(int i = 1;i < list.length;i++){
			if(list[0].size() != list[i].size()){
				System.out.println("toArrayConnectedList():size error");
				return null;
			}
		}

		//connect list
		List<String[]> returnData = new ArrayList<String[]>();
		for(int i = 0;i < list[0].size();i++){
			String temp[] = new String[list.length];
			for(int j = 0;j < list.length;j++){
				temp[j] = list[j].get(i);
			}

			returnData.add(temp);
		}

		return returnData;
	}

}

class DB{
	static String p; //DB price
	static String f; //DB file name
	Connection con=null;
	DatabaseMetaData dm=null;
	Statement st=null;

	//constracter
	public DB(String place,String file){
		p =place;
		f =file;
	}

	//DB connection
	public boolean DBconnection(){
		try{
			Class.forName("org.sqlite.JDBC"); //JDBC
			con =DriverManager.getConnection("jdbc:sqlite:"+p+f); //connect dbms
			dm=con.getMetaData();
			st=con.createStatement();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public void DBclose(){
		try {
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	public boolean booleanExistTable(String tableName){
		boolean judge = false;
		try{
			ResultSet tb=dm.getTables(null, null, tableName, null);
			if(tb.next()){
				judge=true;
			}else{
				judge=false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return judge;
	}


	//create table for INTEGER
	public void creTableIn(String tableName,String column){
		try {

			st.executeUpdate("CREATE TABLE "+tableName+" ( "+column+" INTEGER "+" ) ");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
		}
	}
	//create table for REAL
	public void creTableFlo(String tableName,String column){
		try {
			st.executeQuery("CREATE TABLE "+tableName+" ( "+column+" REAL "+" ) ");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
		}
	}

	//create table for text
	public void creTableTxt(String tableName,String column){
		try {
			st.executeQuery("CREATE TABLE "+tableName+" ( "+column+" TEXT "+" ) ");
		} catch (SQLException e) {
		}
	}

	//add column for integer
	public void addColumnIn(String tableName,String column){
		try {
			st.executeUpdate("ALTER TABLE "+tableName+" ADD COLUMN "+column+"[ INTEGER] ");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	//add column for real
	public void addColumnFlo(String tableName,String column){
		try {
			st.executeUpdate("ALTER TABLE "+tableName+" ADD COLUMN "+column+"[ REAL] ");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//add column for text
	public void addColumnTxt(String tableName,String column){
		try {
			st.executeUpdate("ALTER TABLE "+tableName+" ADD COLUMN "+column+"[ TEXT] ");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//insert data
	public void addData(String tableName,String columns,String data){
		try {
			st.executeUpdate("INSERT INTO "+tableName+" ( "+columns+" ) "+" VALUES "+" ( "+data+" ) ");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
