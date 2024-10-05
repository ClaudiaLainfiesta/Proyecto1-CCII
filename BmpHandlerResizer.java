//Proyecto No. 1 - Grupo No.   | Parte II.
//Adriel Levi Argueta Caal | 24003171 - BN.
//Maria Claudia Lainfiesta Herrera | 24000149 - BN.

import java.io.*;

public class BmpHandlerResizer {

    //campos a inicializar el constructor
    private int ancho;
    private int alto;
    private byte[] header = new byte[54];
    private int[][] Rojo;
    private int[][] Verde;
    private int[][] Azul;

    //constructor de la clase
    public BmpHandlerResizer(String archivo) throws Exception{
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(archivo));
        bis.read(header);
        this.ancho = readAncho();
        this.alto = readAlto();
        this.Rojo = new int[this.alto][this.ancho];
        this.Verde = new int[this.alto][this.ancho];
        this.Azul = new int[this.alto][this.ancho];
        readBmp(bis);
        bis.close(); // cerramos el archivo
    }
    // Lectura del ancho de la imagen desde el encabezado.
    private int readAncho() {
        return getInt(header, 18);
    }

    // Lectura del alto de la imagen desde el encabezado.
    private int readAlto() {
        return getInt(header, 22);
    }

    private static int getInt(byte[] data, int offset) {
        return (data[offset + 0] & 0xFF) |
               ((data[offset + 1] & 0xFF) << 8) |
               ((data[offset + 2] & 0xFF) << 16) |
               ((data[offset + 3] & 0xFF) << 24);
    }
    //lecutra de la imagen
    private void readBmp(BufferedInputStream archivo) throws Exception{
        for(int i = alto -1; i >= 0 ; i--){
            for(int j = 0; j < ancho; j++){
                int blue = archivo.read() & 0xFF;
                int green = archivo.read()& 0xFF;
                int red = archivo.read() & 0xFF;

                Azul[i][j] = blue;
                Verde[i][j]= green;
                Rojo[i][j]= red;
            }
        }
    }

    // Método para reducir el ancho de la imagen al 50%
    public void reducirAncho() {
        int nuevoAncho = ancho / 2;
        int[][] nuevoRojo = new int[alto][nuevoAncho];
        int[][] nuevoVerde = new int[alto][nuevoAncho];
        int[][] nuevoAzul = new int[alto][nuevoAncho];

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < nuevoAncho; j++) {
                nuevoRojo[i][j] = Rojo[i][j * 2];
                nuevoVerde[i][j] = Verde[i][j * 2];
                nuevoAzul[i][j] = Azul[i][j * 2];
            }
        }

        this.ancho = nuevoAncho;
        this.Rojo = nuevoRojo;
        this.Verde = nuevoVerde;
        this.Azul = nuevoAzul;
    }

    // Método para reducir el alto de la imagen al 50%
    public void reducirAlto() {
        int nuevoAlto = alto / 2;
        int[][] nuevoRojo = new int[nuevoAlto][ancho];
        int[][] nuevoVerde = new int[nuevoAlto][ancho];
        int[][] nuevoAzul = new int[nuevoAlto][ancho];

        for (int i = 0; i < nuevoAlto; i++) {
            for (int j = 0; j < ancho; j++) {
                nuevoRojo[i][j] = Rojo[i * 2][j];
                nuevoVerde[i][j] = Verde[i * 2][j];
                nuevoAzul[i][j] = Azul[i * 2][j];
            }
        }

        this.alto = nuevoAlto;
        this.Rojo = nuevoRojo;
        this.Verde = nuevoVerde;
        this.Azul = nuevoAzul;
    }
}
