/*
 * The MIT License
 *
 * Copyright 2025 Luis Guisso &lt;luis.guisso at ifnmg.edu.br&gt;.
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
package io.github.guisso.meleemanager;

import io.github.guisso.jankenpon.Result;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.github.guisso.jankenpon.AbstractPlayer;
import io.github.guisso.jankenpon.Move;

/**
 * Manages disputes between players
 *
 * @author Luis Guisso &lt;luis.guisso at ifnmg.edu.br&gt;
 * @version 0.1
 * @since 0.1, Jul 18, 2025
 */
public final class JanKenPonManager {

    // Turns
    public static final int TURNS = 200;
    private static int currentTurn;

    // Decisions
    private static Move playerAPreviousMove;
    private static Move playerBPreviousMove;

    // Players and their scores
    private static List<AbstractPlayer> players;
    
    static {
        playerAPreviousMove = Move.NONE;
        playerBPreviousMove = Move.NONE;
    }

    public static List<AbstractPlayer> loadPlayers()
            throws ClassNotFoundException, MalformedURLException, IOException,
            NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        File playersDir = new File("players");

        if (!playersDir.exists() || !playersDir.isDirectory()) {
            throw new RuntimeException("Players' directory not found");
        }

        players = new ArrayList<>();

//        Pattern firstNamePattern = Pattern.compile("^.\\S+");
//        Pattern lastNamePattern = Pattern.compile("\\S+.$");
//
        for (File file : playersDir.listFiles()) {
            if (file.getName().endsWith(".jar")) {

                URL jarUrl = file.toURI().toURL();

                try (URLClassLoader classLoader
                        = new URLClassLoader(new URL[]{jarUrl}); //
                         JarFile jarFile = new JarFile(file)) {

                    Enumeration<JarEntry> entries = jarFile.entries();

                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();

                        if (name.endsWith(".class")) {
                            String className = name.replace('/', '.').replace(".class", "");

                            Class<?> clazz = classLoader.loadClass(className);

                            if (AbstractPlayer.class.isAssignableFrom(clazz)) {
                                AbstractPlayer player = (AbstractPlayer) clazz
                                        .getDeclaredConstructor().newInstance();

//                                // First and last names only
//                                String firstName = firstNamePattern
//                                        .matcher(player.getDeveloperName()).group();
//                                String lastName = lastNamePattern
//                                        .matcher(player.getDeveloperName()).group();
//                                
//                                player.setName(firstName + " " + lastName);
//                                
                                players.add(player);
                            }
                        }
                    }
                }
            }
        }

        return players;
    }

    /**
     * Promotes the melee
     *
     * @param playerA Player A
     * @param playerB Player B
     * @return The result of the current melee
     */
    public static Result melee(AbstractPlayer playerA, AbstractPlayer playerB) {
        Move playerAMove = playerA.makeMyMove(playerBPreviousMove);
        Move playerBMove = playerB.makeMyMove(playerAPreviousMove);

        // Tie results on 0 points to each
        Result result = playerAMove.versus(playerBMove, playerA, playerB);

        // Log
        System.out.printf("%d,%s,%s,%d\n",
                playerA.getTotaScore(),
                playerAMove,
                playerBMove,
                playerB.getTotaScore());

        playerAPreviousMove = playerAMove;
        playerBPreviousMove = playerBMove;

        if (++currentTurn >= TURNS) {
            currentTurn = 0;
            playerAPreviousMove = Move.NONE;
            playerBPreviousMove = Move.NONE;
        }

        return result == null ? Result.NONE : result;
    }

    /**
     * Randmomize players positions
     *
     * @param melees All pair players
     */
    public static void randomize(AbstractPlayer[][] melees) {

        for (int currentPosition = 0;
                currentPosition < melees.length;
                currentPosition++) {

            try {
                // Randomize melee position
                int randomPostion = SecureRandom.getInstanceStrong()
                        .nextInt(0, melees.length);
                AbstractPlayer[] temporaryPair = melees[currentPosition];
                melees[currentPosition] = melees[randomPostion];
                melees[randomPostion] = temporaryPair;

                // Randomize first player
                if (SecureRandom.getInstanceStrong().nextBoolean()) {
                    AbstractPlayer temporaryPlayer = melees[randomPostion][0];
                    melees[randomPostion][0] = melees[randomPostion][1];
                    melees[randomPostion][1] = temporaryPlayer;
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(JanKenPonManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Show score
     */
//    private static void printScore(boolean ordered) {
//        // Obtains the stream to be processed
//        Stream<Map.Entry<AbstractPlayer, Integer>> stream = totalScore.entrySet()
//                .stream();
//
//        // Sort if required
//        if (ordered) {
//            stream = stream.sorted(Map.Entry.<AbstractPlayer, Integer>comparingByValue(
//                    Comparator.reverseOrder()));
//        }
//
//        // Updates factor if max score is greater than 2.000
//        int maxPoints = totalScore.entrySet().stream()
//                .map(Map.Entry::getValue)
//                .max(Integer::compare)
//                .orElse(0);
//
//        if (maxPoints > 2000) {
//            factor = 60. / maxPoints;
//        }
//
//        // Prints the score
//        stream.forEach(e -> System.out.println("=".repeat((int) (e.getValue() * factor))
//                + "> [%,d] ".formatted(e.getValue())
//                + ((AbstractPlayer) e.getKey()).getDeveloperName()
//                + " / "
//                + ((AbstractPlayer) e.getKey()).getDeveloperName()
//        //                        + "\n"
//        ));
//    }
}
