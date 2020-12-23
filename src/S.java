import java.awt.Color;
import java.awt.Graphics;

public class S extends Tetromino {
	private Color c;
	public S() {
		super(2,3);

		c = Color.green;
	}
	
	public Tile[][] getShape() {
		shape[0][0] = new Tile(4, 0);
		shape[0][1] = new Tile(5, 0, c);
		shape[0][2] = new Tile(6, 0, c);
		shape[1][0] = new Tile(4, 1, c);
		shape[1][1] = new Tile(5, 1, c, true);
		shape[1][2] = new Tile(6, 1);
		return shape;
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(c);
		g.fillRect(x+10, y, 20, 10);
		g.fillRect(x, y+10, 20, 10);
	}

	public Color getColor() {
		return c;
	}
}
