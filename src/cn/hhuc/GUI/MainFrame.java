package cn.hhuc.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import cn.hhuc.graph.Polygon;
import cn.hhuc.util.FileReaderToPolygon;
import cn.hhuc.util.WriteFile;


public class MainFrame extends JFrame{
	private JMenuBar menuBar;
	private JMenu fileMenu,subMenu;
	private JMenuItem saveItem,openItem,setSpacingItem1,setSpacingItem3,setSpacingItem5;
	private FileDialog openDia,saveDia;
	private File file;
	private JButton btnClear,btnFill;
	private DrawPanel drawPanel;
	FileReaderToPolygon fileR;
	public static void main(String[] args) {
		new MainFrame();
	}
	public MainFrame(){
		this.setTitle("多边形填充画板！");
		this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar=new JMenuBar();
		fileMenu=new JMenu("文件");
		subMenu=new JMenu("设置");
		saveItem=new JMenuItem("保存");
		openItem=new JMenuItem("打开");
		setSpacingItem1=new JMenuItem("设置填充距离1px");
		setSpacingItem3=new JMenuItem("设置填充距离5px");
		setSpacingItem5=new JMenuItem("设置填充距离10px");
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		subMenu.add(setSpacingItem1);
		subMenu.add(setSpacingItem3);
		subMenu.add(setSpacingItem5);
		menuBar.add(fileMenu);
		menuBar.add(subMenu);
		this.setJMenuBar(menuBar);
		openDia=new FileDialog(this,"打开",FileDialog.LOAD);
		saveDia = new FileDialog(this,"保存",FileDialog.SAVE);
		ToolPanel toolPanel=new ToolPanel();
		this.add(toolPanel,BorderLayout.WEST);
		drawPanel=new DrawPanel();
		this.add(drawPanel,BorderLayout.CENTER);
		btnClear=toolPanel.getBtnClear();
		btnFill=toolPanel.getBtnFill();
		fileR=new FileReaderToPolygon();
		addEvent();
		this.setVisible(true);
	}
	private void addEvent() {
		btnClear.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				
				drawPanel.clear();
				//System.out.println("clear");
			}
		});
		btnFill.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				drawPanel.fill();
				
			}
		});
		// TODO 自动生成的方法存根
        saveItem.addActionListener(new ActionListener()  
        {  
            //设置保存文件的功能  
            public void actionPerformed(ActionEvent e)  
            {  
                if(file == null)//文件不存在情况下 创建文件  
                {  
                    saveDia.setVisible(true);  
                    String dirPath = saveDia.getDirectory();  
                    String fileName = saveDia.getFile();
                    if(dirPath==null||fileName==null){
                    	return;
                    }
                    File file = new File(dirPath,fileName);
                    WriteFile wf=new WriteFile();
                    boolean b=wf.writePolygon(file,drawPanel.pList);
                    if(b==true){
                    	System.out.println("写文件成功!");
                    }
                }
            }  
        });
        openItem.addActionListener(new ActionListener()  

        {  
            //设置打开文件功能  
            public void actionPerformed(ActionEvent e)  
            {  
                openDia.setVisible(true);  
                String dirPath = openDia.getDirectory();//获取文件路径  
                String fileName = openDia.getFile();//获取文件名称  
                System.out.println(dirPath +"++"+ fileName);
                if(dirPath==null||fileName==null){
                	return;
                }
                else{
                	List<Polygon> pList=fileR.readPlygon(dirPath+fileName);
                	drawPanel.pList=pList;
                	drawPanel.paintpolygon();
                }
            }  
        });  
        setSpacingItem1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				drawPanel.space=1;
			}
		});
        setSpacingItem3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				drawPanel.space=5;
			}
		});
        setSpacingItem5.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				drawPanel.space=10;
			}
		});

	}

}
