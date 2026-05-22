package com.biblioteca;

import com.biblioteca.dao.BookRepository;
import com.biblioteca.dao.JpaUtil;
import com.biblioteca.dao.UserRepository;
import com.biblioteca.model.Book;
import com.biblioteca.model.User;
import com.biblioteca.service.ViaCepService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        UserRepository ur = new UserRepository();
        BookRepository br = new BookRepository();

        try {
            loop(ur, br);
        } finally {
            JpaUtil.close();
            scanner.close();
        }
    }

    private static void loop(UserRepository ur, BookRepository br) {
        while (true) {
            System.out.println("\n== Biblioteca ==");
            System.out.println("1) Criar usuário");
            System.out.println("2) Listar usuários");
            System.out.println("3) Atualizar usuário");
            System.out.println("4) Deletar usuário");
            System.out.println("5) Criar livro para usuário");
            System.out.println("6) Listar livros");
            System.out.println("0) Sair");
            System.out.print("Escolha: ");
            String opt = scanner.nextLine().trim();

            try {
                switch (opt) {
                    case "1" -> createUser(ur);
                    case "2" -> listUsers(ur);
                    case "3" -> updateUser(ur);
                    case "4" -> deleteUser(ur);
                    case "5" -> createBookForUser(br, ur);
                    case "6" -> listBooks(br);
                    case "0" -> { System.out.println("Saindo..."); return; }
                    default -> System.out.println("Opção inválida");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void createUser(UserRepository ur) throws IOException {
        System.out.print("Nome: ");
        String name = scanner.nextLine().trim();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine().trim();
        System.out.print("CEP: ");
        String cep = scanner.nextLine().trim();

        User u = new User();
        u.setName(name);
        u.setCpf(cpf);
        u.setCep(cep);

        // tentar enriquecer com ViaCEP
        try {
            ViaCepService.Result r = ViaCepService.lookup(cep);
            if (r != null) {
                u.setStreet(r.street);
                u.setNeighborhood(r.neighborhood);
                u.setCity(r.city);
                u.setState(r.state);
            }
        } catch (Exception ex) {
            System.out.println("ViaCEP: " + ex.getMessage());
        }

        try {
            ur.save(u);
            System.out.println("Usuário criado com id=" + u.getId());
        } catch (Exception ex) {
            System.out.println("Falha ao salvar usuário. Verifique conexão MySQL e as credenciais no arquivo .env.");
            ex.printStackTrace();
        }
    }

    private static void listUsers(UserRepository ur) {
        try {
            List<User> all = ur.findAll();
            if (all.isEmpty()) {
                System.out.println("Nenhum usuário encontrado.");
                return;
            }
            System.out.println("Usuários cadastrados:");
            all.forEach(s -> System.out.println(s));
        } catch (Exception ex) {
            System.out.println("Falha ao listar usuários. Verifique conexão MySQL e credenciais em .env.");
            ex.printStackTrace();
        }
    }

    private static void updateUser(UserRepository ur) throws IOException {
        System.out.print("ID do usuário: ");
        Integer id = Integer.valueOf(scanner.nextLine().trim());
        User u = ur.findById(id);
        if (u == null) { System.out.println("Usuário não encontrado"); return; }
        System.out.print("Novo nome (enter para manter): ");
        String name = scanner.nextLine().trim(); if (!name.isEmpty()) u.setName(name);
        System.out.print("Novo CEP (enter para manter): ");
        String cep = scanner.nextLine().trim(); if (!cep.isEmpty()) {
            u.setCep(cep);
            try { var r = ViaCepService.lookup(cep); if (r!=null) { u.setStreet(r.street); u.setNeighborhood(r.neighborhood); u.setCity(r.city); u.setState(r.state); } }
            catch (Exception ex) { System.out.println("ViaCEP: " + ex.getMessage()); }
        }
        ur.update(u);
        System.out.println("Usuário atualizado");
    }

    private static void deleteUser(UserRepository ur) throws IOException {
        System.out.print("ID do usuário: ");
        Integer id = Integer.valueOf(scanner.nextLine().trim());
        ur.delete(id);
        System.out.println("Deletado (se existia)");
    }

    private static void createBookForUser(BookRepository br, UserRepository ur) throws IOException {
        System.out.print("ID do usuário: ");
        Integer uid = Integer.valueOf(scanner.nextLine().trim());
        User u = ur.findById(uid);
        if (u == null) { System.out.println("Usuário não encontrado"); return; }
        System.out.print("Título do livro: ");
        String title = scanner.nextLine().trim();
        System.out.print("Autor: ");
        String author = scanner.nextLine().trim();
        Book b = new Book(title, author);
        b.setUser(u);
        br.save(b);
        System.out.println("Livro criado com id=" + b.getId());
    }

    private static void listBooks(BookRepository br) throws IOException {
        List<Book> all = br.findAll();
        if (all.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return;
        }
        System.out.println("Livros cadastrados:");
        all.forEach(System.out::println);
    }
}
