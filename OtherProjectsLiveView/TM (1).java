import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

Clerk.markdown(
    """
    # Die Turing-Maschine
                
    Zur Verdeutlichung der Abfau der Turing-Maschine möchte ich zwei Beispiele nehmen.

    ## Beispiel 1: ***Hier möchte ich eine Binärzahl dekrementieren***

    1. Anbei die Tabelle mit Transitionen

    fromState | read | write | move | toState
    ----------|------|-------|------|---------
    S |# |# |L |S
    S |1 |0 |R |R
    S |0 |1 |L |L
    R |0 |0 |R |R
    R |1 |1 |R |R
    R |# |# |L |W
    W |1 |1 |R |HALT
    W |0 |0 |R |HALT
    W |# |# |R |HALT
    L |0 |1 |L |L
    L |1 |0 |R |R
    L |# |# |R |R

    2. Bemerkung:

    - Das Band ist wie folgt initialisiert: # 1 1 0 0 0 {#}
    - Vorbelegungszeichen: #
    - Start-Zustand: S

    3. Durchlauf:

    > 0: # 1 1 0 0 0{#} -- S <br>
    1: # 1 1 0 0{0}#  -- S <br>
    2: # 1 1 0{0}1 #  -- L <br>
    3: # 1 1{0}1 1 #  -- L <br>
    4: # 1{1}1 1 1 #  -- L <br>
    5: # 1 0{1}1 1 #  -- R <br>
    6: # 1 0 1{1}1 #  -- R <br>
    7: # 1 0 1 1{1}#  -- R <br>
    8: # 1 0 1 1 1{#} -- R <br>
    9: # 1 0 1 1{1}#  -- W <br>
    10: # 1 0 1 1 1{#} -- HALT <br>
    
    4. Erläuterung mit Schritten:

    ### Erstens: 
    - die Maschine ist im Zustand S;
    - der S/L Kopf ist an der letzen(6-ten) Position;
    - er liest das Zeichen `#`,(das heißt trigger ist jetzt (S,#) und führt der Action(#, L, S)) entsprechend;
    - Nun schreibt er # und geht links wie die Maschine definiert hat;
    - die Machine bleibt beim Zustand S wie erwatet.

    ### Zweitens:
    - die Maschine ist jetzt im Zustand S;
    - der S/L Kopf ist an der vorletzen(5-ten) Position;
    - er liest das Zeichen `0`,(das heißt trigger ist (S,0) und führt der Action(1, L, L)) entsprechend;
    - Weiter schreibt er 1 und geht links wie bei der Machine beschrieben;
    - die Machine geht zum Zustand L wie erwatet.

    ### Drittens:
    - die Maschine ist im Zustand L;
    - der S/L Kopf ist an der 4-ten Position;
    - er liest das Zeichen `0`,(das heißt trigger ist (S,0) und führt normalerweise zur Action(1, L, L));
    - er schreibt doch 1 und geht links wie erwartet;
    - die Machine bleibt im Zustand L wie erwatet.


    ## Beispiel 2: ***Einsen nach rechts Schieben***

    1. Tabelle mit Transitionen

    fromState | read | write | move | toState
    ----------|------|-------|------|---------
    S |1 |1 |L |S
    S |S |S |R |HALT
    S |0 |0 |L |0
    0 |0 |0 |L |0
    0 |1 |0 |R |1
    0 |S |S |R |HALT
    1 |0 |0 |R |1
    1 |1 |1 |L |D
    1 |S |S |L |D
    D |0 |1 |L |S

     2. Bemerkung:

    - Das Band ist wie folgt initialisiert:  S 0 1 0 1 0 {1}
    - Vorbelegungszeichen: 0
    - Start-Zustand: S

    3. Durchlauf:

    > 0: S 0 1 0 1 0{1} -- S <br>
    1: S 0 1 0 1{0}1  -- S <br>
    2: S 0 1 0{1}0 1  -- 0 <br>
    3: S 0 1 0 0{0}1  -- 1 <br>
    4: S 0 1 0 0 0{1} -- 1 <br>
    5: S 0 1 0 0{0}1  -- D <br>
    6: S 0 1 0{0}1 1  -- S <br>
    7: S 0 1{0}0 1 1  -- 0 <br>
    8: S 0{1}0 0 1 1  -- 0 <br>
    9: S 0 0{0}0 1 1  -- 1 <br>
    10: S 0 0 0{0}1 1 -- 1 <br>
    11: S 0 0 0 0{1}1  -- 1 <br>
    12: S 0 0 0{0}1 1  -- D <br>
    13: S 0 0{0}1 1 1  -- S <br>
    14: S 0{0}0 1 1 1  -- 0 <br>
    15: S{0}0 0 1 1 1  -- 0 <br>
    16: {S}0 0 0 1 1 1  -- 0 <br>
    17: S{0}0 0 1 1 1  -- HALT <br>

    4. Erläuterung:

    Zunächst: 
    - die Maschine ist im Zustand S;
    - der S/L Kopf ist an der letzen(diesmal der 6-ten Position);
    - er liest das Zeichen `1`,(das heißt trigger ist (S,1) und führt normalerweise zur Action(1, L, S));
    - er schreibt doch 1 und geht links wie erwartet;
    - die Machine bleibt im Zustand S wie erwatet.

    Danach:
    - die Maschine ist im Zustand S;
    - der S/L Kopf ist an der vorletzen(diesmal der 5-ten Position);
    - er liest das Zeichen `0`,(das heißt trigger ist (S,0) und führt normalerweise zur Action(0, L, 0));
    - er schreibt doch 0 und geht links wie erwartet;
    - die Machine geht zum Zustand 0 wie erwatet.

    Anschließend:
    - die Maschine ist im Zustand L;
    - der S/L Kopf ist an der 4-ten Position;
    - er liest das Zeichen `1`,(das heißt trigger ist (0,1) und führt normalerweise zur Action(0, R, 1));
    - er schreibt doch 1 und geht rechts wie erwartet;
    - die Machine geht zum Zustand 1 wie erwatet.


    # HIER IST EINE HILFSMITTEL ZUR AUSFÜHRUNG DES CODES, ES LANGE SCHRITTE GIBT

    table.construct("S",'#','#',Moves.LEFT,"S")

    table.construct("S",'1','0',Moves.RIGHT,"R")

    table.construct("S",'0','1',Moves.LEFT,"L")

    table.construct("R",'0','0',Moves.RIGHT,"R")
    
    table.construct("R",'1','1',Moves.RIGHT,"R")
    
    table.construct("R",'#','#',Moves.LEFT,"W")
    
    table.construct("W",'1','1',Moves.RIGHT,"HALT")
    
    table.construct("W",'0','0',Moves.RIGHT,"HALT")
    
    table.construct("W",'#','#',Moves.RIGHT,"HALT")
    
    table.construct("L",'0','1',Moves.LEFT,"L")
    
    table.construct("L",'1','0',Moves.RIGHT,"R")
    
    table.construct("L",'#','#',Moves.RIGHT,"R")
    ## Nach Erstellung des Bandobjekts...

    band.add('#')

    band.add('1')
    
    band.add('0')

    ## Live-View-Prozess von der Turing-Maschine
    
    """);

