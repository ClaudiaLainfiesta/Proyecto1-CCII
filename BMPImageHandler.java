//Proyecto No. 1 - Grupo No.6BN | Clase Principal.
//Adriel Levi Argueta Caal | 24003171 - BN.
//Maria Claudia Lainfiesta Herrera | 24000149 - BN.

public class BMPImageHandler {
    public static void main(String[] args) throws Exception {
        try {
            //Separa el comando y la bandera del comando.
            String comando = args[0];
            String bandera = comando.substring(0, 1);
            //Valida que la bandera sea "-".
            if (bandera.equals("-") && args.length == 2) {
                //Separa el nombre del archivo.
                String archivo = args[1];
                //Modifica el nombre del archivo para futuro cambio de este mismo.
                String nuevoNombre = archivo.replace(".bmp", "");

                if (comando.equals("-core")) {
                    //Crea objeto en BmpHandlerCore.
                    BmpHandlerCore handlerCore = new BmpHandlerCore(archivo);
                    //Manda a llamar los métodos para crear las imágenes correspondientes.
                    handlerCore.RedImage(nuevoNombre + "-red.bmp");
                    handlerCore.GreenImage(nuevoNombre + "-green.bmp");
                    handlerCore.BlueImage(nuevoNombre + "-blue.bmp");
                    handlerCore.SepiaImage(nuevoNombre + "-sepia.bmp");
                    //Mensaje en terminal.
                    System.out.println("Imágenes generadas correctamente.");
                } else if (comando.equals("-rotate")) {
                    //Crea objeto en BmpHandlerRotator.
                    BmpHandlerRotator handlerRotator = new BmpHandlerRotator(archivo);
                    //Manda a llamar los métodos para crear las imágenes correspondientes.
                    //Rotación en eje horizontal
                    handlerRotator.rotateVertical(nuevoNombre + "-vrotation.bmp");
                    //Rotación en eje vertical
                    handlerRotator.rotateHorizontal(nuevoNombre + "-hrotation.bmp");
                    //Mensaje en terminal.
                    System.out.println("Imágenes rotadas generadas correctamente.");
                } else if (comando.equals("-resize")) {
                    //Crea objeto en BmpHandlerResizer.
                    BmpHandlerResizer handlerResizer = new BmpHandlerResizer(archivo);
                    //Manda a llamar los métodos para crear las imágenes correspondientes.
                    handlerResizer.reducirAlto(nuevoNombre + "-flat.bmp");
                    handlerResizer.reducirAncho(nuevoNombre + "-thin.bmp");
                    //Mensaje en terminal.
                    System.out.println("Imagen generada correctamente.");
                } else if (comando.equals("-all")) {
                    //Crea objetos en BmpHandlerCore, BmpHandlerRotator y BmpHandlerResizer.
                    BmpHandlerCore handlerCore = new BmpHandlerCore(archivo);
                    BmpHandlerRotator handlerRotator = new BmpHandlerRotator(archivo);
                    BmpHandlerResizer handlerResizer = new BmpHandlerResizer(archivo);
                    //Crea todas las imágenes correspondientes.
                    handlerCore.RedImage(nuevoNombre + "-red.bmp");
                    handlerCore.GreenImage(nuevoNombre + "-green.bmp");
                    handlerCore.BlueImage(nuevoNombre + "-blue.bmp");
                    handlerCore.SepiaImage(nuevoNombre + "-sepia.bmp");

                    handlerRotator.rotateVertical(nuevoNombre + "-vrotation.bmp");
                    handlerRotator.rotateHorizontal(nuevoNombre + "-hrotation.bmp");
                    handlerResizer.reducirAlto(nuevoNombre + "-flat.bmp");
                    handlerResizer.reducirAncho(nuevoNombre + "-thin.bmp");

                    System.out.println("Imágenes generadas correctamente.");
                } else if (comando.equals("-gray")) {
                    //Crea objeto en BmpHandlerCore.
                    BmpHandlerCore handlergray = new BmpHandlerCore(archivo);
                    //Manda a llamar el método para crear la imagen correspondiente.
                    handlergray.GrayImage(nuevoNombre + "-gray.bmp");
                    //Mensaje en terminal.
                    System.out.println("Imagen generada correctamente.");
                }else if(comando.equals("-resizeInvertido")){
                    //Crea objeto en BmpHandlerResizeInvertido (subclase).
                    BmpHandlerResizerInvertido prueba = new BmpHandlerResizerInvertido(archivo);
                    //Manda a llamar los métodos para crear las imágenes correspondientes.
                    prueba.reducirAlto(nuevoNombre + "-resizeInvertidoFlat.bmp");
                    prueba.reducirAncho(nuevoNombre + "-resizeInvertidoThin.bmp");
                    //Mensaje en terminal.
                    System.out.println("Imagen generada correctamente.");
                }else {
                    System.out.println("Comando no válido. Utilice -help para más información.");
                }
            } else if (bandera.equals("-") && args.length == 1) {
                if (comando.equals("-help")) {
                    //Mensajes en terminal de todos los comandos.
                    System.out.println("Comandos disponibles: ");
                    System.out.println("  -core: Genera imágenes en escala de sepia, rojo, verde y azul.");
                    System.out.println(
                            "  -rotate: Genera imágenes rotadas 180 grados sobre la línea horizontal y vertical.");
                    System.out.println("  -resize: Genera imágenes minimizadas en un 50% de su ancho y alto.");
                    System.out.println("  -all: Genera todas las imágenes anteriores.");
                    System.out.println("  -help: Muestra la lista de comandos disponibles.");
                    System.out.println("Extra:");
                    System.out.println("  -gray: Genera imágenes en escala de grises.");
                } else {
                    System.out.println("Comando no válido. Use -help para más información.");
                }
            } else {
                System.out.println("Sintaxis de comando no válida.");
            }
        } catch (Exception e) {
            //Control de cualquier otra excepción que pueda surgir.
            System.out.println("¡ERROR! Ocurrió un error inesperado, revise su sintaxis y vuelva a intentarlo por favor.");
        }
    }
}
