import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.List;
import javax.swing.*;

public class VizualizaceVesmiru extends JPanel {

	private List<Planeta> seznamPlanet;

	double x_min, x_max, y_min, y_max;
	double world_width, world_height;

	public VizualizaceVesmiru(List<Planeta> seznamPlanet) {
		this.setPreferredSize(new Dimension(800, 600));
		this.seznamPlanet = seznamPlanet;
	}

	private void setTimeLabel(Graphics2D g2){
		g2.translate(0, 0);
		g2.setFont(new Font("Calibri", Font.PLAIN, 15));
		int textWidth = g2.getFontMetrics().stringWidth("Simulacni cas: 0.0000" + " ");
		int textHeight = g2.getFontMetrics().getHeight();
		g2.setColor(Color.WHITE);
		g2.drawString("Simulacni cas: 0.0000", this.getWidth() - textWidth + 1, textHeight + 1);
		g2.setColor(Color.BLACK);
		g2.drawString("Simulacni cas: 0.0000", this.getWidth() - textWidth, textHeight);
	}

	private void setSpaceScale(){
		x_min = Double.MAX_VALUE;
		x_max = 0;
		y_min = Double.MAX_VALUE;
		y_max = 0;

		for(Planeta p : seznamPlanet){
			double levyOkrajX = p.posX;
			double pravyOkrajX = p.posX + p.hmotnost;
			double horniOkrajY = p.posY;
			double dolniOkrajY = p.posY + p.hmotnost;

			x_min = Math.min(levyOkrajX, x_min);
			x_max = Math.max(pravyOkrajX, x_max);
			y_min = Math.min(horniOkrajY, y_min);
			y_max = Math.max(dolniOkrajY, y_max);
		}
		world_width = x_max - x_min;
		world_height = y_max - y_min;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;

		setSpaceScale();
		AffineTransform oldTransform = g2.getTransform();

		// Scaling
		double scale_x = this.getWidth() / world_width;
		double scale_y = this.getHeight() / world_height;
		double scale = Math.min(scale_x, scale_y);
		g2.translate(this.getWidth()/2,this.getHeight()/2);
		g2.scale(scale, scale);
		g2.translate(-world_width/2,-world_height/2);

		// Pozadi
		g2.setColor(Color.lightGray);
		Path2D objekt = new Path2D.Double();
		objekt.moveTo(0, 0);
		objekt.lineTo(world_width, 0);
		objekt.lineTo(world_width, world_height);
		objekt.lineTo(0, world_height);
		objekt.closePath();
		g2.fill(objekt);

		// Planety
		g2.setColor(Color.BLACK);
		for(Planeta p : seznamPlanet){
			g2.fill(new Ellipse2D.Double(p.posX - x_min, p.posY - y_min, p.hmotnost, p.hmotnost));
		}

		g2.setTransform(oldTransform);
		setTimeLabel(g2);
	}
}
