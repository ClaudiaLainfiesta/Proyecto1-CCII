//Proyecto No. 1 - Grupo No.6BN | Parte II.
//Adriel Levi Argueta Caal | 24003171 - BN.
//Maria Claudia Lainfiesta Herrera | 24000149 - BN.

//librerias importadas para el proyecto.


import java.io.*;

public class BmpHandlerResizer {

    //Campos a inicializar el constructor.
    private int ancho;
    private int alto;
    private byte[] header = new byte[54];
    private int[][] Rojo;
    private int[][] Verde;
    private int[][] Azul;

    //Constructor de la clase.
    public BmpHandlerResizer(String archivo) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(archivo));
        bis.read(header);
        this.ancho = readAncho();
        this.alto = readAlto();
        this.Rojo = new int[this.alto][this.ancho];
        this.Verde = new int[this.alto][this.ancho];
        this.Azul = new int[this.alto][this.ancho];
        readBmp(bis);
        bis.close();
    }

    //Lectura del ancho de la imagen desde el encabezado.
    private int readAncho() {
        return getInt(header, 18);
    }

    //Lectura del alto de la imagen desde el encabezado.
    private int readAlto() {
        return getInt(header, 22);
    }

    private static int getInt(byte[] data, int offset) {
        return (data[offset + 0] & 0xFF) |
                ((data[offset + 1] & 0xFF) << 8) |
                ((data[offset + 2] & 0xFF) << 16) |
                ((data[offset + 3] & 0xFF) << 24);
    }

    //Lectura de la imagen.
    private void readBmp(BufferedInputStream archivo) throws Exception {
        for (int i = 0; i < alto; i++) {
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

    //Método para reducir el ancho de la imagen al 50% y guardar en archivo de salida.
    public void reducirAncho(String archivoSalida) throws Exception {
        //Crear un archivo de salida.
        BufferedOutputStream bis = new BufferedOutputStream(new FileOutputStream(archivoSalida));

        //Ajustar el ancho en el header (posiciones 18-21 en el header BMP).
        int nuevoAncho = ancho / 2;
        //Actualizar el ancho en el header.
        escribirInt(header, nuevoAncho, 18);
        //Escribir el encabezado BMP actualizado.
        bis.write(header);

        //Crear nuevos arreglos de píxeles.
        int[][] nuevoRojo = new int[alto][nuevoAncho];
        int[][] nuevoVerde = new int[alto][nuevoAncho];
        int[][] nuevoAzul = new int[alto][nuevoAncho];

        //Relleno de bytes: el número de bytes por fila debe ser múltiplo de 4.
        int relleno = (4 - (nuevoAncho * 3) % 4) % 4;

        //Llenar los nuevos arreglos y escribir al archivo.
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < nuevoAncho; j++) {
                nuevoRojo[i][j] = Rojo[i][j * 2];
                nuevoVerde[i][j] = Verde[i][j * 2];
                nuevoAzul[i][j] = Azul[i][j * 2];

                //Escribir los valores de cada canal de color.
                bis.write(nuevoAzul[i][j]);
                bis.write(nuevoVerde[i][j]);
                bis.write(nuevoRojo[i][j]);
            }
            //Añadir relleno si es necesario.
            for (int k = 0; k < relleno; k++) {
                //Escribir bytes de relleno (0).
                bis.write(0);
            }
        }

        //Cerrar el archivo de salida.
        bis.close();

        //Actualizar las propiedades de la imagen.
        this.ancho = nuevoAncho;
        this.Rojo = nuevoRojo;
        this.Verde = nuevoVerde;
        this.Azul = nuevoAzul;
    }

    //Método para reducir el alto de la imagen al 50%.
    public void reducirAlto(String archivoSalida) throws Exception {
        //Crear un archivo de salida.
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(archivoSalida));

        //Ajustar el alto en el header (posiciones 22-25 en el header BMP).
        int nuevoAlto = alto / 2;
        //Actualizar el alto en el header.
        escribirInt(header, nuevoAlto, 22);
        //Escribir el encabezado BMP actualizado.
        bos.write(header);

        //Crear nuevos arreglos de píxeles.
        int[][] nuevoRojo = new int[nuevoAlto][ancho];
        int[][] nuevoVerde = new int[nuevoAlto][ancho];
        int[][] nuevoAzul = new int[nuevoAlto][ancho];

        //Calcular el relleno de bytes (debe alinear a múltiplos de 4 bytes por fila).
        int relleno = (4 - (ancho * 3) % 4) % 4;

        //Llenar los nuevos arreglos y escribir al archivo.
        for (int i = 0; i < nuevoAlto; i++) {
            for (int j = 0; j < ancho; j++) {
                //Tomar cada segunda fila para reducir el alto.
                nuevoRojo[i][j] = Rojo[i * 2][j];
                nuevoVerde[i][j] = Verde[i * 2][j];
                nuevoAzul[i][j] = Azul[i * 2][j];

                //Escribir los valores de cada canal de color.
                bos.write(nuevoAzul[i][j]);
                bos.write(nuevoVerde[i][j]);
                bos.write(nuevoRojo[i][j]);
            }
            //Añadir relleno si es necesario.
            for (int k = 0; k < relleno; k++) {
                //Escribir bytes de relleno (0).
                bos.write(0);
            }
        }

        //Cerrar el archivo de salida.
        bos.close();

        //Actualizar las propiedades de la imagen.
        this.alto = nuevoAlto;
        this.Rojo = nuevoRojo;
        this.Verde = nuevoVerde;
        this.Azul = nuevoAzul;
    }

    //Método auxiliar para escribir enteros en el header BMP.
    private void escribirInt(byte[] header, int valor, int offset) {
        header[offset] = (byte) (valor & 0xFF);
        header[offset + 1] = (byte) ((valor >> 8) & 0xFF);
        header[offset + 2] = (byte) ((valor >> 16) & 0xFF);
        header[offset + 3] = (byte) ((valor >> 24) & 0xFF);
    }

}
