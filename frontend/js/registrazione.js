/** Elementi del DOM
 * @type {HTMLFormElement} formRegistrazione
 * @type {HTMLInputElement} nomeForm
 * @type {HTMLInputElement} cognomeForm
 * @type {HTMLInputElement} emailForm
 * @type {HTMLInputElement} passwordForm
 */
const formRegistrazione = document.getElementById('formRegistrazione');
const nomeForm = document.getElementById('nome');
const cognomeForm = document.getElementById('cognome');
const emailForm = document.getElementById('email');
const passwordForm = document.getElementById('password');

/**
 * Event listener per il form di registrazione.
 * Intercetta il submit, previene il comportamento di default e chiama funzioneRegistraUtente.
 */
formRegistrazione.addEventListener('submit', (event) => {
    event.preventDefault();
    funzioneRegistraUtente();
});

/**
 * Registra un nuovo utente nel database.
 * Recupera i valori dal form, li invia al backend con una richiesta POST e mostra un messaggio di errore.
 */
const funzioneRegistraUtente = () => {
    const nuovoutente = {
        nome: nomeForm.value,
        cognome: cognomeForm.value,
        email: emailForm.value,
        password: passwordForm.value
    };

    fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(nuovoutente)
    })
    .then(response => response.json())
    .then((data) => {
        if (data.statusCode === "OK") {
            Toastify({
                text: `Registrazione completata con successo`,
                gravity: 'top',
                duration: 1000,
                position: 'center',
                style: {
                    background: 'green',
                    boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                }
            }).showToast();

            setTimeout(() => {
                window.location.href = 'login.html';
            }, 1100);
        } else {
            Toastify({
                text: `Registrazione fallita`,
                gravity: 'top',
                duration: 1000,
                position: 'center',
                style: {
                    background: 'red',
                    boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                }
            }).showToast();
        }
    })
    .catch((errore) => {
        console.log(`Errore nella registrazione: ${errore.message}`);
    });
};

