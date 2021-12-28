package edacrawler;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.batik.swing.JSVGCanvas;

import javax.swing.JTextField;
import javax.swing.JButton;

public class test extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("SVGView");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new SVGPanel("https://svgsilh.com/svg/1801287.svg"));
        frame.setSize(770, 540);
        frame.setVisible(true);
	}
}
