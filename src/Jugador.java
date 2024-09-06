import java.util.Random;
import java.util.Arrays;
import javax.swing.JPanel;

public class Jugador {
   
    private final int TOTAL_CARTAS = 10;
    private final int MARGEN = 10;
    private final int DISTANCIA = 50;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir(){

        int i = 0;
        for (Carta c : cartas) {
            cartas[i++] = new Carta(r);
        } 
    }
    public void mostrar(JPanel pnl){
        pnl.removeAll();
       
        int p = 0;
        for (Carta c : cartas) {
            c.mostrar(pnl, MARGEN + p++ * DISTANCIA, MARGEN);
        }
        pnl.repaint();
    }
    
    public String getGrupos() {
        String escalerasMensaje = "Se encontraron las siguientes escaleras:\n";
        String mensaje = "No se encontraron figuras";
        int[] contadores = new int[NombreCarta.values().length];
        int[] cartasIndices = new int[TOTAL_CARTAS];
        String[] listaEscalera = new String[TOTAL_CARTAS];
        String[] vectorNombreCarta = {"AS", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "JACK", "QUEEN", "KING"};
        
        int h = 0;
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
            cartasIndices[h++] = c.getIndiceCarta();
        }
        
        Arrays.sort(cartasIndices);
        boolean hayGrupos = false;
        int contadorEscalera = 0;
        for (int i = 0 ; i < contadores.length; i++) {
            if (contadores[i] >= 2){
                if(!hayGrupos){
                    hayGrupos = true;
                    mensaje = "Se encontraron los siguientes grupos:\n";
                }
                mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] + "\n";
                
            }
            
        }
        
        String pinta = "";
        for (int i = 0; i < cartasIndices.length - 1; i++) {
            String pintaInicial = pinta;

            if (cartasIndices[i] <= 13 && cartasIndices[i + 1] <= 13) {
                pinta = "TREBOLES.";
            } else if (cartasIndices[i] <= 26 && cartasIndices[i + 1] <= 26 && cartasIndices[i] > 13 && cartasIndices[i + 1] > 13) {
                pinta = "PICAS.";
            } else if (cartasIndices[i] <= 39 && cartasIndices[i + 1] <= 39 && cartasIndices[i] > 26 && cartasIndices[i + 1] > 26) {
                pinta = "CORAZONES.";
            } else if (cartasIndices[i] <= 52 && cartasIndices[i + 1] <= 52 && cartasIndices[i] > 39 && cartasIndices[i + 1] > 39) {
                pinta = "DIAMANTES.";
            } else {
                pinta = "CAMBIO";
            }

            if (cartasIndices[i] + 1 == cartasIndices[i + 1] && !pinta.equals("CAMBIO")) {
                int residuo = cartasIndices[i]% 13;
                if (residuo == 0){
                    residuo = 13;
                }   
                listaEscalera[contadorEscalera++] = vectorNombreCarta[residuo - 1];
                int residuo_1 = cartasIndices[i+1]% 13;
                if (residuo_1 == 0){
                    residuo_1 = 13;
                }   
                listaEscalera[contadorEscalera++] = vectorNombreCarta[residuo_1 - 1];
            } else {
                if (contadorEscalera != 0) {
                    escalerasMensaje += "Escalera entre " + listaEscalera[0] + " y " + listaEscalera[contadorEscalera - 1] + " de " + pintaInicial + "\n";
                }
                contadorEscalera = 0;
            }

            if (i + 1 == cartasIndices.length - 1 && contadorEscalera != 0) {
                escalerasMensaje += "Escalera entre " + listaEscalera[0] + " y " + listaEscalera[contadorEscalera - 1] + " de " + pinta + "\n";
            }
        }

        if (escalerasMensaje.equals("Se encontraron las siguientes escaleras:\n")) {
            escalerasMensaje = "No hay escaleras\n";
        }
            
        return mensaje + "\n" + escalerasMensaje;
    }
}
