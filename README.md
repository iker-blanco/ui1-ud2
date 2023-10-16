# Unidad didáctica 2

This repository contains the exercises of the second unit of the subject `Programación Concurrente y Distribuida
` of the `Universidad Isabel I`.

A package is created for each exercise. The package name is the same as the exercise number. Each package contains a
JAVA file with the solution of the exercise.

Everything has been tested with `Java 21` OpenJDK.

## Sincronización de hilos

### Enunciado 1.

Crea un programa en Java que cree 10 hilos de ejecución los cuales
deben imprimir durante su ejecución el nombre de cada uno junto el timestamp de su
creación.

### Enunciado 2.

Crea tres hilos de ejecución que espere después de imprimir su nombre
siguiendo las siguientes reglas:

- Hilo 1: esperar 500ms
- Hilo 2: esperar 1500ms
- Hilo 3: esperar 10000ms

### Enunciado 3.

Crea tres runnables que permitan lanzar diferentes hilos de ejecución. El
comportamiento debe ser similar al del enunciado 1.

### Enunciado 4.

Crea un programa que posea tres hilos de ejecución en el que dos de
ellos queden bloqueado hasta que reciba una señal del tercero para continuar la
ejecución. El hilo 3 deberá dormirse durante 1000 ms antes enviar la señal de
desbloqueo.

### Enunciado 5.

Crear una aplicación de tipo productor consumidor donde el productor se
encargue de generar una serie de números (configurable) y almacenarlos en una lista
de tamaño también configurable. El consumidor será́ un proceso que se encargará de ir
sumando los números que se van almacenando en la lista. Un tercer proceso, que
también hará́ las veces de consumidor, se encargará de, a partir de la suma generada
por el primer consumidor, mostrar en una ventana la media de los números generados.
Esta media deberá́ actualizarse cada medio segundo.

## Aspectos a tener en cuenta:

- Todas las discusiones y valoraciones personales tienen que estar
  justificadas.
- Deberás entregar el código fuente. En caso contrario la entrega no se
  considerará válida.
- Se elaborará un informe que contenta la descripción del problema, y un
  apartado por cada uno de los puntos expuestos.
- El informe entregable final tiene que estar bajo el formato pdf.
- La extensión mínima del informe es de dos folios. La extensión máxima es
  de cinco folios.