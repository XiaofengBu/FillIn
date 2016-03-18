package cn.hhuc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import cn.hhuc.graph.Edge;
import cn.hhuc.graph.EdgeSet;
import cn.hhuc.graph.Polygon;

public class WriteFile {
	public boolean writePolygon(File file,List<Polygon> pList){
		
        try {
			BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
			String text="";
			for(Polygon polygon:pList){
				for(Entry<Integer, EdgeSet> entry:polygon.polygon.entrySet()){
					System.out.println(entry.getKey());
					EdgeSet e=entry.getValue();
					for(Edge edge:e.edgeSet){
						System.out.println("下顶点:"+edge.x1+" "+edge.y1);
						System.out.println("上顶点:"+edge.x2+" "+edge.y2);
						System.out.println(edge.dx);
						text=text+edge.x1+"|"+edge.y1+"|"+edge.x2+"|"+edge.y2+"\r\n";
					}
				}
				text=text+"|"+"\r\n";
			}
			bufw.write(text);             
            bufw.close();  
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			return false;
		}
		return true;
	}

}
