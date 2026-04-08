# Nebenläufigkeit

## Grundlagen

### Einführung in Nebenläufigkeit
- Definition: Was ist Nebenläufigkeit? Unterschied zu Parallelität.

     **Nebenläufigkeit** bezieht sich auf die Fähigkeit eines Systems, mehrere Aufgaben ***gleichzeitig*** zu bearbeiten, während **Parallelität** die gleichzeitige Ausführung von Aufgaben auf mehreren Prozessoren oder Kernen bedeutet.

- Warum Nebenläufigkeit?
  - Reaktive Anwendungen - Anwendungen, die auf Ereignisse reagieren, wie z. B. Benutzerinteraktionen oder Netzwerkantworten.
  - Performance‑Optimierung - Durch die Aufteilung von Aufgaben in kleinere Einheiten, die gleichzeitig ausgeführt werden können, können Anwendungen schneller und effizienter arbeiten.
  - Nutzen moderner Mehrkernprozessoren - Moderne Prozessoren verfügen über mehrere Kerne, die gleichzeitig arbeiten können. Nebenläufigkeit ermöglicht es Anwendungen, diese Ressourcen effektiv zu nutzen.
  - Typische Probleme:
    - Race Conditions
   
      Zu diese situation kommt es, wenn mehrere Threads gleichzeitig auf gemeinsame Ressourcen zugreifen und dabei unvorhersehbare Ergebnisse erzeugen können.
         Beispiel: Zwei Threads erhöhen gleichzeitig einen gemeinsamen Zähler, was zu einem inkonsistenten Wert führen kann, wenn die Operation nicht atomar ist.
   
    - Deadlocks
   
       Zu Deadlocks kommt es, wenn zwei oder mehr Threads sich gegenseitig blockieren, indem sie jeweils auf Ressourcen warten, die von anderen Threads gehalten werden.
         Beispiel: Thread A hält Ressource 1 und wartet auf Ressource 2, während Thread B Ressource 2 hält und auf Ressource 1 wartet. Beide Threads können nicht fortfahren, was zu einem Deadlock führt.
   
    - Livelocks
   
       Ein Livelock tritt auf, wenn zwei oder mehr Threads ständig ihre Zustände ändern, um zu versuchen, ein Problem zu lösen, aber dabei nicht vorankommen. 
         Beispiel: Zwei Threads versuchen, eine Ressource zu erwerben, und wenn sie scheitern, geben sie die Ressource frei und versuchen es erneut. 
         Sie könnten sich gegenseitig blockieren, ohne jemals erfolgreich zu sein.
   
    - Sichtbarkeitsprobleme
   
         Sichtbarkeitsprobleme treten auf, wenn Änderungen an gemeinsamen Variablen von einem Thread nicht von anderen Threads gesehen werden, was zu inkonsistentem Verhalten führen kann.
             Beispiel: Ein Thread aktualisiert eine gemeinsame Variable, aber ein anderer Thread sieht diese Änderung nicht und arbeitet mit veralteten Daten.
   
    **wait()** - Ein Thread, der die Methode `wait()` auf einem Objekt aufruft, wird in den Zustand `WAITING` versetzt und wartet darauf, dass ein anderer Thread die Methode `notify()` oder `notifyAll()` auf demselben Objekt aufruft, um ihn zu wecken.
     
    **lock** - Ein Mechanismus, der verwendet wird, um den Zugriff auf gemeinsame Ressourcen zu steuern.
       
    **synchronized** - Ein Schlüsselwort in Java, das verwendet wird, um sicherzustellen, dass nur ein Thread gleichzeitig auf einen bestimmten Abschnitt von Code oder eine Ressource zugreifen kann.
        Es kann auf Methoden oder Blöcke angewendet werden, um kritische Abschnitte zu schützen, die von mehreren Threads gleichzeitig ausgeführt werden könnten.
       
    **voliatile** - Ein Schlüsselwort in Java, das verwendet wird, um sicherzustellen, dass Änderungen an einer Variablen von einem Thread für andere Threads sichtbar sind.
       
    **atomare Operationen** - Operationen, die unteilbar sind, d. h., sie werden entweder vollständig ausgeführt oder überhaupt nicht, ohne dass andere Threads dazwischen eingreifen können.
       
    **ThreadLocal** - Eine Klasse in Java, die es ermöglicht, dass jeder Thread seine eigene unabhängige Kopie einer Variablen hat.
       
    **notifyAll()** - Eine Methode, die verwendet wird, um alle Threads zu wecken, die auf einem bestimmten Objekt warten.
       
    **notify()** - Eine Methode, die verwendet wird, um einen einzelnen Thread zu wecken, der auf einem bestimmten Objekt wartet.
       
    **Thread.sleep()** - Eine Methode, die den aktuellen Thread für eine bestimmte Zeitspanne in den Zustand `TIMED_WAITING` versetzt, bevor er wieder ausführbar wird.
       
    **join()** - Eine Methode, die verwendet wird, um den aktuellen Thread zu blockieren, bis ein anderer Thread seine Ausführung abgeschlossen hat.
       
    **Interrupt** - Ein Mechanismus, mit dem ein Thread signalisiert werden kann, dass er unterbrochen werden soll, z. B. um eine laufende Operation zu stoppen oder um auf ein Ereignis zu reagieren.
   

