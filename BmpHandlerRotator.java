//Proyecto No. 1 - Grupo No.6BN | Parte II.
//Adriel Levi Argueta Caal | 24003171 - BN.
//Maria Claudia Lainfiesta Herrera | 24000149 - BN.

import java.io.*;

public class BmpHandlerRotator {
    // Campos a inicializar en el constructor.
    private int ancho;
    private int alto;
    private byte[] header = new byte[54];
    private int[][] Rojo;
    private int[][] Verde;
    private int[][] Azul;

    // Constructor de la clase.
    public BmpHandlerRotator(String archivo) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(archivo));
        bis.read(header);
        this.ancho = readAncho();
        this.alto = readAlto();
        this.Rojo = new int[this.alto][this.ancho];
        this.Verde = new int[this.alto][this.ancho];
        this.Azul = new int[this.alto][this.ancho];
        readBmp(bis);
        bis.close(); // Cerrar el archivo de entrada.
    }

    // Lectura de los píxeles de la imagen.
    private void readBmp(BufferedInputStream archivo) throws Exception {
        for (int i = alto - 1; i >= 0; i--) {
            for (int j = 0; j < ancho; j++) {
                int blue = archivo.read() & 0xFF;
                int green = archivo.read() & 0xFF;
                int red = archivo.read() & 0xFF;

                Azul[i][j] = blue;
                Verde[i][j] = green;
                Rojo[i][j] = red;
            }
        }
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

    // Método para escribir la imagen con un giro de 180 grados (ambos ejes).
    public void rotate180AndSave(String archivoSalida) throws Exception {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(archivoSalida));
        bos.write(header); // Escribir el encabezado BMP.

        // Escribir los píxeles en orden invertido tanto en filas como en columnas.
        for (int i = 0; i < alto; i++) {
            for (int j = ancho - 1; j >= 0; j--) {
                bos.write(Azul[alto - 1 - i][j]);
                bos.write(Verde[alto - 1 - i][j]);
                bos.write(Rojo[alto - 1 - i][j]);
            }
        }
        bos.close(); // Cerrar el archivo de salida.
    }

    // Método (2) Rotar 180 grados sobre el eje vertical (invertir columnas)
    public void rotateVerticalAndSave(String archivoSalida) throws Exception {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(archivoSalida));
        bos.write(header); // Escribir el encabezado BMP.

        // Recorre las filas, pero invierte las columnas (espejo en el eje Y).
        for (int i = 0; i < alto; i++) {
            for (int j = ancho - 1; j >= 0; j--) {
                bos.write(Azul[i][j]);
                bos.write(Verde[i][j]);
                bos.write(Rojo[i][j]);
            }
        }

        bos.close(); // Cerrar el archivo de salida.
    }

}
