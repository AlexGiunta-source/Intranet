/** Elementi del DOM
 * @type {HTMLParagraphElement} paragrafoAttesa
 * @type {HTMLParagraphElement} paragrafoAccettate
 * @type {HTMLParagraphElement} paragrafoRifiutate
 * @type {HTMLParagraphElement} paragrafoTotali
 * @type {HTMLParagraphElement} paragrafoMalattia
 * @type {HTMLParagraphElement} paragrafoPermesso
 * @type {HTMLParagraphElement} paragrafoFerie
 * @type {HTMLSelectElement} filtroUtente
 * @type {HTMLInputElement} filtroData
 * @type {HTMLSelectElement} filtroStato
 * @type {HTMLTableElement} bodyTabella
 * @type {HTMLSelectElement} filtraPerUtente
 * @type {HTMLInputElement} dataInserimento
 * @type {HTMLSelectElement} statoRichiesta
 * @type {HTMLButtonElement} bottoneLogout
 */
const paragrafoAttesa = document.getElementById('attesa')
const paragrafoAccettate = document.getElementById('accettate')
const paragrafoRifiutate = document.getElementById('rifiutate')
const paragrafoTotali = document.getElementById('totali')
const paragrafoMalattia = document.getElementById('malattia')
const paragrafoPermesso = document.getElementById('permesso')
const paragrafoFerie = document.getElementById('ferie')
const filtroUtente = document.getElementById('cercautente')
const filtroData = document.getElementById('datainserimento')
const filtroStato = document.getElementById('statorichiesta')
const bodyTabella = document.getElementById('tabellaRichieste')
const filtraPerUtente = document.getElementById('cercautente')
const dataInserimento = document.getElementById('datainserimento')
const statoRichiesta = document.getElementById('statorichiesta')
const bottoneLogout = document.getElementById('pulsanteLogout')


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
    logoutHr();
})
/**
 * Effettua il logout dell'utente corrente.
 * rimuove i dati dell'utente dal localStorage, mostra un messaggio di conferma
 * tramite Toastify e reindirizza l'utente alla pagina di login.
 */

