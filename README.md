# Intranet
## Documentazione 
Per consultare ulteriore documetazione dettagliata, consultare il pdf nella cartella "docs".
## Contesto
Questo progetto fa parte del tirocinio svolto presso AKT S.r.l. 
## Intranet
Intranet è un'applicazione full stack sviluppata durante un tirocinio presso AKT S.r.l. per la gestione di richieste di ferie, permessi e malattie.
Include funzionalità di autenticazione, autorizzazione e gestione delle richieste lato dipendente e HR.
## Caratteristiche principali:
### Dipendente: 
- Creare una nuova richiesta  
- Annullare una richiesta  
- Inserire motivazioni per la richiesta  
- Filtrare le richieste in base allo stato  
- Visualizzare le proprie richieste tramite una tabella  
- Effettuare login, logout e invalidare la sessione
### Hr: 
- Accettare o rifiutare richieste, con motivazione opzionale  
- Filtrare le richieste per utente, stato o data  
- Visualizzare tutte le richieste presenti nel sistema  
- Aggiungere note alle richieste approvate o rifiutate  
- Consultare una **dashboard riepilogativa** con statistiche per stato  
- Effettuare login, logout e invalidare la sessione  
## Requisiti:
- Java 17+
- Maven (incluso nel progetto come wrapper: `./mvnw`)
- Spring Boot
- Browser moderno (per la parte frontend in JS, HTML, CSS e Bootstrap)
## Installazione:
Clona la repository ed entra nella cartella del progetto:
```bash
git clone https://github.com/AlexGiunta-source/Intranet.git
cd Intranet
```
## Compila ed esegui l’applicazione:
```bash
./mvnw spring-boot:run
```
## Apri il browser e vai all’indirizzo:
```
http://localhost:8080
```
## Utilizzo:
Una volta aperta la pagina del browser, devi: 
- **Registrarti** come nuovo utente (automaticamente verrà assegnato il ruolo "DIPENDENTE")
- Effettuare il **login** con le credenziali usate nella registrazione
## Licenza:
Questo progetto è distribuito sotto licenza MIT.
## Contatti:
Autore: Alex Giunta <br> 
GitHub: @AlexGiunta-source <br> 
Email: alexgiunta7@gmail.com <br> 
