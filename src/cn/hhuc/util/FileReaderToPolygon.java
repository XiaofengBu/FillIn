package cn.hhuc.util;

import cn.hhuc.graph.Edge;
import cn.hhuc.graph.EdgeSet;
import cn.hhuc.graph.Polygon;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class FileReaderToPolygon {
	public List<Polygon> pList;
	public FileReaderToPolygon(){
		pList=new ArrayList<Polygon>();
	}
	public List<Polygon> readPlygon(String filePath){
		try {
			FileReader fr=new FileReader(filePath);
			BufferedReader br=new BufferedReader(fr);
			String num=null;
			Polygon polygon=new Polygon();
			while((num=br.readLine())!=null){
				if(num.equals("|")){
					pList.add(polygon);
					polygon=new Polygon();
				} else {
					String[] point = num.split("\\|");
					polygon.insertPoint(Integer.parseInt(point[0]),
							Integer.parseInt(point[1]),
							Integer.parseInt(point[2]),
							Integer.parseInt(point[3]));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return pList;
	}

}