### The Basics of Java Concurrency

#### Life Cycle of a Thread
- In der Programmiersprache Java wird Multithreading durch das Kernkonzept eines Threads gesteuert.
  Threads durchlaufen während ihres Lebenszyklus verschiedene Zustände.

  ![Nebenläufigkeit](diagram.png)

- Die Klasse `java.lang.Thread` enthält ein statisches Enum `State`, das ihre möglichen Zustände definiert.
Zu jedem Zeitpunkt kann sich der Thread nur in einem dieser Zustände befinden:
  
  **NEW** – ein neu erstellter Thread, dessen Ausführung noch nicht begonnen hat, bleibt in diesem Zustand, bis wir es mit der Methode `start()` starten.;

```java
Runnable runnable = new NewState();
Thread t = new Thread(runnable);
System.out.println(t.getState()); 
```
Da wir den erwähnten Thread noch nicht gestartet haben, gibt die Methode `t.getState()` Folgendes aus:
`NEU`

  **RUNNABLE** – Sobald wir einen neuen Thread erstellt und die Methode `start()` aufgerufen haben, wechselt er vom Zustand `NEW` in den Zustand `RUNNABLE`. 
  Threads in diesem Zustand laufen entweder gerade oder sind bereit, ausgeführt zu werden, warten aber auf die Zuweisung von Ressourcen durch das System. 
  In einer Multithread-Umgebung weist der Thread-Scheduler (Teil der JVM) jedem Thread eine feste Laufzeit zu. 
  Er läuft also für eine bestimmte Zeit und gibt dann die Kontrolle an andere Threads im Zustand `RUNNABLE` ab. 
  Fügen wir beispielsweise die Methode `t.start()` zu unserem vorherigen Code hinzu und versuchen wir, auf den aktuellen Zustand zuzugreifen:

```java
Runnable runnable = new NewState();
Thread t = new Thread(runnable);
t.start();
System.out.println(t.getState());
```
Dieser Code gibt höchstwahrscheinlich die Ausgabe `RUNNABLE` zurück.
Es kann sein, dass es vom Thread-Scheduler sofort eingeplant wurde und die Ausführung bereits abgeschlossen ist. 
In solchen Fällen erhalten wir möglicherweise eine andere Ausgabe.

  **BLOCKED** – wartet auf den Erhalt einer Monitorsperre, um einen synchronisierten Block/eine Methode zu betreten oder erneut zu betreten;
  Die Methode `commonResource()` ist **synchronisiert**, d. h., nur ein Thread kann darauf zugreifen. 
  Alle nachfolgenden Threads, die versuchen, auf diese Methode zuzugreifen, werden blockiert, bis der aktuelle Thread die Verarbeitung abgeschlossen hat.
