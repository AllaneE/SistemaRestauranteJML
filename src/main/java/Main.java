import restaurante.view.MenuPrincipalView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuPrincipalView menu = new MenuPrincipalView(scanner);
        menu.iniciar();
        scanner.close();
    }
}
