import java.awt.*;
import java.awt.geom.*;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

/**
 * Instance třídy {@code Vizualizace} představuje plátno na kreslení vesmíru
 * @author Štěpán Faragula 31-03-2022
 */
public class Vizualizace extends JPanel {

	/** Simulační čas v milisekudnách*/
	public long simulationTime;

	private List<Planeta> seznamPlanet;
	private Planeta selectedPlanet;
	private AffineTransform miniTransform;

	// Všechno potřebné na scaling
	private double x_min, x_max, y_min, y_max;
	private double space_width, space_height;
	private double scale;

	/**
	 * Nastaví seznam planet a velikost okna
	 * @param seznamPlanet seznam planet
	 */
	public Vizualizace(List<Planeta> seznamPlanet) {
		this.setPreferredSize(new Dimension(800, 600));
		this.seznamPlanet = seznamPlanet;
	}

	/**
	 * @param t simulační čas v milisekundách
	 */
	public void setSimulationTime(long t){
		simulationTime = t;
	}

	/**
	 * Zjistí okraje vesmíru, jeho šířku, výšku a scale
	 */
	private void setSpaceBorder(){
		x_min = Collections.min(seznamPlanet.stream().map(Planeta::getNegativeRadiusX).toList());
		x_max = Collections.max(seznamPlanet.stream().map(Planeta::getPositiveRadiusX).toList());
		y_min = Collections.min(seznamPlanet.stream().map(Planeta::getNegativeRadiusY).toList());
		y_max = Collections.max(seznamPlanet.stream().map(Planeta::getPositiveRadiusY).toList());

		space_width = x_max - x_min;
		space_height = y_max - y_min;

		double scale_x = this.getWidth() / space_width;
		double scale_y = this.getHeight() / space_height;
		scale = Math.min(scale_x, scale_y);
	}

	/**
	 * Kreslení na plátno
	 * @param g kreslítko
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;

		// Scaling
		setSpaceBorder();
		AffineTransform oldTransform = g2.getTransform();

		g2.translate(this.getWidth()/2,this.getHeight()/2);
		g2.scale(scale, scale);
		g2.translate(-space_width /2,-space_height /2);
		g2.translate(-x_min,-y_min);

		miniTransform = g2.getTransform();

		// Pozadi plochy
		g2.setColor(Color.lightGray);
		Rectangle2D background = new Rectangle2D.Double(x_min, y_min, space_width, space_height);
		g2.fill(background);

		// Vykresleni planet
		g2.setColor(Color.BLACK);
		drawPlanets(g2);

		// Vykresleni oznacene planety
		if(selectedPlanet != null) {
			g2.setColor(Color.GRAY);
			g2.fill(new Ellipse2D.Double(selectedPlanet.getNegativeRadiusX(), selectedPlanet.getNegativeRadiusY(), 2 * selectedPlanet.getRadius(), 2 * selectedPlanet.getRadius()));
		}

		// Label casu
		g2.setTransform(oldTransform);
		setTimeLabel(g2, simulationTime);

		// Label oznacena planeta
		if(selectedPlanet != null) {
			setSelectedPlanetLabel(g2, selectedPlanet);
		}
	}

	/**
	 * Vypíše informace o vybrané planetě v levém horním rohu okna
	 * @param g2 kreslítko
	 * @param p vybraná planeta
	 */
	public void setSelectedPlanetLabel(Graphics2D g2, Planeta p){
		String nameString = " Nazev objektu: " + p.getName();
		String positionString = String.format(" Pozice X, Y: %.3f, %.3f", p.getPositionX(), p.getPositionY());
		String velocityString = String.format(" Rychlost X, Y: %.3f, %.3f", p.getVelocityX(), p.getVelocityY());
		g2.setFont(new Font("Calibri", Font.PLAIN, 15));
		int textHeight = g2.getFontMetrics().getHeight();

		g2.setColor(Color.WHITE);
		g2.drawString(nameString, 0, textHeight + 1);
		g2.drawString(positionString, 0, textHeight*2 + 1);
		g2.drawString(velocityString, 0, textHeight*3 + 1);

		g2.setColor(Color.BLACK);
		g2.drawString(nameString, 0, textHeight);
		g2.drawString(positionString, 0, textHeight*2);
		g2.drawString(velocityString, 0, textHeight*3);
	}

	/**
	 * Vypíše simulační čas v pravém horním rohu okna
	 * @param g2 kreslítko
	 * @param time čas na vykreslení v milisekundách
	 */
	public void setTimeLabel(Graphics2D g2, long time){
		String timeString = String.format("Simulacni cas: %.3f s ", (double) time/1000);
		g2.setFont(new Font("Calibri", Font.PLAIN, 15));
		int textWidth = g2.getFontMetrics().stringWidth(timeString);
		int textHeight = g2.getFontMetrics().getHeight();

		g2.setColor(Color.WHITE);
		g2.drawString(timeString, this.getWidth() - textWidth + 1, textHeight + 1);

		g2.setColor(Color.BLACK);
		g2.drawString(timeString, this.getWidth() - textWidth, textHeight);
	}

	/**
	 * Vykreslí všechny planety na plátno
	 * @param g2 kreslítko
	 */
	public void drawPlanets(Graphics2D g2){
		seznamPlanet.forEach(p -> g2.fill(new Ellipse2D.Double(p.getNegativeRadiusX(), p.getNegativeRadiusY(), 2*p.getRadius(), 2*p.getRadius())));
	}

	/**
	 * Nastaví atribut {@code selectedPlanet} na předanou planetu
	 * @param planeta vybraná planeta
	 */
	public void showSelectedPlanet(Planeta planeta){
		// provizorní řešení
		selectedPlanet = planeta;
		repaint();
	}

	/**
	 * HitTest, projde seznam planet a zjistí, jestli se body x, y nacházejí uvnitř elipsy
	 *
	 * !! PROBLÉM S OPERAČNÍM SYSTÉMEM !!
	 * !! METODA FUNGUJE POUZE NA ZVĚTŠENÍ OKEN APLIKACÍ NA 100% !!
	 * @param x souřadníce x
	 * @param y souřadníce y
	 * @return true pokud ano
	 * 		   false pokud ne
	 */
	public Planeta getHitPlanet(double x, double y) {
		Point2D click = new Point2D.Double(x, y);
		Point2D clickTransformed = new Point2D.Double();

		try {
			miniTransform.invert();
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}

		miniTransform.transform(click, clickTransformed);

		for(Planeta p : seznamPlanet){
			Ellipse2D elipsa = new Ellipse2D.Double(p.getNegativeRadiusX(), p.getNegativeRadiusY(), 2*p.getRadius(), 2*p.getRadius());
			if(elipsa.contains(clickTransformed.getX(), clickTransformed.getY()))
				return p;
		}
		return null;
	}
}
