import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Scanner;

public class biblioteca {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Introduzca su número de socio completo:");
            int numSocio = sc.nextInt();
            boolean terminado = false;
            while(terminado!=true) {
                System.out.println("-------OPCIONES-------");
                System.out.println("Libros disponibles");
                System.out.println("Tomar prestado libro");
                System.out.println("Ver libros listados");
                System.out.println("Devolver libro prestado");
                System.out.println("Salir");
                String respuesta = sc.nextLine();
                try {
                    while (!respuesta.equals("Salir")) {
                        if (respuesta.equals("libros disponibles")) {
                            Path biblio = Path.of("library/books");
                            try (DirectoryStream<Path> ds = Files.newDirectoryStream(biblio)) {
                                for (Path p : ds) {
                                    System.out.println(p);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else if(respuesta.equals("Tomar prestado libro")){
                            System.out.println("¿Que libro desea mover?");
                            try {
                                String eleMove = sc.nextLine();//¿Se tiene que poner .pdf al elegir la película
                                Path origen = Path.of("library/books/"+eleMove);//(creo que esta bien)no se como hacer que el libro introducido por scanner sea el libro que se va a buscar en books/
                                Path destino = Path.of("library/rental/"+numSocio);//lo mismo con el numero de socio
                                //aqui falta el codigo de mover la pelicula de un fichero a otro, crear carpeta del numSocio si no existe
                                Path texto = Path.of("library/rentals/rentals.txt");
                                if (!Files.exists(texto)) {
                                    Files.createFile(texto);
                                }
                                try (BufferedWriter bw = Files.newBufferedWriter(texto, StandardCharsets.UTF_8)) {
                                    LocalDate fechaActual = LocalDate.now();
                                    bw.write("ID: "+numSocio+" Pélicula: "+eleMove+"pdf Fecha: "+fechaActual);
                                    bw.newLine();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }catch (Exception e){
                                System.err.println("El libro introducido no es válido");
                                e.printStackTrace();
                            }
                        }
                        else if(respuesta.equals("Devolver libro prestado")){
                            //Se comprueba que el usuario tiene el libro prestado, se mueve de su carpeta de libros (library/rental/{id_usuario}) a library/books
                            System.out.println("¿Qué libro desea devolver?");
                            try {
                                String eleDev = sc.nextLine();
                                Path devolverOrigen = Path.of("library/rental/"+numSocio+"/"+eleDev);
                                if (!Files.exists(devolverOrigen)) {
                                    //falta el codigo de mover los libros
                                }
                                else{
                                    System.out.println("No ha alquilado este libro");
                                }
                            } catch (Exception e) {
                                System.out.println("El libro introducido no es válido");
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                terminado = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
