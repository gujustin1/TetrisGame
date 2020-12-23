import java.awt.Color;
import java.awt.Graphics;

public class I extends Tetromino {
	private Color c;
	public I() {
		super(1,4);

		c = Color.cyan;
	}
	
	public Tile[][] getShape() {
		shape[0][0] = new Tile(4,0,c);
		shape[0][1] = new Tile(5,0,c, true);
		shape[0][2] = new Tile(6,0,c);
		shape[0][3] = new Tile(7,0,c);
		return shape;
	}
	public void draw(Graphics g, int x, int y){
		g.setColor(c);
		g.fillRect(x-5, y+5, 40, 10);
	}

	public Color getColor() {
		return c;
	}
}


