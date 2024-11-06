async function handleTableSelectChange() {
    const selectedTable = document.getElementById("table-select").value;
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = ""; // Limpa o conteúdo anterior

    switch (selectedTable) {
        case "alocacaoRecursos":
            await fetchAndDisplayAlocacaoRecursos();
            break;
        case "atribuicaoBase":
            await fetchAndDisplayAtribuicaoBase();
            break;
        case "bases":
            await fetchAndDisplayBases();
            break;
        case "envolvimentoEvento":
            await fetchAndDisplayEnvolvimentoEvento();
            break;
        case "envolvimentoVilao":
            await fetchAndDisplayEnvolvimentoVilao();
            break;
        case "eventosHistorico":
            await fetchAndDisplayEventosHistorico();
            break;
        case "heroi":
            await fetchAndDisplayHerois();
            break;
        case "missoes":
            await fetchAndDisplayMissoes();
            break;
        case "participacaoEventos":
            await fetchAndDisplayParticipacaoEventos();
            break;
        case "participacaoHerois":
            await fetchAndDisplayParticipacaoHerois();
            break;
        case "recursos":
            await fetchAndDisplayRecursos();
            break;
        case "utilizacaoRecursos":
            await fetchAndDisplayUtilizacaoRecursos();
            break;
        case "viloes":
            await fetchAndDisplayViloes();
            break;
        case "usuarios":
            await fetchAndDisplayUsuarios();
            break;
        default:
            dataTableContainer.innerHTML = "<p>Escolha uma tabela para exibir os dados.</p>";
    }
}

