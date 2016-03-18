package cn.hhuc.GUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolPanel extends JPanel {
	private JButton btnClear,btnFill;
	public JButton getBtnClear() {
		return btnClear;
	}
	public ToolPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1,4,10));
		btnClear=new JButton("清除");
		btnFill=new JButton("填充");
		panel.add(btnClear);
		panel.add(btnFill);
		this.add(panel);
		this.setVisible(true);
	}
	public JButton getBtnFill() {
		return btnFill;
	}

}
