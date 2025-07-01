Clerk.markdown(
    Text.fillOut(
        """
           # simulateGame()-Methode
           Diese Methode nimmt ein Nim-Objekt als Eingabeparameter, welches den aktuellen Spielzustand darstellt. Dann wird eine lokale Variable currentGame auf das übergebene Nim-Objekt gesetzt. 
           isPlayerTurn ist eine boolean Variable, die anzeigt, ob der Spieler an der Reihe ist. Wenn sie auf true gesetzt wird, heßt es, dass der Spieler den ersten Zug macht.

           die while-Schleife läuft, solange das Spiel nicht beendet ist. Das wurde mit (!currentGame.isGameOver()) gemacht. In dieser Schleife wird ein zufälliger Zug randomMove generiert, indem die randomMove-Methode des Nim-Objekts aufgerufen wird. Der Zug wird auf currentGame angewendet, um den Spielzustand zu aktualisieren, also (currentGame = currentGame.play(randomMove)). Danach wird der Wert von isPlayerTurn negiert mit (isPlayerTurn = !isPlayerTurn), sodass die Reihenfolge zwischen den Spielern wechselt.
           Am Ende gibt die Methode isPlayerTurn zurück, wenn das Spiel vorbei ist. Wenn isPlayerTurn true ist, dann hat der Spieler den letzten Zug gemacht und gewonnen, andernfalls hat der Gegner gewonnen. 
           ## Anbei ist den Code zu meiner Erklärung. 

           ```java
                ${0} 
            ```
                    """, Text.cutOut("./NimView2222.java", "//simulateGame")));

//simulateGame  
boolean simulateGame(Nim nim) {
    Nim currentGame = nim;
    boolean isPlayerTurn = true; // Spieler startet, da der erste Zug im Aufruf gemacht wurde
    
    while (!currentGame.isGameOver()) {
        Move randomMove = currentGame.randomMove();
        currentGame = currentGame.play(randomMove);
        isPlayerTurn = !isPlayerTurn;
    }
    
    return isPlayerTurn; /*  Wenn letzter Zug des Spielers, dann gewonnen*/
}
//simulateGame


Clerk.markdown(
    Text.fillOut(
        """
 # mcMove()-Methode

Die Methode mcMove() nimmt ein Nim-Objekt nim als Paramter, das den aktuellen Zustand des Spiels darstellt. move ist eine List<Move>, die alle möglichen Züge im aktuellen Spiel speichert. N gibt die Anzahl der Simulationen an, die pro Zug durchgeführt werden, hier(10). g und v sind Variablen, die die Anzahl der gewonnenen bzw. verlorenen Simulationen zählen.

Die verschachtelten for-Schleifen generieren alle möglichen Züge fürs aktuelle Spiel(i geht durch die Reihen und j representiert die Anzahl der Objekte in jeder Reihe, die der Spieler nehmen könnte). Jeder mögliche Zug wird zur moves-Liste hinzugefügt.

Das scores-Array speichert die Erfolgschancen(in Prozent) für jede Bewegung. Die äußere for-Schleife (for(int i = 0; i < moves.size(); i++)) iteriert durch jeden möglichen Zug in moves.
Innerhalb der Schleife wird eine weitere Schleife (for(int j = 0; j < N; j++)) N-mal ausgeführt, um für den aktuellen Zug mehrere Spiele zu simulieren.
Bei jeder Simulatation wird dann simulateGame aufgerufen, um ein Spiel zu simulieren und festzulegen, ob der Spieler gewonnen hat(player == true) oder verloren hat (player == false).
Die g-Variable wird bei einer gewonnenen Situation um eins erhöht, und v bei einer verlorenen.
Nach N Simulationen wird der Gewinnteil für den Zug als Prozentsatz berechnet und in scores[i] gespeichert:((g/N)*100) gibt die Erfolgswahrscheinlichkeit in Prozent an.
Danach werden g und v zurückgesetzt, damit die nächste Runde von Simulationen für den nächsten Zug beginnen kann.

# bestScore
bestScore speichert den höchsten Wert im scores-Array, der der erfolgversprechendste Zug ist.
Eine Schleife durchsucht das scores-Array nach dem bestScore und speichert die Position des besten Zugs in pos.
Die Methode gibt den Zug in moves zurück, def der erfolgversprechendste war.

```java
${0}
```
    """, Text.cutOut("./NimView2222.java", "//mcMove")));

//mcMove
public Move mcMove(Nim nim){
    List<Move> moves = new ArrayList<>();
    int N = 10;
    int g = 0;
    int v = 0;
    boolean player;
    for(int i = 0; i < nim.rows.length; i++){
        for(int j = 1; j <= nim.rows[i]; j++){
            moves.add(Move.of(i, j));
        }
    }
    int[] scores = new int[moves.size()];
    for(int i = 0; i < moves.size(); i++) { 
        for(int j = 0; j < N; j++){
            player = simulateGame(nim);
            if(player == false) {
                g = g++;  
            } else 
            if(player == true) {
                v = v++;
            }
        }
        scores[i] = (g/N)*100;
        g = 0;
        v = 0;
    }
    int bestScore = Arrays.stream(scores).max().getAsInt();
    int pos = 0;
    for(int i = 0; i < scores.length; i++){
        if(scores[i] == bestScore){
            pos = i;
        }
    }
    return moves.get(pos);
}


