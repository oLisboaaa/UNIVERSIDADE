class Aluno {
    // Atributos que armazenam informações do aluno
    private String nomeComp; // Nome completo do aluno
    private String ra; // Registro acadêmico do aluno
    private String curso; // Curso em que o aluno está matriculado

    // Construtor que inicializa os atributos do aluno
    public Aluno(String nomeComp, String ra, String curso) {
        this.nomeComp = nomeComp;
        this.ra = ra;
        this.curso = curso;
    }

    // Método que retorna uma ficha do aluno formatada
    public String getFicha() {
        return "Nome: " + nomeComp +
                "\nRA: " + ra +
                "\nCurso: " + curso + "\n";
    }
}

class Notas {
    // Atributos que armazenam as notas e informações do aluno
    private double nota1; // Primeira nota
    private double nota2; // Segunda nota
    private double nota3; // Terceira nota
    private double nota4; // Quarta nota
    private double notaFinal; // Nota final calculada
    private int avaliacao; // Tipo de avaliação
    private int presenca; // Percentual de presença
    private boolean ead; // Indica se o curso é EAD
    private String reprov; // Mensagem de reprovação ou aprovação

    // Construtor que inicializa as notas e verifica a validade dos dados
    public Notas(double nota1, double nota2, double nota3, double nota4, int avaliacao, int presenca, boolean ead) {
        // Valida as notas e a presença antes de atribuir os valores
        if (validarNotas(nota1, nota2, nota3, nota4) && validarPresenca(presenca)) {
            this.nota1 = nota1;
            this.nota2 = nota2;
            this.nota3 = nota3;
            this.nota4 = nota4;
            this.avaliacao = avaliacao;
            this.presenca = presenca;
            this.ead = ead; // Armazena se o curso é EAD
            this.notaFinal = calcularNotaFinal(); // Calcula a nota final
            this.reprov = definirSituacao(); // Define a situação do aluno
        } else {
            throw new IllegalArgumentException("Notas ou presença inválidas."); // Lança exceção se os dados forem
                                                                                // inválidos
        }
    }

    // Método que calcula a nota final com base no tipo de avaliação
    private double calcularNotaFinal() {
        switch (avaliacao) {
            case 2: // Média simples para 2 avaliações
                return (nota1 + nota2) / 2;
            case 3: // Média ponderada para 3 avaliações
                double peso1 = 10.0 / 7.0;
                double peso2 = 20.0 / 7.0;
                double peso3 = 40.0 / 7.0;
                return (nota1 * peso1 + nota2 * peso2 + nota3 * peso3) / (peso1 + peso2 + peso3);
            case 4: // Média ponderada com pesos específicos para 4 avaliações
                return (nota1 * 0.15) + (nota2 * 0.30) + (nota3 * 0.10) + (nota4 * 0.45);
            default:
                throw new IllegalArgumentException("Número de avaliações inválido!"); // Lança exceção se o número de
                                                                                      // avaliações for inválido
        }
    }

    // Método que define a situação do aluno com base na nota final e presença
    private String definirSituacao() {
        if (ead && notaFinal > 5) { // Se é EAD, só verifica a nota
            return "Sua nota foi de " + notaFinal + ". Você está APROVADO.";
        }

        if (notaFinal < 5) { // Reprovação por nota
            return "Sua nota foi de " + notaFinal + ". Você está REPROVADO.";
        } else if (presenca < 75) { // Reprovação por falta
            return "Sua nota foi de " + notaFinal + ", mas sua presença ficou em " + presenca
                    + "%. Você está REPROVADO.";
        } else { // Aprovação
            return "Sua nota foi de " + notaFinal + ". Você está APROVADO.";
        }
    }

    // Método para validar as notas, garantindo que estejam entre 0 e 10
    private boolean validarNotas(double... notas) {
        for (double nota : notas) {
            if (nota < 0 || nota > 10) {
                return false; // Retorna falso se alguma nota for inválida
            }
        }
        return true; // Todas as notas são válidas
    }

    // Método para validar a presença, garantindo que esteja entre 0% e 100%
    private boolean validarPresenca(int presenca) {
        return presenca >= 0 && presenca <= 100; // Retorna verdadeiro se a presença for válida
    }

    // Método para obter a situação de reprovação ou aprovação
    public String getReprov() {
        return reprov;
    }
}

public class Universidade {
    public static void main(String[] args) {
        try {
            // Criação de um objeto Aluno com dados específicos
            Aluno aluno = new Aluno("Marcelo Lisboa", "248347", "ADS - EAD");
            // Criação de um objeto Notas com notas e informações de avaliação
            Notas notas = new Notas(8.0, 0.5, 5.0, 5.0, 4, 66, true);
            // Impressão da ficha do aluno e da situação final
            System.out.println(aluno.getFicha() + notas.getReprov() + "\n");
        } catch (IllegalArgumentException e) {
            // Captura e exibe erros de validação
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
