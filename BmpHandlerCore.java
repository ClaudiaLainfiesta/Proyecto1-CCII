//Proyecto No. 1 - Grupo No.   | Parte I.
//Adriel Levi Argueta Caal | 24003171 - BN.
//Maria Claudia Lainfiesta Herrera | 24000149 - BN.

//Librerías importadas.
import java.io.*;
import java.lang.Math;
//Clase principal para tonos de la imagen.
public class BmpHandlerCore {
    //Campos a inicializar en el constructor.
    //Campos de dimensiones de la imagen.
    private int ancho;
    private int alto;
    //Campo de encabezado de la imagen.
    private byte[] header = new byte[54];
    //Campos de matrices utilizados para guardar los valores de cada pixel.
    private int[][] Rojo;
    private int[][] Verde;
    private int[][] Azul;
    private int[][] sepiaRed;
    private int[][] sepiaGreen;
    private int[][] sepiaBlue;

    //Constructor de la clase.
    public BmpHandlerCore(String archivo) throws Exception {
        //Lectura de imagen bmp.
        BufferedInputStream lectura = new BufferedInputStream(new FileInputStream(archivo));
        //Lectura de los primeros 54 bytes, que corresponde al encabezado de la imagen.
        lectura.read(header);
        //Inicialización de dimensiones.
        this.ancho = readAncho();
        this.alto = readAlto();
        //Inicialización de las matrices con las dimensiones de la imagen.
        this.Rojo = new int[this.alto][this.ancho];
        this.Verde = new int[this.alto][this.ancho];
        this.Azul = new int[this.alto][this.ancho];
        this.sepiaRed = new int[this.alto][this.ancho];
        this.sepiaGreen = new int[this.alto][this.ancho];
        this.sepiaBlue = new int[this.alto][this.ancho];
        //Llamada del método que leerá los valores de lo pixeles y se guardaran en las matrices.
        readBmp(lectura);
    }

    //Método que hace la lectura de los pixeles.
    private void readBmp(BufferedInputStream archivo) throws Exception {
        //For que itera en las filas de la imagen.
        for (int i = this.alto - 1; i >= 0; i--) {
            //For que itera las columnas de la imagen.
            for (int j = 0; j < this.ancho; j++) {
                //Lectura de los valores azul,verde y rojo y se hace una conversion a enteros.
                int blue = archivo.read() & 0xFF;
                int green = archivo.read() & 0xFF;
                int red = archivo.read() & 0xFF;
                //Guarda los valores en diferentes matrices.
                Azul[i][j] = blue;
                Verde[i][j] = green;
                Rojo[i][j] = red;
            }
        }
    }

    //Método que toma el valor en la posición 18 para leer los 4 bytes de ancho.
    private int readAncho() {
        return getInt(header, 18);
    }
    //Método que toma el valor en la posición 22 para leer los 4 bytes de alto.
    private int readAlto() {
        return getInt(header, 22);
    }
    //Método que interpreta 4 bytes como un entero de 32 bits.
    private static int getInt(byte[] data, int offset) {
        return (data[offset + 0] & 0xFF) |
                ((data[offset + 1] & 0xFF) << 8) |
                ((data[offset + 2] & 0xFF) << 16) |
                ((data[offset + 3] & 0xFF) << 24);
    }

    //Método para escribir una imagen bmp con los valores los pixeles en diferentes matrices de color.
    private void writeBmp(String archivo, int[][] red, int[][] green, int[][] blue) throws Exception {
        //Creación archivo de salida.
        BufferedOutputStream creacion = new BufferedOutputStream(new FileOutputStream(archivo));
        //Se escribe el encabezado en la nueva imagen.
        creacion.write(header);
        //For que itera las filas de la nueva imagen.
        for (int i = this.alto - 1; i >= 0; i--) {
            //For que itera las columnas de la nueva imagen.
            for (int j = 0; j < this.ancho; j++) {
                //Escribe los valores de las diferentes matrices.
                creacion.write(blue[i][j]);
                creacion.write(green[i][j]);
                creacion.write(red[i][j]);
            }
        }
        //Cierra el archivo.
        creacion.close();
    }
    //Método para aplicar el filtro sepia a la imagen.
    public void writeSepiaImage(String archivo) throws Exception {
        //For que itera cada fila de la imagen.
        for (int i = 0; i < this.alto; i++) {
            //For que itera cada columna de la imagen.
            for (int j = 0; j < this.ancho; j++) {
                //Obtiene los valores originales de cada pixel.
                int red = this.Rojo[i][j];
                int green = this.Verde[i][j];
                int blue = this.Azul[i][j];
                //Aplica el filtro sepia a cada color.
                int newRed = Math.min((int) (0.393 * red + 0.769 * green + 0.189 * blue), 255);
                int newGreen = Math.min((int) (0.349 * red + 0.686 * green + 0.168 * blue), 255);
                int newBlue = Math.min((int) (0.272 * red + 0.534 * green + 0.131 * blue), 255);
                //Guarda los nuevos valores es matrices para el filtro sepia.
                this.sepiaRed[i][j] = newRed;
                this.sepiaGreen[i][j] = newGreen;
                this.sepiaBlue[i][j] = newBlue;
            }
        }
        //Escribe la imagen con los pixeles con filtro.
        writeBmp(archivo, sepiaRed, sepiaGreen, sepiaBlue);
    }

    //Métodos para crear imágenes con solo un componente de color.
    public void RedImage(String archivo) throws Exception {
        writeBmp(archivo, this.Rojo, new int[this.alto][this.ancho], new int[this.alto][this.ancho]);
    }

    public void GreenImage(String archivo) throws Exception {
        writeBmp(archivo, new int[this.alto][this.ancho], this.Verde, new int[this.alto][this.ancho]);
    }

    public void BlueImage(String archivo) throws Exception {
        writeBmp(archivo, new int[this.alto][this.ancho], new int[this.alto][this.ancho], this.Azul);
    }
    //Método para crear una imagen con el filtro sepia.
    public void SepiaImage(String archivo) throws Exception{
        writeSepiaImage(archivo);
    }

    //*Extra - imagen en escala de grises.
    public void writeGrayImage(String archivo) throws Exception {
        //Creación archivo de salida.
        BufferedOutputStream creacion1 = new BufferedOutputStream(new FileOutputStream(archivo));
        //Se escribe el encabezado en la nueva imagen.
        creacion1.write(header);
        //For que itera las filas de la nueva imagen.
        for (int i = this.alto - 1; i >= 0; i--) {
            //For que itera las columnas de la nueva imagen.
            for (int j = 0; j < this.ancho; j++) {
                //Obtiene los valores originales de cada pixel.
                int red = this.Rojo[i][j];
                int green = this.Verde[i][j];
                int blue = this.Azul[i][j];
                //Aplica el filtro de grises.
                int gray = Math.min((int) (0.3 * red + 0.59 * green + 0.11 * blue), 255);
                //Escribe los valores de las diferentes matrices.
                creacion1.write(gray);
                creacion1.write(gray);
                creacion1.write(gray);
            }
        }
        //Cierra el archivo.
        creacion1.close();
    }
     //Método para crear una imagen en escala de grises.
    public void GrayImage(String archivo) throws Exception {
        //Llama al método que crea y guarda la imagen en escala de grises.
        writeGrayImage(archivo);
    }
}