package cn.hhuc.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import cn.hhuc.GUI.DrawPanel;
import cn.hhuc.graph.Edge;
import cn.hhuc.graph.EdgeSet;
import cn.hhuc.graph.Polygon;

public class Test {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		FileReaderToPolygon fp=new FileReaderToPolygon();
/*		Polygon polygon=fp.readPlygon("data/data.txt");
		System.out.println(polygon.bottomY);
		System.out.println(polygon.topY);
		for(Entry<Integer, EdgeSet> entry:polygon.polygon.entrySet()){
			System.out.println(entry.getKey());
			EdgeSet e=entry.getValue();
			for(Edge edge:e.edgeSet){
				System.out.println("下顶点:"+edge.x1+" "+edge.y1);
				System.out.println("上顶点:"+edge.x2+" "+edge.y2);
				System.out.println(edge.dx);
			}
		}
		Test t=new Test();
		t.fillPolygon(polygon);*/
	}
	public void fillPolygon(Polygon polygon){
		//相交边集合
		List<Edge> intersectionEdges=new ArrayList<Edge>();
		//相交点集合
		List<Integer> intersectionPoints=new ArrayList<Integer>();
		for(int y=polygon.bottomY;y>=polygon.topY;y--){
			//判断是否有边加入
			if(polygon.polygon.get(y)!=null){
				EdgeSet edgeSet=polygon.polygon.get(y);
				for(Edge edge:edgeSet.edgeSet){
					//焦点初始化
					edge.xNow=edge.x1;
					intersectionEdges.add(edge);
				}
			}
			//判断是否右边删除
/*			for(Edge edge:intersectionEdges){
				//如果上顶点==y 并且这个点不是极值,从列表删除边 保证交点出现一次
				if(edge.y2==y&&polygon.polygon.get(y)!=null){
					intersectionEdges.remove(edge);
				}
				else if(edge.y2<y){
					intersectionEdges.remove(edge);
				}
			}*/
			for(Iterator it=intersectionEdges.iterator();it.hasNext();){
				//如果上顶点==y 并且这个点不是极值,从列表删除边 保证交点出现一次
				Edge edge=(Edge) it.next();
				if(edge.y2==y&&polygon.polygon.get(y)!=null){
					it.remove();
				}
				else if(edge.y2>y){
					it.remove();
				}
			}
			//寻找焦点
			for(Edge edge:intersectionEdges){
				//求焦点 方法1
				edge.xNow=edge.x1-edge.dx*(edge.y1-y);
				//方法二
				//edge.xNow=edge.xNow-edge.dx;
				intersectionPoints.add((int) (edge.xNow+0.5));
			}
			//焦点集排序
			Collections.sort(intersectionPoints);
			//测试用输出焦点
			for(Integer i:intersectionPoints){
				System.out.print(i+" ");
			}
			System.out.println(y);
			intersectionPoints.clear();
			System.out.println();
		}

	}
}
