import java.awt.Color;
import java.awt.Graphics;

public class X extends Tetromino {
	private Color c;
	public X() {
		super(3,3);

		c = Color.pink;
	}
	
	public Tile[][] getShape() {
		shape[0][0] = new Tile(4, 0);
		shape[0][1] = new Tile(5, 0, c);
		shape[0][2] = new Tile(6, 0);
		shape[1][0] = new Tile(4, 1, c);
		shape[1][1] = new Tile(5, 1, c, true);
		shape[1][2] = new Tile(6, 1, c);
		shape[2][0] = new Tile(4, 2);
		shape[2][1] = new Tile(5, 2, c);
		shape[2][2] = new Tile(6, 2);
		return shape;
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(c);
		g.fillRect(x+10, y, 10, 30);
		g.fillRect(x, y+10, 30, 10);
	}

	public Color getColor() {
		return c;
	}
}
