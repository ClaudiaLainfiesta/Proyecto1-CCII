//Proyecto No. 1 - Grupo No.6BN | EXTRA.
//Adriel Levi Argueta Caal | 24003171 - BN.
//Maria Claudia Lainfiesta Herrera | 24000149 - BN.

//Librería importada para la clase.
import java.io.*;

public class BmpHandlerResizerInvertido extends BmpHandlerResizer{
    //Constructor.
    public BmpHandlerResizerInvertido(String archivo) throws Exception{
        //Manda a llamar todo el constructor original.
        super(archivo);
    }
    //Reescribimos el método readbmp.
    @Override
    protected void readBmp(BufferedInputStream archivo) throws Exception{
        //Invertimos la lectura.
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
}