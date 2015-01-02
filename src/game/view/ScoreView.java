package game.view;

import game.interfaces.IGameWorld;
import game.interfaces.IObservable;
import game.interfaces.IObserver;

import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * Provides the view that holds the player's current statistics for the game.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class ScoreView extends JPanel implements IObserver {

	private IGameWorld gw;		// The observable class in which to watch.
	private JLabel stats;		// The label that will holds the game stats.

	/**
	 * Constructor that registers this view with the Observable. Also sets up the view with all of the game's
	 * statistics.
	 * @param observing - The Observable object in which to observe.
	 */
	public ScoreView(IObservable observing)
	{
		String score, sound;
		
		// Sets the object that will update this view with the score.
		gw = (IGameWorld) observing;
		observing.addObserver(this);
		
		// Gives this panel a layout that puts everything in a line, and gives it an etched border.
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setBorder(new EtchedBorder());
		
		// Determines the state of the sound.
		if (gw.getSound())
			sound = "ON";
		else
			sound = "OFF";
		
		// Creates the labels that will hold the game stats.
		score = String.format("Level: %d; Lives Remaining: %d; Time Left: %d; Score: %.2f; Goal: %.2f, Sound: %s",
				gw.getLevel(), gw.getLives(), gw.getTime(), (gw.getScore() * 100), (gw.getGoal() * 100), sound);
		
		stats = new JLabel(score);
		
		// Adds the label to the panel.
		add(stats);
	}
	
	/**
	 * Updates the Score View whenever the data that this view is observing updates.
	 */
	@Override
	public void update(IObservable o, Object arg)
	{
		String message;
		
		// Grabs the message that was sent by the class this is observing. If it equals score, then the
		//	score will be updated.
		message = (String) arg;
		if (message.compareToIgnoreCase("score") == 0)
		{
			repaint();
		}
	}
	
	/**
	 * The method that updates all of the labels with the current statistics.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		String score, sound;
		
		if (gw.getSound())
			sound = "ON";
		else
			sound = "OFF";
		
		score = String.format("Level: %d\t\t Lives Remaining: %d\t\t Time Left: %d\t\t Score: %.2f\t\t Goal: %.2f\t\t Sound: %s",
				gw.getLevel(), gw.getLives(), gw.getTime(), (gw.getScore() * 100), (gw.getGoal() * 100), sound);
		
		stats.setText(score);
		
	}
}
