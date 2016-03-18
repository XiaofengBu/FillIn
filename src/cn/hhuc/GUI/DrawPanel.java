package cn.hhuc.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.ImageGraphicAttribute;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.hhuc.graph.Edge;
import cn.hhuc.graph.EdgeSet;
import cn.hhuc.graph.Polygon;
import cn.hhuc.util.FileReaderToPolygon;

public class DrawPanel extends JPanel {
	private int startX=-1, startY=-1, endX=-1, endY=-1,firstX=-1,firstY=-1;
	private Graphics og,imageGraphics;
	private BufferedImage image;
	private Polygon polygon;
	public List<Polygon> pList;
	public int space;
	public DrawPanel(){
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(Color.WHITE);
		this.setVisible(true);
	    this.addEvents();
	    //polygon=new Polygon();
	    pList=new ArrayList<Polygon>();
	    space=1;
	}
	public void addEvents(){
		this.addMouseMotionListener(new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			public void mouseDragged(MouseEvent me) {
				// TODO 自动生成的方法存根	
				if(firstX!=-1&&firstY!=-1){
					endX=me.getX();
					endY=me.getY();
					repaint();
					//System.out.println("dr");
				}

			}
		});
		this.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent me) {
				// TODO 自动生成的方法存根
				if(me.getButton()==MouseEvent.BUTTON1){
					endX=me.getX();
					endY=me.getY();
					og.drawLine(startX,startY,endX,endY);
					imageGraphics.drawLine(startX,startY,endX,endY);
					polygon.insertPoint(startX, startY, endX, endY);
					//System.out.println(startX+" "+startY+" "+endX+" "+endY);

					//System.out.println("re");
				}
				
			}
			
			public void mousePressed(MouseEvent me) {
				// TODO 自动生成的方法存根
				if(me.getButton()==MouseEvent.BUTTON1){
					if(firstX==-1||firstY==-1){
						firstX=startX=me.getX();
						firstY=startY=me.getY();
						polygon=new Polygon();
					}
					else{
						startX=endX;
						startY=endY;
					}
				}
			}
			
			public void mouseExited(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			public void mouseEntered(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			public void mouseClicked(MouseEvent me) {
				// TODO 自动生成的方法存根
				if(me.getButton()==MouseEvent.BUTTON3){
					if(firstX!=-1&&firstY!=-1){
						startX=endX;
						startY=endY;
						endX=firstX;
						endY=firstY;
						og.drawLine(startX,startY,endX,endY);
						imageGraphics.drawLine(startX,startY,endX,endY);
						polygon.insertPoint(startX, startY, endX, endY);
						//System.out.println(startX+" "+startY+" "+endX+" "+endY);
						System.out.println(polygon.bottomY);
						System.out.println(polygon.topY);
						for(int key:polygon.polygon.keySet()){
							System.out.println(key);
							EdgeSet e=polygon.polygon.get(key);
							for(Edge edge:e.edgeSet){
								System.out.println("下顶点:"+edge.x1+" "+edge.y1);
								System.out.println("上顶点:"+edge.x2+" "+edge.y2);
								System.out.println(edge.dx);
							}
						}
						pList.add(polygon);
						repaint();
						firstX=firstY=-1;
					}
				}				
			}
		});
	}
	public void paint(Graphics g){
		if(og==null||imageGraphics==null){
			super.paint(g);
		    image = (BufferedImage) this.createImage(this.getWidth(),this.getHeight());
		    imageGraphics = image.getGraphics();
		    image.setRGB(0, 0, 0);
			og=g;
			//System.out.println("paint1");
		}
		else{
			super.paint(g);
			g.drawImage(image, 0, 0,Color.WHITE, null);
			g.setColor(Color.red);
			g.drawLine(startX,startY,endX,endY);
			System.out.println("paint2");
		}
	}
	public void clear(){
		og.clearRect(0,0,this.getWidth(),this.getHeight());
		og=null;
		imageGraphics=null;
		image=null;
		firstX=firstY=endX=endY=startX=startY=-1;
		if(pList.size()>0){
			polygon.polygon.clear();
		}
		pList.clear();
		repaint();
		//System.out.println("cc");
	}
	public void fill(){
		long startTime=System.currentTimeMillis();   //获取开始时间
		for(Polygon polygon:pList){
			this.fillPolygon(polygon);
		}		
		repaint();
		long endTime=System.currentTimeMillis(); //获取结束时间
		JOptionPane.showMessageDialog(null, "运行时间:"+(endTime-startTime)+"ms\r\n消耗内存:"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024/1024+"m"); 
	}
	//单个多边形填充
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
			//测试用输出焦点 划线
			int count=0;
			int startx=0;
			int endx=0;
			for(Iterator it=intersectionPoints.iterator();it.hasNext();){
				int pointValue=(Integer) it.next();
				//System.out.print(pointValue+" ");
				if(count%2==0&&y%space==0){
					startx=pointValue;
				}
				else if(y%space==0){
					endx=pointValue;
					imageGraphics.drawLine(startx, y, endx, y);
					og.drawLine(startx, y, endx, y);
					og.setColor(Color.BLUE);
					imageGraphics.setColor(Color.BLUE);
					System.out.print(startx+" "+y+" "+endx+" "+y+" ");
				}
				count++;
			}
			System.out.println(y);
			intersectionPoints.clear();
			System.out.println();
		}

	}
	public void paintpolygon(){
		for (Polygon polygon : pList) {
			for (Entry<Integer, EdgeSet> entry : polygon.polygon.entrySet()) {
				System.out.println(entry.getKey());
				EdgeSet e = entry.getValue();
				for (Edge edge : e.edgeSet) {
					System.out.println("下顶点:" + edge.x1 + " " + edge.y1);
					System.out.println("上顶点:" + edge.x2 + " " + edge.y2);
					this.og.drawLine(edge.x1, edge.y1, edge.x2, edge.y2);
					this.imageGraphics.drawLine(edge.x1, edge.y1, edge.x2, edge.y2);
				}
			}
		}
		repaint();
	}
}
