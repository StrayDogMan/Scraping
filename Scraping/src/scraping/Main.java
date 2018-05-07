package scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.select.Elements;

public class Main {
	static boolean debug = true;

	//気象庁のURL
	static String url = "http://www.data.jma.go.jp/obd/stats/etrn/select/";

	static String getAreaApp = "prefecture00.php";//都道府県選択
	static String getPointApp = "prefecture.php";//地点選択
	static String getHourApp = "";//一時間おきのデータ


	public static void main(String args[]) throws IOException{
//		String testURL = "http://www.data.jma.go.jp/obd/stats/etrn/view/hourly_a1.php?prec_no=43&block_no=0363&year=2018&month=1&day=1&view=";
//		Scraping sc = new Scraping(testURL);
//		Elements el = sc.getDocument().select("tr.mtx");
//		el = el.select("th");
//		String data[] = sc.getElementsTextArray(el);
//
//		for(int i = 0;i < data.length;i++){
//			System.out.println(data[i]);
//		}


	}

	static void init() throws IOException {
		List<String[]> Prefectures = getPrec_no();
		String tempArray[] = Prefectures.get(0);
		String para[][] = blockPrameter(tempArray[1]);

		StoreData.outputCsv("data.csv", "Shift-JIS", para);

		for(int i = 0;i < para.length;i++) {
			for(int j = 0;j < para[i].length;j++) {

				System.out.print(para[i][j]+" ");
			}
			System.out.println();
		}
	}

	//都道府県取得
	static List<String[]> getPrec_no(){
		//都道府県取得
		Scraping sc = new Scraping(url+getAreaApp);//get document
		Elements el = sc.getDocument().select("area");//select id
		List<String> areaHref[] = new ArrayList[2];//to store list
		for(int i = 0;i<areaHref.length;i++){
			areaHref[i] = new ArrayList<String>();
		}
		areaHref[0] = sc.getElementsAttrList(el, "alt");//都道府県
		areaHref[1] = sc.getElementsAttrList(el, "href");//URL

		List<String[]> temp = StoreData.toArrayConnectedList(areaHref);
		return temp;
	}


	static String[][] blockPrameter(String prec_no) throws IOException{

		//地点data 取得
		Scraping sc = new Scraping(url +prec_no);
		Elements el = sc.getDocument().select("area");
		List<String> data = sc.getElementsAttrList(el, "href");//access url data
		String onclick[] = sc.getElementsAttrArray(el, "onmouseover");//詳細データ
		for(int k =0;k< onclick.length;k++){
			//trim data
			onclick[k] =  onclick[k].replace("javascript:viewPoint('", "");
			onclick[k] =  onclick[k].replace(");", "");
			onclick[k] =  onclick[k].replace("'", "");
			onclick[k] =  onclick[k].replace(",", " ");
			onclick[k] =  onclick[k].trim();
		}

		int len = data.size();
		for(int j = 0;j<len;j++){

			if(data.get(j).indexOf("block_no=00&") >= 0){//「全地点」データ削除
//					pointName.remove(j);
				data.remove(j);
				if(j != 0){
					j--;
				}
				len = data.size();
			}else if(data.get(j).indexOf("index") == -1){//隣の地方のデータ削除
//					pointName.remove(j);
				data.remove(j);
				if(j != 0){
					j--;
				}
				len = data.size();
			}else{
				data.set(j,data.get(j).replace("year=&month=&day=&view=", ""));
				data.set(j,data.get(j).replace("../", ""));
			}
		}

		String dataArray[] = data.toArray(new String[data.size()]);
		String onClickArray[][] = new String[onclick.length][onclick[0].split(" ").length];
		String returnData[][] = new String[data.size()][1 + onClickArray[0].length];

		for(int i = 0;i < data.size();i++) {
			returnData[i][0] = dataArray[i];
			String onClickSplit[] = onclick[i].split(" ");
			for(int j  =0;j < onClickSplit.length;j++) {
				returnData[i][j+1] = onClickSplit[j];
			}
		}
		return returnData;
	}

	static void getData() {

	}

}
