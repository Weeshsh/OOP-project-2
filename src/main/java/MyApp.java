package main.java;

import main.java.utils.BoardPanel;
import main.java.utils.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class MyApp {
    private World world;
    private BoardPanel boardPanel;
    private JTextArea logs;
    private JFrame mainFrame;
    private final int maxWindowHeight = 750;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MyApp app = new MyApp();
                app.inputPanel();
            }
        });
    }

    private void inputPanel() {
        JTextField widthField = new JTextField(5);
        JTextField heightField = new JTextField(5);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Szerokość:"));
        inputPanel.add(widthField);
        inputPanel.add(Box.createHorizontalStrut(15));
        inputPanel.add(new JLabel("Wysokość:"));
        inputPanel.add(heightField);

        JButton hexButton = new JButton("Hex World");
        JButton rectangleButton = new JButton("Rectangle World");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hexButton);
        buttonPanel.add(rectangleButton);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(inputPanel);
        panel.add(buttonPanel);

        JFrame frame = new JFrame("Podaj rozmiar świata");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        hexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int width = Integer.parseInt(widthField.getText()) - 1;
                int height = Integer.parseInt(heightField.getText()) - 1;
                world = World.getActive("Hexagonal", width, height);
                world.init();
                createAppInterface(width, height);
                frame.dispose();
            }
        });

        rectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int width = Integer.parseInt(widthField.getText()) - 1;
                int height = Integer.parseInt(heightField.getText()) - 1;
                world = World.getActive("Rectangle", width, height);
                world.init();
                createAppInterface(width, height);
                frame.dispose();
            }
        });
    }

    public void createAppInterface(int worldWidth, int worldHeight) {
        mainFrame = new MyFrame();
        boardPanel = new BoardPanel(world, maxWindowHeight, worldWidth, worldHeight);

        JPanel logsView = new JPanel(new BorderLayout());
        logsView.setBackground(Color.WHITE);
        logs = new JTextArea(world.logs);
        logs.setEditable(false);
        logs.setLineWrap(true);
        logs.setWrapStyleWord(true);
        JScrollPane scrollableLogs = new JScrollPane(logs);
        scrollableLogs.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableLogs.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        logsView.add(scrollableLogs, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        int maxWindowWidth = 1200;
        bottomPanel.setPreferredSize(new Dimension(maxWindowWidth, 75));
        bottomPanel.setBackground(Color.GRAY);

        JButton turn = new JButton("Tura");
        turn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.turn();
                boardPanel.repaint();
                logs.setText(world.logs);
                boardPanel.requestFocusInWindow();
            }
        });

        JButton switchType = new JButton("Zmien rodzaj");
        switchType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world = world.swapType();
                world.init();
                updateAppInterface(world.width, world.height);
            }
        });

        JButton save = new JButton("Zapisz");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileOutputStream fos = new FileOutputStream("save.txt");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(world);
                    oos.close();
                    fos.close();
                    logs.setText("Zapisano swiat do pliku!");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton read = new JButton("Wczytaj");
        read.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileInputStream fis = new FileInputStream("save.txt");
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    world = (World) ois.readObject();
                    World.setActive(world);
                    ois.close();
                    fis.close();
                    updateAppInterface(world.width, world.height);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton ability = new JButton("Umiejetnosc");
        ability.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.getHuman().activateAbility();
                boardPanel.requestFocusInWindow();
            }
        });

        bottomPanel.add(turn);
        bottomPanel.add(switchType);
        bottomPanel.add(save);
        bottomPanel.add(read);
        bottomPanel.add(ability);

        JPanel remainingSpace = new JPanel(new BorderLayout());
        remainingSpace.add(logsView, BorderLayout.CENTER);

        mainFrame.add(remainingSpace, BorderLayout.CENTER);
        mainFrame.add(boardPanel, BorderLayout.WEST);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);
        mainFrame.pack();
        boardPanel.requestFocusInWindow();
    }

    public void updateAppInterface(int worldWidth, int worldHeight) {
        boardPanel.updatePanel(world, maxWindowHeight, worldWidth, worldHeight);
        logs.setText(world.logs);
        mainFrame.pack();
        boardPanel.requestFocusInWindow();
    }
}