```java
public class BlockedState {
   public static void main(String[] args) throws
           InterruptedException {
      Thread t1 = new Thread(new DemoBlockedRunnable());
      Thread t2 = new Thread(new DemoBlockedRunnable());
      t1.start();
      t2.start();
      Thread.sleep(1000);
      System.out.println(t2.getState());
      System.exit(0);
   }
}

public static synchronized void commonResource() {
while(true) {
// Infinite loop to mimic heavy processing
// ‘t1’ won’t leave this method
// when ‘t2’ tries to enter this
}
}
```
Wenn t1 diese Methode aufruft, befindet es sich in einer Endlosschleife; dies dient lediglich dazu,
eine rechenintensive Verarbeitung zu simulieren, damit keine anderen Threads diese Methode aufrufen können.
Wenn wir nun t2 starten, versucht es, die Methode `commonResource()` aufzurufen,
die bereits von t1 verwendet wird; daher verbleibt t2 im Zustand `BLOCKED`.
Wenn wir in diesem Zustand `t2.getState()` aufrufen, erhalten wir die Ausgabe:
`BLOCKED`

  **WAITING** – wartet darauf, dass ein anderer Thread eine bestimmte Aktion ausführt (ohne Zeitlimit), z. B. durch Aufrufen von `wait()`, `join()` oder `park()`. Beachten Sie, dass wir in wait() und join() keine Timeout-Periode definieren.;
  **TIMED_WAITING** – wartet darauf, dass ein anderer Thread eine bestimmte Aktion für einen festgelegten Zeitraum ausführt, z. B. durch Aufrufen von `sleep()`, `wait()` mit einem Timeout oder `join()` mit einem Timeout.;
  **TERMINATED** – hat seine Ausführung beendet oder wurde abnormal beendet.
#### How to Start a Thread in Java
- Es gibt zwei Möglichkeiten, einen Thread in Java zu starten:
  1. Durch Erstellen einer Klasse, die das `Runnable`-Interface implementiert und die `run()`-Methode überschreibt.
  2. Durch Erstellen einer Klasse, die die `Thread`-Klasse erweitert und die `run()`-Methode überschreibt, Dies ist weit entfernt von produktionsreifem Code, 
  wo es entscheidend ist, Ressourcen korrekt zu verwalten, um häufige Kontextwechsel oder übermäßigen Speicherverbrauch zu vermeiden. 
  Um produktionsreif zu werden, müssen wir nun zusätzlichen Code schreiben, um Folgendes zu berücksichtigen:
     • die konsistente Erstellung neuer Threads
     • die Anzahl gleichzeitig aktiver Threads
     • die Freigabe von Threads – besonders wichtig für Daemon-Threads, um Speicherlecks zu vermeiden. 
  Wir könnten unseren Code für all diese Fälle schreiben, wenn wir wollten.
  3. Durch die Verwendung von **Lambda-Ausdrücken** (ab Java 8) für eine einfachere und prägnantere Syntax.
  4. Durch die Verwendung von **ExecutorService**, das eine höhere Abstraktionsebene für die Verwaltung von Threads bietet, 
     Der ExecutorService implementiert das Thread-Pool-Entwurfsmuster (auch Replizierter-Worker- oder Worker-Crew-Modell genannt) und übernimmt die
     oben erwähnte Thread-Verwaltung. Zusätzlich bietet er einige sehr nützliche Funktionen wie Thread-Wiederverwendbarkeit und Aufgabenwarteschlangen.
     Insbesondere die Thread-Wiederverwendbarkeit ist sehr wichtig. In einer großen Anwendung verursacht die Zuweisung und Freigabe vieler Thread-Objekte einen
     erheblichen Speicherverwaltungsaufwand.
     Mit Worker-Threads minimieren wir den durch die Thread-Erstellung verursachten Aufwand.
     Um die Pool-Konfiguration zu vereinfachen, verfügt der ExecutorService über einen einfachen Konstruktor und einige Anpassungsoptionen, wie z. B. den Warteschlangentyp, die
     minimale und maximale Anzahl von Threads und deren Namenskonvention.
  
