/** Elementi del DOM
 * @type {HTMLButtonElement} bottoneLogout
 * @type {HTMLInputElement} dataInizio
 * @type {HTMLInputElement} dataFine
 * @type {HTMLInputElement} tipoRichiesta
 * @type {HTMLInputElement} motivazione
 * @type {HTMLSelectElement} statoRichiesteInput
 * @type {HTMLFormElement} filtroForm
 */
const bottoneLogout = document.getElementById('bottoneLogout');
const dataInizio = document.getElementById("dataInizio")
const dataFine = document.getElementById("dataFine")
const tipoRichiesta = document.getElementById("tipoRichiesta")
const motivazioneRichiesta = document.getElementById("motivazione")
const formCreaRichiesta = document.getElementById('formCreaRichiesta')
const statoRichiesteInput = document.getElementById("filtraInBaseAlloStato")
const filtroForm = document.getElementById('filtroForm')

/**
 * Opzioni per formattare le date e gli orari in italiano.
 */
const opzioniFormatoDataOrario = {
    day: "2-digit",
    month: "long",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit"
}
/** 
 * Esegue il logout quando viene cliccato il bottone "bottoneLogout"
 */
bottoneLogout.addEventListener('click', () => {
    logout();
})

/**
 * Effettua il logout dell'utente corrente.
 * rimuove i dati dell'utente dal localStorage, mostra un messaggio di conferma
 * tramite Toastify e reindirizza l'utente alla pagina di login.
 */
const logout = () => {
    fetch('http://localhost:8080/api/auth/logout', {
        method: 'POST',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json' }
    }).then((response => response.json()))
        .then((data) => {
            if (data.statusCode === 'OK') {
                localStorage.removeItem('utente')
                Toastify({
                    text: "Logout effettuato",
                    duration: 1000,
                    gravity: "top",
                    position: "center",
                    backgroundColor: "red"
                }).showToast();
                setTimeout(() => {
                    window.location.href = '/html/login.html'
                }, 1100);
            }
        })
}

/**
 * Esegue la funzione `caricaRichiesteUtenteLoggato` al caricamento della pagina.
 */
document.addEventListener('DOMContentLoaded', () => {
    caricaRichiesteUtenteLoggato();
})
/**
 * Carica tutte le richieste effettuate dall'utente loggato.
 */
const caricaRichiesteUtenteLoggato = () => {
    const utente = JSON.parse(localStorage.getItem('utente'))
    const idUtente = utente.data.id_utente;
    fetch(`http://localhost:8080/api/cercaRichiestePerUtente/${idUtente}`, {
        method: 'GET',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json' }
    }).then(response => response.json())
        .then((data) => {
            if (data.statusCode === 'OK') {
                const bodyTabella = document.getElementById("tabellabody")
                bodyTabella.innerHTML = "";
                data.data.forEach(richiesta => {
                    richiesta.dataFine = new Date(richiesta.dataFine)
                    richiesta.dataInizio = new Date(richiesta.dataInizio)
                    richiesta.dataFine = new Intl.DateTimeFormat("it-IT", opzioniFormatoDataOrario).format(richiesta.dataFine)
                    richiesta.dataInizio = new Intl.DateTimeFormat("it-IT", opzioniFormatoDataOrario).format(richiesta.dataInizio)
                    bodyTabella.innerHTML += `
                        <tr>
                            <td>${richiesta.dataInizio}</td>
                            <td>${richiesta.dataFine}</td>
                            <td>${richiesta.tipoPermesso}</td>
                            <td>${richiesta.motivazione}</td>
                            

                            ${richiesta.statoRichiesta === "in attesa" ? '<td><p class="text-warning">in attesa</p></td>' : ''}
                            ${richiesta.statoRichiesta === "accettata" ? '<td><p class="text-success">accettata</p></td>' : ''}
                            ${richiesta.statoRichiesta === "rifiutata" ? '<td><p class="text-danger">rifiutata</p></td>' : ''}
                            <td>${richiesta.noteHr}</td>
                             ${richiesta.statoRichiesta === "in attesa" ? `<td><button class="btn btn-danger" onclick="cancellaRichiesta(${richiesta.idRichiesta})">annulla</button></td>` : ''}
                        </tr>
                        
                    `
                })
            } else {
                Toastify({
                    text: `Errore nel caricamento della lista delle richieste`,
                    duration: 1000,
                    gravity: "top",
                    position: "center",
                    backgroundColor: "red"
                }).showToast();
            }
        }).catch((errore) => {
            console.log(`Errore: ${errore.message}`)
        })
}

