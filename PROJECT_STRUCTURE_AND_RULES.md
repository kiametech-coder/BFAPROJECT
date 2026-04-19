# Estrutura do Projeto e Regras de Codificação (Apache Ant)

## 1. Estrutura de Diretórios (Ant)

Utilizaremos o **Apache Ant** para compilar, empacotar e executar a aplicação. A estrutura de diretórios é:

| Diretório/Arquivo | Descrição |
|-------------------|-----------|
| `build.xml` | Script de build do Ant |
| `lib/` | Bibliotecas externas (JavaFX SDK, etc.) |
| `src/com/bfa/solidario/` | Código fonte principal |
| `src/com/bfa/solidario/controller/` | Controladores JavaFX para cada seção |
| `src/com/bfa/solidario/model/` | Classes do modelo de dados |
| `src/com/bfa/solidario/service/` | Lógica de negócio e manipulação de arquivos |
| `src/com/bfa/solidario/util/` | Classes utilitárias |
| `src/com/bfa/solidario/view/` | Arquivos FXML e CSS |
| `resources/` | Recursos adicionais (imagens, etc.) |
| `build/` | Classes compiladas |
| `dist/` | Arquivo JAR final |


**Pacotes principais:**
- `com.bfa.solidario` – raiz da aplicação.
- `controller` – classes que controlam a lógica das telas (FXML controllers).
- `model` – classes de domínio (POJOs) que representam os dados.
- `service` – camada de serviços (persistência, validação, etc.).
- `util` – classes utilitárias (constantes, formatadores, etc.).
- `view` – arquivos FXML e CSS.

---

## 2. Regras de Codificação

### 2.1. Convenções de Nomenclatura

| Elemento              | Regra                                                     | Exemplo                          |
|-----------------------|-----------------------------------------------------------|----------------------------------|
| **Pacotes**           | letras minúsculas, sem underscores                        | `com.bfa.solidario.model`        |
| **Classes**           | PascalCase                                             | `Candidatura`, `GerenciadorArquivo` |
| **Interfaces**        | PascalCase, preferencialmente adjetivos                   | `Validador`, `Persistivel`       |
| **Métodos**           | camelCase, verbo no início                        | `salvarCandidatura()`, `calcularTotal()` |
| **Variáveis**         | camelCase, nomes descritivos                              | `nomeOrganizacao`, `anoInicio`   |
| **Constantes**        | UPPER_SNAKE_CASE, `static final`                          | `TAMANHO_MAX_NOME = 200`         |
| **Parâmetros**        | camelCase                                                 | `String nome`, `int ano`         |
| **Enums**             | PascalCase                                                | `TipoDocumento`, `Provincia`     |
| **Atributos privados**| camelCase, sem prefixo especial                           | `private String nif;`            |

### 2.2. Estilo de Chavetas (Braces)

Utilizamos o estilo **Java padrão** (chaveta de abertura na mesma linha):

