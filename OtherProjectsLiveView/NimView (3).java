Clerk.markdown(
    Text.fillOut(
"""
>Betrachtet man den obigen Code, so kann man feststellen, dass der Code das Nim_Spiel, ein bekanntes mathematisches Spiel, implementiert, das von zwei Spieler gespielt wird. Ziel des Spiels ist es, durchs Löschen von Objekten z.B. Hölzchen, aus divers Reihen den letzten Zug strategisch so zu setzen, dass der Gegner gezwungen wird, das Spiel zu verlieren.

Die erstellten Codes sehen folgendermaßen aus:
```java
${0}
```
    """, Text.cutOut("./NimView.java", "// meinCode")));

// meinCode

import java.util.ArrayList;
import java.util.Arrays;
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
    NimGame play(Move... moves);
    Move randomMove();
    Move bestMove();
    boolean isGameOver();
    String toString();
}

class Nim implements NimGame {
    private Random r = new Random();
    int[] rows;
    public static Nim of(int... rows) {
        return new Nim(rows);
    }
    private Nim(int... rows) {
        assert rows.length >= 1;
        assert Arrays.stream(rows).allMatch(n -> n >= 0);
        this.rows = Arrays.copyOf(rows, rows.length);
    }
    public Nim play(Move m) {
        assert !isGameOver();
        assert m.row < rows.length && m.number <= rows[m.row];
        Nim nim = Nim.of(rows);
        nim.rows[m.row] -= m.number;
        this.rows = nim.rows;
        return this;
    }
    public Nim play(Move... moves) {
        Nim nim = this;
        for(Move m : moves) nim = nim.play(m);
        return nim;
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
    public Move bestMove() {
        assert !isGameOver();
        if (!NimGame.isWinning(rows)) return randomMove();
        Move m;
        do {
            m = randomMove();
        } while(NimGame.isWinning(play(m).rows));
        return m;
    }
    public boolean isGameOver() {
        return Arrays.stream(rows).allMatch(n -> n == 0);
    }
    /*public String toString() {
        String s = "";
        for(int n : rows) s += "\n" + "I ".repeat(n);
        return s;
    }*/
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





Nim nim = Nim.of(2,3,4);
assert nim != nim.play(Move.of(1,2)) : "Return a new Nim instance";

int[] randomSetup(int... maxN) {
    Random r = new Random();
    int[] rows = new int[maxN.length];
    for(int i = 0; i < maxN.length; i++) {
        rows[i] = r.nextInt(maxN[i]) + 1;
    }
    return rows;
}

ArrayList<Move> autoplay(NimGame nim) {
    ArrayList<Move> moves = new ArrayList<>();
    while (!nim.isGameOver()) {
        Move m = nim.bestMove();
        moves.add(m);
        nim = nim.play(m);
    }
    return moves;
}

boolean simulateGame(int... maxN) {
    Nim nim = Nim.of(randomSetup(maxN));
    // System.out.println(nim);
    // System.out.println((NimGame.isWinning(nim.rows) ? "first" : "second") + " to win"); 
    ArrayList<Move> moves = autoplay(nim);
    // System.out.println(moves);
    return (NimGame.isWinning(nim.rows) && (moves.size() % 2) == 1) ||
           (!NimGame.isWinning(nim.rows) && (moves.size() % 2) == 0); 
}

assert IntStream.range(0,100).allMatch(i -> simulateGame(3,4,5));
assert IntStream.range(0,100).allMatch(i -> simulateGame(3,4,6,8));

// meinCode

Clerk.markdown(
    Text.fillOut(
        """
                # Hier die Erklärung des Codes
## Klasse Move
Diese Klasse wurde erstellt, um es den Spielern zu erlauben, Züge zu machen. Dabei werden die Felder "rows und Number " benutzt. Diese representieren die Anzahl der Reihen und die Anzahl der zu entfernenden Objekte. Die Varisblen wurden final deklariert, weil sie nicht mehr zu modifizieren sind.
Die statische Methode "static Move of (int row, int number)" erstellt die verschiedenen Moves-Instanzen. Mehr dazu später in der Dokumentation.
Der Konstruktor "private Move(int row, int number) initialisiert ein neues Move-Objekt und überprüft, dass die Reihe, also rows positiv ist und minimal ein Objekt entfernt wird. Da dieser konstruktor privat ist, wurde dann die of()-Methode erstellt, die diese Objekte vom Typ "Move" erstellt. Deshalb wurde auch diese Methode statisch deklariert.
Ganz am Ende dieser Klasse wird die toString()-Methode erstellt und sie sorgt für die Darstellung der erstellten Objekten. Alsp liefert Züge in der Form (row, number) zurück.


## Interface NimGame
Diese Schnittestelle, also Interface wurde verwendet, damit der Programierer die Hauptmethoden des Spiels ausführlicher sortiert. Erst danach kann er dieser in einer anderen Klasse, die dieses Interface implemetiert, erstellen.
Das Interface NimGame enthält folgende Methoden:
1. Methode isWinning(int ... numbers): Die berechnet, ob der aktuelle Spielstatus eine Gewinnsituation ist. Drin wird die Bitweise_XOR (^)b verwendet.
Wenn das Resultat des XORs auf alle Reihen ungleich null ist, dann ist es eine Gewinngelegenheit.
Des Weiteren haben wir auch Abstrakte-Methden, die mit leeren Körper sind.
1. NimGame play(Move...moves), die die angegebenen Züge ausführt und das neue Spielfeld mit Aktualisierung ausgibt.
2. isGameOver() prüft,ob das Spiel vorbei ist.
3. Anschließend gibt die toString()-Methode den Spielstatus als Text zurück.

Nach der Erstellung dieser Schnittstelle(NimGame) stellt man sich die Frage, was die Inhalte der erähnten Methoden sind. Diese Informationen liefert uns die Klasse Nim, die das Nimgame-Interface implementiert,dh. class Nim implements NimGame.

Erstens wird ein Array erstellt, das die Anzahl der Objekte in jeder Reihe darstellt. Zweitens wird eine statische Methode Namens of(int...rows) mit Nim als Rückgabetyp, die dazu dient, neue Nim-Objekte zu erzeugen.
Wie oben ist diese Methode sttisch und hat den Rückgabetyp Nim, denn der Konstruktor ist privat und daher nicht direkt zugänglich.
Dieser Konstruktor initialisiert das rows-Array und überprüft, dass mindestens eine Reihe vorhanden ist und alle Reihen eine nicht-negative Anzahl Objekte haben.

## Jetzt kommen die abstrakten Methoden, die die Spiel-Logik hindeuten.
### private Nim play(Move m){...}
1. Reduziert die Anzahl der Objekte in der gegebenen Reihen, um "m.number".
2. Gibt ein neues Nim-Objekt zurück, das den neuen Spielstatus enthält.
### play (Move ... moves){...}
Diese Methode iteriert über ein Array von Züge und führt jeden Zug aus

### randonMove()
Wählt eine nicht leere Reihe und dann eine zufällige Anzahl Objekte aus dieser Reihe

### isGameOver()
Überprüft vor einem Zug, ob das Spiel nicht beendet ist. In anderen Worten, ob alle Reihen leer sind.
### toString()
Diese Methode zeigt den aktuellen/aktualisierten Spielstatus an, wobei jedes Objekt als "I" dargestellt wird.

Jetzt kommen wir zu den von mir implementierten Methoden, nämlich die hashCode()- und equals()-Methoden

## 1. hashCode()-Methode
Diese Methode berechnet die Hashwerte der Nim-Objekte anahand der Variablen: sum, prime und mul. sum sowie mul sind als Zwischenspeicher für die Berechnung benutzt. Dagegen ist prime ein konstanter Primfaktor(hier 97), um Kollisionen zu vermeiden.
Die Reihenlängen werden mittels einer If-Anweisung überprüft. Die sollen nicht größer als 5 sein und die Werte der Reihe dürfen auch nicht mehr als 7 sein. Sonst wird eine IlegalArgumentException geworfen.
Bei der Berechnung  des Hashwerts wird zuerst geprüft, ob rows null ist. Wenn ja, dann 0 zurückgeben. Falls value in rows == 0, wird dieser Wert übersprungen. Während mul durch (value + prime) multipliziert wird, wird sum um value erhöht. Das zu uns gelieferten Hashwert ist das Produkt aus sum und prime, addiert mit mul. Diese kombination verringert kollisionen und sorgt dafür, dass ähnliche ros-Werte unterschiedliche Hashwerte haben.  

## 2. equals()-Methode
Diese Methode überprüft, ob zwei Nim-Objekte gleich sind und es funktioniert wie folgt aus:
-Wenn other == this, wird true zurückgegeben, da beide Referenzen dasselbe Objekt sind.
-this.getClass != other.getClass stellt sicher, dass other vom Typ Nim ist.
-Falls other == null, gibt die Methode false zurück.
-Beide Objekte werden mit ihren hashCode()-Werten verglichen, um festzustellen, ob sie denselben Inhalt haben. Das alles bedeutet, dass equals() hier als ein Vergleich über den Hashwert realisiert wird, statt jedes Array-Element einzeln zu prüfen.


## NimView Klasse
Diese Klasse zeigt eine visuelle Darstellung eines Nim-Obkejts kraft der Turtle-Grafik. Mit Turtle turtle wird ein Turtle-Objekt Namens turtle erstellt. Diese wird die Nim-Objekte zeichnen. Danach wird eine Nim-Objekt Namens tim erstellt, das die Spiellogik enthält und x und y sind die Koordinaten, die die Position der Turtle auf der Umgebung, worauf es gezeichnet wird, festlegt.
Der Konstruktor nimmt turtle und til als Attribute, so dass die Turtle mit dem Nim-Objekt verbunden ist.
### Die toString()-Methode
Sie liefert eine Zeichenketterepresentation des aktuellen Nim-Spielzustands zurück.
Erstens wird die show()-Methode aufgerufen, um den visuellen Zustand anzuzeigen.
Das Zeichen "I" wird dann verwendet, um den aktuellen Zustand in String Form zu konvertieren.

### show()-Methode
Diese Methode zeichnet die Reihen des Nim-Objekts grafisch auf der Turtle-Oberfläche
Für jede Reihe in tim.rows wird die Position von x aud 100 zurückgesetzt. Jede Linie wird durch eine senkrechte Linie gezeichnet, die 50 Einheiten lang ist und mit einer Breite von 10 Einheiten.
Nach jedre Reihe wird die y-Koordinate um 60 erhöht, damit die nächste Reihe darunter angezeigt wird, ohne dass die Objekte auf beide Reihen sich anfassen.
Am Ende jeder Reihe wird die Turtle wieder an den Startpunkt der nächsten Reihe bewegt.


                """
    )
);