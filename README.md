# DEI Academic Management System (AMS)

## Visão Geral
O DEI Academic Management System (AMS) é uma plataforma web para a gestão académica do Departamento de Engenharia Informática (DEI), permitindo a administração centralizada de Unidades Curriculares (UCs), pessoas, avaliações, projetos, recursos e calendário. O sistema foi desenvolvido como exercício prático, seguindo o enunciado oficial.

## Funcionalidades Principais
- Gestão de pessoas (alunos, professores regentes, assistentes, administradores)
- Gestão de cursos e unidades curriculares
- Gestão de avaliações (testes e projetos)
- Submissão e revisão de provas
- Upload/download de recursos (enunciados, materiais)
- Calendário de avaliações com deteção de conflitos
- Visualização de estatísticas e dashboards
- Histórico de submissões de projetos

## Estrutura do Projeto
```
├── enunciado.md                # Enunciado oficial do exercício
├── package.json                # Dependências de topo
├── src/
│   ├── backend/                # Backend Spring Boot (Java)
│   │   ├── pom.xml             # Configuração Maven
│   │   └── ...
│   ├── frontend/               # Frontend Vue 3 + Vite
│   │   ├── package.json        # Dependências do frontend
│   │   └── ...
│   ├── docker-compose.yml      # Orquestração de serviços (DB)
│   ├── populate.sql            # Dump para popular a base de dados
│   └── README.md               # Instruções detalhadas (ver src/README.md)
└── README.md                   # Este ficheiro
```

## Instalação e Execução
### Pré-requisitos
- Java 21+
- Maven
- Node.js 14+
- Docker (opcional, recomendado para a base de dados)

### 1. Clonar o repositório
```bash
git clone <REPO_URL>
cd dei-ams/src
```

### 2. Base de Dados (PostgreSQL via Docker)
```bash
docker compose up
```
- A base de dados ficará disponível em `localhost:7654` (user: postgres, db: deidb)
- Para parar: `docker compose down`

### 3. Backend (Spring Boot)
```bash
cd backend
cp src/main/resources/application.properties.example src/main/resources/application.properties
mvn clean spring-boot:run
```

Se não quiser a população automática da base de dados ao iniciar a aplicação, basta definir a propriedade `spring.jpa.hibernate.ddl-auto` como `create` no ficheiro `application.properties`.

### 4. Frontend (Vue 3 + Vite)
```bash
cd frontend
cp example.env .env
npm install
npm run dev
```

## Aceder à Aplicação
- Frontend: http://localhost:5173
- Backend API: http://localhost:8080

## Estrutura das Pastas
- `src/backend/` — Backend Java Spring Boot
- `src/frontend/` — Frontend Vue 3 + Vite
- `src/docker-compose.yml` — Orquestração da base de dados
- `src/populate.sql` — Dados de teste para a base de dados

## Documentação e Enunciado
Consulte o ficheiro `enunciado.md` para os requisitos detalhados e regras de gestão do sistema.

## Funcionalidades Extra (para nota superior)
- Submissões múltiplas de projetos com histórico
- Estatísticas avançadas (incrições em unidades curriculares, progresso de revisões de prova)

---
> Projeto académico para a disciplina de AMS @ DEI/IST. Para mais detalhes, consulte o enunciado oficial.
