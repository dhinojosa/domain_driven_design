package com.xyzcorp.adapter.in;

import com.xyzcorp.domain.Game;
import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleGame {

    public static String inputFromPlayer() {
        System.out.println("[H]it or [S]tand?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void welcomeScreen() {
        System.out.println(ansi()
            .bgBright(Ansi.Color.WHITE)
            .eraseScreen()
            .cursor(1, 1)
            .fgGreen().a("Welcome to")
            .fgRed().a(" Jitterted's")
            .fgBlack().a(" BlackJack"));
    }

    public static void resetScreen() {
        System.out.println(ansi().reset());
    }

    public static void main(String[] args) {
        Game game = new Game();

        welcomeScreen();

        game.initialDeal();
        game.play();

        resetScreen();
    }
}
