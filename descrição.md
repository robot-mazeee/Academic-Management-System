# AMS: Academic Management System

O **Academic Management System** (ou _AMS_) é uma plataforma que permite a gestão centralizada das Unidades Curriculares (UCs), facilitando a organização de alunos, professores, avaliações e projetos.

Esta plataforma permite a gestão de:

- **Pessoas**, incluindo, para cada uma, pelo menos o nome, IST ID, email e o seu tipo no sistema (aluno, professor regente, professor assistente, administrador, ou outros tipos que considere relevantes);
- **Cursos**, incluindo código e nome;
- **Unidades Curriculares**, incluindo código, nome, semestre, ECTS e curso(s) a que pertence(m);
- **Avaliações**, organizadas em duas secções: testes e projetos;
- **Recursos**, incluindo enunciados, materiais de estudo e documentos associados a cada UC;
- **Calendário**, com agendamento de avaliações e gestão de prazos.

## Estrutura e Regras de Gestão

- Cada **Unidade Curricular** deve ter **um único Professor Regente**, responsável pela sua gestão.
- O Professor Regente pode associar **Professores Assistentes** e **Alunos** à UC, bem como definir as suas permissões.
- Os Professores Assistentes podem atribuir avaliações e notas aos alunos, mas não podem alterar a composição da UC.
- Os Alunos podem visualizar as suas avaliações, submeter projetos e pedir revisões de prova.

## Avaliações

### Testes
- Cada UC pode ter vários testes, definidos pelo Professor Regente (título, data, peso na nota final).
- Professores Regentes e Professores Assistentes podem:
  - Atribuir notas a cada aluno;
  - Fazer upload do teste individual corrigido em formato PDF, para posterior consulta pelo aluno;
  - Fazer upload do enunciado do teste para consulta posterior.
- Alunos podem:
  - Consultar as suas notas;
  - Submeter pedidos de revisão de prova (com justificação e prazo limite);
  - Visualizar testes corrigidos quando disponibilizados.

### Projetos
- Cada UC pode ter um ou mais projetos, definidos pelo Professor Regente (título, prazo de submissão, peso na nota final).
- Projetos podem ser **individuais ou em grupo**:
  - Para projetos em grupo, o Professor Regente define o tamanho máximo dos grupos;
  - De modo a simplificar a implementação, os grupos podem ser atribuídos automaticamente e aleatoriamente;
  - Submissões são feitas pelo grupo, assim como as suas notas finais.
- Alunos podem submeter ficheiros de projeto (ex: `.py`, `.c`, `.zip`) através da aplicação.
- Professores Regentes e Professores Assistentes podem atribuir notas ao projeto.
- Opcionalmente, cada submissão de projeto pode desencadear uma avaliação automática por parte do backend, que atribui uma **nota provisória**. Esta nota pode depois ser confirmada ou ajustada pelo professor.

## Workflow de Revisão de Provas

O sistema implementa um workflow estruturado para revisão de provas:

1. **Estudante solicita revisão** - Submete pedido com justificação dentro do prazo limite
2. **Professor Assistente analisa** - Revê o pedido e emite parecer inicial (se aplicável)
3. **Professor Regente decide** - Toma a decisão final (aprovar/rejeitar)
4. **Histórico mantido** - Todas as decisões de revisão ficam registadas

## Gestão de Recursos

O sistema permite a gestão centralizada de materiais por UC:

- **Upload de enunciados** de testes e projetos pelos professores
- **Biblioteca de materiais** de estudo organizados por UC

## Calendário de Avaliações

- **Agendamento** de testes e prazos de entrega de projetos
- **Deteção automática de conflitos** entre as várias UCs

## Recursos

- [https://vuetifyjs.com/en/](https://vuetifyjs.com/en/)
- [https://spring.io/guides](https://spring.io/guides)
- [https://www.javatpoint.com/spring-boot-architecture](https://www.javatpoint.com/spring-boot-architecture)