public class TM {
    Tape tape;
    String endState = "HALT";
    boolean finished = false;
    TM(Tape tape) {
        this.tape = tape;
    }
    
    Turtle turtle = new Turtle(1000,500);
    TM step() {
        if(tape.currentState != endState) {
            tape.read();
            tape.write();
            tape.move();
        } else {
            System.out.println("Schon fertig!");
            finished = true;
        }
        return this;
    }

    TM run() {
        while(finished == false) {
            this.step();
        }
        return this;
    }

@Override
    public String toString () {
        int x = 250;
        int y = 250;
        turtle.reset();
        turtle.moveTo(x, y);
        String s = "";

        if(this.tape.pos < 5 ) {
            for(int j = 0; j < (5 - this.tape.pos); j++){
                turtle.moveTo(x, y).left(90).penUp().forward(20).penDown().left(90).forward(15).left(90).forward(40).left(90).forward(30).left(90).forward(40).left(90).forward(15).left(180);
                x = x + 40;
            } 
            for(int j = 0; j <= this.tape.pos; j++) {
                if(j == this.tape.pos) {
                    s = s + this.tape.band.get(tape.pos);
                    turtle.moveTo(x, y).lineWidth(7).left(90).text(s).penUp().forward(20).penDown().left(90).forward(15).left(90).forward(40).left(90).forward(30).left(90).forward(40).left(90).forward(15).left(180).lineWidth(1);
                    s = "";
                    x = x + 40;
                } else {
                    s = this.tape.band.get(j) + s;
                    turtle.moveTo(x, y).left(90).text(s).penUp().forward(20).penDown().left(90).forward(15).left(90).forward(40).left(90).forward(30).left(90).forward(40).left(90).forward(15).left(180);
                    s = "";
                    x = x + 40;
                }
            }
        } else 
        if(this.tape.pos >= 5){
            for(int j = this.tape.pos - 5; j <= this.tape.pos; j++) {
                if(j == this.tape.pos) {
                    s = s + this.tape.band.get(tape.pos);
                    turtle.moveTo(x, y).lineWidth(7).left(90).text(s).penUp().forward(20).penDown().left(90).forward(15).left(90).forward(40).left(90).forward(30).left(90).forward(40).left(90).forward(15).left(180).lineWidth(1);
                    s = "";
                    x = x + 40;
                } else {
                    s = this.tape.band.get(j) + s;
                    turtle.moveTo(x, y).left(90).text(s).penUp().forward(20).penDown().left(90).forward(15).left(90).forward(40).left(90).forward(30).left(90).forward(40).left(90).forward(15).left(180);
                    s = "";
                    x = x + 40;
                }
            }
        }

        if((this.tape.band.size() - this.tape.pos) < 6) {  
            for(int j = (this.tape.pos + 1); j < this.tape.band.size(); j++) {
                s = this.tape.band.get(j) + s;
                turtle.moveTo(x, y).left(90).text(s).penUp().forward(20).penDown().left(90).forward(15).left(90).forward(40).left(90).forward(30).left(90).forward(40).left(90).forward(15).left(180);
                s = "";
                x = x + 40;
            }
            for(int j = this.tape.band.size(); j < (this.tape.band.size() + (5 - (this.tape.band.size() - this.tape.pos) + 1)); j++) {
                turtle.moveTo(x, y).left(90).penUp().forward(20).penDown().left(90).forward(15).left(90).forward(40).left(90).forward(30).left(90).forward(40).left(90).forward(15).left(180);
                x = x + 40;
            }
        } else 
        if((this.tape.band.size() - this.tape.pos) >= 6 ) {
            for(int j = (this.tape.pos + 1); j < this.tape.pos + 5; j++) {
                s = this.tape.band.get(j) + s;
                turtle.moveTo(x, y).left(90).text(s).penUp().forward(20).penDown().left(90).forward(15).left(90).forward(40).left(90).forward(30).left(90).forward(40).left(90).forward(15).left(180);
                s = "";
                x = x + 40;
            }
        }
        return s;
    }
     
}