async function fetchAndDisplayHerois() {
    console.log("Buscando dados da tabela herois...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    const insertButton = document.createElement("button");
    insertButton.textContent = "Inserir Novo Herói";
    insertButton.addEventListener("click", exibirFormularioNovoHeroi);
    dataTableContainer.appendChild(insertButton);

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

        const headerRow = document.createElement("tr");
        ["Nome", "Codinome", "Afiliacao", "Status", "Localizacao", "Poder", "Ações"].forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        thead.appendChild(headerRow);

        data.forEach(hero => {
            const rowElement = document.createElement("tr");
            ["nome", "codinome", "afiliacao", "status", "localizacao", "poder"].forEach(key => {
                const td = document.createElement("td");
                td.textContent = hero[key];
                rowElement.appendChild(td);
            });

            const actions = document.createElement("td");

            // Botão de Edição
            const editButton = document.createElement("button");
            editButton.textContent = "Editar";
            editButton.addEventListener("click", function () {
                exibirFormularioEdicaoHeroi(hero); // Abre o formulário de edição com os dados do herói
            });
            actions.appendChild(editButton);

            // Botão de Exclusão
            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Excluir";
            deleteButton.addEventListener("click", async function () {
                await deleteHero(hero.id);
                fetchAndDisplayHerois();
            });
            actions.appendChild(deleteButton);

            rowElement.appendChild(actions);
            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}


async function fetchAndDisplayAlocacaoRecursos() {
    console.log("Buscando dados da tabela alocacaoRecursos...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    // Cria o botão de Inserir no topo da tabela
    const insertButton = document.createElement("button");
    insertButton.textContent = "Inserir Nova Alocação de Recurso";
    insertButton.addEventListener("click", function() {
        alert("Abrir formulário de inserção de nova alocação de recurso"); // Substitua com a lógica de inserção
    });
    dataTableContainer.appendChild(insertButton);

    try {
        const response = await fetch("http://localhost:8080/api/api/alocacao-recursos");

        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela alocacaoRecursos");
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
        ["Base", "Recurso"].forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        // Coluna de ações
        const thActions = document.createElement("th");
        thActions.textContent = "Ações";
        headerRow.appendChild(thActions);
        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(allocation => {
            const rowElement = document.createElement("tr");

            const baseTd = document.createElement("td");
            baseTd.textContent = allocation.base ? allocation.base.nome : "N/A"; // Substitua 'nome' pelo atributo da base
            rowElement.appendChild(baseTd);

            const recursoTd = document.createElement("td");
            recursoTd.textContent = allocation.recurso ? allocation.recurso.nome : "N/A"; // Substitua 'nome' pelo atributo do recurso
            rowElement.appendChild(recursoTd);

            const actions = document.createElement("td");
            const editButton = document.createElement("button");
            editButton.textContent = "Editar";
            editButton.addEventListener("click", function() {
                alert("Editar alocação de recurso");
            });
            actions.appendChild(editButton);

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Excluir";
            deleteButton.addEventListener("click", async function() {
                try {
                    const deleteResponse = await fetch(`http://localhost:8080/api/alocacao-recursos/${allocation.alocacaoId}`, {
                        method: "DELETE"
                    });

                    if (!deleteResponse.ok) {
                        throw new Error("Erro ao excluir a alocação de recurso");
                    }
                    alert("Alocação excluída com sucesso");
                    fetchAndDisplayAlocacaoRecursos(); // Atualiza a tabela após exclusão
                } catch (error) {
                    console.error("Erro ao excluir alocação:", error.message);
                }
            });
            actions.appendChild(deleteButton);

            rowElement.appendChild(actions);
            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}

async function fetchAndDisplayAtribuicaoBase() {
    console.log("Buscando dados da tabela atribuicoes...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    // Cria o botão de Inserir no topo da tabela
    const insertButton = document.createElement("button");
    insertButton.textContent = "Inserir Nova Atribuição de Base";
    insertButton.addEventListener("click", function() {
        alert("Abrir formulário de inserção de nova atribuição de base"); // Substitua com a lógica de inserção
    });
    dataTableContainer.appendChild(insertButton);

    try {
        const response = await fetch("http://localhost:8080/atribuicaoBase");

        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela atribuicoes");
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
        ["Herói", "Base"].forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        // Coluna de ações
        const thActions = document.createElement("th");
        thActions.textContent = "Ações";
        headerRow.appendChild(thActions);
        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(atribuicao => {
            const rowElement = document.createElement("tr");

            const heroiTd = document.createElement("td");
            heroiTd.textContent = atribuicao.heroi ? atribuicao.heroi.nome : "N/A"; // Substitua 'nome' pelo atributo do herói
            rowElement.appendChild(heroiTd);

            const baseTd = document.createElement("td");
            baseTd.textContent = atribuicao.base ? atribuicao.base.nome : "N/A"; // Substitua 'nome' pelo atributo da base
            rowElement.appendChild(baseTd);

            const actions = document.createElement("td");
            const editButton = document.createElement("button");
            editButton.textContent = "Editar";
            editButton.addEventListener("click", function() {
                alert("Editar atribuição de base");
            });
            actions.appendChild(editButton);

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Excluir";
            deleteButton.addEventListener("click", async function() {
                try {
                    const deleteResponse = await fetch(`http://localhost:8080/atribuicoes/${atribuicao.atribuicaoId}`, {
                        method: "DELETE"
                    });

                    if (!deleteResponse.ok) {
                        throw new Error("Erro ao excluir a atribuição de base");
                    }
                    alert("Atribuição excluída com sucesso");
                    fetchAndDisplayAtribuicoes(); // Atualiza a tabela após exclusão
                } catch (error) {
                    console.error("Erro ao excluir atribuição:", error.message);
                }
            });
            actions.appendChild(deleteButton);

            rowElement.appendChild(actions);
            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}

async function fetchAndDisplayBases() {
    console.log("Buscando dados da tabela bases...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    // Cria o botão de inserção dentro da função
    const insertButton = document.createElement("button");
    insertButton.textContent = "Inserir Nova Base";
    insertButton.addEventListener("click", exibirFormularioNovaBase);
    dataTableContainer.appendChild(insertButton);

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

        // Coluna de ações
        const thActions = document.createElement("th");
        thActions.textContent = "Ações";
        headerRow.appendChild(thActions);
        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(base => {
            const rowElement = document.createElement("tr");

            ["nomeBase", "localizacaoBase", "propositoBase", "capacidadeBase", "statusBase", "comandanteBase"].forEach(key => {
                const td = document.createElement("td");
                td.textContent = base[key] || "N/A";
                rowElement.appendChild(td);
            });

            const actions = document.createElement("td");

            // Botão de Edição
            const editButton = document.createElement("button");
            editButton.textContent = "Editar";
            editButton.addEventListener("click", function() {
                exibirFormularioEdicaoBase(base);
            });
            actions.appendChild(editButton);

            // Botão de Exclusão
            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Excluir";
            deleteButton.addEventListener("click", async function() {
                await deleteBase(base.baseId);
                fetchAndDisplayBases();
            });
            actions.appendChild(deleteButton);

            rowElement.appendChild(actions);
            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}

// Função para exibir o formulário de nova base
function exibirFormularioNovaBase() {
    const dataTableContainer = document.getElementById("data-table-container");

    // Remove o formulário existente antes de adicionar um novo
    const existingForm = document.getElementById("form-nova-base");
    if (existingForm) {
        existingForm.remove();
    }

    const form = document.createElement("form");
    form.id = "form-nova-base";

    ["Nome Base", "Localização", "Propósito", "Capacidade", "Status", "Comandante"].forEach(field => {
        const input = document.createElement("input");
        input.type = "text";
        input.placeholder = field;
        input.name = field.replace(" ", "").toLowerCase();
        form.appendChild(input);
    });

    const submitButton = document.createElement("button");
    submitButton.type = "button";
    submitButton.textContent = "Salvar";
    submitButton.addEventListener("click", async function () {
        const baseData = {
            nomeBase: form.querySelector("[name=nomebase]").value,
            localizacaoBase: form.querySelector("[name=localizacao]").value,
            propositoBase: form.querySelector("[name=proposito]").value,
            capacidadeBase: form.querySelector("[name=capacidade]").value,
            statusBase: form.querySelector("[name=status]").value,
            comandanteBase: form.querySelector("[name=comandante]").value
        };

        try {
            const response = await fetch("http://localhost:8080/bases", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(baseData),
            });

            if (response.ok) {
                alert("Base salva com sucesso!");
                form.reset();
                fetchAndDisplayBases();
            } else {
                alert("Erro ao salvar a base.");
            }
        } catch (error) {
            alert("Erro ao conectar com o servidor.");
        }
    });

    form.appendChild(submitButton);
    dataTableContainer.appendChild(form);
}

// Chame a função para carregar a tabela e exibir o botão de inserção ao carregar a página
fetchAndDisplayBases();



async function fetchAndDisplayEnvolvimentoEvento() {
    console.log("Buscando dados da tabela envolvimentoEvento...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    // Cria o botão de Inserir no topo da tabela
    const insertButton = document.createElement("button");
    insertButton.textContent = "Inserir Novo Envolvimento de Evento";
    insertButton.addEventListener("click", function() {
        alert("Abrir formulário de inserção de novo envolvimento de evento"); // Substitua com a lógica de inserção
    });
    dataTableContainer.appendChild(insertButton);

    try {
        const response = await fetch("http://localhost:8080/api/envolvimento-evento");

        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela envolvimentoEvento");
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
        ["Vilão", "Evento"].forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        // Coluna de ações
        const thActions = document.createElement("th");
        thActions.textContent = "Ações";
        headerRow.appendChild(thActions);
        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(envolvimento => {
            const rowElement = document.createElement("tr");

            const vilaoTd = document.createElement("td");
            vilaoTd.textContent = envolvimento.vilao ? envolvimento.vilao.nome : "N/A"; // Substitua 'nome' pelo atributo relevante do vilão
            rowElement.appendChild(vilaoTd);

            const eventoTd = document.createElement("td");
            eventoTd.textContent = envolvimento.evento ? envolvimento.evento.nomeEvento : "N/A"; // Substitua 'nomeEvento' pelo atributo relevante do evento
            rowElement.appendChild(eventoTd);

            const actions = document.createElement("td");
            const editButton = document.createElement("button");
            editButton.textContent = "Editar";
            editButton.addEventListener("click", function() {
                alert("Editar envolvimento de evento");
            });
            actions.appendChild(editButton);

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Excluir";
            deleteButton.addEventListener("click", async function() {
                try {
                    const deleteResponse = await fetch(`http://localhost:8080/api/envolvimento-evento/${envolvimento.envolvimentoEventoId}`, {
                        method: "DELETE"
                    });

                    if (!deleteResponse.ok) {
                        throw new Error("Erro ao excluir o envolvimento de evento");
                    }
                    alert("Envolvimento excluído com sucesso");
                    fetchAndDisplayEnvolvimentoEvento(); // Atualiza a tabela após exclusão
                } catch (error) {
                    console.error("Erro ao excluir envolvimento:", error.message);
                }
            });
            actions.appendChild(deleteButton);

            rowElement.appendChild(actions);
            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}


async function fetchAndDisplayEnvolvimentoVilao() {
    console.log("Buscando dados da tabela envolvimentoVilao...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    // Cria o botão de Inserir no topo da tabela
    const insertButton = document.createElement("button");
    insertButton.textContent = "Inserir Novo Envolvimento de Vilão";
    insertButton.addEventListener("click", function() {
        alert("Abrir formulário de inserção de novo envolvimento de vilão"); // Substitua com a lógica de inserção
    });
    dataTableContainer.appendChild(insertButton);

    try {
        const response = await fetch("http://localhost:8080//api/envolvimento-vilao");

        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela envolvimentoVilao");
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
        ["Vilão", "Missão"].forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        // Coluna de ações
        const thActions = document.createElement("th");
        thActions.textContent = "Ações";
        headerRow.appendChild(thActions);
        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(envolvimento => {
            const rowElement = document.createElement("tr");

            const vilaoTd = document.createElement("td");
            vilaoTd.textContent = envolvimento.vilao ? envolvimento.vilao.nome : "N/A"; // Substitua 'nome' pelo atributo relevante do vilão
            rowElement.appendChild(vilaoTd);

            const missaoTd = document.createElement("td");
            missaoTd.textContent = envolvimento.missao ? envolvimento.missao.nomeMissao : "N/A"; // Substitua 'nomeMissao' pelo atributo relevante da missão
            rowElement.appendChild(missaoTd);

            const actions = document.createElement("td");
            const editButton = document.createElement("button");
            editButton.textContent = "Editar";
            editButton.addEventListener("click", function() {
                alert("Editar envolvimento de vilão");
            });
            actions.appendChild(editButton);

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Excluir";
            deleteButton.addEventListener("click", async function() {
                try {
                    const deleteResponse = await fetch(`http://localhost:8080/api/envolvimento-vilao/${envolvimento.envolvimentoId}`, {
                        method: "DELETE"
                    });

                    if (!deleteResponse.ok) {
                        throw new Error("Erro ao excluir o envolvimento de vilão");
                    }
                    alert("Envolvimento excluído com sucesso");
                    fetchAndDisplayEnvolvimentoVilao(); // Atualiza a tabela após exclusão
                } catch (error) {
                    console.error("Erro ao excluir envolvimento:", error.message);
                }
            });
            actions.appendChild(deleteButton);

            rowElement.appendChild(actions);
            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}


async function fetchAndDisplayEventosHistorico() {
    console.log("Buscando dados da tabela eventosHistoricos...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    // Cria o botão de Inserir no topo da tabela
    const insertButton = document.createElement("button");
    insertButton.textContent = "Inserir Novo Evento Histórico";
    insertButton.addEventListener("click", function() {
        alert("Abrir formulário de inserção de novo evento histórico"); // Substitua com a lógica de inserção
    });
    dataTableContainer.appendChild(insertButton);

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

        // Coluna de ações
        const thActions = document.createElement("th");
        thActions.textContent = "Ações";
        headerRow.appendChild(thActions);
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

            const actions = document.createElement("td");
            const editButton = document.createElement("button");
            editButton.textContent = "Editar";
            editButton.addEventListener("click", function() {
                alert("Editar evento histórico");
            });
            actions.appendChild(editButton);

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Excluir";
            deleteButton.addEventListener("click", async function() {
                try {
                    const deleteResponse = await fetch(`http://localhost:8080/eventosHistoricos/${evento.eventosId}`, {
                        method: "DELETE"
                    });

                    if (!deleteResponse.ok) {
                        throw new Error("Erro ao excluir o evento histórico");
                    }
                    alert("Evento excluído com sucesso");
                    fetchAndDisplayEventosHistoricos(); // Atualiza a tabela após exclusão
                } catch (error) {
                    console.error("Erro ao excluir evento histórico:", error.message);
                }
            });
            actions.appendChild(deleteButton);

            rowElement.appendChild(actions);
            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}


async function fetchAndDisplayMissoes() {
    console.log("Buscando dados da tabela missoes...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    // Cria o botão de Inserir no topo da tabela
    const insertButton = document.createElement("button");
    insertButton.textContent = "Inserir Nova Missão";
    insertButton.addEventListener("click", function() {
        alert("Abrir formulário de inserção de nova missão"); // Substitua com a lógica de inserção
    });
    dataTableContainer.appendChild(insertButton);

    try {
        const response = await fetch("http://localhost:8080/missoes");

        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela missoes");
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

        // Coluna de ações
        const thActions = document.createElement("th");
        thActions.textContent = "Ações";
        headerRow.appendChild(thActions);
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

            const actions = document.createElement("td");
            const editButton = document.createElement("button");
            editButton.textContent = "Editar";
            editButton.addEventListener("click", function() {
                alert("Editar missão");
            });
            actions.appendChild(editButton);

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Excluir";
            deleteButton.addEventListener("click", async function() {
                try {
                    const deleteResponse = await fetch(`http://localhost:8080/missoes/${missao.missoesId}`, {
                        method: "DELETE"
                    });

                    if (!deleteResponse.ok) {
                        throw new Error("Erro ao excluir a missão");
                    }
                    alert("Missão excluída com sucesso");
                    fetchAndDisplayMissoes(); // Atualiza a tabela após exclusão
                } catch (error) {
                    console.error("Erro ao excluir missão:", error.message);
                }
            });
            actions.appendChild(deleteButton);

            rowElement.appendChild(actions);
            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}


async function fetchAndDisplayParticipacaoEventos() {
    console.log("Buscando dados da tabela participacaoEventos...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    // Cria o botão de Inserir no topo da tabela
    const insertButton = document.createElement("button");
    insertButton.textContent = "Inserir Nova Participação de Evento";
    insertButton.addEventListener("click", function() {
        alert("Abrir formulário de inserção de nova participação de evento"); // Substitua com a lógica de inserção
    });
    dataTableContainer.appendChild(insertButton);

    try {
        const response = await fetch("http://localhost:8080/api/participacao-evento");

        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela participacaoEventos");
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
        ["Herói", "Vilão", "Evento"].forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        // Coluna de ações
        const thActions = document.createElement("th");
        thActions.textContent = "Ações";
        headerRow.appendChild(thActions);
        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(participacao => {
            const rowElement = document.createElement("tr");

            const heroiTd = document.createElement("td");
            heroiTd.textContent = participacao.heroi ? participacao.heroi.nome : "N/A"; // Substitua 'nome' pelo atributo relevante do herói
            rowElement.appendChild(heroiTd);

            const vilaoTd = document.createElement("td");
            vilaoTd.textContent = participacao.vilao ? participacao.vilao.nome : "N/A"; // Substitua 'nome' pelo atributo relevante do vilão
            rowElement.appendChild(vilaoTd);

            const eventoTd = document.createElement("td");
            eventoTd.textContent = participacao.evento ? participacao.evento.nomeEventos : "N/A"; // Substitua 'nomeEventos' pelo atributo relevante do evento
            rowElement.appendChild(eventoTd);

            const actions = document.createElement("td");
            const editButton = document.createElement("button");
            editButton.textContent = "Editar";
            editButton.addEventListener("click", function() {
                alert("Editar participação de evento");
            });
            actions.appendChild(editButton);

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Excluir";
            deleteButton.addEventListener("click", async function() {
                try {
                    const deleteResponse = await fetch(`http://localhost:8080/api/participacao-evento/${participacao.participacaoEventoId}`, {
                        method: "DELETE"
                    });

                    if (!deleteResponse.ok) {
                        throw new Error("Erro ao excluir a participação de evento");
                    }
                    alert("Participação excluída com sucesso");
                    fetchAndDisplayParticipacaoEventos(); // Atualiza a tabela após exclusão
                } catch (error) {
                    console.error("Erro ao excluir participação:", error.message);
                }
            });
            actions.appendChild(deleteButton);

            rowElement.appendChild(actions);
            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}


async function fetchAndDisplayParticipacaoHerois() {
    console.log("Buscando dados da tabela participacaoHerois...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    // Cria o botão de Inserir no topo da tabela
    const insertButton = document.createElement("button");
    insertButton.textContent = "Inserir Nova Participação de Herói";
    insertButton.addEventListener("click", function() {
        alert("Abrir formulário de inserção de nova participação de herói"); // Substitua com a lógica de inserção
    });
    dataTableContainer.appendChild(insertButton);

    try {
        const response = await fetch("http://localhost:8080/api/participacao-herois");

        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela participacaoHerois");
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
        ["Herói", "Missão"].forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        // Coluna de ações
        const thActions = document.createElement("th");
        thActions.textContent = "Ações";
        headerRow.appendChild(thActions);
        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(participacao => {
            const rowElement = document.createElement("tr");

            const heroiTd = document.createElement("td");
            heroiTd.textContent = participacao.heroi ? participacao.heroi.nome : "N/A"; // Substitua 'nome' pelo atributo relevante do herói
            rowElement.appendChild(heroiTd);

            const missaoTd = document.createElement("td");
            missaoTd.textContent = participacao.missao ? participacao.missao.nomeMissoes : "N/A"; // Substitua 'nomeMissoes' pelo atributo relevante da missão
            rowElement.appendChild(missaoTd);

            const actions = document.createElement("td");
            const editButton = document.createElement("button");
            editButton.textContent = "Editar";
            editButton.addEventListener("click", function() {
                alert("Editar participação de herói");
            });
            actions.appendChild(editButton);

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Excluir";
            deleteButton.addEventListener("click", async function() {
                try {
                    const deleteResponse = await fetch(`http://localhost:8080/api/participacao-herois/${participacao.participacaoId}`, {
                        method: "DELETE"
                    });

                    if (!deleteResponse.ok) {
                        throw new Error("Erro ao excluir a participação de herói");
                    }
                    alert("Participação excluída com sucesso");
                    fetchAndDisplayParticipacaoHerois(); // Atualiza a tabela após exclusão
                } catch (error) {
                    console.error("Erro ao excluir participação:", error.message);
                }
            });
            actions.appendChild(deleteButton);

            rowElement.appendChild(actions);
            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}


async function fetchAndDisplayRecursos() {
    console.log("Buscando dados da tabela recursos...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    // Cria o botão de Inserir no topo da tabela
    const insertButton = document.createElement("button");
    insertButton.textContent = "Inserir Novo Recurso";
    insertButton.addEventListener("click", function() {
        alert("Abrir formulário de inserção de novo recurso"); // Substitua com a lógica de inserção
    });
    dataTableContainer.appendChild(insertButton);

    try {
        const response = await fetch("http://localhost:8080/recursos");

        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela recursos");
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
        ["Tipo", "Nome", "Disponibilidade", "Usuário", "Localização"].forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        // Coluna de ações
        const thActions = document.createElement("th");
        thActions.textContent = "Ações";
        headerRow.appendChild(thActions);
        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(recurso => {
            const rowElement = document.createElement("tr");

            const tipoTd = document.createElement("td");
            tipoTd.textContent = recurso.tipoRecurso || "N/A";
            rowElement.appendChild(tipoTd);

            const nomeTd = document.createElement("td");
            nomeTd.textContent = recurso.nomeRecurso || "N/A";
            rowElement.appendChild(nomeTd);

            const disponibilidadeTd = document.createElement("td");
            disponibilidadeTd.textContent = recurso.disponibilidadeRecurso || "N/A";
            rowElement.appendChild(disponibilidadeTd);

            const usuarioTd = document.createElement("td");
            usuarioTd.textContent = recurso.usuarioRecurso || "N/A";
            rowElement.appendChild(usuarioTd);

            const localizacaoTd = document.createElement("td");
            localizacaoTd.textContent = recurso.localizacaoRecurso || "N/A";
            rowElement.appendChild(localizacaoTd);

            const actions = document.createElement("td");
            const editButton = document.createElement("button");
            editButton.textContent = "Editar";
            editButton.addEventListener("click", function() {
                alert("Editar recurso");
            });
            actions.appendChild(editButton);

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Excluir";
            deleteButton.addEventListener("click", async function() {
                try {
                    const deleteResponse = await fetch(`http://localhost:8080/recursos/${recurso.recursoId}`, {
                        method: "DELETE"
                    });

                    if (!deleteResponse.ok) {
                        throw new Error("Erro ao excluir o recurso");
                    }
                    alert("Recurso excluído com sucesso");
                    fetchAndDisplayRecursos(); // Atualiza a tabela após exclusão
                } catch (error) {
                    console.error("Erro ao excluir recurso:", error.message);
                }
            });
            actions.appendChild(deleteButton);

            rowElement.appendChild(actions);
            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}


async function fetchAndDisplayUtilizacaoRecursos() {
    console.log("Buscando dados da tabela utilizacaoRecursos...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    // Cria o botão de Inserir no topo da tabela
    const insertButton = document.createElement("button");
    insertButton.textContent = "Registrar Nova Utilização de Recurso";
    insertButton.addEventListener("click", function() {
        alert("Abrir formulário de inserção de nova utilização de recurso"); // Substitua com a lógica de inserção
    });
    dataTableContainer.appendChild(insertButton);

    try {
        const response = await fetch("http://localhost:8080/api/utilizacao-recursos");

        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela utilizacaoRecursos");
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
        ["Herói", "Recurso"].forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        // Coluna de ações
        const thActions = document.createElement("th");
        thActions.textContent = "Ações";
        headerRow.appendChild(thActions);
        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(utilizacao => {
            const rowElement = document.createElement("tr");

            const heroiTd = document.createElement("td");
            heroiTd.textContent = utilizacao.heroi ? utilizacao.heroi.nome : "N/A"; // Substitua 'nome' pelo atributo relevante do herói
            rowElement.appendChild(heroiTd);

            const recursoTd = document.createElement("td");
            recursoTd.textContent = utilizacao.recurso ? utilizacao.recurso.nomeRecurso : "N/A"; // Substitua 'nomeRecurso' pelo atributo relevante do recurso
            rowElement.appendChild(recursoTd);

            const actions = document.createElement("td");
            const editButton = document.createElement("button");
            editButton.textContent = "Editar";
            editButton.addEventListener("click", function() {
                alert("Editar utilização de recurso");
            });
            actions.appendChild(editButton);

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Excluir";
            deleteButton.addEventListener("click", async function() {
                try {
                    const deleteResponse = await fetch(`http://localhost:8080/api/utilizacao-recursos/${utilizacao.utilizacaoId}`, {
                        method: "DELETE"
                    });

                    if (!deleteResponse.ok) {
                        throw new Error("Erro ao excluir a utilização de recurso");
                    }
                    alert("Utilização excluída com sucesso");
                    fetchAndDisplayUtilizacaoRecursos(); // Atualiza a tabela após exclusão
                } catch (error) {
                    console.error("Erro ao excluir utilização de recurso:", error.message);
                }
            });
            actions.appendChild(deleteButton);

            rowElement.appendChild(actions);
            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}


async function fetchAndDisplayViloes() {
    console.log("Buscando dados da tabela viloes...");
    const dataTableContainer = document.getElementById("data-table-container");
    dataTableContainer.innerHTML = "";

    // Cria o botão de Inserir no topo da tabela
    const insertButton = document.createElement("button");
    insertButton.textContent = "Inserir Novo Vilão";
    insertButton.addEventListener("click", function() {
        alert("Abrir formulário de inserção de novo vilão"); // Substitua com a lógica de inserção
    });
    dataTableContainer.appendChild(insertButton);

    try {
        const response = await fetch("http://localhost:8080/viloes");

        if (!response.ok) {
            throw new Error("Erro ao buscar dados da tabela viloes");
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
        ["Nome", "Títulos", "Poderes", "Motivação", "Status", "Localização"].forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        // Coluna de ações
        const thActions = document.createElement("th");
        thActions.textContent = "Ações";
        headerRow.appendChild(thActions);
        thead.appendChild(headerRow);

        // Linhas de dados
        data.forEach(vilao => {
            const rowElement = document.createElement("tr");

            const nomeTd = document.createElement("td");
            nomeTd.textContent = vilao.nomeViloes || "N/A";
            rowElement.appendChild(nomeTd);

            const titulosTd = document.createElement("td");
            titulosTd.textContent = vilao.titulosViloes || "N/A";
            rowElement.appendChild(titulosTd);

            const poderesTd = document.createElement("td");
            poderesTd.textContent = vilao.poderesViloes || "N/A";
            rowElement.appendChild(poderesTd);

            const motivacaoTd = document.createElement("td");
            motivacaoTd.textContent = vilao.motivacaoViloes || "N/A";
            rowElement.appendChild(motivacaoTd);

            const statusTd = document.createElement("td");
            statusTd.textContent = vilao.statusViloes || "N/A";
            rowElement.appendChild(statusTd);

            const localizacaoTd = document.createElement("td");
            localizacaoTd.textContent = vilao.localizacaoViloes || "N/A";
            rowElement.appendChild(localizacaoTd);

            const actions = document.createElement("td");
            const editButton = document.createElement("button");
            editButton.textContent = "Editar";
            editButton.addEventListener("click", function() {
                alert("Editar vilão");
            });
            actions.appendChild(editButton);

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Excluir";
            deleteButton.addEventListener("click", async function() {
                try {
                    const deleteResponse = await fetch(`http://localhost:8080/viloes/${vilao.viloesId}`, {
                        method: "DELETE"
                    });

                    if (!deleteResponse.ok) {
                        throw new Error("Erro ao excluir o vilão");
                    }
                    alert("Vilão excluído com sucesso");
                    fetchAndDisplayViloes(); // Atualiza a tabela após exclusão
                } catch (error) {
                    console.error("Erro ao excluir vilão:", error.message);
                }
            });
            actions.appendChild(deleteButton);

            rowElement.appendChild(actions);
            tbody.appendChild(rowElement);
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        dataTableContainer.appendChild(table);
    } catch (error) {
        dataTableContainer.innerHTML += `<p>Erro ao carregar dados: ${error.message}</p>`;
    }
}



// Exemplo de função fetch para outra tabela (implementação similar à de Herois)
async function fetchAndDisplayMissoes() {
    console.log("Buscando dados da tabela missoes...");
    // Similar logic to fetch and display data for Missoes
    // Fetch URL, structure table headers, and populate rows
}

function exibirFormularioNovoHeroi() {
    const dataTableContainer = document.getElementById("data-table-container");

    if (document.getElementById("form-novo-heroi")) {
        return;
    }

    const form = document.createElement("form");
    form.id = "form-novo-heroi";

    // Cria os campos do formulário
    ["Nome", "Codinome", "Afiliacao", "Status", "Localizacao", "Poder"].forEach(field => {
        const input = document.createElement("input");
        input.type = "text";
        input.placeholder = field;
        input.name = field.toLowerCase(); // Nome deve corresponder ao DTO no backend
        form.appendChild(input);
    });

    const submitButton = document.createElement("button");
    submitButton.type = "button";
    submitButton.textContent = "Salvar";
    form.appendChild(submitButton);

    submitButton.addEventListener("click", async function () {
        const heroData = {
            nome: form.querySelector("[name=nome]").value,
            codinome: form.querySelector("[name=codinome]").value,
            afiliacao: form.querySelector("[name=afiliacao]").value,
            status: form.querySelector("[name=status]").value,
            localizacao: form.querySelector("[name=localizacao]").value,
            poder: form.querySelector("[name=poder]").value
        };

        try {
            const response = await fetch("http://localhost:8080/herois", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(heroData),
            });

            if (response.ok) {
                alert("Herói salvo com sucesso!");
                form.reset();
                fetchAndDisplayHerois(); // Atualiza a tabela após a inserção
            } else {
                const errorText = await response.text();
                console.error("Erro ao salvar o herói:", errorText);
                alert("Erro ao salvar o herói. Verifique os dados e tente novamente.");
            }
        } catch (error) {
            console.error("Erro ao enviar dados:", error);
            alert("Erro ao conectar com o servidor.");
        }
    });

    dataTableContainer.appendChild(form);
}
async function deleteHero(heroId) {
    try {
        const response = await fetch(`http://localhost:8080/herois/${heroId}`, {
            method: "DELETE"
        });
        if (response.ok) {
            alert("Herói excluído com sucesso!");
        } else {
            alert("Erro ao excluir o herói.");
        }
    } catch (error) {
        console.error("Erro ao excluir herói:", error.message);
    }
}

// Função para exibir o formulário de herói, usado para inserção e edição
function exibirFormularioHeroi(hero = null) {
    const dataTableContainer = document.getElementById("data-table-container");

    // Remove formulário existente, se houver
    const existingForm = document.getElementById("form-heroi");
    if (existingForm) existingForm.remove();

    // Cria o formulário
    const form = document.createElement("form");
    form.id = "form-heroi";

    // Cria os campos do formulário e preenche com os valores do herói para edição
    ["Nome", "Codinome", "Afiliacao", "Status", "Localizacao", "Poder"].forEach(field => {
        const input = document.createElement("input");
        input.type = "text";
        input.placeholder = field;
        input.name = field.toLowerCase();
        input.value = hero ? hero[field.toLowerCase()] : ""; // Preenche os valores se for edição
        form.appendChild(input);
    });

    const submitButton = document.createElement("button");
    submitButton.type = "button";
    submitButton.textContent = hero ? "Salvar Alterações" : "Salvar"; // Altera o texto do botão conforme a ação
    form.appendChild(submitButton);

    // Define o comportamento do botão para inserir ou atualizar
    submitButton.addEventListener("click", async function () {
        const heroData = {
            nome: form.querySelector("[name=nome]").value,
            codinome: form.querySelector("[name=codinome]").value,
            afiliacao: form.querySelector("[name=afiliacao]").value,
            status: form.querySelector("[name=status]").value,
            localizacao: form.querySelector("[name=localizacao]").value,
            poder: form.querySelector("[name=poder]").value
        };

        try {
            let response;
            if (hero) {
                // Atualiza o herói existente
                response = await fetch(`http://localhost:8080/herois/${hero.id}`, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(heroData),
                });
            } else {
                // Cria um novo herói
                response = await fetch("http://localhost:8080/herois", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(heroData),
                });
            }

            if (response.ok) {
                alert(hero ? "Herói atualizado com sucesso!" : "Herói salvo com sucesso!");
                form.reset();
                fetchAndDisplayHerois(); // Atualiza a tabela após a inserção ou edição
            } else {
                const errorText = await response.text();
                console.error("Erro ao salvar o herói:", errorText);
                alert("Erro ao salvar o herói. Verifique os dados e tente novamente.");
            }
        } catch (error) {
            console.error("Erro ao enviar dados:", error);
            alert("Erro ao conectar com o servidor.");
        }
    });

    dataTableContainer.appendChild(form);
}

// Função para exibir o formulário com os dados preenchidos ao editar
function exibirFormularioEdicaoHeroi(hero) {
    exibirFormularioHeroi(hero);
}



// Exemplo de chamada para exibir o formulário de novo herói
document.getElementById("botao-inserir-heroi").addEventListener("click", () => exibirFormularioHeroi());

// Exemplo de chamada para exibir o formulário de edição de herói ao clicar no botão de edição
// editButton.addEventListener("click", function () { exibirFormularioEdicaoHeroi(hero); });


// Chamando a função ao clicar no botão de inserir
document.getElementById("botao-inserir-heroi").addEventListener("click", exibirFormularioNovoHeroi);

// Função para exibir o formulário de nova base
function exibirFormularioNovaBase() {
    const dataTableContainer = document.getElementById("data-table-container");

    // Verifica se o formulário já existe
    if (document.getElementById("form-nova-base")) {
        return;
    }

    const form = document.createElement("form");
    form.id = "form-nova-base";

    // Cria os campos do formulário
    ["Nome Base", "Localização", "Propósito", "Capacidade", "Status", "Comandante"].forEach(field => {
        const input = document.createElement("input");
        input.type = "text";
        input.placeholder = field;
        input.name = field.replace(" ", "").toLowerCase(); // Ajusta para o nome do campo no backend
        form.appendChild(input);
    });

    const submitButton = document.createElement("button");
    submitButton.type = "button";
    submitButton.textContent = "Salvar";
    form.appendChild(submitButton);

    submitButton.addEventListener("click", async function () {
        const baseData = {
            nomeBase: form.querySelector("[name=nomebase]").value,
            localizacaoBase: form.querySelector("[name=localizacao]").value,
            propositoBase: form.querySelector("[name=proposito]").value,
            capacidadeBase: form.querySelector("[name=capacidade]").value,
            statusBase: form.querySelector("[name=status]").value,
            comandanteBase: form.querySelector("[name=comandante]").value
        };

        try {
            const response = await fetch("http://localhost:8080/bases", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(baseData),
            });

            if (response.ok) {
                alert("Base salva com sucesso!");
                form.reset();
                fetchAndDisplayBases(); // Atualiza a tabela após a inserção
            } else {
                const errorText = await response.text();
                console.error("Erro ao salvar a base:", errorText);
                alert("Erro ao salvar a base. Verifique os dados e tente novamente.");
            }
        } catch (error) {
            console.error("Erro ao conectar com o servidor:", error.message);
            alert("Erro ao conectar com o servidor.");
        }
    });

    dataTableContainer.appendChild(form);
}

// Função para deletar uma base
async function deleteBase(baseId) {
    try {
        const response = await fetch(`http://localhost:8080/bases/${baseId}`, {
            method: "DELETE"
        });
        if (response.ok) {
            alert("Base excluída com sucesso!");
            fetchAndDisplayBases(); // Atualiza a tabela após a exclusão
        } else {
            alert("Erro ao excluir a base.");
        }
    } catch (error) {
        console.error("Erro ao excluir base:", error.message);
    }
}

// Função para exibir o formulário de edição de base
function exibirFormularioEdicaoBase(base) {
    const dataTableContainer = document.getElementById("data-table-container");

    // Remove qualquer formulário de edição existente antes de criar um novo
    if (document.getElementById("form-edicao-base")) {
        document.getElementById("form-edicao-base").remove();
    }

    const form = document.createElement("form");
    form.id = "form-edicao-base";

    ["Nome Base", "Localização", "Propósito", "Capacidade", "Status", "Comandante"].forEach(field => {
        const input = document.createElement("input");
        input.type = "text";
        input.placeholder = field;
        input.name = field.replace(" ", "").toLowerCase();
        input.value = base[input.name] || "";
        form.appendChild(input);
    });

    const submitButton = document.createElement("button");
    submitButton.type = "button";
    submitButton.textContent = "Atualizar";
    form.appendChild(submitButton);

    submitButton.addEventListener("click", async function () {
        const baseData = {
            nomeBase: form.querySelector("[name=nomebase]").value,
            localizacaoBase: form.querySelector("[name=localizacao]").value,
            propositoBase: form.querySelector("[name=proposito]").value,
            capacidadeBase: form.querySelector("[name=capacidade]").value,
            statusBase: form.querySelector("[name=status]").value,
            comandanteBase: form.querySelector("[name=comandante]").value
        };

        try {
            const response = await fetch(`http://localhost:8080/bases/${base.baseId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(baseData),
            });

            if (response.ok) {
                alert("Base atualizada com sucesso!");
                fetchAndDisplayBases(); // Atualiza a tabela após a atualização
            } else {
                const errorText = await response.text();
                console.error("Erro ao atualizar a base:", errorText);
                alert("Erro ao atualizar a base. Verifique os dados e tente novamente.");
            }
        } catch (error) {
            console.error("Erro ao conectar com o servidor:", error.message);
            alert("Erro ao conectar com o servidor.");
        }
    });

    dataTableContainer.appendChild(form);
}

// Chamada para exibir o formulário de nova base
document.getElementById("botao-inserir-base").addEventListener("click", exibirFormularioNovaBase);

// Exemplo de chamada para exibir o formulário de edição de base ao clicar no botão de edição
// editButton.addEventListener("click", function () { exibirFormularioEdicaoBase(base); });

document.getElementById("botao-inserir-base").addEventListener("click", exibirFormularioEdicaoBase);