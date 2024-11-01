async function login(username, password) {
    console.log(username, password);
    const response = await fetch('http://localhost:8080/entrar', { 
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username: username, password: password }),
    });

    if (response.ok) {
        console.log("Login bem-sucedido");
        // Redirecionar para a página principal ou realizar alguma ação adicional
        window.location.href = 'admPage.html'; // Redirecionamento para a página admPage.html
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

function navigateToTable() {
    const tableSelect = document.getElementById("tableSelect");
    const selectedTable = tableSelect.value;

    if (selectedTable) {
        alert(`Navegando para a tabela: ${selectedTable}`);
        // Aqui pode-se incluir a navegação para a rota específica, como:
        // window.location.href = `${selectedTable}.html`;
    } else {
        alert("Por favor, selecione uma tabela.");
    }
}

