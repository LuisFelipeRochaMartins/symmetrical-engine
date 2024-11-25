package trees;

import trees.models.CategoriaQuarto;
import trees.models.Reserva;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.stream.Collectors;

public class RedBlackTree {

    private enum Color {
        RED,
        BLACK
    }

    private class Node {
        Reserva reserva;
        Color color;
        Node left, right, father;

        public Node(Reserva reserva) {
            this.reserva = reserva;
            this.color = Color.RED;
            left = right = father = null;
        }
    }

    private Node root;
    public final List<Reserva> cancelamentos = new ArrayList<>();

    public RedBlackTree() {
        this.root = null;
    }

    public void add(Reserva reserva) {
        if (!isQuartoDisponivel(reserva.quarto, reserva.checkIn, reserva.checkOut)) {
            throw new IllegalArgumentException("O quarto " + reserva.quarto + " já está reservado para as datas solicitadas.");
        }
        var newNode = new Node(reserva);
        root = insertNode(root, newNode);
        correctInsertion(newNode);
    }

    private Node insertNode(Node root, Node newNode) {
        if (root == null) {
            return newNode;
        }

        if (newNode.reserva.id < root.reserva.id) {
            root.left = insertNode(root.left, newNode);
            root.left.father = root;
        } else if (newNode.reserva.id > root.reserva.id) {
            root.right = insertNode(root.right, newNode);
            root.right.father = root;
        }

        return root;
    }

    private void correctInsertion(Node newNode) {
        Node father, grandFather;

        while (newNode != root && newNode.father.color == Color.RED) {
            father = newNode.father;
            grandFather = father.father;

            if (father == grandFather.left) {
                var uncle = grandFather.left;

                if (uncle != null && uncle.color == Color.RED) {
                    father.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    grandFather.color = Color.RED;
                    newNode = grandFather;
                } else {
                    if (newNode == father.right) {
                        newNode = father;
                        leftRotation(newNode);
                    }
                    father.color = Color.BLACK;
                    grandFather.color = Color.RED;
                    rightRotation(newNode);
                }
            } else {
                var uncle = grandFather.left;

                if (uncle != null && uncle.color == Color.RED) {
                    father.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    grandFather.color = Color.RED;
                    newNode = grandFather;
                } else {
                    if (newNode == father.left) {
                        newNode = father;
                        rightRotation(newNode);
                    } else {
                        father.color = Color.BLACK;
                        grandFather.color = Color.RED;
                        leftRotation(newNode);
                    }
                }
            }
        }
        root.color = Color.BLACK;
    }

    private void leftRotation(Node node) {
        if (node == null || node.right == null) {
            return;
        }

        Node newNode = node.right;
        node.right = newNode.left;

        if (newNode.left != null) {
            newNode.left.father = node;
        }

        newNode.father = node.father;
        if (node.father == null) {
            root = newNode; // Update root if necessary
        } else if (node == node.father.left) {
            node.father.left = newNode;
        } else {
            node.father.right = newNode;
        }

        newNode.left = node;
        node.father = newNode;
    }

    private void rightRotation(Node node) {
        if (node == null || node.left == null) {
            return;
        }
        var newNode = node.left;
        node.left = newNode.right;

        if (newNode.right != null) {
            newNode.right.father = node;
        }

        newNode.father = node.father;
        if (node.father == null) {
            root = newNode;
        } else if (node == node.father.right) {
            node.father.right = newNode;
        } else {
            node.father.left = newNode;
        }

        newNode.right = node;
        node.father = newNode;
    }

    public void remove(Reserva reserva) {
        var nodeToRemove = findNode(root, reserva);
        if (nodeToRemove == null) {
            throw new IllegalArgumentException("Reserva não encontrada.");
        }
        cancelamentos.add(nodeToRemove.reserva);
        root = removeNode(root, nodeToRemove);
    }

    private Node findNode(Node node, Reserva reserva) {
        if (node == null) {
            return null;
        }
        if (reserva.id < node.reserva.id) {
            return findNode(node.left, reserva);
        } else if (reserva.id > node.reserva.id) {
            return findNode(node.right, reserva);
        }
        return node;
    }

