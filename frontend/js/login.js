/** Elementi del DOM 
 * @type {HTMLFormElement} formLogin
 * @type {HTMLInputElement} formEmail
 * @type {HTMLInputElement} formPassword
 */


const formLogin = document.getElementById('formLogin');       
const formEmail = document.getElementById('email');         
const formPassword = document.getElementById('password');

/** Event Listener per il login */
formLogin.addEventListener('submit', (event) => {
    event.preventDefault();
    loginUtente();
});

/**
 * Effettua il login dell'utente.
 * Invia email e password al backend, salva i dati nel localStorage
 * e reindirizza alla pagina corretta in base al ruolo.
 */
const loginUtente = () => {
    const utenteLoggato = {
        email: formEmail.value,
        password: formPassword.value
    };

    fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        credentials: 'include',
        body: JSON.stringify(utenteLoggato),
        headers: {'Content-Type': 'application/json'}
    }).then(response => response.json())
      .then((data) => {
          if(data.statusCode === 'OK'){
              localStorage.setItem("utente", JSON.stringify(data));
              const ruoloUtente = data.data.ruolo;
              const nomeUtente = data.data.nome;

              Toastify({
                  text: `Benvenuto ${nomeUtente}, adesso verrai reindirizzato alla pagina principale`,
                  gravity: 'top',
                  duration: 1000,
                  position: 'center',
                  style: {
                      background: 'green',
                      boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                  }
              }).showToast();

              setTimeout(() => {
                  if(ruoloUtente === 'HR'){
                      window.location.href = '/html/portaleHr.html';
                  } else {
                      window.location.href = '/html/portaleDipendente.html';
                  }
              }, 1100);
          }
      }).catch((errore) => {
          console.log(`Errore ${errore.message}`);
      });
};