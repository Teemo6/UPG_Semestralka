import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.List;
import javax.swing.*;

public class VizualizaceVesmiru extends JPanel {

	double[] world_x = {0, 800, 800, 0};
	double[] world_y = {0, 0, 600, 600};

	double x_min, x_max, y_min, y_max;
	double world_width, world_height;

	private List<Planeta> seznamPlanet;

	public VizualizaceVesmiru(List<Planeta> seznamPlanet) {
		this.seznamPlanet = seznamPlanet;
		this.setPreferredSize(new Dimension(800, 600));

		x_min = 0;
		x_max = 800;
		y_min = 0;
		y_max = 600;

		for(Planeta p : seznamPlanet){

		}

		world_width = x_max - x_min;
		world_height = y_max - y_min;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		double scale_x = this.getWidth() / world_width;
		double scale_y = this.getHeight() / world_height;
		double scale = Math.min(scale_x, scale_y);

		Graphics2D g2 = (Graphics2D)g;

		g.setColor(Color.lightGray);
		Path2D objekt = new Path2D.Double();
		objekt.moveTo((world_x[0] - x_min) * scale, (world_y[0] - y_min) * scale);

		for (int i = 1; i < world_x.length; i++) {
			objekt.lineTo((world_x[i] - x_min) * scale, (world_y[i] - y_min) * scale);
		}
		objekt.closePath();
		g2.fill(objekt);


		g2.setColor(Color.BLACK);

		for(Planeta p : seznamPlanet){
			g2.fill(new Ellipse2D.Double(p.posX, p.posY, p.hmotnost * scale, p.hmotnost * scale));
		}
	}
}