class Tape {
    Table table;
    List<Character> band = new ArrayList<>();
    int pos;//pos
    String currentState, nextState; //currentSate und nextState
    char c;
    Tape(List<Character> band, Table table, String currentState) {
        if(band.size() < 1) {
            throw new IllegalArgumentException("band muss Symbole enthalten");
        }
        this.band = band; //tape
        this.table = table;
        this.currentState = currentState;
        this.pos = band.size() - 1;
        
    }

    Character read() {
        c = band.get(pos);
        return c;
    }

    Tape write() {
        this.table.transitions.forEach((Trigger trigger, Action a) -> {
            if(trigger.fromState == currentState && this.c == trigger.read) {
                this.band.add(pos, a.write);
                this.band.remove(pos + 1);
            }
        });
        return this;
    }

    Tape move() {
        this.table.transitions.forEach((Trigger trigger, Action a) -> {
            if(trigger.fromState == currentState && this.c == trigger.read) {

                System.out.println(pos);
                if(table.transitions.get(trigger).move == Moves.LEFT) {
                    this.pos = pos - 1;
                } else {
                    this.pos = pos + 1;
                }
                System.out.println(this.pos);
                nextState = a.toState;
            } 
        });
        currentState = nextState;
        return this;
    }
}

class Table {
    Map<Trigger, Action> transitions = new HashMap<Trigger,Action>();

    Map<Trigger,Action> construct(String fromState, char read, char write, Moves move, String toState) {  //aufbau
                Trigger trigger = new Trigger(fromState, read);
                Action action = new Action(write, move, toState);
                transitions.put(trigger, action); 
                return transitions; 
        }

    Map<Trigger,Action> constructions(List<Trigger> triggers, List<Action> actions) {
        //assert triggers.size() == actions.size();
        if(triggers.size() != actions.size()){
            throw new IllegalArgumentException("Please verify your enteries!");
        }
        for(Trigger trigger: triggers) {
            for(Action action: actions) {
                transitions.put(trigger, action);  
            }
        }
        return transitions;
    }

    Action aufruf(Trigger trigger) {
        return transitions.get(trigger);
    }
}

class Trigger {
    String fromState;
    char read;

    Trigger(String fromState, char read) {
        this.fromState = fromState;
        this.read = read;
    }
}

class Action {
    char write;
    Moves move;
    String toState;

    Action(char write, Moves move, String toState) {
        this.write = write;
        this.move = move;
        this.toState = toState;
    }
}

enum Moves {
    LEFT,
    RIGHT
    }
        
