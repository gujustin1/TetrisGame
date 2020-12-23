import java.awt.Color;
import java.awt.Graphics;

public class O extends Tetromino {
	private Color c;
	public O() {
		super(2,2);

		c = Color.yellow;
	}
	
	public Tile[][] getShape() {
		shape[0][0] = new Tile(4, 0, c, true);
		shape[0][1] = new Tile(5, 0, c);
		shape[1][0] = new Tile(4, 1, c);
		shape[1][1] = new Tile(5, 1, c);
		return shape;
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(c);
		g.fillRect(x, y, 20, 20);

	}

	public Color getColor() {
		return c;
	}
}
