package GUI;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import org.apache.batik.swing.JSVGCanvas;

import de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel;
import edacrawler.API;
import edacrawler.Payload;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	private JPanel contentPane;
	private JTextField textLink;
	private JTextField textTema;
	private static JPanel panel;
	private static JButton btnFiltrar;
	private static JProgressBar progressBar;
	private static boolean bool = false;
	private static int width = 0;
	private static int height = 0;
	private static int iVer = 1;
	private Thread th;
	private LinkedHashMap<String, ArrayList<String>> Himgs;
	private static JLabel lName;
	private static JPanel pName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());
		} catch (ParseException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
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
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1160, 728);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent paramMouseEvent) {
				bool = false;
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane jScrollPane1 = new JScrollPane();
		jScrollPane1.setBounds(12, 156, 1130, 524);
		contentPane.add(jScrollPane1);
		
		panel = new JPanel();
		jScrollPane1.setViewportView(panel);
		/*DragLayout layout = new DragLayout();
		panel.setLayout(layout);*/
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblLink = new JLabel();
		lblLink.setText("Link :");
		lblLink.setBounds(20, 17, 40, 16);
		contentPane.add(lblLink);
		
		JLabel lblNvel = new JLabel();
		lblNvel.setText(" N\u00EDvel :");
		lblNvel.setBounds(12, 64, 48, 16);
		contentPane.add(lblNvel);
		
		JLabel lblStatus = new JLabel("Estado: Inativo");
		lblStatus.setVisible(false);
		lblStatus.setBounds(12, 127, 542, 16);
		contentPane.add(lblStatus);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(12, 127, 686, 16);
		contentPane.add(progressBar);
		
		JSlider Slider = new JSlider();
		Slider.setValue(1);
		Slider.setPaintTicks(true);
		Slider.setPaintLabels(true);
		Slider.setMinimum(1);
		Slider.setMaximum(12);
		Slider.setMajorTickSpacing(1);
		Slider.setBounds(62, 62, 420, 52);
		contentPane.add(Slider);
		
		JCheckBox chckbxMesmoDomnio = new JCheckBox("Mesmo dom\u00EDnio");
		chckbxMesmoDomnio.setBounds(566, 53, 132, 25);
		contentPane.add(chckbxMesmoDomnio);
		
		btnFiltrar = new JButton();
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread th2 = new Thread() {
	                @Override
	                public void run(){
	                	
	                	if(!textTema.getText().isEmpty()) {
	                		panel.removeAll();
	                		width = 0;
					    	height = 0;
					    	iVer = 1;
		                	for (String key : Himgs.keySet()) {
		                		//System.out.println("key: " + key + " value: " + Himgs.get(key));
		                		if(API.removerSpecialCharacter(key.toLowerCase()).contains(API.removerSpecialCharacter(textTema.getText().toLowerCase()))) {
		                			//System.out.println("key: " + key + " value: " + Himgs.get(key));
		                			
		                			addImageToPanel(Himgs, key);
		                		}else {
		                			//System.out.println("key: " + key + " value: " + Himgs.get(key));
		                			
		                			for (String value : Himgs.get(key)) {
		                				if(API.removerSpecialCharacter(value.toLowerCase()).contains(API.removerSpecialCharacter(textTema.getText().toLowerCase()))) {
		                					addImageToPanel(Himgs, key);
		                				}
		                			}
		                			
		                		}
		                	}
		                	progressBar.setValue(progressBar.getMaximum());
	                	}
	                	
	                }
				};th2.start();
			}
		});
		btnFiltrar.setEnabled(false);
		btnFiltrar.setText("Filtrar");
		btnFiltrar.setBounds(888, 92, 95, 25);
		contentPane.add(btnFiltrar);
		
		JButton btnSearch = new JButton();
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramActionEvent) {
				progressBar.setMaximum(100);
				
				th = new Thread() {
	                @Override
	                public void run(){
	                	//lblStatus.setForeground(new Color(255, 102, 0));
	                	//lblStatus.setText("Estado: Procurando as imagens...");
	                	progressBar.setValue(1);
	                	
						String link = textLink.getText();
				    	ArrayList<String> links = new ArrayList<>();
				    	links.add(link);
				    	ArrayList<Payload> payloads = new ArrayList<>();
				    	
				    	//ArrayList<String> imgsDu = new ArrayList<>();
				    	Himgs = null;
				    	for(Payload payl : API.getImages(link, links, Slider.getValue(), chckbxMesmoDomnio.isSelected(), payloads)) {
				    		progressBar.setValue(3);
				    		
				    		//imgsDu.addAll(payl.imgs);
				    		
				    		if(Himgs != null) {
				    			Himgs = API.removeDuplicates(payl.imgs, Himgs);
				    		}else {
				    			Himgs = payl.imgs;
				    		}
				    		//System.out.println(payl.imgs);
				    	}
				    	//ArrayList<String> imgs = API.removeDuplicates(imgsDu);
				    	//System.out.println(Himgs);
				    	
				    	//lblStatus.setText("Estado: 0\t/\t" + imgs.size());
				    	
				    	LinkedHashMap<String, ArrayList<String>> imgs = new LinkedHashMap<String, ArrayList<String>>();
				    	if(textTema.getText().isEmpty()) {
				    		imgs = Himgs;
				    		progressBar.setMaximum(imgs.size());
				    	}else {
				    		for (String key : Himgs.keySet()) {
		                		//System.out.println("key: " + key + " value: " + Himgs.get(key));
		                		if(API.removerSpecialCharacter(key.toLowerCase()).contains(API.removerSpecialCharacter(textTema.getText().toLowerCase()))) {
		                			//System.out.println("key: " + key + " value: " + Himgs.get(key));
		                			
		                			imgs.put(key, Himgs.get(key));
		                		}else {
		                			//System.out.println("key: " + key + " value: " + Himgs.get(key));
		                			
		                			for (String value : Himgs.get(key)) {
		                				if(API.removerSpecialCharacter(value.toLowerCase()).contains(API.removerSpecialCharacter(textTema.getText().toLowerCase()))) {
		                					imgs.put(key, Himgs.get(key));
		                				}
		                			}
		                			
		                		}
		                	}
				    		progressBar.setMaximum(imgs.size());
				    	}
				    	
				    	btnFiltrar.setEnabled(false);
				    	panel.removeAll();
				    	
				    	width = 0;
				    	height = 0;
				    	iVer = 1;
				    	for(String img : imgs.keySet()) {
				    		addImageToPanel(imgs, img);
				    	}
				    	
				    	//lblStatus.setForeground(new Color(51, 153, 0));
				    	//lblStatus.setText("Estado: Acabado");
					}
		        };th.start();
				
			}
		});
		
		btnSearch.setText("Procurar");
		btnSearch.setBounds(503, 90, 95, 25);
		contentPane.add(btnSearch);
		
		JButton btnCancel = new JButton();
		btnCancel.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				th.stop();
				//lblStatus.setForeground(new Color(204, 0, 0));
				//lblStatus.setText("Estado: Cancelado");
				progressBar.setValue(0);
			}
		});
		btnCancel.setText("Cancelar");
		btnCancel.setBounds(603, 90, 88, 25);
		contentPane.add(btnCancel);
		
		textLink = new JTextField();
		textLink.setBounds(62, 13, 636, 27);
		contentPane.add(textLink);
		
		JLabel lblTema = new JLabel("Tema");
		lblTema.setHorizontalAlignment(SwingConstants.CENTER);
		lblTema.setBounds(901, 17, 56, 16);
		contentPane.add(lblTema);
		
		textTema = new JTextField();
		textTema.setBounds(818, 53, 228, 27);
		contentPane.add(textTema);
		
		/*JLabel lblUrl = new JLabel("Url");
		lblUrl.setBounds(96, 93, 56, 16);
		contentPane.add(lblUrl);
		
		textField = new JTextField();
		textField.setBounds(96, 122, 225, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel btnNewButton = new JLabel();
		btnNewButton.setOpaque(true);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent paramMouseEvent) {
				btnNewButton.setBackground(new Color(0, 102, 255));
				btnNewButton.setForeground(new Color(255, 255, 255));
				
				if(!bool){
					bool = true;
				}else {
					String iLink = "";
					for (String url : ini.imgs) {
						if(url.endsWith(btnNewButton.getText())) {
							iLink = url;
							break;
						}
					}
					
					if (btnNewButton.getIcon() != null) {
			            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			            int w = (int)(screenSize.width * 0.75);
			            int h = (int)(screenSize.height * 0.75);
			            
			            BufferedImage image = null;
			    		try {
			    			image = ImageIO.read(new URL(iLink));
			    		} catch (IOException e) {
			    			// TODO Auto-generated catch block
			    			e.printStackTrace();
			    		}
			            
			            ImageIcon img = resizeProportional(new ImageIcon(image), w, h);
			            showImage(img);
			        }
				}
			}
		});
		btnNewButton.setBounds(145, 282, 220, 220);
		btnNewButton.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("New button");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					EDACrawler eda = new EDACrawler();
					ini = eda.process("http://orion.ipt.pt/");
					
					for (String url : ini.imgs) {
						
						setImageByPath(btnNewButton, url);
						
						btnNewButton.setText(url.split("/")[url.split("/").length-1]);
						
						break;
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setBounds(266, 529, 392, 87);
		contentPane.add(btnNewButton_1);*/
		
	}
	
	public static void addImageToPanel(LinkedHashMap<String, ArrayList<String>> imgs, String img) {
		
		JLabel Jimage;
		JLabel lblImgNome;
		JPanel panel1;
		
		//System.out.println(img);
		if(img.contains(".svg") || img.contains(".gif") || img.contains(".apng") || img.contains(".jpg") || img.contains(".png") || img.contains(".jpeg") || img.contains(".jfif")) {
			if(!img.endsWith(".svg")) {
    			//System.out.println(img);
    			
				String nome1 = "";
	    		if(img.split("/")[img.split("/").length-1].contains("?")) { nome1 = img.split("/")[img.split("/").length-1].split("\\?")[0]; }else{ nome1 = img.split("/")[img.split("/").length-1]; }
	    		final String nome2 = nome1;
	    		
				JPopupMenu popup = new JPopupMenu();
	    		JMenuItem down = new JMenuItem("Salvar imagem");
	    		down.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent arg0) {
	    				Thread th1 = new Thread() {
	    	                @Override
	    	                public void run(){
	    	                	try {
									API.saveImage(img, nome2);
									
									JOptionPane.showMessageDialog(null,"Imagem salva com sucesso" ,"Sucesso", JOptionPane.INFORMATION_MESSAGE);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									JOptionPane.showMessageDialog(null,"Houve um erro ao salvar a imagem." ,"Erro", JOptionPane.ERROR_MESSAGE);
								}
	    	                }
	    	            };th1.start();
	    			}
	    		});
	    		popup.add(down);
	    		
	    		if(nome1.length() > 20) { nome1 = nome1.substring(0, 15)+"..."+nome1.substring(nome1.lastIndexOf(".")-1, nome1.length()); }
	    		final String nome = nome1;
				
	    		Jimage = new JLabel();
	    		Jimage.setOpaque(true);
	    		Jimage.addMouseListener(new MouseAdapter() {
	    			@Override
	    			public void mouseClicked(MouseEvent paramMouseEvent) {
	    				if(SwingUtilities.isRightMouseButton(paramMouseEvent)) {
	    					if(pName != null) {
    							pName.setBackground(new Color(240, 240, 240));
    						}
	    					if(lName != null) {
	    						lName.setBackground(new Color(240, 240, 240));
	    						lName.setForeground(new Color(0, 0, 0));
	    						lName = Jimage;
	    					}
	    					
	    					Jimage.setBackground(new Color(0, 102, 255));
		    				Jimage.setForeground(new Color(255, 255, 255));
	    					
		    				lName = Jimage;
	    					bool = true;
		    				
	    					popup.show(Jimage, paramMouseEvent.getX(), paramMouseEvent.getY());
	    				}else if(SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
	    				
		    				Jimage.setBackground(new Color(0, 102, 255));
		    				Jimage.setForeground(new Color(255, 255, 255));
		    				
		    				if(!bool){
		    					lName = Jimage;
		    					bool = true;
		    				}else {
		    					if(lName.getIcon() != null && lName.getIcon().equals(Jimage.getIcon())) {
		    						String iLink = "";
			    					for (String url : imgs.keySet()) {
			    						if(url.equals(img)) {
			    							iLink = url;
			    							break;
			    						}
			    					}
			    					
			    					if (Jimage.getIcon() != null) {
			    			            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			    			            int w = (int)(screenSize.width * 0.75);
			    			            int h = (int)(screenSize.height * 0.75);
			    			            
										try {
											ImageIcon imag = API.resizeProportional(new ImageIcon(new URL(iLink)), w, h);
								    		
											API.showImage(imag, nome);
										} catch (MalformedURLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
			    			        }
		    					}else if(lName.getText().equals(Jimage.getText()) && lName.getIcon().equals(Jimage.getIcon())) {
			    					String iLink = "";
			    					for (String url : imgs.keySet()) {
			    						if(url.equals(img)) {
			    							iLink = url;
			    							break;
			    						}
			    					}
			    					
			    					if (Jimage.getIcon() != null) {
			    			            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			    			            int w = (int)(screenSize.width * 0.75);
			    			            int h = (int)(screenSize.height * 0.75);
			    			            
										try {
											ImageIcon imag = API.resizeProportional(new ImageIcon(new URL(iLink)), w, h);
								    		
											API.showImage(imag, nome);
										} catch (MalformedURLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
			    			        }
		    					}else {
		    						if(pName != null) {
		    							pName.setBackground(new Color(240, 240, 240));
		    						}
		    						lName.setBackground(new Color(240, 240, 240));
		    						lName.setForeground(new Color(0, 0, 0));
		    						lName = Jimage;
		    					}
		    					pName = null;
		    				}
	    				}
	    			}
	    		});
	    		Jimage.setBounds(0+width, 0+height, 220, 220);
	    		Jimage.setPreferredSize(new Dimension(220, 220));
	    		Jimage.setHorizontalTextPosition(SwingConstants.CENTER);
	    		Jimage.setVerticalTextPosition(SwingConstants.BOTTOM);
	    		Jimage.setHorizontalAlignment(SwingConstants.CENTER);
	    		Jimage.setVerticalAlignment(SwingConstants.CENTER);
	    		
	    		Jimage.setText(nome);
	    		
		    	if(img.contains(".gif") || img.contains(".apng")) {
		    		API.setGifByURL(Jimage, img);
			    }else {
			    	API.setImageByUrl(Jimage, img);
			    }
	    		
		    	GridBagConstraints gbc_Jimage = new GridBagConstraints();
				gbc_Jimage.gridx = width;
				gbc_Jimage.gridy = height;
                panel.add(Jimage, gbc_Jimage);
                panel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent paramMouseEvent) {
						if(pName != null) {
							pName.setBackground(new Color(240, 240, 240));
						}
						if(lName != null) {
							lName.setForeground(new Color(0, 0, 0));
						}
						
						Jimage.setBackground(new Color(240, 240, 240));
						Jimage.setForeground(new Color(0, 0, 0));
						bool = false;
					}
				});
                width++;
                if(width >= 5) { width = 0; width = 0; height ++;}
                
    		}else {
    			if(img.contains(".svg")) {
    				
    				String nome = "";
		    		if(img.split("/")[img.split("/").length-1].contains("?")) { nome = img.split("/")[img.split("/").length-1].split("\\?")[0]; }else{ nome = img.split("/")[img.split("/").length-1]; }
		    		final String nome2 = nome;
		    		
		    		JPopupMenu popup = new JPopupMenu();
		    		JMenuItem down = new JMenuItem("Salvar imagem");
		    		down.addActionListener(new ActionListener() {
		    			public void actionPerformed(ActionEvent arg0) {
		    				Thread th1 = new Thread() {
		    	                @Override
		    	                public void run(){
				    				try {
										API.saveImage(img, nome2);
										
										JOptionPane.showMessageDialog(null,"Imagem salva com sucesso" ,"Sucesso", JOptionPane.INFORMATION_MESSAGE);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										JOptionPane.showMessageDialog(null,"Houve um erro ao salvar a imagem." ,"Erro", JOptionPane.ERROR_MESSAGE);
									}
		    	                }
		    	            };th1.start();
		    			}
		    		});
		    		popup.add(down);
		    		
		    		if(nome.length() > 20) { nome = nome.substring(0, 15)+"..."+nome.substring(nome.lastIndexOf(".")-1, nome.length()); }
		    		
	    			lblImgNome = new JLabel(nome);
	    			lblImgNome.setAlignmentY(0.0f);
	    			lblImgNome.setAlignmentX(0.5f);
	    			
	    			JSVGCanvas svg = new JSVGCanvas();
	    			svg.setURI(img);
	    			svg.setBackground(new Color(0, 0, 0, 0));
	    			svg.setBounds(0+width, 0+height, 220, 220);
	    			
	    			panel1 = new JPanel();
	    			panel1.setOpaque(true);
	    			svg.addMouseListener(new MouseAdapter() {
	    				@Override
	    				public void mouseClicked(MouseEvent paramMouseEvent) {
	    					
	    					if(SwingUtilities.isRightMouseButton(paramMouseEvent)) {
	    						if(lName != null) {
		    						if(lName.getBackground() != new Color(0, 102, 255)) {
	    								lName.setBackground(new Color(240, 240, 240));
	    							}
		    						lName.setForeground(new Color(0, 0, 0));
	    						}
    							if(pName != null) {
									pName.setBackground(new Color(240, 240, 240));
								}
	    						
	    						panel1.setBackground(new Color(0, 102, 255));
		    					lblImgNome.setForeground(new Color(255, 255, 255));
	    						
		    					lName = lblImgNome;
	    						pName = panel1;
	    						bool = true;
		    					
		    					popup.show(svg, paramMouseEvent.getX(), paramMouseEvent.getY());
		    				}else if(SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
	    					
		    					panel1.setBackground(new Color(0, 102, 255));
		    					lblImgNome.setForeground(new Color(255, 255, 255));
		    					
		    					if(!bool){
		    						lName = lblImgNome;
		    						pName = panel1;
		    						bool = true;
		    					}else {
		    						if(lName.getText().equals(lblImgNome.getText())) {
		    	    					JOptionPane.showMessageDialog(null,"Infelizmente o formato '.svg' não é suportado." ,"Aviso", JOptionPane.WARNING_MESSAGE);
		    						}else {
		    							if(lName.getBackground() != new Color(0, 102, 255)) {
		    								lName.setBackground(new Color(240, 240, 240));
		    							}
		    							if(pName != null) {
											pName.setBackground(new Color(240, 240, 240));
										}
		    							
		    							lName.setForeground(new Color(0, 0, 0));
		    							lName = lblImgNome;
		    							pName = panel1;
		    						}
		    					}
		    				}
	    				}
	    			});
	    			panel1.addMouseListener(new MouseAdapter() {
	    				@Override
	    				public void mouseClicked(MouseEvent paramMouseEvent) {
	    					
	    					if(SwingUtilities.isRightMouseButton(paramMouseEvent)) {
	    						if(lName != null) {
		    						if(lName.getBackground() != new Color(0, 102, 255)) {
	    								lName.setBackground(new Color(240, 240, 240));
	    							}
		    						lName.setForeground(new Color(0, 0, 0));
	    						}
    							if(pName != null) {
									pName.setBackground(new Color(240, 240, 240));
								}
	    						
	    						panel1.setBackground(new Color(0, 102, 255));
		    					lblImgNome.setForeground(new Color(255, 255, 255));
	    						
		    					lName = lblImgNome;
	    						pName = panel1;
	    						bool = true;
		    					
		    					popup.show(svg, paramMouseEvent.getX(), paramMouseEvent.getY());
		    				}else if(SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
	    					
		    					panel1.setBackground(new Color(0, 102, 255));
		    					lblImgNome.setForeground(new Color(255, 255, 255));
		    					
		    					if(!bool){
		    						lName = lblImgNome;
		    						pName = panel1;
		    						bool = true;
		    					}else {
		    						if(lName.getText().equals(lblImgNome.getText())) {
		    	    					JOptionPane.showMessageDialog(null,"Infelizmente o formato '.svg' não é suportado." ,"Aviso", JOptionPane.WARNING_MESSAGE);
		    						}else {
		    							if(lName.getBackground() != new Color(0, 102, 255)) {
		    								lName.setBackground(new Color(240, 240, 240));
		    							}
		    							if(pName != null) {
											pName.setBackground(new Color(240, 240, 240));
										}
		    							
		    							lName.setForeground(new Color(0, 0, 0));
		    							lName = lblImgNome;
		    							pName = panel1;
		    						}
		    					}
		    				}
	    				}
	    			});
	    			panel1.setBounds(0+width, 0+height, 220, 220);
	    			panel1.setPreferredSize(new Dimension(220, 220));
	    			panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
	    			
	    			panel1.add(svg);
	    			
	    			panel1.add(lblImgNome);
	    			
	    			GridBagConstraints gbc_panel1 = new GridBagConstraints();
					gbc_panel1.gridx = width;
					gbc_panel1.gridy = height;
	                panel.add(panel1, gbc_panel1);
	                width++;
	                if(width >= 5) { width = 0; width = 0; height++;}
    			}
    		}
		}
		
		//lblStatus.setText("Estado: " + (iVer++) + "\t/\t" + imgs.size());
		progressBar.setValue(iVer++);
		panel.revalidate();
		panel.repaint();
		btnFiltrar.setEnabled(true);
		
    }
}
