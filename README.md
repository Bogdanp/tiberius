## tiberius

tiberius is a _tiny_ stack-based functional programming language.

### Tiny

    tiberius(master)$ cloc src/main/scala/**/*
          27 text files.
          11 unique files.
           1 file ignored.
    
    http://cloc.sourceforge.net v 1.58  T=0.5 s (20.0 files/s, 812.0 lines/s)
    -------------------------------------------------------------------------------
    Language                     files          blank        comment           code
    -------------------------------------------------------------------------------
    Scala                           10             70              0            336
    -------------------------------------------------------------------------------
    SUM:                            10             70              0            336
    -------------------------------------------------------------------------------

### OHGODWHY?

B/c boredom and because I wanted to see exactly how much I could get
done in a few hours, turns out it's quite a lot.

### Show me!

    > "Hello, World!" showLn
    Hello, World!
    > 3 2 + 5 *
    25.0
    > 3
    3.0
    25.0
    > 2
    2.0
    3.0
    25.0
    > +
    5.0
    25.0
    > fn +2 { 2 + }
    > 25 +2
    27.0
    > {1 2 3} #{2 *} map
    { 2.0 4.0 6.0 }