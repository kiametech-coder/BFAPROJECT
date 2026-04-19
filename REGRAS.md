# Regras de Colaboração – Trabalho de Grupo

Este documento define **obrigatoriamente** como os 5 membros da equipa devem colaborar no repositório. O não cumprimento pode causar conflitos, perda de código e atrasos.

## 1. Acesso e permissões

- Todos os membros têm permissão **Write**.
- A branch `main` está **protegida**: commits diretos estão bloqueados.
- Qualquer alteração só entra na `main` através de um **Pull Request (PR)** aprovado.

## 2. Fluxo obrigatório para cada tarefa

Para cada nova funcionalidade, correção ou documento:

1. **Atualizar a `main` local**  
   ```bash
   git checkout main
   git pull origin main
   
2. **Criar uma branch com nome descritivo**
   ```bash
   git checkout -b feature/nome-da-tarefa   # ou fix/, docs/, refactor/
   
3. **Fazer commits pequenos e frequentes**
   - Mensagens claras: git commit -m "Adiciona validação do email"
   - Evitar commits gigantes com 500+ linhas.
  
4. **Subir a branch para o GitHub**
   ```bash
   git push origin feature/nome-da-tarefa
   
5. **Abrir um Pull Request (PR) no GitHub**
   - Base: main ← Compare: feature/nome-da-tarefa
   - Título e descrição explicando o que foi feito
   - Solicitar revisão a pelo menos um colega

6. **Aguardar aprovação – só depois de aprovado, o revisor (não o autor) faz o merge.**
      - ❌ Proibido: Fazer merge do próprio PR.
      - ❌ Proibido: Trabalhar diretamente na branch main.







