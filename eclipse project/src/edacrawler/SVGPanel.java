package edacrawler;

import java.awt.Dimension;

import javax.swing.JPanel;
import org.apache.batik.swing.JSVGCanvas;

public class SVGPanel extends JPanel {
     public SVGPanel(String img){
          JSVGCanvas svg = new JSVGCanvas();
          svg.setURI(img);
          add(svg);
     }
} 