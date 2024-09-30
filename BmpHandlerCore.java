//Parte I - Claudia
//Librerías importadas.
import java.io.*;
import java.lang.Math;

public class BmpHandlerCore {
    // Campos a inicializar en el constructor.
    private int ancho;
    private int alto;
    private byte[] header = new byte[54];
    private int[][] Rojo;
    private int[][] Verde;
    private int[][] Azul;
    private int[][] sepiaRed;
    private int[][] sepiaGreen;
    private int[][] sepiaBlue;

    // Constructor de la clase.
    public BmpHandlerCore(String archivo) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(archivo));
        bis.read(header);
        this.ancho = readAncho();
        this.alto = readAlto();
        this.Rojo = new int[this.alto][this.ancho];
        this.Verde = new int[this.alto][this.ancho];
        this.Azul = new int[this.alto][this.ancho];
        this.sepiaRed = new int[this.alto][this.ancho];
        this.sepiaGreen = new int[this.alto][this.ancho];
        this.sepiaBlue = new int[this.alto][this.ancho];
        readBmp(bis);
    }

    // Lectura de los pixeles.
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

    // Métodos para leer datos de la imagen bmp.
    private int readAncho() {
        return getInt(header, 18);
    }

    private int readAlto() {
        return getInt(header, 22);
    }

    private static int getInt(byte[] data, int offset) {
        return (data[offset + 0] & 0xFF) |
                ((data[offset + 1] & 0xFF) << 8) |
                ((data[offset + 2] & 0xFF) << 16) |
                ((data[offset + 3] & 0xFF) << 24);
    }

    // Creación de imagen bmp.
    private void writeBmp(String archivo, int[][] red, int[][] green, int[][] blue) throws Exception {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(archivo));
        bos.write(header);
        for (int i = this.alto - 1; i >= 0; i--) {
            for (int j = 0; j < this.ancho; j++) {
                bos.write(blue[i][j]);
                bos.write(green[i][j]);
                bos.write(red[i][j]);
            }
        }
        bos.write(0);
        bos.write(0);
    }

    public void writeSepiaImage(String archivo) throws Exception {
        for (int i = 0; i < this.alto; i++) {
            for (int j = 0; j < this.ancho; j++) {
                int red = this.Rojo[i][j];
                int green = this.Verde[i][j];
                int blue = this.Azul[i][j];
                int newRed = Math.min((int) (0.393 * red + 0.769 * green + 0.189 * blue), 255);
                int newGreen = Math.min((int) (0.349 * red + 0.686 * green + 0.168 * blue), 255);
                int newBlue = Math.min((int) (0.272 * red + 0.534 * green + 0.131 * blue), 255);
                this.sepiaRed[i][j] = newRed;
                this.sepiaGreen[i][j] = newGreen;
                this.sepiaBlue[i][j] = newBlue;
            }
        }
        writeBmp(archivo, sepiaRed, sepiaGreen, sepiaBlue);
    }

    // Descarga de imágenes según el color.
    public void RedImage(String archivo) throws Exception {
        writeBmp(archivo, this.Rojo, new int[this.alto][this.ancho], new int[this.alto][this.ancho]);
    }

    public void GreenImage(String archivo) throws Exception {
        writeBmp(archivo, new int[this.alto][this.ancho], this.Verde, new int[this.alto][this.ancho]);
    }

    public void BlueImage(String archivo) throws Exception {
        writeBmp(archivo, new int[this.alto][this.ancho], new int[this.alto][this.ancho], this.Azul);
    }

    public void SepiaImage(String archivo) throws Exception{
        writeSepiaImage(archivo);
    }
}