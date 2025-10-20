import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class biblioteca {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Introduzca su número de socio completo:");
            int numSocio = sc.nextInt();
            boolean terminado = false;
            try{
            while(terminado!=true) {
                System.out.println("-------OPCIONES-------");
                System.out.println("Libros disponibles");
                System.out.println("Tomar prestado libro");
                System.out.println("Ver libros listados");
                System.out.println("Devolver libro prestado");
                System.out.println("Salir");
                String respuesta = sc.nextLine();
                    while (!respuesta.equals("Salir")) {
                        String eleccion = sc.nextLine();
                        if (respuesta.equals("Libros disponibles")|eleccion.equals("Libros disponibles")) {
                            Path biblio = Path.of("src/library/books");
                            try (DirectoryStream<Path> ds = Files.newDirectoryStream(biblio)) {
                                for (Path p : ds) {
                                    System.out.println(p);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else if(respuesta.equals("Tomar prestado libro")|eleccion.equals("Tomar prestado libro")){
                            System.out.println("¿Que libro desea mover?");
                            try {
                                String eleMove = sc.nextLine();
                                Path origen = Path.of("src/library/books/"+eleMove);
                                Path destinoCarpeta = Path.of("src/library/rentals/"+numSocio);
                                Path destinoArchivo = destinoCarpeta.resolve(eleMove);
                                Files.createDirectories(destinoCarpeta.getParent());
                                if (Files.notExists(destinoCarpeta)) {
                                    Files.createDirectory(destinoCarpeta);
                                }
                                Files.move(origen, destinoArchivo, REPLACE_EXISTING);
                                System.out.println("El libro has sido alquilado correctamente");
                                Path texto = Path.of("src/library/rentals/rentals.txt");
                                if (!Files.exists(texto)) {
                                    Files.createFile(texto);
                                }
                                try (BufferedWriter bw = Files.newBufferedWriter(texto, StandardCharsets.UTF_8)) {
                                    LocalDate fechaActual = LocalDate.now();
                                    bw.write("ID: "+numSocio+" Película: "+eleMove+" Fecha: "+fechaActual);
                                    bw.newLine();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }catch (Exception e){
                                System.err.println("El libro introducido no es válido");
                                e.printStackTrace();
                            }
                        }
                        else if(respuesta.equals("Ver libros listados")|eleccion.equals("Ver libros listados")){
                            Path listado = Path.of("src/library/rentals/"+numSocio);
                            //podria poner un if(si el fichero no esta vacio hace esto )else("Este usuario no tiene peliculas")
                            try (DirectoryStream<Path> ds = Files.newDirectoryStream(listado)) {
                                for (Path p : ds) {
                                    System.out.println(p);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else if(respuesta.equals("Devolver libro prestado")|eleccion.equals("Devolver libro prestado")){
                            System.out.println("¿Qué libro desea devolver?");
                            try {
                                String eleDev = sc.nextLine();
                                Path devolverOrigen = Path.of("src/library/rentals/"+numSocio+"/"+eleDev);
                                Path devolverDestino = Path.of("src/library/books/"+eleDev);
                                if (Files.exists(devolverOrigen)) {
                                    Files.move(devolverOrigen, devolverDestino, ATOMIC_MOVE, REPLACE_EXISTING);
                                    System.out.println("Ha devuelto el libro alquilado");
                                }
                                else{
                                    System.out.println("No ha alquilado este libro");
                                }
                            } catch (Exception e) {
                                System.out.println("El libro introducido no es válido");
                                e.printStackTrace();
                            }
                        } else if (respuesta.equals("Salir")|eleccion.equals("Salir")) {
                            System.exit(0);
                        }
                    }
                }
                terminado = true;
            }catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
