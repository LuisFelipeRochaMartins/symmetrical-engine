package trees;

import trees.models.Reserva;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatoriosGerais {

    private RedBlackTree arvore;

    public RelatoriosGerais(RedBlackTree arvore) {
        this.arvore = arvore;
    }

    // Método que agora recebe o total de quartos como parâmetro
    public void gerarRelatorios(LocalDate dataInicio, LocalDate dataFim, int totalQuartos) {
        // Taxa de Ocupação
        double taxaOcupacao = calcularTaxaOcupacao(dataInicio, dataFim, totalQuartos);
        System.out.println("Taxa de ocupação de " + dataInicio + " a " + dataFim + ": " + taxaOcupacao + "%");

        // Quartos mais e menos reservados
        imprimirQuartosMaisEMenosReservados();

        // Número de cancelamentos
        long cancelamentos = contarCancelamentos(dataInicio, dataFim);
        System.out.println("Número de cancelamentos de " + dataInicio + " a " + dataFim + ": " + cancelamentos);
    }

    public double calcularTaxaOcupacao(LocalDate dataInicio, LocalDate dataFim, int totalQuartos) {
        List<Reserva> reservasNoPeriodo = consultarReservasPorPeriodo(dataInicio, dataFim);
        int ocupacao = reservasNoPeriodo.size();
        return (ocupacao / (double) totalQuartos) * 100;
    }

    private List<Reserva> consultarReservasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return arvore.consultarReservasPorCheckIn().stream()
                .filter(reserva -> !reserva.checkOut.isBefore(dataInicio) && !reserva.checkIn.isAfter(dataFim))
                .collect(Collectors.toList());
    }

    public void imprimirQuartosMaisEMenosReservados() {
        Map<Integer, Long> reservasPorQuarto = arvore.consultarReservasPorCheckIn().stream()
                .collect(Collectors.groupingBy(Reserva::getQuarto, Collectors.counting()));

        // Quarto mais reservado
        var maxEntry = reservasPorQuarto.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new IllegalStateException("Nenhuma reserva encontrada."));
        System.out.println("Quarto mais reservado: " + maxEntry.getKey() + " com " + maxEntry.getValue() + " reservas.");

        // Quarto menos reservado
        var minEntry = reservasPorQuarto.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .orElseThrow(() -> new IllegalStateException("Nenhuma reserva encontrada."));
        System.out.println("Quarto menos reservado: " + minEntry.getKey() + " com " + minEntry.getValue() + " reservas.");
    }

    public long contarCancelamentos(LocalDate dataInicio, LocalDate dataFim) {
        // Simula o histórico de reservas canceladas usando uma lista ou outra árvore
        List<Reserva> historicoCancelamentos = arvore.cancelamentos; // Método que retorna histórico
        return historicoCancelamentos.stream()
                .filter(reserva -> !reserva.checkOut.isBefore(dataInicio) && !reserva.checkIn.isAfter(dataFim))
                .count();
    }
}
