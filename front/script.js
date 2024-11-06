async function login(username, password, endpoint) {
  console.log("Tentando login com:", username, password);

  try {
      const response = await fetch(`http://localhost:8080/entrar/${endpoint}`, { 
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify({ username: username, password: password }),
      });

      if (response.ok) {
          console.log("Login bem-sucedido");
          // Redirecionar para a página apropriada
          window.location.href = endpoint === "admin" ? 'admPage.html' : 'userPage.html';
      } else {
          console.error("Erro no login. Acesso negado.");
          alert("Usuário ou senha incorretos, ou acesso negado!");
      }
  } catch (error) {
      console.error("Erro na requisição de login:", error);
  }
}

// Adicionar event listeners aos formulários
document.getElementById('admin-login-form').addEventListener('submit', function(event) {
  event.preventDefault();
  const username = document.getElementById('adm-username').value;
  const password = document.getElementById('adm-password').value;
  login(username, password, 'admin');
});

document.getElementById('visitor-login-form').addEventListener('submit', function(event) {
  event.preventDefault();
  const username = document.getElementById('visit-username').value;
  const password = document.getElementById('visit-password').value;
  login(username, password, 'visitor');
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

// Função para finalizar a missão
function finalizarMissao(missaoId) {
    console.log("Botão clicado, iniciando requisição..."); // Log adicional para confirmar o clique
  
    fetch(`http://localhost:8080/missaoService`, {
      method: 'POST',
    })
      .then(response => response.text())
      .then(message => {
        console.log(message); // Exibe mensagem de sucesso no console
        alert(message); // Exibe uma mensagem de alerta para o usuário
      })
      .catch(error => {
        console.error('Erro ao finalizar a missão:', error);
      });
  }
  
  
  // Função para obter os detalhes das missões
  function obterDetalhesMissoes() {
    fetch('http://localhost:8080/missoes/detalhes')
      .then(response => response.json())
      .then(detalhesMissoes => {
        console.log(detalhesMissoes); // Exibe os detalhes das missões no console
        // Aqui você pode manipular os dados para exibir na página, se desejar
      })
      .catch(error => {
        console.error('Erro ao obter detalhes das missões:', error);
      });

      function obterDetalhesMissoes() {
        console.log("Função obterDetalhesMissoes chamada"); // Log para verificar o clique
      
        fetch('http://localhost:8080/missoes/detalhes')
          .then(response => {
            if (!response.ok) {
              throw new Error(`Erro na requisição: ${response.statusText}`);
            }
            return response.json();
          })
          .then(detalhesMissoes => {
            console.log("Dados recebidos:", detalhesMissoes); // Log dos dados recebidos
      
            // Local onde a tabela será inserida
            const detalhesContainer = document.getElementById('detalhes-missoes');
      
            // Cria a tabela HTML
            let tabelaHtml = `
              <table border="1">
                <thead>
                  <tr>
                    <th>Nome da Missão</th>
                    <th>Descrição</th>
                    <th>Data de Início</th>
                    <th>Data de Término</th>
                    <th>Status</th>
                    <th>Herói Envolvido</th>
                    <th>Vilão Envolvido</th>
                  </tr>
                </thead>
                <tbody>
            `;
      
            // Adiciona cada missão como uma linha na tabela
            detalhesMissoes.forEach(missao => {
              tabelaHtml += `
                <tr>
                  <td>${missao[0]}</td>
                  <td>${missao[1]}</td>
                  <td>${new Date(missao[2]).toLocaleDateString()}</td>
                  <td>${new Date(missao[3]).toLocaleDateString()}</td>
                  <td>${missao[4]}</td>
                  <td>${missao[5]}</td>
                  <td>${missao[6]}</td>
                </tr>
              `;
            });
      
            // Fecha a tabela
            tabelaHtml += `
                </tbody>
              </table>
            `;
      
            // Insere a tabela no HTML
            detalhesContainer.innerHTML = tabelaHtml;
          })
          .catch(error => {
            console.error('Erro ao obter detalhes das missões:', error);
          });
      }
 
      
      function desativarERealocar() {
        console.log("Iniciando desativação da base e realocação de recursos...");
        
        fetch('http://localhost:8080/missoes/desativar-e-realocar', {
          method: 'POST'
        })
          .then(response => response.text())
          .then(message => {
            console.log(message);
            alert(message); // Exibe a mensagem de sucesso ou erro para o usuário
          })
          .catch(error => {
            console.error('Erro ao desativar a base e realocar recursos:', error);
          });
      }
      
      
  }



// Função para buscar e exibir as fotos do herói com base no ID digitado no campo de busca
async function fetchHeroPhotos() {
  const heroId = document.getElementById("heroiIdSearch").value;
  const heroImageContainer = document.getElementById("hero-image-container");

  if (!heroId) {
      alert("Por favor, insira um ID de herói válido.");
      return;
  }

  try {
      const response = await fetch(`http://localhost:8080/fotos/heroi/${heroId}`);
      const photos = await response.json();

      // Limpa as imagens anteriores
      heroImageContainer.innerHTML = '';

      if (photos.length > 0) {
          photos.forEach(photo => {
              const img = document.createElement("img");
              img.src = `data:image/jpeg;base64,${photo.imagemBase64}`; // Define o src com a imagem em Base64
              img.style.width = "150px";
              img.style.height = "150px";
              img.style.margin = "5px";
              heroImageContainer.appendChild(img);
          });
      } else {
          heroImageContainer.innerHTML = "<p>Nenhuma foto encontrada para este herói.</p>";
      }
  } catch (error) {
      console.error("Erro ao carregar fotos do herói:", error.message);
      heroImageContainer.innerHTML = "<p>Erro ao carregar as fotos.</p>";
  }
}


// Função para fazer o upload da foto para o herói com o ID fornecido no campo de upload
async function uploadFoto(event) {
  event.preventDefault();

  const formData = new FormData(document.getElementById("uploadFotoForm"));
  const heroId = document.getElementById("heroiIdUpload").value; // Reutiliza o ID do herói para o upload

  if (!heroId) {
      alert("Por favor, insira um ID de herói válido para o upload.");
      return;
  }

  formData.append("heroiId", heroId); // Adiciona o ID do herói ao FormData

  try {
      const response = await fetch("http://localhost:8080/fotos/upload", {
          method: "POST",
          body: formData
      });

      if (response.ok) {
          alert("Foto enviada com sucesso!");
          document.getElementById("uploadFotoForm").reset(); // Limpa o formulário após o upload
          fetchHeroPhotos(); // Atualiza a lista de fotos, caso você queira ver a nova foto na busca
      } else {
          alert("Erro ao enviar a foto.");
      }
  } catch (error) {
      console.error("Erro ao enviar a foto:", error.message);
  }
}

// Adiciona evento para o formulário de upload
document.getElementById("uploadFotoForm").addEventListener("submit", uploadFoto);