- Beispiel für die erste Methode:
***Runnable-Interface***
```java
class MyRunnable implements Runnable {
   @Override
   public void run() {
      System.out.println("Runnable läuft in: " + Thread.currentThread().getName());
   }
}

public class RunnableExample {
   public static void main(String[] args) {
      Thread t = new Thread(new MyRunnable());
      t.start();
   }
}
```
***Extend Thread***
```java
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread läuft: " + getName());
    }
}

public class Main {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
    }
}
```
***Lambda-Ausdrücke***
```java
public class Main {
   public static void main(String[] args) {
      Thread t = new Thread(() -> {
         System.out.println("Lambda Thread: " + Thread.currentThread().getName());
      });
      t.start();
   }
}
```
***ExecutorService***
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            System.out.println("Executor Thread: " + Thread.currentThread().getName());
        });

        executor.shutdown();
    }
}
```
- Ausführung verzögerter oder periodischer Aufgaben
  Bei der Arbeit mit komplexen Webanwendungen müssen wir möglicherweise Aufgaben zu bestimmten Zeiten oder sogar regelmäßig ausführen.
  Java bietet einige Werkzeuge, die uns bei der Ausführung verzögerter oder wiederkehrender Operationen helfen:

• java.util.Timer
• java.util.concurrent.ScheduledThreadPoolExecutor

  - **Timer**: Ein Timer ermöglicht es uns, eine Aufgabe zu einem bestimmten Zeitpunkt oder nach einer bestimmten Verzögerung auszuführen oder wiederholte Ausführung in
    regelmäßigen Abständen.
    Er verwendet einen Hintergrund-Thread, um die geplanten Aufgaben auszuführen. 
    Wir können eine Aufgabe planen, indem wir die Methode `schedule()` aufrufen und die gewünschte Verzögerung oder den gewünschten Zeitpunkt angeben.
```java - DELAY
TimerTask task = new TimerTask() {
public void run() {
    System.out.println(“Task performed on: “ + new Date() +“n”+ “Thread’s name: “ + Thread.currentThread().getName());
    }
};
Timer timer = new Timer(“Timer”);
long delay = 1000L;
timer.schedule(task, delay);
```
```java - REPEAT
timer.scheduleAtFixedRate(repeatedTask, delay, period);
```
  - **ScheduledThreadPoolExecutor**: Eine Alternative zum Timer ist der ScheduledThreadPoolExecutor, der eine flexiblere und robustere Möglichkeit bietet, geplante Aufgaben auszuführen. 
    Er ist Teil des java.util.concurrent-Pakets und bietet Funktionen wie die Möglichkeit, mehrere Threads zu verwenden, um geplante Aufgaben auszuführen, und die Fähigkeit, geplante Aufgaben zu stornieren.
    Wenn der Prozessor die Bearbeitung der Aufgabe nicht rechtzeitig vor dem nächsten Auftreten abschließen kann, wartet der ScheduledExecutorService, bis die aktuelle Aufgabe abgeschlossen ist, bevor er die nächste startet.
    Um diese Wartezeit zu vermeiden, können wir `scheduleWithFixedDelay()` verwenden, das, wie der Name schon sagt, eine Verzögerung fester Länge zwischen den Iterationen der Aufgabe garantiert.

**Timer:**
• Bietet keine Echtzeitgarantie; plant Aufgaben mithilfe der Methode `Object.(long)`.
• Es gibt einen einzigen Hintergrundthread, daher werden Aufgaben sequenziell ausgeführt, und eine langlaufende Aufgabe kann andere verzögern.
• Laufzeitausnahmen, die in einem TimerTask ausgelöst werden, beenden den einzigen verfügbaren Thread und damit den Timer.
**ScheduledThreadPoolExecutor:**
• Kann mit einer beliebigen Anzahl von Threads konfiguriert werden.
• Kann alle verfügbaren CPU-Kerne nutzen.
• Fängt Laufzeitausnahmen ab und ermöglicht deren Behandlung (durch Überschreiben der Methode `afterExecute` von `ThreadPoolExecutor`).
• Bricht die Aufgabe ab, die die Ausnahme ausgelöst hat, während andere Aufgaben weiterlaufen.
• Nutzt das Planungssystem des Betriebssystems, um Zeitzonen, Verzögerungen, Sonnenzeit usw. zu berücksichtigen.
• Bietet eine kollaborative API für die Koordination mehrerer Aufgaben, z. B. zum Warten auf den Abschluss aller übermittelten Aufgaben.
• Bietet eine verbesserte API für die Verwaltung des Thread-Lebenszyklus.

#### wait() und notify()

In einer Multithread-Umgebung können mehrere Threads versuchen, dieselbe Ressource zu verändern. Eine fehlerhafte Thread-Verwaltung führt zu Inkonsistenzen.

Ein Werkzeug zur Koordination der Aktionen mehrerer Threads in Java sind geschützte Blöcke. Diese Blöcke prüfen eine bestimmte Bedingung, bevor die Ausführung fortgesetzt wird.
Daher verwenden wir Folgendes:
• `Object.()` zum Anhalten eines Threads
• `Object.notify()` zum Aufwecken eines Threads
Das folgende Diagramm veranschaulicht den Lebenszyklus eines Threads:
Bitte beachten Sie, dass es viele Möglichkeiten gibt, diesen Lebenszyklus zu steuern. In diesem Artikel konzentrieren wir uns jedoch nur auf `wait()` und `notify()`.

***wait()***: Ein Thread, der die Methode `wait()` auf einem Objekt aufruft, wird in den Zustand `WAITING` versetzt und wartet darauf, dass ein anderer Thread die Methode `notify()` oder `notifyAll()` auf demselben Objekt aufruft, um ihn zu wecken.
Dafür muss der aktuelle Thread den Monitor des Objekts besitzen. Laut Javadocs kann dies auf folgende Weise geschehen:
• wenn die synchronisierte Instanzmethode für das jeweilige Objekt ausgeführt wurde
• wenn der Rumpf eines synchronisierten Blocks für das jeweilige Objekt ausgeführt wurde
• durch die Ausführung synchronisierter statischer Methoden für Objekte vom Typ `Class`.
Beachten Sie, dass immer nur ein aktiver Thread den Monitor eines Objekts besitzen kann.
Die `wait()`-Methode verfügt über drei überladene Signaturen. Schauen wir uns diese genauer an.

*wait(long timeout)* – wartet bis zum Ablauf der angegebenen Zeit oder bis ein anderer Thread die Methode `notify()` oder `notifyAll()` auf demselben Objekt aufruft, um ihn zu wecken.

*wait(long timeout, int nanos)* – wartet bis zum Ablauf der angegebenen Zeit oder bis ein anderer Thread die Methode `notify()` oder `notifyAll()` auf demselben Objekt aufruft, um ihn zu wecken. Die Zeitangabe erfolgt in Millisekunden und Nanosekunden.
```java
public static sinchtonized ping(){
while(!condition) {
    obj.wait();
   notifyAll();
}
}
```
In diesem Beispiel wird der Thread in einer Schleife angehalten, bis die Bedingung erfüllt ist. Sobald die Bedingung erfüllt ist, wird der Thread durch einen Aufruf von `notify()` oder `notifyAll()` geweckt.   
notify() – weckt einen einzelnen Thread, der auf einem bestimmten Objekt wartet. Wenn mehrere Threads auf demselben Objekt warten, wird einer von ihnen zufällig ausgewählt und geweckt.
notifyAll() – weckt alle Threads, die auf einem bestimmten Objekt warten. Alle geweckten Threads konkurrieren dann um die CPU, und einer von ihnen wird fortfahren, während die anderen wieder in den Zustand `WAITING` zurückkehren, wenn sie die CPU nicht erhalten.  

### Grundlagen

### Synchronisation

## Fortgeschritten Anwendungen