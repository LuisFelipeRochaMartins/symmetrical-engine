package trees.models;

import java.time.LocalDate;

public class Reserva {

    public Integer id;
    public Cliente cliente;
    public Integer quarto;
    public LocalDate checkIn;
    public LocalDate checkOut;
    public CategoriaQuarto categoria;

    public Reserva() {}

    public Reserva(Integer id, Cliente cliente, Integer quarto, LocalDate checkIn, LocalDate checkOut, CategoriaQuarto categoria) {
        this.id = id;
        this.cliente = cliente;
        this.quarto = quarto;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return String.format("Reserva(id=%d, cliente=%s, quarto=%d, checkIn=%s, checkOut=%s, categoria=%s)",
                id, cliente, quarto, checkIn, checkOut, categoria);
    }

    public Integer getQuarto() {
        return this.quarto;
    }
}
