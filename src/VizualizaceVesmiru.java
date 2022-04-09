import java.awt.*;
import java.awt.geom.*;
import java.util.List;
import javax.swing.*;

public class VizualizaceVesmiru extends JPanel {

	public long startTime;
	public long simulationTime;

	private List<Planeta> seznamPlanet;
	private VstupDat vstupDat;
	AffineTransform miniTransform;
	double scale;

	private boolean simulationRunning = true;

	double x_min, x_max, y_min, y_max;
	double world_width, world_height;

	public VizualizaceVesmiru(VstupDat vstupDat, long startTime) {
		this.setPreferredSize(new Dimension(800, 600));
		this.startTime = startTime;
		this.seznamPlanet = vstupDat.getSeznamPlanet();
		this.vstupDat = vstupDat;
	}

	public void updateSystem(double t){
		double dt_min =  (t/100) * (t/1000);

		while(t > 0){
			double dt = Math.min(t, dt_min);

			computeAcc(seznamPlanet, vstupDat.getKonstantaG());

			for(Planeta p : seznamPlanet){
				p.velX += (dt/2)*p.accX;
				p.velY += (dt/2)*p.accY;

				p.posX += dt * p.velX;
				p.posY += dt * p.velY;

				p.velX += (dt/2)*p.accX;
				p.velY += (dt/2)*p.accY;
			}
			t -= dt;
		}
	}

	public void computeAcc(List<Planeta> seznamPlanet, double G){

		for(Planeta iPlaneta : seznamPlanet){
			double zrychleniX = 0;
			double zrychleniY = 0;

			for(Planeta jPlaneta : seznamPlanet){
				if(iPlaneta.equals(jPlaneta)) continue;

				double posXDiff = jPlaneta.posX - iPlaneta.posX;
				double posYDiff = jPlaneta.posY - iPlaneta.posY;

				double vektorDelka = Math.sqrt(posXDiff * posXDiff + posYDiff * posYDiff);
				double thirdPower = vektorDelka * vektorDelka * vektorDelka;

				zrychleniX += jPlaneta.hmotnost * (posXDiff / thirdPower);
				zrychleniY += jPlaneta.hmotnost * (posYDiff / thirdPower);
			}
			/*
			if(Double.isNaN(zrychleniX)){
				zrychleniX = 0;
			}
			if(Double.isNaN(zrychleniY)){
				zrychleniY = 0;
			}
			 */
			zrychleniX *= G;
			zrychleniY *= G;

			iPlaneta.setAcceleration(zrychleniX, zrychleniY);
		}
	}

	private void setTimeLabel(Graphics2D g2, long time){
		g2.translate(0, 0);
		String timeString = String.format("Simulacni cas: %,d ", time);
		g2.setFont(new Font("Calibri", Font.PLAIN, 15));
		int textWidth = g2.getFontMetrics().stringWidth(timeString);
		int textHeight = g2.getFontMetrics().getHeight();
		g2.setColor(Color.WHITE);
		g2.drawString(timeString, this.getWidth() - textWidth + 1, textHeight + 1);
		g2.setColor(Color.BLACK);
		g2.drawString(timeString, this.getWidth() - textWidth, textHeight);
	}

	private void createWorld(){
		x_min = Double.MAX_VALUE;
		x_max = 0;
		y_min = Double.MAX_VALUE;
		y_max = 0;

		for(Planeta p : seznamPlanet){
			double minimumX = p.posX - p.polomer;
			double maximumX = p.posX + p.polomer;
			double minimumY = p.posY - p.polomer;
			double maximumY = p.posY + p.polomer;

			x_min = Math.min(minimumX, x_min);
			x_max = Math.max(maximumX, x_max);
			y_min = Math.min(minimumY, y_min);
			y_max = Math.max(maximumY, y_max);
		}
		world_width = x_max - x_min;
		world_height = y_max - y_min;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		long currentTime = System.currentTimeMillis();

		createWorld();
		AffineTransform oldTransform = g2.getTransform();

		// Scaling
		double scale_x = this.getWidth() / world_width;
		double scale_y = this.getHeight() / world_height;
		scale = Math.min(scale_x, scale_y);

		g2.translate(this.getWidth()/2,this.getHeight()/2);
		g2.scale(scale, scale);
		g2.translate(-world_width/2,-world_height/2);

		miniTransform = g2.getTransform();

		// Pozadi se scale
		g2.setColor(Color.lightGray);
		Rectangle2D background = new Rectangle2D.Double(0, 0, world_width, world_height);
		g2.fill(background);

		g2.setColor(Color.BLACK);
		drawPlanets(g2);

		// Label
		g2.setTransform(oldTransform);
		setTimeLabel(g2, simulationTime);
	}

	public void drawPlanets(Graphics2D g2){
		seznamPlanet.forEach(p ->
				g2.fill(new Ellipse2D.Double(p.posX - p.polomer - x_min, p.posY - p.polomer - y_min, 2*p.polomer, 2*p.polomer))
		);
	}

	public boolean isPlanetHit(double x, double y) {
		Point2D click = new Point2D.Double(x, y);
		Point2D clickTransformed = new Point2D.Double();

		try {
			miniTransform.invert();
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}

		miniTransform.transform(click, clickTransformed);

		for(Planeta p : seznamPlanet){
			Ellipse2D elipsa = new Ellipse2D.Double(p.posX - p.polomer - x_min, p.posY - p.polomer - y_min, 2*p.polomer, 2*p.polomer);

			if(elipsa.contains(clickTransformed.getX(), clickTransformed.getY())){
				return true;
			}
		}
		return false;
	}
}
