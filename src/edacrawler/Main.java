/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edacrawler;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

/**
 *
 * @author pedrodias & paulosantos
 */
public class Main {
	static int i = 1;
	static int size = 0;
	
	private static void setImageByUrl(JLabel label, String url) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new URL(url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image imgScale = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon newimg = new ImageIcon(imgScale);
		label.setIcon(newimg);
    }
	
	private static void setGifByURL(JLabel label, String url) throws MalformedURLException {
		ImageIcon ico = new ImageIcon(new URL(url));
		ico.setImage(ico.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
		label.setIcon(ico);
    }
	
	public static void writeImage(String path, String url, boolean svg) {
		
		try {
			InputStream in = new BufferedInputStream(new URL(url).openStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1!=(n=in.read(buf)))
			{
			   out.write(buf, 0, n);
			}
			out.close();
			in.close();
			byte[] response = out.toByteArray();
			
			if(svg) {
		        InputStream svgFileStream = new ByteArrayInputStream(out.toByteArray());
				TranscoderInput inputSvgImage = new TranscoderInput(svgFileStream);
				PNGTranscoder converter = new PNGTranscoder();
				ByteArrayOutputStream pngFileStream = new java.io.ByteArrayOutputStream();
				TranscoderOutput outputPngImage = new TranscoderOutput(pngFileStream);

				try {
				    converter.transcode(inputSvgImage, outputPngImage);
				} catch (TranscoderException e) {
				    System.out.println("Error while converting svg to PNG");
				    e.printStackTrace();
				}
				response = pngFileStream.toByteArray();
			}
			
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(response);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void recur(String urlO, ArrayList<String> links, int profundidade, boolean flag) {
		
		ArrayList<String> linksTemp = new ArrayList<>();
		for (String url : links) {
			if(flag && url.startsWith(urlO) || !flag) {
				try {
					EDACrawler eda = new EDACrawler();
					Payload ini = eda.process(url);
					
					size += ini.imgs.size();
					linksTemp.addAll(ini.links);
					System.out.println(url + " - " + ini.links);
			        System.out.println(url + " - " + ini.imgs);
			        
					for (String str : ini.imgs) {
						
						/*JFrame frm = new JFrame();
				        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				        frm.setTitle(str);
				        JLabel img = new JLabel();*/
				        
						if(!str.endsWith(".svg")) {
							
					        //Image image = null;
					        //URL url1 = new URL(str);
					        //image = ImageIO.read(url1);
							BufferedImage bimg = ImageIO.read(new URL(str));
					        
					        //img.setBounds(0, 0, bimg.getWidth(), bimg.getHeight());
					        //ImageIcon icon = new ImageIcon(image);
					        //img.setIcon(icon);
					        if(str.endsWith(".gif")) {
					        	//setGifByURL(img, str);
					        	//writeImage("C:\\Users\\Asus\\Desktop\\" + str.split("/")[str.split("/").length-1], str, false);
					        }else {
					        	//setImageByUrl(img, str);
					        	//writeImage("C:\\Users\\Asus\\Desktop\\" + str.split("/")[str.split("/").length-1], str, false);
					        }
						}else {
							//FlatSVGIcon svgIcon = new FlatSVGIcon(str, 500, 200);
							//JSVGCanvas svg = new JSVGCanvas();
							//svg.setURI("file:/C:/Users/Linda/Desktop/test.svg");
							//writeImage("C:\\Users\\Asus\\Desktop\\ola.png", str, true);
						}
						
						/*frm.getContentPane().add(img);
				        frm.setSize(500, 200);
				        frm.setVisible(true);*/
				    }
				} catch (IOException e) {
					System.out.println(url + " - Indispon�vel");
					continue;
				}
			}
		}
		links.removeAll(links);
		
		if(profundidade != i) {
			size = 0;
			i++;
			recur(urlO, linksTemp, profundidade, flag);
		}
	}
	
    public static void main(String[] args) throws IOException {
    	//"http://portal2.ipt.pt/"
    	String link = "http://orion.ipt.pt/";
    	ArrayList<String> links = new ArrayList<>();
    	links.add(link);
    	ArrayList<Payload> payloads = new ArrayList<>();
    	
    	//recur(link, links, 2, false);
    	
    	ArrayList<String> imgsDu = new ArrayList<>();
    	for(Payload payl : API.getImages(link, links, 2, false, payloads)) {
    		imgsDu.addAll(payl.imgs);
    	}
    	ArrayList<String> imgs = API.removeDuplicates(imgsDu);
    	System.out.println(imgs);
    	
    	i = 1;
    	//System.out.println(size);
    	
        //EDACrawler eda = new EDACrawler();
        //Payload ini = eda.process("http://orion.ipt.pt/");

        //System.out.println(ini.links);   //mostra os links contidos na página inicial 
        //System.out.println(ini.imgs);    //mostra as imagens todas da página inicial 
        //html bruto se quisermos fazer algo com ele nomeadamente pesquisar imagens por um texto
        //System.out.println(pl.html);

        //agora aqui começavam a mapear para a estruturas de dados deles, indexar, etc
        //utilizavam a estrutura de dados para ver se já visitaram ou não e visitivam só o que ainda não foi visitado
        //exemplo a visitar apenas os filhos da semente...
//          for (String string : pl.links) {
//            try {
//                Payload pla = eda.process(string);
//                System.out.println(pla.links);
//                
//                //isto depois tem que ser recursivo
//            } catch (Exception e) {
//                System.out.println(string + " invalid");
//            }
//        }
        
        
        /* codigo exemplo para mostrar uma imagem a partir da sua localização */
        /* isto depois não é para ser feito tudo no main */
        /*for (String str:ini.imgs) {
        Image image = null;
        URL url = new URL(str);
        image = ImageIO.read(url);
        
        JFrame frm = new JFrame();
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel img = new JLabel();
        ImageIcon icon = new ImageIcon(image);
        img.setIcon(icon);
        frm.getContentPane().add(img);
        frm.setSize(200, 200);
        frm.setVisible(true);
        
        }*/
    }

}
