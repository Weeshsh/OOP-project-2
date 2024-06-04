package main.java.utils;

import main.java.HexWorld;
import main.java.RectangleWorld;
import main.java.World;
import main.java.organisms.Organism;
import main.java.organisms.animals.Human;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardPanel extends JPanel implements KeyListener, MouseListener {
    private int HEIGHT;
    private int WIDTH;
    private final int PADDING = 20;
    int cellSize;
    World world;
    Human player;
    private JPopupMenu popupMenu;
    private int clickedX;
    private int clickedY;


    public BoardPanel(World world, int maxWindowHeight, int width, int height) {
        updatePanel(world, maxWindowHeight, width, height);
        initPopupMenu();

        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.setFocusTraversalKeysEnabled(false);
        this.requestFocusInWindow();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopupMenu(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopupMenu(e);
            }
        });
    }

    public void updatePanel(World world, int maxWindowHeight, int width, int height) {
        int MAX_CELL_SIZE = 40;
        this.cellSize = Math.min(MAX_CELL_SIZE, maxWindowHeight / Math.max(width, height));
        this.setPreferredSize(new Dimension(cellSize * (height + 1) + 2 * PADDING, cellSize * (width + 1) + 2 * PADDING));

        this.world = world;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.player = world.getHuman();
        this.revalidate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < WIDTH + 1; i++) {
            for (int j = 0; j < HEIGHT + 1; j++) {
                Organism temp = world.getOrganism(new Pair(i, j));
                int[][] coords = coordsToHex(i,j, cellSize);

               if(world instanceof RectangleWorld) {
                    g.setColor(Color.BLACK);
                    g.drawRect(j * cellSize + PADDING, i * cellSize + PADDING, cellSize, cellSize);
                } else {
                    g.setColor(Color.BLACK);
                    g.drawPolygon(coords[0], coords[1], 6);
                }

                g.setColor(new Color(255, 255, 255));

                if (temp != null) {
                    Color color = temp.getColor();
                    g.setColor(color);
                    if(world instanceof RectangleWorld) {
                        g.fillRect(j * cellSize + PADDING, i * cellSize + PADDING, cellSize, cellSize);
                    } else {
                        g.fillPolygon(coords[0], coords[1], 6);
                    }
                }
            }
        }
    }

    public int[][] coordsToHex(int i, int j, int fieldSize) {
        int[][] coords = new int[2][6];
        double temp = j;
        if (i % 2 == 1)
            temp = j + 0.5;
        for (int k = 0; k < 6; k++) {
            int x = (int) (temp * fieldSize + fieldSize / 2 * Math.sin(k * 2 * Math.PI / 6.0));
            int y = (int) (i * fieldSize + fieldSize / 2 * Math.cos(k * 2 * Math.PI / 6.0));
            coords[0][k] = x + PADDING;
            coords[1][k] = y + fieldSize / 2 + PADDING;
        }

        return coords;
    }

    private void initPopupMenu() {
        popupMenu = new JPopupMenu();
        String[] options = {
                "Antylopa", "Lis", "Owca", "Żółw", "Wilk",
                "Trawa", "Mlecz", "Guarana", "Wilcze Jagody", "Barszcz S."
        };
        for(String opt : options) {
            JMenuItem item = new JMenuItem(opt);
            item.addActionListener(new MenuActionListener());
            popupMenu.add(item);
        }
    }

    private void showPopupMenu(MouseEvent e) {
        if (e.isPopupTrigger()) {
            clickedY = (e.getY() - PADDING) / cellSize;
            clickedX = (e.getX() - PADDING) / cellSize;
            if(world instanceof HexWorld){
                clickedX = (e.getX() - PADDING + cellSize/2) / cellSize;
            }

            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                player.setNextMove(-1, 0);
                break;
            case KeyEvent.VK_S:
                player.setNextMove(1, 0);
                break;
            case KeyEvent.VK_A:
                player.setNextMove(0, -1);
                break;
            case KeyEvent.VK_D:
                player.setNextMove(0, 1);
                break;
        }

        if(world instanceof HexWorld){
            switch (key){
                case KeyEvent.VK_Q:
                    player.setNextMove(1,-1);
                    break;
                case KeyEvent.VK_E:
                    player.setNextMove(-1,1);
                    break;
            }
        }
    }
    private class MenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem source = (JMenuItem) e.getSource();
            String option = source.getText();
            if(world.getOrganism(new Pair(clickedY, clickedX)) != null){
                System.out.println("Zajete pole!!!!!!");
                return;
            }
            switch(option){
                case "Antylopa":
                    world.addOrganism("animals.Antelope", new Pair(clickedY, clickedX));
                    break;
                case "Lis":
                    world.addOrganism("animals.Fox", new Pair(clickedY, clickedX));
                    break;
                case "Owca":
                    world.addOrganism("animals.Sheep", new Pair(clickedY, clickedX));
                    break;
                case "Żółw":
                    world.addOrganism("animals.Turtle", new Pair(clickedY, clickedX));
                    break;
                case "Wilk":
                    world.addOrganism("animals.Wolf", new Pair(clickedY, clickedX));
                    break;
                case "Trawa":
                    world.addOrganism("plants.Grass", new Pair(clickedY, clickedX));
                    break;
                case "Mlecz":
                    world.addOrganism("plants.Dandelion", new Pair(clickedY, clickedX));
                    break;
                case "Guarana":
                    world.addOrganism("plants.Guarana", new Pair(clickedY, clickedX));
                    break;
                case "Wilcze Jagody":
                    world.addOrganism("plants.Wolfberries", new Pair(clickedY, clickedX));
                    break;
                case "Barszcz S.":
                    world.addOrganism("plants.HSosnowskyi", new Pair(clickedY, clickedX));
                    break;
                default:
                    break;

            }
            repaint();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
        showPopupMenu(e);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        showPopupMenu(e);
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }

}
