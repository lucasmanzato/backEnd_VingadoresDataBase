async function loadUserTable() {
    const selectedTable = document.getElementById("table-select").value;

    switch (selectedTable) {
        case "heroi":
            await fetchAndDisplayHeroisForUser();
            break;
        case "bases":
            await fetchAndDisplayBasesForUser();
            break;
        case "missoes":
            await fetchAndDisplayMissoesForUser();
            break;
        case "eventosHistoricos":
             await fetchAndDisplayEventosHistoricoForUser();
             break;
        
        // Adicione os outros casos conforme necessário
        default:
            document.getElementById("data-table-container").innerHTML = "<p>Escolha uma tabela para exibir os dados.</p>";
    }
}


async function fetchAndDisplayHeroisForUser() {
    console.log("Buscando dados da tabela herois...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    try {
        const response = await fetch("http://localhost:8080/herois");

        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela herois");
        }

        const data = await response.json();

        if (data.length === 0) {
            dataTableContainer.innerHTML += "<p>Nenhum dado encontrado.</p>";
            return;
        }

        const table = document.createElement("table");
        const thead = document.createElement("thead");
        const tbody = document.createElement("tbody");

        // Cabeçalho da tabela
        const headerRow = document.createElement("tr");
        ["Nome", "Codinome", "Afiliacao", "Status", "Localizacao", "Poder"].forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(hero => {
            const rowElement = document.createElement("tr");
            ["nome", "codinome", "afiliacao", "status", "localizacao", "poder"].forEach(key => {
                const td = document.createElement("td");
                td.textContent = hero[key];
                rowElement.appendChild(td);
            });

            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}

async function fetchAndDisplayBasesForUser() {
    console.log("Buscando dados da tabela bases...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    try {
        const response = await fetch("http://localhost:8080/bases");

        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela bases");
        }

        const data = await response.json();

        if (data.length === 0) {
            dataTableContainer.innerHTML += "<p>Nenhum dado encontrado.</p>";
            return;
        }

        const table = document.createElement("table");
        const thead = document.createElement("thead");
        const tbody = document.createElement("tbody");

        // Cabeçalho da tabela
        const headerRow = document.createElement("tr");
        ["Nome Base", "Localização", "Propósito", "Capacidade", "Status", "Comandante"].forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(base => {
            const rowElement = document.createElement("tr");

            ["nomeBase", "localizacaoBase", "propositoBase", "capacidadeBase", "statusBase", "comandanteBase"].forEach(key => {
                const td = document.createElement("td");
                td.textContent = base[key] || "N/A";
                rowElement.appendChild(td);
            });

            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}

async function fetchAndDisplayMissoesForUser() {
    console.log("Buscando dados da tabela missões...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    try {
        const response = await fetch("http://localhost:8080/missoes");

        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela missões");
        }

        const data = await response.json();

        if (data.length === 0) {
            dataTableContainer.innerHTML += "<p>Nenhum dado encontrado.</p>";
            return;
        }

        const table = document.createElement("table");
        const thead = document.createElement("thead");
        const tbody = document.createElement("tbody");

        // Cabeçalho da tabela
        const headerRow = document.createElement("tr");
        ["Nome", "Descrição", "Data de Início", "Data de Término", "Status", "Heróis Envolvidos", "Resultado"].forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(missao => {
            const rowElement = document.createElement("tr");

            const nomeTd = document.createElement("td");
            nomeTd.textContent = missao.nomeMissoes || "N/A";
            rowElement.appendChild(nomeTd);

            const descricaoTd = document.createElement("td");
            descricaoTd.textContent = missao.descricaoMissoes || "N/A";
            rowElement.appendChild(descricaoTd);

            const dataInicioTd = document.createElement("td");
            dataInicioTd.textContent = missao.dataInicioMissoes ? new Date(missao.dataInicioMissoes).toLocaleDateString() : "N/A";
            rowElement.appendChild(dataInicioTd);

            const dataTerminoTd = document.createElement("td");
            dataTerminoTd.textContent = missao.dataTerminoMissoes ? new Date(missao.dataTerminoMissoes).toLocaleDateString() : "N/A";
            rowElement.appendChild(dataTerminoTd);

            const statusTd = document.createElement("td");
            statusTd.textContent = missao.statusMissoes || "N/A";
            rowElement.appendChild(statusTd);

            const heroisEnvolvidoTd = document.createElement("td");
            heroisEnvolvidoTd.textContent = missao.heroisEnvolvidoMissoes || "N/A";
            rowElement.appendChild(heroisEnvolvidoTd);

            const resultadoTd = document.createElement("td");
            resultadoTd.textContent = missao.resultadoMissoes || "N/A";
            rowElement.appendChild(resultadoTd);

            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}

async function fetchAndDisplayEventosHistoricoForUser() {
    console.log("Buscando dados da tabela eventosHistoricos...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    try {
        const response = await fetch("http://localhost:8080/eventosHistoricos");

        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela eventosHistoricos");
        }

        const data = await response.json();

        if (data.length === 0) {
            dataTableContainer.innerHTML += "<p>Nenhum dado encontrado.</p>";
            return;
        }

        const table = document.createElement("table");
        const thead = document.createElement("thead");
        const tbody = document.createElement("tbody");

        // Cabeçalho da tabela
        const headerRow = document.createElement("tr");
        ["Nome", "Data", "Local", "Tipo", "Participantes", "Impacto"].forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(evento => {
            const rowElement = document.createElement("tr");

            const nomeTd = document.createElement("td");
            nomeTd.textContent = evento.nomeEventos || "N/A";
            rowElement.appendChild(nomeTd);

            const dataTd = document.createElement("td");
            dataTd.textContent = evento.dataEventos ? new Date(evento.dataEventos).toLocaleDateString() : "N/A";
            rowElement.appendChild(dataTd);

            const localTd = document.createElement("td");
            localTd.textContent = evento.localEventos || "N/A";
            rowElement.appendChild(localTd);

            const tipoTd = document.createElement("td");
            tipoTd.textContent = evento.tipoEventos || "N/A";
            rowElement.appendChild(tipoTd);

            const participantesTd = document.createElement("td");
            participantesTd.textContent = evento.participantesEventos || "N/A";
            rowElement.appendChild(participantesTd);

            const impactoTd = document.createElement("td");
            impactoTd.textContent = evento.impactoEventos || "N/A";
            rowElement.appendChild(impactoTd);

            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}



