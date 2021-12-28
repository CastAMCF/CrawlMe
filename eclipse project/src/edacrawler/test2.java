package edacrawler;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.batik.swing.JSVGCanvas;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

public class test2 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test2 frame = new test2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public test2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblImgNome = new JLabel("Ola");
		lblImgNome.setAlignmentY(0.0f);
		lblImgNome.setAlignmentX(0.5f);
		
		JSVGCanvas svg = new JSVGCanvas();
        svg.setURI("https://svgsilh.com/svg/1801287.svg");
        svg.setBounds(186, 65, 220, 220);
		
		JPanel panel = new JPanel();
		panel.setOpaque(true);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent paramMouseEvent) {
				
				panel.setBackground(new Color(0, 102, 255));
				lblImgNome.setForeground(new Color(255, 255, 255));
				
				/*if(!bool){
					lName = lblNewLabel;
					bool = true;
				}else {
					if(lName.getText().equals(Jimage.getText())) {
    					JOptionPane.showMessageDialog(null,"Unfortunately '.svg' format isn't supported." ,"Warning",1);
					}else {
						panel.setBackground(new Color(240, 240, 240));
						lblImgNome.setForeground(new Color(0, 0, 0));
						lName = lblNewLabel;
					}
				}*/
			}
		});
		panel.setBounds(186, 65, 220, 220);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(svg);
		panel.add(lblImgNome);
		contentPane.add(panel);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(32, 338, 97, 25);
		contentPane.add(btnNewButton);
	}

}
