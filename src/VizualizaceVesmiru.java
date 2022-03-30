import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.List;
import javax.sound.sampled.Line;
import javax.swing.*;

public class VizualizaceVesmiru extends JPanel {

	public long startTime;
	public long simulationTime;

	private List<Planeta> seznamPlanet;
	private VstupDat vstupDat;

	double x_min, x_max, y_min, y_max;
	double world_width, world_height;

	public VizualizaceVesmiru(VstupDat vstupDat, long startTime) {
		this.setPreferredSize(new Dimension(800, 600));
		this.startTime = startTime;
		this.seznamPlanet = vstupDat.getSeznamPlanet();
		this.vstupDat = vstupDat;
	}

	public void updateSystem(){
		double dt_min = 0.02;
		int t = 50;

		while(t > 0){
			double dt = Math.min(t, dt_min);

			computeAcc(seznamPlanet, vstupDat.getKonstantaG());

			for(Planeta p : seznamPlanet){
				p.velX += 0.5*dt*p.accX;
				p.velY += 0.5*dt*p.accY;

				p.posX += dt * p.velX;
				p.posY += dt * p.velY;

				p.velX += 0.5*dt*p.accX;
				p.velY += 0.5*dt*p.accY;
			}
			t -= dt;
		}
	}

	public void computeAcc(List<Planeta> seznamPlanet, double G){
		Planeta[] polePlanet = new Planeta[seznamPlanet.size()];
		polePlanet = seznamPlanet.toArray(polePlanet);

		for(int i = 0; i < polePlanet.length; i++){
			double sumX = 0;
			double sumY = 0;

			for(int j = 0; j < polePlanet.length; j++){
				if(i == j) continue;
				double posXDiff = polePlanet[j].posX - polePlanet[i].posX;
				double posYDiff = polePlanet[j].posY - polePlanet[i].posY;

				double vektorDelka = Math.sqrt((polePlanet[j].posX - polePlanet[i].posX) * (polePlanet[j].posX - polePlanet[i].posX) + (polePlanet[j].posY - polePlanet[i].posY) * (polePlanet[j].posY - polePlanet[i].posY));
				double absoluteXDif = Math.abs(posXDiff);
				double absoluteYDif = Math.abs(posYDiff);

				double rdPowerX = vektorDelka * vektorDelka * vektorDelka;
				double rdPowerY = vektorDelka * vektorDelka * vektorDelka;

				sumX += polePlanet[j].getHmotnost() * (posXDiff / rdPowerX);
				sumY += polePlanet[j].getHmotnost() * (posYDiff / rdPowerY);
			}
			if(Double.isNaN(sumX)){
				sumX = 0;
			}
			if(Double.isNaN(sumY)){
				sumY = 0;
			}
			polePlanet[i].setAcc(G * sumX, G * sumY);
		}
	}

	private void setTimeLabel(Graphics2D g2, long time){
		g2.translate(0, 0);
		String timeString = String.format("Simulacni cas: %,d", time);
		g2.setFont(new Font("Calibri", Font.PLAIN, 15));
		int textWidth = g2.getFontMetrics().stringWidth(timeString);
		int textHeight = g2.getFontMetrics().getHeight();
		g2.setColor(Color.WHITE);
		g2.drawString(timeString, this.getWidth() - textWidth + 1, textHeight + 1);
		g2.setColor(Color.BLACK);
		g2.drawString(timeString, this.getWidth() - textWidth, textHeight);
	}

	private void setSpaceScale(){
		x_min = Double.MAX_VALUE;
		x_max = 0;
		y_min = Double.MAX_VALUE;
		y_max = 0;

		for(Planeta p : seznamPlanet){
			double levyOkrajX = p.posX;
			double pravyOkrajX = p.posX + p.getHmotnost();
			double horniOkrajY = p.posY;
			double dolniOkrajY = p.posY + p.getHmotnost();

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

		long currentTime = System.currentTimeMillis();

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


		updateSystem();
		// Planety
		g2.setColor(Color.BLACK);
		for(Planeta p : seznamPlanet){
			g2.setColor(Color.BLACK);
			g2.fill(new Ellipse2D.Double(p.posX - x_min, p.posY - y_min, p.getHmotnost(), p.getHmotnost()));
			g2.setColor(Color.GREEN);
			g2.fill(new Ellipse2D.Double(p.posX - x_min - 0.25, p.getPosY() - 0.25 - y_min, 0.5, 0.5));
			System.out.println(p);
		}
		System.out.println("--");
		long timeDiff = currentTime - startTime;
		g2.setTransform(oldTransform);
		setTimeLabel(g2, simulationTime);
	}
}
