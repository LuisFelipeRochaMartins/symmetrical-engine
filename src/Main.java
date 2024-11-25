import trees.RedBlackTree;
import trees.models.CategoriaQuarto;
import trees.models.Cliente;
import trees.models.Reserva;

import java.time.LocalDate;

public class Main {

	public static void main(String[] args) {
		var arvore = new RedBlackTree();
		var cliente = new Cliente(1, "10695491938", "Luis");

		var reserva1 = new Reserva(1, cliente, 1, LocalDate.of(2024, 11, 7), LocalDate.of(2024, 11, 16), CategoriaQuarto.PREMIUM);
		var reserva2 = new Reserva(2, cliente, 2, LocalDate.of(2024, 12, 5), LocalDate.of(2024, 12, 6), CategoriaQuarto.LUXO);
		var reserva3 = new Reserva(3, cliente, 3, LocalDate.of(2024, 12, 5), LocalDate.of(2024, 12, 6), CategoriaQuarto.LUXO);

		arvore.add(reserva1);
		arvore.add(reserva2);
		arvore.add(reserva3);


		var listas = arvore.consultarDisponibilidadePorPeriodo(
			LocalDate.of(2024,12,6),
			LocalDate.of(2024,12,11),
				CategoriaQuarto.ECONOMICO
		);
		System.out.println(listas);
	}
}