    private Node removeNode(Node root, Node nodeToRemove) {
        if (nodeToRemove.left != null && nodeToRemove.right != null) {
            Node successor = findMin(nodeToRemove.right);
            nodeToRemove.reserva = successor.reserva;
            root = removeNode(root, successor);
        } else {
            Node replacement = (nodeToRemove.left != null) ? nodeToRemove.left : nodeToRemove.right;
            if (replacement != null) {
                replacement.father = nodeToRemove.father;
                if (nodeToRemove.father == null) {
                    root = replacement;
                } else if (nodeToRemove == nodeToRemove.father.left) {
                    nodeToRemove.father.left = replacement;
                } else {
                    nodeToRemove.father.right = replacement;
                }
                if (nodeToRemove.color == Color.BLACK) {
                    fixRemove(replacement);
                }
            } else if (nodeToRemove.father == null) {
                root = null;
            } else {
                if (nodeToRemove.color == Color.BLACK) {
                    fixRemove(nodeToRemove);
                }
                if (nodeToRemove.father != null) {
                    if (nodeToRemove == nodeToRemove.father.left) {
                        nodeToRemove.father.left = null;
                    } else if (nodeToRemove == nodeToRemove.father.right) {
                        nodeToRemove.father.right = null;
                    }
                }
            }
        }
        return root;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private void fixRemove(Node node) {
        Node sibling;
        while (node != root && node.color == Color.BLACK) {
            if (node == node.father.left) {
                sibling = node.father.right;
                if (sibling.color == Color.RED) {
                    sibling.color = Color.BLACK;
                    node.father.color = Color.RED;
                    leftRotation(node.father);
                    sibling = node.father.right;
                }

                if ((sibling.left == null || sibling.left.color == Color.BLACK) &&
                        (sibling.right == null || sibling.right.color == Color.BLACK)) {
                    sibling.color = Color.RED;
                    node = node.father;
                } else {
                    if (sibling.right == null || sibling.right.color == Color.BLACK) {
                        if (sibling.left != null) {
                            sibling.left.color = Color.BLACK;
                        }
                        sibling.color = Color.RED;
                        rightRotation(sibling);
                        sibling = node.father.right;
                    }
                    sibling.color = node.father.color;
                    node.father.color = Color.BLACK;
                    if (sibling.right != null) {
                        sibling.right.color = Color.BLACK;
                    }
                    leftRotation(node.father);
                    node = root;
                }
            } else {
                sibling = node.father.left;
                if (sibling.color == Color.RED) {
                    sibling.color = Color.BLACK;
                    node.father.color = Color.RED;
                    rightRotation(node.father);
                    sibling = node.father.left;
                }

                if ((sibling.left == null || sibling.left.color == Color.BLACK) &&
                        (sibling.right == null || sibling.right.color == Color.BLACK)) {
                    sibling.color = Color.RED;
                    node = node.father;
                } else {
                    if (sibling.left == null || sibling.left.color == Color.BLACK) {
                        if (sibling.right != null) {
                            sibling.right.color = Color.BLACK;
                        }
                        sibling.color = Color.RED;
                        leftRotation(sibling);
                        sibling = node.father.left;
                    }
                    sibling.color = node.father.color;
                    node.father.color = Color.BLACK;
                    if (sibling.left != null) {
                        sibling.left.color = Color.BLACK;
                    }
                    rightRotation(node.father);
                    node = root;
                }
            }
        }
        node.color = Color.BLACK;
    }

    public boolean isQuartoDisponivel(int quarto, LocalDate checkIn, LocalDate checkOut) {
        return isQuartoDisponivel(root, quarto, checkIn, checkOut);
    }

    private boolean isQuartoDisponivel(Node node, int quarto, LocalDate checkIn, LocalDate checkOut) {
        if (node == null) {
            return true;
        }

        // Verifica se são os mesmos quartos e se possuem datas conflitantes
        if (node.reserva.quarto == quarto && datasConflitam(node.reserva.checkIn, node.reserva.checkOut, checkIn, checkOut)) {
            return false;
        }

        // utiliza recursão para verificar se os quartos estão desocupados
        return isQuartoDisponivel(node.left, quarto, checkIn, checkOut) &&
                isQuartoDisponivel(node.right, quarto, checkIn, checkOut);
    }

    private boolean datasConflitam(LocalDate checkInExistente, LocalDate checkOutExistente, LocalDate checkInNovo, LocalDate checkOutNovo) {
        return !(checkOutNovo.isBefore(checkInExistente) || checkInNovo.isAfter(checkOutExistente));
    }

    public Reserva consultarPorId(int id) {
        Node node = buscarPorId(root, id);
        if (node != null) {
            return node.reserva; // retorna o node correto
        }
        return null; // Não encontrado
    }

    private Node buscarPorId(Node node, int id) {
        if (node == null) {
            return null;
        }

        if (node.reserva.id == id) {
            // Retorna o node certo baseado no ID da reserva
            return node;
        }

        // caso não achar, utiliza recursão nas subarvores para encontrar o node correto
        if (id < node.reserva.id) {
            return buscarPorId(node.left, id);
        } else {
            return buscarPorId(node.right, id);
        }
    }

    public Reserva consultarPorCpf(String cpf) {
        Node node = buscarPorCpf(root, cpf);
        if (node != null) {
            return node.reserva;
        }
        return null;
    }

    private Node buscarPorCpf(Node node, String cpf) {
        if (node == null) {
            return null;
        }
        // Retorna o node certo baseado no CPF do cliente
        if (node.reserva.cliente.cpf.equals(cpf)) {
            return node;
        }

        Node leftSearch = buscarPorCpf(node.left, cpf);
        if (leftSearch != null) {
            return leftSearch;
        }

        return buscarPorCpf(node.right, cpf);
    }

    public List<Reserva> consultarDisponibilidadePorPeriodo(LocalDate dataCheckIn, LocalDate dataCheckOut, CategoriaQuarto categoria) {
        return consultarDisponibilidade(root, dataCheckIn, dataCheckOut, categoria);
    }

    private List<Reserva> consultarDisponibilidade(Node node, LocalDate dataCheckIn, LocalDate dataCheckOut, CategoriaQuarto categoria) {
        if (node == null) {
            return new ArrayList<>();
        }

        var quartosDisponiveis = new ArrayList<Reserva>();

        //Verificando se pertence a esse período utilizando
        if (node.reserva.checkIn.isBefore(dataCheckOut) || node.reserva.checkOut.isAfter(dataCheckIn))  {
            // Verificando se pertence a categoria que o usuário está procurando
            if (node.reserva.categoria.equals(categoria)) {
                //adicionando as reservas aqui
                quartosDisponiveis.add(node.reserva);
            }
        }

        //adicionando todas as reservas das subarvores atraves de recursão.
        quartosDisponiveis.addAll(consultarDisponibilidade(node.left, dataCheckIn, dataCheckOut, categoria));
        quartosDisponiveis.addAll(consultarDisponibilidade(node.right, dataCheckIn, dataCheckOut, categoria));

        return quartosDisponiveis;
    }

    public List<Reserva> listarReservasPorPeriodo(LocalDate inicio, LocalDate fim) {
        List<Reserva> reservasNoPeriodo = new ArrayList<>();
        listarReservasPorPeriodo(root, inicio, fim, reservasNoPeriodo);
        return reservasNoPeriodo;
    }

    private void listarReservasPorPeriodo(Node node, LocalDate inicio, LocalDate fim, List<Reserva> reservasNoPeriodo) {
        if (node == null) {
            return;
        }
        boolean dentroDoPeriodo = (node.reserva.checkIn.isEqual(inicio) || node.reserva.checkIn.isAfter(inicio)) &&
                (node.reserva.checkIn.isEqual(fim) || node.reserva.checkIn.isBefore(fim)) ||
                (node.reserva.checkOut.isEqual(inicio) || node.reserva.checkOut.isAfter(inicio)) &&
                        (node.reserva.checkOut.isEqual(fim) || node.reserva.checkOut.isBefore(fim));

        if (dentroDoPeriodo) {
            reservasNoPeriodo.add(node.reserva);
        }

        if (node.reserva.checkOut.isAfter(inicio)) {
            listarReservasPorPeriodo(node.left, inicio, fim, reservasNoPeriodo); // Subárvore esquerda
        }
        if (node.reserva.checkOut.isBefore(fim)) {
            listarReservasPorPeriodo(node.right, inicio, fim, reservasNoPeriodo); // Subárvore direita
        }
    }

    public List<Reserva> consultarReservasPorCheckIn() {
        List<Reserva> reservas = new ArrayList<>();
        consultarReservasPorCheckIn(root, reservas);

        // Ordenando as reservas por data de check-in
        return reservas.stream()
                .sorted(Comparator.comparing(r -> r.checkIn)) // Ordenação por checkIn
                .collect(Collectors.toList());
    }

    private void consultarReservasPorCheckIn(Node node, List<Reserva> reservas) {
        if (node == null) {
            return;
        }

        // Primeiro, percorre a subarvore esquerda
        consultarReservasPorCheckIn(node.left, reservas);

        // Adiciona a reserva na lista
        reservas.add(node.reserva);

        // dai percorre a subárvore direita
        consultarReservasPorCheckIn(node.right, reservas);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RedBlackTree:\n");
        buildTreeString(sb, root, "", true);
        return sb.toString();
    }

    private void buildTreeString(StringBuilder sb, Node node, String prefix, boolean isTail) {
        if (node != null) {
            sb.append(prefix)
                    .append(isTail ? "└── " : "├── ")
                    .append("[")
                    .append(node.color)
                    .append("] ")
                    .append(node.reserva)
                    .append("\n");

            String childPrefix = prefix + (isTail ? "    " : "│   ");
            buildTreeString(sb, node.left, childPrefix, node.right == null);
            buildTreeString(sb, node.right, childPrefix, true);
        }
    }
}
