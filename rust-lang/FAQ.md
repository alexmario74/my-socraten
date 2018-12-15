
# Domande

 1. in quali scenari lo useresti oggi?
 2. quanto tempo pensi serva per entrare ad un programmatore ad esperienza media?
 3. facilità di debugging del codice
 4. usabilità attuale dei tool base (cargo + crates)
 5. stato delle librerie per fare testing
 6. qual è lo scenario di utilizzo in cui consiglieresti vivamente l'utilizzo di Rust?
 7. e al contrario, c'è uno scenario in cui ritieni che Rust sia meglio lasciarlo da parte?

# Risposte

## 1. in quali scenari lo useresti oggi?
## 6. qual è lo scenario di utilizzo in cui consiglieresti vivamente l'utilizzo di Rust?

_Rust_ nasce in _Mozilla Foundation_ come linguaggio per sostituire il _C++_, e per poterlo fare doveva essere superiore in quelle che venivano più comunemente considerate essere le debolezze del _C++_.

In particolare la totale mancanza di controllo sulla memoria ereditato dal _C_.

Così _Rust_ offre un compilatore che fa una profonda analisi del codice e che ne identifica i punti critici e li segnala.

Anche il linguaggio è stato costruito in modo tale da indirizzare il programmatore a scrivere codice più sicuro. Tuttavia è sempre possibile by-passare le sicurezze utilizzando alcune strutture specifiche come gli _Smart pointers_ o i blocchi _unsafe_ che però sono stati introdotti soprattutto per integrare il linguaggio con altri che consentono costrutti meno vincolanti.

_Rust_ assieme a _Go_ sono linguaggi che reintroducono i puntatori, lasciandosi, però, alle spalle tutta la loro aritmetica, considerata da molti sia il punto di forza, sia la debolezza del _C_ e del _C++_.
Dato che attraverso l'aritmentica dei puntatori è possibile manipolare direttamente la memoria creando programmi molto efficienti, e al contempo anche molto dificili da scrivere in modo che siano sicuri.

L'efficienza di _Rust_ è data dall'essere un linguaggio compilato con un runtime piccolissimo, molto inferiore al _C++_, grazie anche al fatto che non prevede le classi.

Io lo consiglio per scrivere programmi batch che richiedono una interfaccia da riga di comando, dei deamon che devono fare processamenti che richiedono molta memoria e molta CPU, o dei server non troppo complessi.

E' utilizzato come sostituto di _Node.js_ nelle applicazioni _Javascript_ ricoprendo la parte di application server.

Se avete una parte del vostro sistema che non riuscite a far funzionare a causa di memory leack, di uso massiccio di memoria o di tempi di processamento troppo elevati, insomma se dovete migliorare enormemente le performance di una parte dei vostri sistemi, vi consiglio di fare un tentativo con _Rust_.

## 3. facilità di debugging del codice

Scrivere codice in _Rust_ risulta complicato soprattutto dalle regole del _borrowing_ e dal _lifetime_ delle variabili.

In _Javascript_ c'è il problema dell'hoisting delle variabili con _var_, ma è un problema ridicolo se paragonato ai ragionamenti che occorre fare sul _lifetime_ o sul _borrowing_ in _Rust_.

Questi aspetti incidono notevolmente sul design delle _API_ del proprio programma.

Tuttavia il compilatore è uno strumento molto potente che indirizza molto bene il programmatore.
La mia esperienza di neofita è stata senz'altro positiva nell'utilizzo e nel risolvere problemi.

Ci sono strumenti per debuggare il codice in runtime come per altri linguaggi compilati.

C'è tutta via una difficoltà a capire bene cosa avviene nel codice, perché le regole di _Rust_ sono complesse e occorre andare molto a fondo nella loro conoscenza, molto più che per altri linguaggi.

Ma qui credo che il punto sia che _Rust_ è pensato per programmatori che vengono dal mondo _C_ o _C++_ dove questo tipo di analisi è necessaria quotidianamente nello scrivere il proprio codice.

Chi viene da mondi come _Java_ o _C#_ dove il focus è molto più dedicato alla logica di business e il linguaggi presentano poche "sorprese", può essere considerato troppo oneroso o difficile.

## 4. usabilità attuale dei tool base (cargo + crates)

La toolchain di _Rust_ è bella ricca e piuttosto collaudata.

Si comincia con _rustup_ che è un tool che serve per gestire la versione installata, aggiornarla e switchare fra le varie versioni.