/**
 * Elimina una determinata richiesta sfruttando l'ID fornito.
 */
const cancellaRichiesta = (idRichiesta) => {
    fetch(`http://localhost:8080/api/eliminaRichiesta/${idRichiesta}`, {
        method: 'DELETE',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json' }
    }).then(response => response.json())
    .then((data) => {
        if (data.statusCode === 'OK') {
            Toastify({
                text: `Richiesta rimossa correttamente`,
                duration: 1000,
                gravity: "top",
                position: "center",
                backgroundColor: "red"
            }).showToast();
            setTimeout(() => {
                window.location.reload();
            }, 1100);
        }
    }).catch((errore) => {
        console.log(`Errore rimozione richiesta: ${errore.message}`)
    })
}
/**
 * Esegue la funzione creaRichiesta(); quando viene compilato il form form "formCreaRichiesta".
 */
formCreaRichiesta.addEventListener('submit', (event) => {
    event.preventDefault();
    creaRichiesta();
})
/** Funzione creaRichiesta 
 * Crea una nuova richiesta da parte dell'utente
 */
const creaRichiesta = () => {
    const inputForm = {
        tipoPermesso: tipoRichiesta.value,
        dataInizio: dataInizio.value,
        dataFine: dataFine.value,
        motivazione: motivazioneRichiesta.value
    }
    fetch('http://localhost:8080/api/salvaRichiesta', {
        method: 'POST',
        credentials: 'include',
        body: JSON.stringify(inputForm),
        headers: { 'Content-Type': 'application/json' }
    }).then(response => response.json())
        .then((data) => {
            if (data.statusCode === 'OK') {
                Toastify({
                    text: `Richiesta creata correttamente`,
                    duration: 1000,
                    gravity: "top",
                    position: "center",
                    backgroundColor: "green"
                }).showToast();
                window.location.reload();
            }
        }).catch((errore) => {
            console.log(`Errore creazione richiesta: ${errore.message}`)
        })
}
/**
 * Esegue la funzione filtraRichieste();  quando viene compilato il form form "filtroForm".
 */
filtroForm.addEventListener('submit', (event) => {
    event.preventDefault();
    filtraRichieste();
})
/**
 * Filtra le richieste dell'utente loggato in base allo stato della richiesta  selezionato.
 *
 */
const filtraRichieste = () => {
    const utente = JSON.parse(localStorage.getItem("utente"));
    const idUtenteLoggato = utente.data.id_utente;
    const statoRichieste = statoRichiesteInput.value 
    fetch(`http://localhost:8080/api/cercaRichiestePerStatoEId/${statoRichieste}/${idUtenteLoggato}`, {
        method: 'GET',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json' }
    }).then(response => response.json())
        .then((data) => {
            if (data.statusCode === 'OK') {
                const bodyTabella = document.getElementById("tabellabody")
                bodyTabella.innerHTML = "";
                data.data.forEach((richiesta) => {
                    richiesta.dataFine = new Date(richiesta.dataFine)
                    richiesta.dataInizio = new Date(richiesta.dataInizio)
                    richiesta.dataFine = new Intl.DateTimeFormat("it-IT", opzioniFormatoDataOrario).format(richiesta.dataFine)
                    richiesta.dataInizio = new Intl.DateTimeFormat("it-IT", opzioniFormatoDataOrario).format(richiesta.dataInizio)
                    bodyTabella.innerHTML += `
                        <tr>
                            <td>${richiesta.dataInizio}</td>
                            <td>${richiesta.dataFine}</td>
                            <td>${richiesta.tipoPermesso}</td>
                            <td>${richiesta.motivazione}</td>
                            ${richiesta.statoRichiesta === "in attesa" ? '<td><p class="text-warning">in attesa</p></td>' : ''}
                            ${richiesta.statoRichiesta === "accettata" ? '<td><p class="text-success">accettata</p></td>' : ''}
                            ${richiesta.statoRichiesta === "rifiutata" ? '<td><p class="text-danger">rifiutata</p></td>' : ''}
                            <td>${richiesta.noteHr}</td>
                            ${richiesta.statoRichiesta === "in attesa" ? `<td><button class="btn btn-danger" onclick="cancellaRichiesta(${richiesta.idRichiesta})">annulla</button></td>` : ''}
                        </tr>
                        
                    `
                })
            }
        })
}





