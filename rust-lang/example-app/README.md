# example-app

Questa applicazione è un esempio dimostrativo di utilizzo del linguaggio di programmazione _Rust_.

L'applicazione si occupa di fare il parsing di un file in formato _csv_ e di contare le istanze di diverso tipo presenti, stampando a video il risultato.

Si tratta di un programma molto semplice che permette di cominciare a prendere confidenza con _Rust_.

Al fine di agevolare la comprensione, lo stesso programma è stato scritto in diversi linguaggi (anche in _Go_ e _Java_).

## Build parte Rust

Per fare il build della parte _Rust_ basta avere già installato l'ambiente _Rust_ e _Cargo_.

Siccome su Windows l'installazione richiede anche la presenza del packetto di build utils di _.NET_, che è piuttosto pesante da installare, ho messo a disposizione un _Dockerfile_.

### Istruzioni per rust

    $ rust> cargo build
    $ rust> ./target/debug/netstats /percorso/del/file.csv

Utilizzando docker, prima occorre creare una cartella _data_ che contenga il file csv che si desidera utilizzare per il test.

    $ ls
        Mode                LastWriteTime         Length Name
    ----                -------------         ------ ----
    d-----       15/12/2018     11:06                go
    d-----       15/12/2018     11:08                java
    d-----       08/12/2018     09:07                rust
    -a----       11/12/2018     21:23            240 Dockerfile-4-rust
    -a----       15/12/2018     11:14            507 README.md

    $ mkdir data
    $ cp /mio/file.csv .\data

Quindi si può procedere alla build dell'immagine:

    $ docker build -f Dockerfile-4-rust -t netstats-rust .

> Attenzione a mettere il '.' finale!

Una volta terminato sarà possibile mandare in esecuzione il container:

    $ docker run --rm netstats-rust

