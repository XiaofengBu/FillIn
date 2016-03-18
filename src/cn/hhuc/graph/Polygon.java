package cn.hhuc.graph;

import java.util.HashMap;
import java.util.HashSet;

public class Polygon {
	public int bottomY=-1;
	public int topY=-1;
	public HashMap<Integer, EdgeSet> polygon;
	public Polygon(){
		polygon=new HashMap<Integer, EdgeSet>();
	}
	public void insertPoint(int x1,int y1,int x2,int y2){
		Edge edge=new Edge(x1, y1, x2, y2);
		int key=edge.y1;
		if(bottomY<0||bottomY<key){
			bottomY=key;
		}
		if(topY<0||topY>edge.y2){
			topY=edge.y2;
		}
		if(polygon.get(key)!=null){
			EdgeSet edgeSet=polygon.get(key);
			edgeSet.edgeSet.add(edge);
		}
		else{
			EdgeSet edgeSet=new EdgeSet();
			edgeSet.edgeSet.add(edge);
			polygon.put(key, edgeSet);
		}
	}

}