//mcMove

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

class Move {
    final int row, number;
    static Move of(int row, int number) {//nimm ein Number auf eine Zeile
        return new Move(row, number);
    }
    private Move(int row, int number) {
        if (row < 0 || number < 1) throw new IllegalArgumentException();//muss mindestens eine Zeile sein oder 
        this.row = row;
        this.number = number;
    }
    public String toString() {
        return "(" + row + ", " + number + ")";
    }
}

interface NimGame {
    static boolean isWinning(int... numbers) {
        return Arrays.stream(numbers).reduce(0, (i,j) -> i ^ j) != 0;
        // klassische Variante:
        // int res = 0;
        // for(int number : numbers) res ^= number;
        // return res != 0;
    }
    NimGame play(Move m);
    String toString();
}

class Nim implements NimGame {
    private Random r = new Random();
    int[] rows;
    public static Nim of(int... rows) {
        return new Nim(rows);
    }
    public Move randomMove() {
        assert !isGameOver();
        int row;
        do {
            row = r.nextInt(rows.length);
        } while (rows[row] == 0);
        int number = r.nextInt(rows[row]) + 1;
        return Move.of(row, number);
    }
    public boolean isGameOver() {
        return Arrays.stream(rows).allMatch(n -> n == 0);
    }
    public Nim(int... rows) {
        assert rows.length >= 1;
        assert Arrays.stream(rows).allMatch(n -> n >= 0);
        this.rows = Arrays.copyOf(rows, rows.length);
    }
    public Nim play(Move m) {
        
        assert m.row < rows.length && m.number <= rows[m.row];
        Nim nim = Nim.of(rows);
        nim.rows[m.row] -= m.number;
        this.rows = nim.rows;
        return this;
    }

    boolean simulateGame(Nim nim) {
        Nim currentGame = nim;
        boolean isPlayerTurn = true; // Spieler startet, da der erste Zug im Aufruf gemacht wurde
        
        while (!currentGame.isGameOver()) {
            Move randomMove = currentGame.randomMove();
            currentGame = currentGame.play(randomMove);
            isPlayerTurn = !isPlayerTurn;
        }
        
        return isPlayerTurn; // Wenn letzter Zug des Spielers, dann gewonnen
    }

    public Move mcMove(Nim nim){
        List<Move> moves = new ArrayList<>();
        int N = 10;
        int g = 0;
        int v = 0;
        boolean player;
        for(int i = 0; i < nim.rows.length; i++){
            for(int j = 1; j <= nim.rows[i]; j++){
                moves.add(Move.of(i, j));
            }
        }
        int[] scores = new int[moves.size()];
        for(int i = 0; i < moves.size(); i++) { 
            for(int j = 0; j < N; j++){
                player = simulateGame(nim);
                if(player == false) {
                    g = g++;  
                } else 
                if(player == true) {
                    v = v++;
                }
            }
            scores[i] = (g/N)*100;
            g = 0;
            v = 0;
        }
        int bestScore = Arrays.stream(scores).max().getAsInt();
        int pos = 0;
        for(int i = 0; i < scores.length; i++){
            if(scores[i] == bestScore){
                pos = i;
            }
        }
        return moves.get(pos);
    }
    
  
    public String toString() {
        String s = "";
        for(int n : rows) s += "\n" + "I ".repeat(n);
        return s;
    }
@ Override
    public int hashCode() {
        int sum = 0;
        int mul = 1;
        if(this.rows.length > 5 || Arrays.stream(rows).allMatch(n -> n>7)) {
             throw new IllegalArgumentException("Please enter  correct values");
        }
        if(this.rows == null) return 0;
        int prime = 97;
        for(int value : this.rows){
            if(value == 0) continue;
            sum += value;
            mul *= (value + prime);
        } return sum * prime + mul;
    }

    @Override
    public boolean equals (Object other){
    int wert;
   // if(other == null) return false;
    if(other == this) return true;
    if(this.getClass() != other.getClass() || other == null) return false;
    Nim that = (Nim)other;
    return this.hashCode() == that.hashCode();
}

}
    public class NimView {
        Turtle turtle;
        Nim tim;
        int x;
        int y = 100;
     NimView(Turtle turtle, Nim tim){
        this.tim = tim;
        this.turtle = turtle;
        turtle.reset();
    }
       
        public String toString() {
            this.show();
        String s = "";
        for(int n : tim.rows) s += "\n" + "I".repeat(n) ;
        return s;
    }

    private void show(){
        String str = "";
        for(int i = 0; i < tim.rows.length; i++){
            x = 100;
            for(int j = 0; j < tim.rows[i]; j++){
                turtle.moveTo(x,y);
                turtle.left(90);
                turtle.lineWidth(10);
                turtle.forward(50);
                x = x + 50;
                turtle.right(90).moveTo(x, y);

            } str = "\n";
            y = y + 60;
            turtle.moveTo(100,y);
        }
        
    }

    
    }