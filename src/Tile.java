import java.awt.Color;
import java.awt.Graphics;

public class Tile {
	private int x, y;
	private Color c;
	boolean tetromino, solid, center, rotate;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		c = Color.black;
		tetromino = false;
		rotate = true;
		solid = false;
	}
	
	public Tile(int x, int y, Color c) {
		this.x = x;
		this.y = y;
		this.c = c;
		tetromino = true;
		rotate = true;
		this.center = false;
	}

	public Tile(int x, int y, Color c, boolean center) {
		this.x = x;
		this.y = y;
		this.c = c;
		tetromino = true;
		rotate = true;
		this.center = center;
	}

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setTetromino(boolean tetromino) {
        this.tetromino = tetromino;
    }

    public boolean isTetromino() {
        return tetromino;
    }
    
    public void setRotate(boolean r) {
    	rotate = r;
    }
    public boolean getRotate() {
    	return rotate;
    }

    public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Color getColor() {
		return c;
	}
	
	public void reset() {
	    setTetromino(false);
		c = Color.black;
		center = false;
		rotate = true;
		solid = false;
	}
	
	public void draw(Graphics g) {
		if(c == Color.black) {
			g.setColor(Color.GRAY);
			g.fillRect(x * 25, y * 25, 25, 25);
		}else{
			g.setColor(c.darker());
			g.fillRect(x * 25, y * 25, 25, 25);
		}
		g.setColor(c);
		g.fillRect(x*25+1 , y*25+1, 23, 23);
	}
	
	public void setColor(Color c) {
		this.c = c;
	}

	public void setCenter(boolean b) {
		center = b;
	}
	
	public boolean isCenter() {
		return center;
	}
    public void draw2(Graphics g) {
        if(c == Color.black) {
            g.setColor(Color.GRAY);
            g.fillRect(x * 25+400, y * 25, 25, 25);
        }else{
            g.setColor(c.darker());
            g.fillRect(x * 25+400, y * 25, 25, 25);
        }
        g.setColor(c);
        g.fillRect(x*25+401 , y*25+1, 23, 23);
    }
}