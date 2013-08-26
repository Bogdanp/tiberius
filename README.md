## tiberius

tiberius is a _tiny_ stack-based functional programming language.

### Tiny

    tiberius(master)$ cloc src/main/scala/**/*
          18 text files.
           8 unique files.
           0 files ignored.
    
    http://cloc.sourceforge.net v 1.58  T=0.5 s (16.0 files/s, 630.0 lines/s)
    -------------------------------------------------------------------------------
    Language                     files          blank        comment           code
    -------------------------------------------------------------------------------
    Scala                            8             56              0            259
    -------------------------------------------------------------------------------
    SUM:                             8             56              0            259
    -------------------------------------------------------------------------------

### OHGODWHY?

B/c boredom and because I wanted to see exactly how much I could get
done in a few hours, turns out it's quite a lot.

### Show me!

    > "Hello, World!" putStrLn
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
    > fn +2 { -> x x 2 + }
    > 25 +2
    27.0