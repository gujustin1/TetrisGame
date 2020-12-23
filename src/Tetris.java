import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

public class Tetris extends JPanel implements KeyListener, JavaArcade, ActionListener {

    private javax.swing.Timer timer;
    private int points, points2, highscore;
    private boolean start= false, pause= false, gameover= false, canRotate= true;
    private ArrayList<Tetromino> list, list2;
    private Board board;
    private Board board2;
    private int n, n2, frames, currentFrames, speed;
    private Font f, f2, f3, f4;

    public Tetris(int width, int height) throws IOException, FontFormatException {

        list= new ArrayList<>();
        list2= new ArrayList<>();
        // Creates list for mino order
        makeList();

        board= new Board(20, 10);
        board2= new Board(20, 10);
        speed= 120;
        points= 0;
        points2= 0;
        n= 0;
        n2= 0;
        frames= 0;

        timer= new javax.swing.Timer(5, this);
        addKeyListener(this);// used for key controls

        f= Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("prstartk.ttf")))
            .deriveFont(Font.PLAIN,
                18);

        f2= Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("prstartk.ttf")))
            .deriveFont(Font.PLAIN,
                6);

        f3= Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("prstartk.ttf")))
            .deriveFont(Font.PLAIN,
                30);

        f4= Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("prstartk.ttf")))
            .deriveFont(Font.PLAIN,
                15);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        Color backColor= Color.white;
        setBackground(backColor);

        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        board2.draw(g);
        board.draw2(g);
        g.setColor(Color.black);
        g.fillRect(250, 0, 150, 500);
        g.fillRect(650, 0, 150, 500);
        g.setColor(Color.white);
        g.setFont(f);
        g.drawString("Score", 660, 20);
        g.drawString(Integer.toString(getPoints()), 660, 39);
        g.setColor(Color.white);
        g.setFont(f);
        g.drawString("Score", 260, 20);
        g.drawString(Integer.toString(getPoints2()), 260, 39);

        g.setColor(Color.white);
        g.fillRect(260, 50, 70, 70);
        g.fillRect(660, 50, 70, 70);
        g.setColor(Color.gray);
        g.fillRect(262, 52, 66, 66);
        g.fillRect(662, 52, 66, 66);
        list.get(n + 1).draw(g, 680, 75);
        list2.get(n2 + 1).draw(g, 280, 75);

        if (!start) {
            g.setColor(Color.white);
            g.setFont(f3);
            g.drawString("TETRIS", 300, 190);
            g.setFont(f2);
            g.drawString("but better", 355, 200);
            g.setFont(f4);
            g.drawString("PRESS START TO START GAME", 205, 220);
        }

        if (pause) {
            g.setColor(Color.white);
            g.setFont(f3);
            g.drawString("PAUSED", 60, 230);
            g.drawString("PAUSED", 460, 230);
        }

        if (gameover) {
            g.setColor(Color.white);
            g.setFont(f3);
            if (points > points2) {
                g.drawString("Player 1 Wins", 200, 300);
            }
            if (points2 > points) {
                g.drawString("Player 2 Wins", 200, 300);
            }
            if (points2 == points) {
                g.drawString("Tie", 300, 300);
            }

        }
        if (board2.lose()) {
            g.setColor(Color.white);
            g.setFont(f4);
            g.drawString("You topped out", 20, 190);
        }
        if (board.lose()) {
            g.setColor(Color.white);
            g.setFont(f4);
            g.drawString("You topped out", 420, 190);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!pause) {
            frames++ ;
            if (frames % 2000 == 0) {
                speed*= .8;
            }
            if (frames % speed == 0) {
                if (!board.lose()) {
                    board.updateDrop();
                }
                if (!board2.lose()) {
                    board2.updateDrop();

                }
            }
            if (frames % 100 == 0) {
                if (!board.isFalling() && !board.lose()) {
                    points+= 100;
                    board.solidify();
                    n++ ;
                    board.add(list.get(n));
                }
                if (!board2.isFalling() && !board2.lose()) {
                    points2+= 100;
                    board2.solidify();
                    n2++ ;
                    board2.add(list.get(n2));
                }
                if (board.isClear().size() > 0) {
                    if (board.isClear().size() == 1) {
                        points+= 400;
                    }
                    if (board.isClear().size() == 2) {
                        points+= 1600;
                    }
                    if (board.isClear().size() == 3) {
                        points+= 3600;
                    }
                    if (board.isClear().size() == 4) {
                        points+= 8000;
                    }
                    board.clearLines();
                }
                if (board2.isClear().size() > 0) {
                    if (board2.isClear().size() == 1) {
                        points2+= 400;
                    }
                    if (board2.isClear().size() == 2) {
                        points2+= 1600;
                    }
                    if (board2.isClear().size() == 3) {
                        points2+= 3600;
                    }
                    if (board2.isClear().size() == 4) {
                        points2+= 8000;
                    }
                    board2.clearLines();
                }

            }

            if (board.lose() && board2.lose()) {
                gameover= true;
            }
        }
        if (n % 100 == 0) {
            makeList();
        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_RIGHT:
            if (!pause) {
                board.moveRight();
            }
            break;
        case KeyEvent.VK_LEFT:
            if (!pause) {
                board.moveLeft();
            }
            break;
        case KeyEvent.VK_UP:
            if (canRotate && !pause) {
                board.rotate();
            }
            break;
        case KeyEvent.VK_DOWN:
            if (!pause) {
                board.updateDrop();
            }
            break;
        case KeyEvent.VK_SPACE:
            if (!pause && !board.lose()) {
                while (board.isFalling()) {
                    board.updateDrop();
                }
                points+= 100;
                board.solidify();
                n++ ;
                board.add(list.get(n));
            }
            break;
        case KeyEvent.VK_D:
            if (!pause) {
                board2.moveRight();
            }
            break;
        case KeyEvent.VK_A:
            if (!pause) {
                board2.moveLeft();
            }
            break;
        case KeyEvent.VK_W:
            board2.rotate();
            break;
        case KeyEvent.VK_S:
            if (!pause) {
                board2.updateDrop();
            }
            break;
        case KeyEvent.VK_Z:
            if (!pause && !board2.lose()) {
                while (board2.isFalling())
                    board2.updateDrop();
                points2+= 100;
                board2.solidify();
                n2++ ;
                board2.add(list.get(n2));
            }
            break;
        case KeyEvent.VK_1:
            System.out.println(getInstructions());
            break;
        case KeyEvent.VK_2:
            System.out.println(getCredits());
            break;
        case KeyEvent.VK_P:
            pauseGame();
            break;
        case KeyEvent.VK_ESCAPE:
            stopGame();
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public boolean running() {
        if (start) { return true; }
        return false;

    }

    @Override
    public void startGame() {
        board.add(list.get(n));
        board2.add(list.get(n2));
        timer.start();
        start= true;
    }

    @Override
    public void restart() {
        board.resetBoard();
        board2.resetBoard();
        points= 0;
        points2= 0;
        frames= 0;
        speed= 120;
        gameover= false;
        list= new ArrayList<>(0);
        list2= new ArrayList<>(0);
        makeList();
        startGame();
    }

    @Override
    public String getGameName() {
        return "Tetris";
    }

    @Override
    public void pauseGame() {
        if (!pause) {
            pause= true;
        } else {
            pause= false;
        }
    }

    @Override
    public void resumeGame() {
        if (!pause) {
            pause= true;
        } else {
            pause= false;
        }
    }

    @Override
    public String getInstructions() {
        String retValue= "";
        retValue+= "Player 1:\n";
        retValue+= "Left Arrow: move left\nRight Arrow: move right\nDown Arrow : soft-drop\nUp Arrow : rotate\nSpace : hard-drop\n\n";
        retValue+= "Player 2:\n";
        retValue+= "A: move left\nD: move right\nS: soft-drop\nW: rotate\nZ: hard-drop";
        retValue+= "\n\nHint: Soft-Dropping and then quickly moving right/left will also move the piece";
        retValue+= "\n\nP: pause\nEsc : end game";
        return retValue;
    }

    @Override
    public String getCredits() {
        return "Created by the Gu brothers";
    }

    @Override
    public String getHighScore() {
        return null;
    }

    @Override
    public void stopGame() {
        timer.stop();

    }

    @Override
    public int getPoints() {
        return points;
    }

    public int getPoints2() {
        return points2;
    }

    public void makeList() {
        for (int x= 0; x < 15; x++ ) {
            for (int rng : shuffle()) {
                if (rng == 0) {
                    list.add(new T());
                    list2.add(new T());
                }
                if (rng == 1) {
                    list.add(new L());
                    list2.add(new L());
                }
                if (rng == 2) {
                    list.add(new J());
                    list2.add(new J());
                }
                if (rng == 3) {
                    list.add(new O());
                    list2.add(new O());
                }
                if (rng == 4) {
                    list.add(new S());
                    list2.add(new S());
                }
                if (rng == 5) {
                    list.add(new Z());
                    list2.add(new Z());
                }
                if (rng == 6) {
                    list.add(new I());
                    list2.add(new I());
                }
                if (rng == 7) {
                    list.add(new X());
                    list2.add(new X());
                }
                if (rng == 8) {
                    list.add(new U());
                    list2.add(new U());
                }
            }
        }
    }

    public ArrayList<Integer> shuffle() {
        ArrayList<Integer> group= new ArrayList<>();
        group.add(0);
        group.add(1);
        group.add(2);
        group.add(3);
        group.add(4);
        group.add(5);
        group.add(6);
        group.add(7);
        group.add(8);
        Collections.shuffle(group);
        return group;
    }

}