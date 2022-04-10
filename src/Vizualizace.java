import java.awt.*;
import java.awt.geom.*;
import java.util.List;
import javax.swing.*;

public class Vizualizace extends JPanel {

	public long startTime;
	public long simulationTime;

	private List<Planeta> seznamPlanet;
	Planeta selectedPlanet;
	AffineTransform miniTransform;
	double scale;

	double x_min, x_max, y_min, y_max;
	double world_width, world_height;

	double maxVzdalenost;

	public Vizualizace(List<Planeta> seznamPlanet, long startTime) {
		this.setPreferredSize(new Dimension(800, 600));
		this.startTime = startTime;
		this.seznamPlanet = seznamPlanet;

		maxVzdalenost = seznamPlanet.get(0).getPositionVector().computeDistance(seznamPlanet.get(1).getPositionVector());
	}

	private void createWorld(){
		x_min = Double.MAX_VALUE;
		x_max = 0;
		y_min = Double.MAX_VALUE;
		y_max = 0;

		for(Planeta p : seznamPlanet){
			double minimumX = p.getPositionX() - p.getRadius();
			double maximumX = p.getPositionX() + p.getRadius();
			double minimumY = p.getPositionY() - p.getRadius();
			double maximumY = p.getPositionY() + p.getRadius();

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

		// Planety
		g2.setColor(Color.BLACK);
		drawPlanets(g2);

		// Planeta oznacena
		if(selectedPlanet != null) {
			g2.setColor(Color.GRAY);
			g2.fill(new Ellipse2D.Double(selectedPlanet.getPositionX() - selectedPlanet.getRadius() - x_min, selectedPlanet.getPositionY() - selectedPlanet.getRadius() - y_min, 2 * selectedPlanet.getRadius(), 2 * selectedPlanet.getRadius()));
		}

		// Label
		g2.setTransform(oldTransform);
		setTimeLabel(g2, simulationTime);

		// Label oznacena
		if(selectedPlanet != null) {
			setSelectedPlanetLabel(g2, selectedPlanet);
		}
	}

	private void setSelectedPlanetLabel(Graphics2D g2, Planeta p){
		String nameString = " Nazev objektu: " + p.getName();
		String positionString = String.format(" Pozice X, Y: %.3f, %.3f", p.getPositionX(), p.getPositionY());
		String velocityString = String.format(" Rychlost X, Y: %.3f, %.3f", p.getVelocityX(), p.getVelocityY());
		int textHeight = g2.getFontMetrics().getHeight();
		g2.setFont(new Font("Calibri", Font.BOLD, 15));
		g2.setColor(Color.WHITE);
		g2.drawString(nameString, 0, textHeight + 1);
		g2.drawString(positionString, 0, textHeight*2 + 1);
		g2.drawString(velocityString, 0, textHeight*3 + 1);
		g2.setFont(new Font("Calibri", Font.PLAIN, 15));
		g2.setColor(Color.BLACK);
		g2.drawString(nameString, 0, textHeight);
		g2.drawString(positionString, 0, textHeight*2);
		g2.drawString(velocityString, 0, textHeight*3);
	}

	private void setTimeLabel(Graphics2D g2, long time){
		String timeString = String.format("Simulacni cas: %,d ", time);
		g2.setFont(new Font("Calibri", Font.PLAIN, 15));
		int textWidth = g2.getFontMetrics().stringWidth(timeString);
		int textHeight = g2.getFontMetrics().getHeight();
		g2.setFont(new Font("Calibri", Font.BOLD, 15));
		g2.setColor(Color.WHITE);
		g2.drawString(timeString, this.getWidth() - textWidth + 1, textHeight + 1);
		g2.setFont(new Font("Calibri", Font.PLAIN, 15));
		g2.setColor(Color.BLACK);
		g2.drawString(timeString, this.getWidth() - textWidth, textHeight);
	}

	public void drawPlanets(Graphics2D g2){
		seznamPlanet.forEach(p ->
				g2.fill(new Ellipse2D.Double(p.getPositionX() - p.getRadius() - x_min, p.getPositionY() - p.getRadius() - y_min, 2*p.getRadius(), 2*p.getRadius()))
		);
	}

	public void showSelectedPlanet(Planeta planeta){
		repaint();
	}

	public Planeta getHitPlanet(double x, double y) {
		Point2D click = new Point2D.Double(x, y);
		Point2D clickTransformed = new Point2D.Double();

		AffineTransform megaTransform = (AffineTransform) miniTransform.clone();

		try {
			megaTransform.invert();
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}

		megaTransform.transform(click, clickTransformed);

		for(Planeta p : seznamPlanet){
			Ellipse2D elipsa = new Ellipse2D.Double(p.getPositionX() - p.getRadius() - x_min, p.getPositionY() - p.getRadius() - y_min, 2*p.getRadius(), 2*p.getRadius());

			if(elipsa.contains(clickTransformed.getX(), clickTransformed.getY())){
				selectedPlanet = p;
				return p;
			}
		}
		return null;
	}
}
