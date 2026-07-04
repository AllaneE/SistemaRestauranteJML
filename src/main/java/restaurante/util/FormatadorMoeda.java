package restaurante.util;

/**
 * Classe utilitária para formatar valores como moeda brasileira (R$).
 */
public final class FormatadorMoeda {
    private  FormatadorMoeda() {}

    public  static String Formatador(double valor){
        return String.format("R$ %.2f", valor);
    }
}
