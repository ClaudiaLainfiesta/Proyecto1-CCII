//Clase principal del proyecto.
public class BMPImageHandler {
    public static void main(String[] args) throws Exception {
        String comando = args[0];
        String bandera = comando.substring(0, 1);
        if (bandera.equals("-") && args.length == 2) {
            String archivo = args[1];
            if (comando.equals("-core")) {
                BmpHandlerCore handlerCore = new BmpHandlerCore(archivo);
                String nuevoNombre = archivo.replace(".bmp", "");
                handlerCore.RedImage(nuevoNombre + "-red.bmp");
                handlerCore.GreenImage(nuevoNombre + "-green.bmp");
                handlerCore.BlueImage(nuevoNombre + "-blue.bmp");
                handlerCore.SepiaImage(nuevoNombre + "-sepia.bmp");
                System.out.println("Imágenes generadas correctamente.");
            } else if (comando.equals("-rotate")) {
                //BmpHandlerRotator handlerTotator = new BmpHandlerRotator(archivo);
                String nuevoNombre = archivo.replace(".bmp", "");
                System.out.println("En construcción aún :D");
            } else if (comando.equals("-resize")) {
                //BmpHandlerResizer handlerResizer = new BmpHandlerResizer(archivo);
                String nuevoNombre = archivo.replace(".bmp", "");
                System.out.println("En construcción aún :D");
            } else if (comando.equals("-all")) {
                BmpHandlerCore handlerCore = new BmpHandlerCore(archivo);
                String nuevoNombre = archivo.replace(".bmp", "");
                handlerCore.RedImage(nuevoNombre + "-red.bmp");
                handlerCore.GreenImage(nuevoNombre + "-green.bmp");
                handlerCore.BlueImage(nuevoNombre + "-blue.bmp");
                handlerCore.SepiaImage(nuevoNombre + "-sepia.bmp");
                //BmpHandlerRotator handlerRotator = new BmpHandlerRotator(archivo);
                //BmpHandlerResizer handlerResizer = new BmpHandlerResizer(archivo);
                System.out.println("En media construcción aún :D");
            } else {
                System.out.println("Comando no válido. Use -help para más información.");
            }
        } else if (bandera.equals("-") && args.length == 1) {
            if (comando.equals("-help")) {
                System.out.println("Comandos disponibles: ");
                System.out.println("-core: Genera imágenes en escala de sepia, rojo, verde y azul.");
                System.out.println("-rotate: Genera imágenes rotadas 180 grados sobre la linea horizontal y vertical.");
                System.out.println("-resize: Genera imágenes minimizadas en un 50% de su ancho y alto.");
                System.out.println("-all: Genera todas las imágenes anteriores.");
                System.out.println("-help: Muestra la lista de comandos disponibles.");
            } else {
                System.out.println("Comando no válido. Use -help para más información.");
            }
        } else {
            System.out.println("Sintaxis de comando no válida.");
        }
    }
}
