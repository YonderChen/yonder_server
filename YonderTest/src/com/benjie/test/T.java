package com.benjie.test;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author cyd
 * @date 2015-6-17
 */
public class T {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("hellow world");
		frame.setVisible(true);
		JPanel panel = new JPanel();
		frame.setBounds(500, 300, 500, 300);
		PopupMenu menu = new PopupMenu("menu");
		menu.setEnabled(true);
		MenuItem item1 = new MenuItem("item1");
		item1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("click item 1");
			}
		});
		menu.add(item1);
		MenuItem item2 = new MenuItem("item2");
		item2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("click item 2");
			}
		});
		menu.add(item2);
		MenuItem close = new MenuItem("close");
		item2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("click close");
				Runtime.getRuntime().exit(0);
			}
		});
		menu.add(close);
		panel.add(menu);
		frame.add(panel);
		panel.setVisible(true);
	}

}