const logoutHr = () => {
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
 * Carica tutte le richieste degli utenti e le mostra nella tabella HTML.
 */
const caricaRichiesteTabella = () => {
    fetch('http://localhost:8080/api/mostraRichieste', {
        method: 'GET',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json' }
    }).then((response => response.json()))
        .then((data) => {
            if (data.statusCode == 'OK') {
                const bodyTabella = document.getElementById("tabellaRichieste")
                bodyTabella.innerHTML = "";
                data.data.forEach((richiesta) => {
                    richiesta.dataFine = new Date(richiesta.dataFine)
                    richiesta.dataInizio = new Date(richiesta.dataInizio)
                    richiesta.dataFine = new Intl.DateTimeFormat("it-IT", opzioniFormatoDataOrario).format(richiesta.dataFine)
                    richiesta.dataInizio = new Intl.DateTimeFormat("it-IT", opzioniFormatoDataOrario).format(richiesta.dataInizio)
                    bodyTabella.innerHTML += `
                        <tr>
                            <td>${richiesta.emailUtente}</td>
                            <td>${richiesta.dataInizio}</td>
                            <td>${richiesta.dataFine}</td>
                            <td>${richiesta.tipoPermesso}</td>
                            <td>${richiesta.motivazione}</td>
                            

                            ${richiesta.statoRichiesta === "in attesa" ? '<td><p class="text-warning">in attesa</p></td>' : ''}
                            ${richiesta.statoRichiesta === "accettata" ? '<td><p class="text-success">accettata</p></td>' : ''}
                            ${richiesta.statoRichiesta === "rifiutata" ? '<td><p class="text-danger">rifiutata</p></td>' : ''}
                             
                            
                            ${richiesta.statoRichiesta === "in attesa" ? `<td><input data-id="${richiesta.idRichiesta}" type="text" name="noteHr" placeholder="inserisci le note"></td> ` : `<td> ${richiesta.noteHr} </td>`}
                            ${richiesta.statoRichiesta === "in attesa" ? `<td><button class="btn btn-danger" onclick="aggiornaRichiesta('rifiutata',${richiesta.idRichiesta})" >rifiuta</button><button class="btn btn-success" onclick="aggiornaRichiesta('accettata', ${richiesta.idRichiesta})" >accetta</button></td>` : ''}
                        </tr>
                        
                    `
                })
            }
        }).catch((errore) => {
            console.log(`Errore funzione caricaRichiesteTabella: ${errore.message} `)
        })
}
/**
 *
 * Quando viene caricata la pagina, vengono eseguite queste funzioni:
 *  - caricaRichiesteTabella()
 *  - riepilogoRichiesteHr()
 *  - mostraUtenti()
 */
document.addEventListener("DOMContentLoaded", () => {
    caricaRichiesteTabella();
    riepilogoRichiesteHr();
    mostraUtenti();
});
/**
 * Aggiorna lo stato e le note di una richiesta.
 */
const aggiornaRichiesta = (stato, idRichiesta) => {
    const inputNote = document.querySelector(`input[data-id='${idRichiesta}']`);
    const noteHr = inputNote ? inputNote.value : '';
    const richiestaAggiornata = {
        statoRichiesta: stato,
        noteHr: noteHr,
    }
    fetch(`http://localhost:8080/api/aggiornaRichiesta/${idRichiesta}`, {
        method: 'PUT',
        credentials: 'include',
        body: JSON.stringify(richiestaAggiornata),
        headers: { 'Content-Type': 'application/json' }
    }).then(response => response.json())
        .then((data) => {
            if (data.statusCode == 'OK') {
                caricaRichiesteTabella();
            }
        }).catch((errore) => {
            console.log(`Errore nella funzione aggiornaRichiesta: ${errore.message} `)
        })
}

/**
 * Aggiorna il riepilogo delle richieste HR.
 */
const riepilogoRichiesteHr = () => {
    fetch('http://localhost:8080/api/dashBoardHr', {
        method: 'GET',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json' }
    }).then(response => response.json())
        .then((data) => {
            if (data.statusCode == 'OK') {
                paragrafoAttesa.innerHTML = `${data.data.numeroInAttesa}`
                paragrafoAccettate.innerHTML = `${data.data.numeroApprovate}`
                paragrafoRifiutate.innerHTML = `${data.data.numeroRifiutate}`
                paragrafoTotali.innerHTML = `${data.data.numeroTotaleRichieste}`
                paragrafoMalattia.innerHTML = `${data.data.malattia}`
                paragrafoPermesso.innerHTML = `${data.data.permesso}`
                paragrafoFerie.innerHTML = `${data.data.ferie}`

            }
        }).catch((errore) => {
            console.log(`Errore nella funzione riepilogoRichiesteHr: ${errore.message} `)
        })
}

/**
 * Filtra le richieste in base ai parametri selezionati.
 */
const filtraRichieste = () => {
    filtraPerUtente.value === " " ? null : filtraPerUtente.value
    filtroData.value === " " ? null : filtroData.value
    filtroStato.value === " " ? null : filtroStato.value
    const filtra = {
        idUtente: filtraPerUtente.value,
        dataRichiesta: filtroData.value,
        statoRichiesta: filtroStato.value
    }
    fetch('http://localhost:8080/api/richiestefiltrate', {
        method: 'POST',
        credentials: 'include',
        body: JSON.stringify(filtra),
        headers: { 'Content-Type': 'application/json' }
    }).then(response => response.json())
        .then((data) => {
            if (data.statusCode == 'OK') {
                bodyTabella.innerHTML = "";
                data.data.forEach((richiesta) => {
                    richiesta.dataFine = new Date(richiesta.dataFine)
                    richiesta.dataInizio = new Date(richiesta.dataInizio)
                    richiesta.dataFine = new Intl.DateTimeFormat("it-IT", opzioniFormatoDataOrario).format(richiesta.dataFine)
                    richiesta.dataInizio = new Intl.DateTimeFormat("it-IT", opzioniFormatoDataOrario).format(richiesta.dataInizio)
                    bodyTabella.innerHTML += `
                        <tr>
                            <td>${richiesta.emailUtente}</td>
                            <td>${richiesta.dataInizio}</td>
                            <td>${richiesta.dataFine}</td>
                            <td>${richiesta.tipoPermesso}</td>
                            <td>${richiesta.motivazione}</td>
                            

                            ${richiesta.statoRichiesta === "in attesa" ? '<td><p class="text-warning">in attesa</p></td>' : ''}
                            ${richiesta.statoRichiesta === "accettata" ? '<td><p class="text-success">accettata</p></td>' : ''}
                            ${richiesta.statoRichiesta === "rifiutata" ? '<td><p class="text-danger">rifiutata</p></td>' : ''}
                             
                            
                            ${richiesta.statoRichiesta === "in attesa" ? `<td><input data-id="${richiesta.idRichiesta}" type="text" name="noteHr" placeholder="inserisci le note"></td> ` : `<td> ${richiesta.noteHr} </td>`}
                            ${richiesta.statoRichiesta === "in attesa" ? `<td><button class="btn btn-danger" onclick="aggiornaRichiesta('rifiutata',${richiesta.idRichiesta})" >rifiuta</button><button class="btn btn-success" onclick="aggiornaRichiesta('accettata', ${richiesta.idRichiesta})" >accetta</button></td>` : ''}
                        </tr>
                        
                    `
                })
            }
        }).catch((errore) => {
            console.log(`Errore nella funzione filtraRichieste: ${errore.message}`)
        })


}
/**
 * Esegue il filtro delle richieste quando cambia il valore della select "filtraPerUtente".
 */
filtraPerUtente.addEventListener('change', () => {
    filtraRichieste();
})
/**
 * Esegue il filtro delle richieste quando cambia il valore della select "dataInserimento".
 */
dataInserimento.addEventListener('change', () => {
    filtraRichieste();
})
/**
 * Esegue il filtro delle richieste quando cambia il valore della select "statoRichiesta".
 */
statoRichiesta.addEventListener('change', () => {
    filtraRichieste();
})

/**
 * Recupera tutti gli utenti  e popola la select "filtraPerUtente".
 */
const mostraUtenti = () => {
    fetch('http://localhost:8080/api/mostraUtenti', {
        method: 'GET',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json' }
    }).then(response => response.json())
        .then((data) => {
            if (data.statusCode == 'OK') {
                data.data.forEach((utente) => {
                    filtraPerUtente.innerHTML += `<option value="${utente.id}">${utente.email}</option>`
                })
            }
        }).catch((errore) => {
            console.log(`Errore nella funzione mostraUtenti: ${errore.message}`)
        })
}