```java
public void exemplo() {
    if (condicao) {
        // código
    } else {
        // código
    }
}


2.3. Indentação
4 espaços por nível de indentação.

Não utilizar tabs.

Linhas devem ter no máximo 120 caracteres.

2.4. Comentários
Javadoc obrigatório para classes públicas, interfaces, métodos públicos e campos static final não triviais.

Exemplo de Javadoc:

/**
 * Representa uma candidatura ao programa BFA Solidário.
 * Contém todos os dados do formulário.
 */
public class Candidatura { ... }


Comentários de linha (//) apenas para explicar lógica complexa ou temporária (TODO, FIXME). Evitar comentários óbvios.

Usar // TODO para tarefas pendentes e // FIXME para bugs conhecidos.

2.5. Organização de Código (dentro de uma classe)
A ordem recomendada dentro de uma classe é:

Atributos static final (constantes)

Atributos static

Atributos de instância

Construtores

Métodos públicos

Métodos protegidos / package-private

Métodos privados

Classes internas (se houver)

Exemplo:
public class Exemplo {
    // 1. Constantes
    private static final int LIMITE = 100;

    // 2. Atributos estáticos
    private static int contador;

    // 3. Atributos de instância
    private String nome;
    private int idade;

    // 4. Construtores
    public Exemplo(String nome) {
        this.nome = nome;
    }

    // 5. Métodos públicos
    public void fazerAlgo() { ... }

    // 6. Métodos protegidos
    protected void auxiliar() { ... }

    // 7. Métodos privados
    private void calcular() { ... }
}

2.6. Naming de Ficheiros
Classes Java: mesmo nome da classe + .java.

FXML: nome descritivo + Pane.fxml (ex: SecaoBPane.fxml). Os controllers correspondentes devem ter o sufixo Controller (ex: SecaoBController).

CSS: style.css (ou separado por tela se necessário).

Ficheiro binário de dados: candidaturas.dat (ou extensão .bin).

3. Padrões de Projeto e Arquitetura
3.1. MVC (Model-View-Controller)
Model (model): classes que representam os dados e a lógica de negócio (ex: Candidatura, Contato).

View (view): arquivos FXML que definem a interface gráfica. Podem conter estilização via CSS.

Controller (controller): classes que intermedeiam entre Model e View, respondendo a eventos e atualizando a interface.

3.2. Persistência
A classe GerenciadorArquivo encapsula todas as operações com RandomAccessFile.

Utiliza tamanho fixo para cada campo (codificação UTF-8) para permitir acesso aleatório.

Um registo no ficheiro representa uma Candidatura. A posição de cada registo é calculada como posicao = indice * TAMANHO_REGISTO.

Os métodos escreverCandidatura e lerCandidatura lançam exceções específicas (ex: IOException, IllegalArgumentException).

3.3. Tratamento de Exceções
Nunca usar catch (Exception e) {} sem log ou tratamento.

Usar try-with-resources para recursos que implementam AutoCloseable (ex: RandomAccessFile, FileInputStream).

Lançar exceções personalizadas quando necessário (ex: ArquivoCorrompidoException).

3.4. Logging
Utilizar java.util.logging (simples) ou SLF4J se necessário.

Níveis de log: FINE para debug, INFO para ações importantes, WARNING para situações anómalas, SEVERE para erros.

Evitar System.out.println em código de produção.

4. Regras Específicas do Projeto
4.1. Definição de Tamanhos Fixos (para RandomAccessFile)
Todas as strings terão um tamanho máximo definido em bytes (UTF-8). Para garantir o acesso aleatório, cada campo ocupa um número fixo de bytes, independentemente do conteúdo. O cálculo deve ser feito na classe Constantes:

public final class Constantes {
    // Tamanho máximo em caracteres (não bytes) para campos de texto
    public static final int MAX_NOME_ORGANIZACAO = 100;
    public static final int MAX_DESCRICAO_MISSAO = 600; // 150 palavras * ~4 caracteres
    // ... etc.

    // Tamanhos em bytes (UTF-8) – usar valor generoso para evitar truncamento
    public static final int BYTES_NOME_ORGANIZACAO = 500;
    // ...
}

Importante: Os métodos de leitura/escrita devem respeitar esses limites, truncando ou lançando exceção.

4.2. Validação de Dados
Criar uma classe Validador com métodos estáticos para validar campos obrigatórios, e-mail, telefone, limites de texto, valores numéricos, etc.

A validação deve ser chamada antes de submeter a candidatura (Secção J) e também em tempo real (opcional).

4.3. Anexo de Documentos
Na Secção F, o utilizador pode anexar um documento comprovativo. A aplicação deve copiar o ficheiro para uma pasta dentro do projeto (ex: anexos/) e guardar o caminho relativo no modelo.

O GerenciadorArquivo deve considerar esse caminho como parte do registo (tamanho fixo).

4.4. Internacionalização (i18n)
Não é obrigatório, mas se implementada, usar ResourceBundle e arquivos .properties na pasta resources.

5. Convenções para FXML e CSS
5.1. FXML
O fx:controller deve apontar para a classe controller correspondente no pacote controller.

Os elementos devem ter fx:id para serem injetados no controller.

Prefira layouts responsivas (VBox, HBox, GridPane, AnchorPane).

Utilize fx:include para reutilizar partes comuns (ex: cabeçalho, rodapé).

5.2. CSS
Usar nomes de classes em kebab-case para seletores (.campo-texto, .botao-submeter).

Criar um tema base em style.css e carregá-lo na cena principal.

Evitar estilos inline.

6. Script de Build (build.xml)
Um exemplo mínimo de build.xml para Ant:
<project name="BFASolidario" default="jar" basedir=".">
    <property name="src.dir" value="src"/>
    <property name="build.dir" value="build"/>
    <property name="dist.dir" value="dist"/>
    <property name="lib.dir" value="lib"/>
    <property name="main.class" value="com.bfa.solidario.MainApp"/>

    <path id="classpath">
        <fileset dir="${lib.dir}" includes="*.jar"/>
    </path>

    <target name="clean">
        <delete dir="${build.dir}"/>
        <delete dir="${dist.dir}"/>
    </target>

    <target name="compile">
        <mkdir dir="${build.dir}"/>
        <javac srcdir="${src.dir}" destdir="${build.dir}" classpathref="classpath" includeantruntime="false"/>
    </target>

    <target name="jar" depends="compile">
        <mkdir dir="${dist.dir}"/>
        <jar destfile="${dist.dir}/BFASolidario.jar" basedir="${build.dir}">
            <manifest>
                <attribute name="Main-Class" value="${main.class}"/>
            </manifest>
            <zipgroupfileset dir="${lib.dir}" includes="*.jar"/>
        </jar>
    </target>

    <target name="run" depends="jar">
        <java jar="${dist.dir}/BFASolidario.jar" fork="true"/>
    </target>
</project>

Nota: O JavaFX SDK deve ser colocado em lib/ e adicionado ao classpath.

7. Testes
Os testes unitários devem estar na pasta test/ (paralela a src/), com estrutura de pacotes semelhante.

Usar JUnit 5 (adicionar o JAR em lib/).

Testar as operações de leitura/escrita no ficheiro binário com casos limite (ficheiro vazio, registos corrompidos, etc.).

Testar a validação com dados válidos e inválidos.

8. Ferramentas e Ambiente
JDK 11+ (com JavaFX incluso? A partir do JDK 11 o JavaFX foi removido, então é necessário baixar o SDK separadamente e adicionar ao classpath).

Apache Ant para build.

IDE: IntelliJ IDEA, Eclipse ou NetBeans (configurados para usar Ant).

Controle de versão: Git, com commits atómicos e mensagens descritivas (preferencialmente em português ou inglês).

9. Exemplo de Trecho de Código (Conforme Regras)
package com.bfa.solidario.model;

/**
 * Representa um contato de referência ou responsável.
 */
public class Contato {
    private String primeiroNome;
    private String ultimoNome;
    private String telefone;
    private String email;
    private String funcao;

    public Contato(String primeiroNome, String ultimoNome, String telefone, String email, String funcao) {
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.telefone = telefone;
        this.email = email;
        this.funcao = funcao;
    }

    // Getters e setters (omitted for brevity)

    @Override
    public String toString() {
        return primeiroNome + " " + ultimoNome + " (" + funcao + ")";
    }
}
Este documento deve ser seguido por todos os membros da equipa para garantir a consistência e qualidade do código.