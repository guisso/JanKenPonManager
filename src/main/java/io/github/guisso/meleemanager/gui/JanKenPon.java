/*
 * The MIT License
 *
 * Copyright 2025 Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.guisso.meleemanager.gui;

import io.github.guisso.jankenpon.AbstractPlayer;
import io.github.guisso.jankenpon.Move;
import io.github.guisso.meleemanager.JanKenPonManager;
import io.github.guisso.jankenpon.Result;
import io.github.guisso.jankenpon.Util;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;
 * @version 0.5
 * @since 0.1, Jul 19, 2025
 */
public class JanKenPon
        extends JFrame {

    private static int currentMelee;
    private static int totalMelees;
    private static AbstractPlayer[][] melees;

    public static final int MAX_TURNS = JanKenPonManager.TURNS;
    private static int currentTurn;

    private List<AbstractPlayer> players;
    private final DefaultListModel<AbstractPlayer> playersListModel;

    private AbstractPlayer playerA;
    private AbstractPlayer playerB;

    private static final java.util.logging.Logger logger
            = java.util.logging.Logger.getLogger(JanKenPon.class.getName());

    public static final int ROWS = 10;
    public static final int COLUMNS = 20;
    private static final JLabel[][] MOVES;

    private Timer timer;
    public static int DEFAULT_DELAY = 250;

    static {
        MOVES = new JLabel[ROWS][COLUMNS];
    }

    /**
     * Creates new form JanKenPon
     */
    public JanKenPon() {
        playersListModel = new DefaultListModel<>();

        initComponents();

        initilizeAllMoves();

        SwingUtilities.invokeLater(() -> {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setVisible(true);
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMelees = new javax.swing.JPanel();
        pnlRealtimeDispute = new javax.swing.JPanel();
        pnlPlayerA = new javax.swing.JPanel();
        lblPlayerA = new javax.swing.JLabel();
        lblPlayerAScore = new javax.swing.JLabel();
        lblCurrentTurn = new javax.swing.JLabel();
        pnlPlayerB = new javax.swing.JPanel();
        lblPlayerB = new javax.swing.JLabel();
        lblPlayerBScore = new javax.swing.JLabel();
        scrPlayers = new javax.swing.JScrollPane();
        lstPlayers = new javax.swing.JList<>();
        mnuMainBar = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuFileDelay = new javax.swing.JMenu();
        mnuFileDelay100 = new javax.swing.JMenuItem();
        mnuFileDelay250 = new javax.swing.JMenuItem();
        mnuFileDelay500 = new javax.swing.JMenuItem();
        mnuFileStartTournament = new javax.swing.JMenuItem();
        mnuHelp = new javax.swing.JMenu();
        mnuHelpAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JanKenPon");

        pnlMelees.setLayout(new java.awt.GridLayout(10, 20, 4, 2));

        pnlPlayerA.setLayout(new java.awt.BorderLayout());

        lblPlayerA.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblPlayerA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPlayerA.setText("Player A");
        lblPlayerA.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblPlayerA.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        pnlPlayerA.add(lblPlayerA, java.awt.BorderLayout.PAGE_END);

        lblPlayerAScore.setFont(new java.awt.Font("Segoe UI", 1, 60)); // NOI18N
        lblPlayerAScore.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPlayerAScore.setText("?");
        lblPlayerAScore.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        pnlPlayerA.add(lblPlayerAScore, java.awt.BorderLayout.LINE_END);

        pnlRealtimeDispute.add(pnlPlayerA);

        lblCurrentTurn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCurrentTurn.setPreferredSize(new java.awt.Dimension(350, 200));
        pnlRealtimeDispute.add(lblCurrentTurn);

        pnlPlayerB.setLayout(new java.awt.BorderLayout());

        lblPlayerB.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblPlayerB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPlayerB.setText("Player B");
        pnlPlayerB.add(lblPlayerB, java.awt.BorderLayout.CENTER);

        lblPlayerBScore.setFont(new java.awt.Font("Segoe UI", 1, 60)); // NOI18N
        lblPlayerBScore.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPlayerBScore.setText("?");
        lblPlayerBScore.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        pnlPlayerB.add(lblPlayerBScore, java.awt.BorderLayout.PAGE_START);

        pnlRealtimeDispute.add(pnlPlayerB);

        lstPlayers.setModel(playersListModel);
        scrPlayers.setViewportView(lstPlayers);

        mnuFile.setText("File");

        mnuFileDelay.setText("Delay");

        mnuFileDelay100.setText("100 ms");
        mnuFileDelay100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFileDelay100ActionPerformed(evt);
            }
        });
        mnuFileDelay.add(mnuFileDelay100);

        mnuFileDelay250.setText("250 ms");
        mnuFileDelay250.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFileDelay250ActionPerformed(evt);
            }
        });
        mnuFileDelay.add(mnuFileDelay250);

        mnuFileDelay500.setText("500 ms");
        mnuFileDelay500.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFileDelay500ActionPerformed(evt);
            }
        });
        mnuFileDelay.add(mnuFileDelay500);

        mnuFile.add(mnuFileDelay);

        mnuFileStartTournament.setText("Start tournament");
        mnuFileStartTournament.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFileStartTournamentActionPerformed(evt);
            }
        });
        mnuFile.add(mnuFileStartTournament);

        mnuMainBar.add(mnuFile);

        mnuHelp.setText("Help");

        mnuHelpAbout.setText("About");
        mnuHelpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHelpAboutActionPerformed(evt);
            }
        });
        mnuHelp.add(mnuHelpAbout);

        mnuMainBar.add(mnuHelp);

        setJMenuBar(mnuMainBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlRealtimeDispute, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlMelees, javax.swing.GroupLayout.PREFERRED_SIZE, 1168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(scrPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlMelees, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlRealtimeDispute, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuFileStartTournamentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuFileStartTournamentActionPerformed
        if (timer != null && timer.isRunning()) {
            mnuFileStartTournament.setText("Start again");
            timer.stop();
            return;
        }

        try {
            startMelees();
        } catch (Exception ex) {
            System.getLogger(JanKenPon.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }//GEN-LAST:event_mnuFileStartTournamentActionPerformed

    private void mnuFileDelay100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuFileDelay100ActionPerformed
        adjustDelay(100);
    }//GEN-LAST:event_mnuFileDelay100ActionPerformed

    private void mnuFileDelay250ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuFileDelay250ActionPerformed
        adjustDelay(250);
    }//GEN-LAST:event_mnuFileDelay250ActionPerformed

    private void mnuFileDelay500ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuFileDelay500ActionPerformed
        adjustDelay(500);
    }//GEN-LAST:event_mnuFileDelay500ActionPerformed

    private void mnuHelpAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuHelpAboutActionPerformed
        JOptionPane.showMessageDialog(this, """
                                            Press "File/Start tournament" to first start
                                            or press "File/Pause" to pause the execution
                                            or press "File/Start again" to resume.
                                            
                                            👨‍💻 Guisso
                                            """,
                "JanKenPon Version 0.5", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_mnuHelpAboutActionPerformed

    private void adjustDelay(int delay) {
        if (timer != null && timer.isRunning()) {
            timer.setDelay(delay);
        }
    }

    public void startMelees()
            throws Exception {

        // Stop if players fail to load
        try {
            if (players == null || players.isEmpty()) {
                players = JanKenPonManager.loadPlayers();
                playersListModel.addAll(players);
            }

        } catch (ClassNotFoundException | IOException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new Exception("Não foi possível carregar os competidores");
        }

        // Organize the melees for first time
        if (currentMelee == 0) {
            // Start configuration
            totalMelees = (players.size() * (players.size() - 1)) / 2;
            melees = new AbstractPlayer[totalMelees][2];

            // Sets up all the melees
            int count = 0;
            for (int i = 0; i < players.size(); i++) {
                // You x You?? No: j = i + 1, Yes: j = i <-- update totalMelees (!)
                for (int j = i + 1; j < players.size(); j++) {

                    // Store couple players
                    melees[count][0] = players.get(i);
                    melees[count][1] = players.get(j);

                    // Next melee...
                    count++;
                }
            }

            // Randomize the melees
            JanKenPonManager.randomize(melees);

            timer = new Timer(DEFAULT_DELAY, e -> {

                // End of the turns
                if (currentMelee >= totalMelees) {
                    ((Timer) e.getSource()).stop();

                    lblPlayerA.setText(null);
                    lblPlayerB.setText(null);
                    lblPlayerAScore.setText("---");
                    lblPlayerBScore.setText("---");
                    lblCurrentTurn.setIcon(Result.SCISSORS_SCISSORS_TIE.IMAGE);

                    // Log
                    for (AbstractPlayer player : players) {
                        System.out.println(player);
                    }

                    return;
                }

                // Update the players' name
                if (currentTurn == 0) {
                    playerA = melees[currentMelee][0];
                    playerB = melees[currentMelee][1];

                    lblPlayerA.setText(playerA.getDeveloperName());
                    lblPlayerB.setText(playerB.getDeveloperName());

                    // Log
                    System.out.println(playerA.getDeveloperName()
                            + "," + playerB.getDeveloperName());
                }

                // Log
                System.out.printf("%d,%d,",
                        currentMelee,
                        currentTurn);

                //Run the melee and store the image result
                // melee() prints ..,points,move,move,points\n
                Result result = JanKenPonManager.melee(playerA, playerB);

                String imagePath
                        = result == null
                                ? null
                                : result.IMAGE_PATH;

                Icon currentResult
                        = imagePath == null
                                ? null
                                : Util.loadImage(imagePath, 200f, 0);

                // Show the moves
                int row = currentTurn / COLUMNS;
                int column = currentTurn % COLUMNS;

                lblCurrentTurn.setIcon(currentResult);
                MOVES[row][column].setIcon(result.IMAGE);

                // Update the scores
                lblPlayerAScore.setText(Integer.toString(playerA.getTotaScore()));
                lblPlayerBScore.setText(Integer.toString(playerB.getTotaScore()));

                // Update the result list
                Collections.sort(players,
                        Comparator.comparing(AbstractPlayer::getTotaScore)
                                .reversed());
                playersListModel.clear();
                playersListModel.addAll(players);
//                playersListModel.notifyDataUpdate();

                // Next turn
                if (++currentTurn >= MAX_TURNS) {
                    currentTurn = 0;
                    currentMelee++;
                    Move.resetConsecutiveWins();
                    initilizeAllMoves();
                }
            });

            timer.setRepeats(true);
        }

        mnuFileStartTournament.setText("Pause");
        timer.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        new JanKenPon();

    }

    private void initilizeAllMoves() {
        // Initialize all moves
        pnlMelees.removeAll();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                MOVES[i][j] = new JLabel();
                pnlMelees.add(MOVES[i][j]);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblCurrentTurn;
    private javax.swing.JLabel lblPlayerA;
    private javax.swing.JLabel lblPlayerAScore;
    private javax.swing.JLabel lblPlayerB;
    private javax.swing.JLabel lblPlayerBScore;
    private javax.swing.JList<AbstractPlayer> lstPlayers;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenu mnuFileDelay;
    private javax.swing.JMenuItem mnuFileDelay100;
    private javax.swing.JMenuItem mnuFileDelay250;
    private javax.swing.JMenuItem mnuFileDelay500;
    private javax.swing.JMenuItem mnuFileStartTournament;
    private javax.swing.JMenu mnuHelp;
    private javax.swing.JMenuItem mnuHelpAbout;
    private javax.swing.JMenuBar mnuMainBar;
    private javax.swing.JPanel pnlMelees;
    private javax.swing.JPanel pnlPlayerA;
    private javax.swing.JPanel pnlPlayerB;
    private javax.swing.JPanel pnlRealtimeDispute;
    private javax.swing.JScrollPane scrPlayers;
    // End of variables declaration//GEN-END:variables

    /**
     * List model with manual update
     */
//    public static class CustomListModel
//            extends DefaultListModel<AbstractPlayer> {
//
//        // Updates GUI when data changes
//        public void notifyDataUpdate() {
//            fireContentsChanged(this, 0, getSize());
//        }
//    }
}