_Rust_ viene distribuito con 3 versioni:

 * nightly: che costituisce l'ultimo build giornaliero
 * beta: che costituisce la prossima nuova release
 * stable: che è la release effettiva

Il ciclo di aggiornamento è di **6 settimane**.

_rustup_ consente di gestire le varie versioni, di installarle, di cambiarle e quindi di avere sempre quella adatta al progetto corrente.

Poi c'è il compilatore _rustc_ che è lo strumento base per compilare i programmi _Rust_ e di cui ho già elogiato le enormi qualità di messaggistica e di help che offre.

Veniamo a _cargo_ che è considerato lo strumento di project build e gestione delle dipendenze nella community di _Rust_.

E' uno strumento ben collaudato che consente di creare e buildare facilmente il proprio progetto.

Riguardo alla pubblicazione di _crates_ non sono molto informato, in quanto non ho mai provato. Sulla presenza di _crates_ utilizzabili direi che c'è molta roba.

Quello che abbiamo riscontrato è una certa debolezza delle librerie per l'accesso ai database.

_Rust_ è un linguaggio che ha fatto proprio il principio di velocità e quindi cerca di pubblicare nuove feature il prima possibile. Questo è spesso un problema con la compatibilità del codice scritto e di conseguenza la stabilità delle librerie.

Molte sono sviluppate direttamente sulla _nightly_ per far si che siano sempre aggiornate e che possano girare sulla versione _stable_ prossima.

Ma come già sapete non sempre le feature di un ramo di sviluppo instabile possono essere passate sul ramo stabile in maniera prevedibile, e quindi la stabilizzazione di parecchio del codice presente è un po' incerta.

Questo rende _Rust_ un linguaggio, per il momento, poco adatto a tool che richiedono una certa affidabilità e stabilità in produzione.

Se non siete in grado di coprire il vostro tool con una adeguata suite di test non vi consiglio di lavorare con _Rust_.

## 5. stato delle librerie per fare testing

Il linguaggio prevede il test in maniera nativa, nel senso che mette a disposizione degli strumenti per dichiarare che una funzione o un modulo sono dei test e che quindi non faranno parte del compilato che va in produzione.

Non c'è molto più supporto di questo al momento.

Con _cargo_ è possibile eseguire tutti o parte dei test scritti per un progetto, ma per avere la copertura sul codice occorre utilizzare estensioni da installare e configurare a parte.

E' consuetudine avere il codice dei test presente nello stesso file del codice di produzione. A volte si crea un modulo dedicato sempre nello stesso file.

E' possibile distribuire il codice su un file esterno.

Questi test sono gli _Unit Tests_ ed in _Rust_ si tengono vicino al codice, dato che il linguaggio consente di "marcarli" esplicitamente come test e che vengono eseguiti e compilati solo nel caso in cui si lancia il test.

_cargo_ consente di avere una cartella dove scrivere dei test, ma la considera come test di integrazione e non più di unità.

Una delle feature interessanti di _cargo_ è che consente di avere delle cartelle speciali denominate _benches_ e _examples_ dove mettere del codice che esegue rispettivamente dei testi di performance e degli esempi d'uso del crate.

Si tratta di cartelle opzionali, che si possono aggiungere solo nel caso in cui servano, ma se si decide di utilizzarle il vantaggio è chiaro.

Il codice in queste cartelle viene toccato solo se si utilizza l'apposito comando di _cargo_ e quindi di nuovo è separato dal codice di produzione.

Questa è una lista di tool che ho visto, ma non sono ancora molto utilizzati:

 * [Hamcrest](https://github.com/ujh/hamcrest-rust)
 * [rust-skeptic](https://github.com/budziq/rust-skeptic)
 * [Noir](https://github.com/BonsaiDen/noir)

## 7. e al contrario, c'è uno scenario in cui ritieni che Rust sia meglio lasciarlo da parte?

Penso che in alcuni scenari si dovrebbe adottare una certa prudenza per via dell'instabilità di molte librerie, specie di interfacciamento con database.

Al momento c'è in ballo un cambiamento per quanto riguarda le feature di concurrency, si stanno introducendo i Feature e prossimamente arriverà anche il costrutto async/await.

Direi che se lo si vuole adottare in sostituzione di un linguaggio OO come Java starei molto attento, specie se si tratta 
di applicazioni di alto livello o dove c'è da implementare molta logica di business.
