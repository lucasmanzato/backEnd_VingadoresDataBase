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

async function fetchData() {
    const tableSelect = document.getElementById("table-select");
    const selectedTable = tableSelect.value;
    const dataTableContainer = document.getElementById("data-table-container");

    // Limpa o conteúdo anterior
    dataTableContainer.innerHTML = "";

    if (!selectedTable) return;

    try {
        const response = await fetch(`http://localhost:8080/api/${selectedTable}/getAll`);
        
        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela");
        }
        
        const data = await response.json();

        if (data.length === 0) {
            dataTableContainer.innerHTML = "<p>Nenhum dado encontrado.</p>";
            return;
        }

        // Cria a tabela de dados
        const table = document.createElement("table");
        const thead = document.createElement("thead");
        const tbody = document.createElement("tbody");

        // Cabeçalho da tabela
        const headerRow = document.createElement("tr");
        Object.keys(data[0]).forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });
        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(row => {
            const rowElement = document.createElement("tr");
            Object.values(row).forEach(value => {
                const td = document.createElement("td");
                td.textContent = value;
                rowElement.appendChild(td);
            });
            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML = `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}

