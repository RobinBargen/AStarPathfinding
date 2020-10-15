package com.company;

import com.company.Board.Board;
import com.company.Problem.ProblemSolvingAgent;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;

    private static final int OBSTACLE_START_PERCENTAGE = 10;

    private static Board board;
    private static ProblemSolvingAgent agent;

    private static JPanel sliderPanel() {
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.PAGE_AXIS));
        JLabel sliderLabel = new JLabel("Adjust Obstacle Percentage", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, board.getMinObstaclePercentage(), board.getMaxObstaclePercentage(), OBSTACLE_START_PERCENTAGE);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                board.setObstaclePercentage(slider.getValue());
            }
        });
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setToolTipText("Adjust slider to set the percentage coverage of obstacles.");
        slider.setAlignmentX(Component.CENTER_ALIGNMENT);

        sliderPanel.add(sliderLabel);
        sliderPanel.add(slider);

        return sliderPanel;
    }

    private static JPanel buttonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton generateButton = new JButton("Generate new Map!");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.updateBoard();
                board.repaint();
                try {
                    agent.solveProblem(board);
                } catch (Exception e1) {
                    System.out.println("Couldn't find path!");
                }
            }
        });
        buttonPanel.add(generateButton);

        return buttonPanel;
    }

    private static JPanel optionsPanel() {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        optionsPanel.setLayout(new FlowLayout());
        optionsPanel.add(sliderPanel());
        optionsPanel.add(buttonPanel());
        return optionsPanel;
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("A* Pathfinding");
        frame.setSize(WIDTH, HEIGHT + 108);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout());

        board = new Board(HEIGHT, WIDTH, 10, OBSTACLE_START_PERCENTAGE);
        try {
            agent = new ProblemSolvingAgent(board);
        } catch (Exception e) {
            System.out.println("Couldn't find path!");
        }

        rootPanel.add(board, BorderLayout.CENTER);
        rootPanel.add(optionsPanel(), BorderLayout.SOUTH);
        frame.add(rootPanel);
        frame.setVisible(true);
    }
}
