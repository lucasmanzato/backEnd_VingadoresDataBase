async function login(username, password) {
    const response = await fetch('http://localhost:8080/entrar', { 
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username: username, password: password }),
        credentials: 'include' // Manter credenciais na requisição
    });

    if (response.ok) {
        console.log("Login bem-sucedido");
        // Redirecionar para a página principal ou realizar alguma ação adicional
    } else {
        console.error("Erro no login. Credenciais inválidas.");
        alert("Usuário ou senha incorretos!");
    }
}

// Adicionar event listeners aos formulários
document.getElementById('admin-login-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Impede o envio padrão do formulário
    const username = document.getElementById('adm-username').value;
    const password = document.getElementById('adm-password').value;
    login(username, password);
});

document.getElementById('visitor-login-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Impede o envio padrão do formulário
    const username = document.getElementById('visit-username').value;
    const password = document.getElementById('visit-password').value;
    login(username, password);
});
