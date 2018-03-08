package scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class Scraping {
	private boolean debug = false;
	private String uri = "";
	private Document document = null;

	public Scraping(String URL){
		uri = URL;
		try {
			document = Jsoup.connect(uri).get();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void setURL(String URL){
		uri = URL;
		try {
			document = Jsoup.connect(uri).get();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	String getURL(){
		return uri;
	}

	Document getDocument(){
		return document;
	}

	//get text list
	 List<String> getElementsTextList(Elements elements){
		List<String> list = new ArrayList<String>();

		//get text value
		for(Element element: elements){
			list.add(element.text());
		 }

		return list;
	}

		//get text list
	 String[] getElementsTextArray(Elements elements){
		List<String> list = new ArrayList<String>();

		//get text value
		for(Element element: elements){
			list.add(element.text());
		 }

		return list.toArray(new String[list.size()]);
	}

	//get list
	 List<String> getElementsAttrList(Elements elements, String attr){
		 if(debug)
			 System.out.println("getElementsAttrList");
		List<String> list = new ArrayList<String>();

		//get attr value
		for(Element element: elements){
			list.add(element.attr(attr).toString());
		 }
		list = new ArrayList<String>(new LinkedHashSet<>(list));//重複削除
		list.removeAll(Collections.singleton(""));//空行削除


		return list;
	}

		//get list
	 String[] getElementsAttrArray(Elements elements, String attr){
		List<String> list = new ArrayList<String>();

		//get attr value
		for(Element element: elements){
			list.add(element.attr(attr).toString());
		 }
		list = new ArrayList<String>(new LinkedHashSet<>(list));//重複削除
		list.removeAll(Collections.singleton(""));//空行削除
		return list.toArray(new String[list.size()]);
	}

	 List<String> getElementsList(Elements elements){
			List<String> list = new ArrayList<String>();
		 //get attr value
			for(Element element: elements){
				list.add(element.toString());
			 }

			list = new ArrayList<String>(new LinkedHashSet<>(list));//重複削除
			list.removeAll(Collections.singleton(""));//空行削除

			return list;
	 }

	 String[] getElementsArray(Elements elements){
			List<String> list = new ArrayList<String>();
		 //get attr value
			for(Element element: elements){
				list.add(element.toString());
			 }

			list = new ArrayList<String>(new LinkedHashSet<>(list));//重複削除
			list.removeAll(Collections.singleton(""));//空行削除

			return list.toArray(new String[list.size()]);
	 }

}
