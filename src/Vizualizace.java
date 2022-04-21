import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

/**
 * Instance třídy {@code Vizualizace} představuje plátno na kreslení vesmíru
 * @author Štěpán Faragula 10-04-2022
 * @version 1.21
 */
public class Vizualizace extends JPanel {

	/** Simulační čas v milisekudnách*/
	public long simulationTime;

	// Minimální průměr vykreslené planety, v pixelech
	public final double MINIMAL_PLANET_SIZE = 4;

	// Všechno potřebné na planety
	private final List<Planeta> planetList;
	private final Map<Planeta, Ellipse2D> planetMap;
	private Planeta selectedPlanet;

	// Všechno potřebné na scaling
	private double x_min, x_max, y_min, y_max;
	private double space_width, space_height;
	private double scale;
	private AffineTransform miniTransform;

	/**
	 * Nastaví seznam planet a velikost okna
	 * @param planetList seznam planet
	 */
	public Vizualizace(List<Planeta> planetList) {
		this.setPreferredSize(new Dimension(800, 600));
		this.planetList = planetList;
		planetMap = new HashMap<>(planetList.size());
		updatePlanetMap();
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
	private void computeSpaceBorder(){
		x_min = Collections.min(planetMap.keySet().stream().map(Planeta::getNegativeRadiusX).toList());
		x_max = Collections.max(planetMap.keySet().stream().map(Planeta::getPositiveRadiusX).toList());
		y_min = Collections.min(planetMap.keySet().stream().map(Planeta::getNegativeRadiusY).toList());
		y_max = Collections.max(planetMap.keySet().stream().map(Planeta::getPositiveRadiusY).toList());

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
		computeSpaceBorder();

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
		updatePlanetMap();
		drawPlanets(g2);

		// Vykresleni oznacene planety
		if(selectedPlanet != null) {
			g2.setColor(Color.GRAY);
			g2.fill(planetMap.get(selectedPlanet));
		}

		// Label casu
		g2.setTransform(oldTransform);
		showTimeLabel(g2, simulationTime);

		// Label oznacena planeta
		if(selectedPlanet != null) {
			showSelectedPlanetLabel(g2, selectedPlanet);
		}
	}

	/**
	 * Vypíše informace o vybrané planetě v levém horním rohu okna
	 * @param g2 kreslítko
	 * @param p vybraná planeta
	 */
	public void showSelectedPlanetLabel(Graphics2D g2, Planeta p){
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
	public void showTimeLabel(Graphics2D g2, long time){
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
		planetMap.forEach((p, e) -> g2.fill(e));
	}

	/**
	 * Aktualizuje mapu planet, vypočítá elipsy pro planety
	 * pokud by byli menší než MINIMAL_PLANET_SIZE nastaví jim tuto velikost
	 */
	public void updatePlanetMap(){
		planetMap.clear();
		for(Planeta p : planetList){
			Ellipse2D e;
			if(2*p.getRadius() * scale < MINIMAL_PLANET_SIZE){
				e = new Ellipse2D.Double(p.getPositionX() - (MINIMAL_PLANET_SIZE/2)/scale, p.getPositionY() - (MINIMAL_PLANET_SIZE/2)/scale, MINIMAL_PLANET_SIZE/scale, MINIMAL_PLANET_SIZE/scale);
			} else {
				e = new Ellipse2D.Double(p.getNegativeRadiusX(), p.getNegativeRadiusY(), 2 * p.getRadius(), 2 * p.getRadius());
			}
			planetMap.put(p ,e);
		}
	}

	/**
	 * Projde planety a zjistí, jestli se body x, y nacházejí uvnitř elipsy
	 * označí kliknutou planetu
	 *
	 * !! PROBLÉM S OPERAČNÍM SYSTÉMEM !!
	 * !! METODA FUNGUJE POUZE NA ZVĚTŠENÍ OKEN APLIKACÍ NA 100% !!
	 * @param x souřadníce x
	 * @param y souřadníce y
	 */
	public void showHitPlanet(double x, double y) {
		Point2D click = new Point2D.Double(x, y);
		Point2D clickTransformed = new Point2D.Double();

		try {
			miniTransform.invert();
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		miniTransform.transform(click, clickTransformed);

		selectedPlanet = null;
		planetMap.forEach((p, e) -> {
			if(e.contains(clickTransformed.getX(), clickTransformed.getY()))
				selectedPlanet = p;
		});
	}
}
