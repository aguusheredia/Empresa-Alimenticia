  El proyecto tiene como objetivo el modelaje de de una empresa alimenticia y su funcionamiento. Para dicho modelaje se
utilizará el lenguaje informativo Java. Se utilizaran propiedades de programación orientada a objetos (Herencia, Polimorfismo,
Sobreescritura,encapsulamiento).

  "La Santafesina es una empresa alimenticia, la cual cuenta con distintos depósitos en los cuales alojan sus paquetes de 
productos. La empresa también cuenta con distintos tipos de transportes los cuales se encargan de trasladar los paquetes 
de productos a los distintos centros de distribución a lo largo y ancho del país. Estos paquetes tienen un peso,
un volumen, un destino y pueden necesitar o no frío.
  Los depósitos y transportes tienen sus atributos específicos: 
Por un lado, los depósitos son los puntos donde los camiones cargan sus pedidos. Los depósitos pueden ser propios o 
tercerizados, tener o no cámara frigorífica para almacenar paquetes que necesiten frío, y deben tener una capacidad máxima
(en volumen). Además, los depósitos tercerizados con frigorífico tienen un valor de costo por cada 1000Kg que carga un 
camión.
  Por otro lado, todos los transportes tienen características en común. Todos llevan un número de identificación, 
una carga máxima (en peso), una capacidad máxima (en volumen), costo por km y pueden tener o no refrigeración. La empresa 
tiene distintos tipos de transportes. Estos son camión-Trailer, camión-MegaTrailer y Flete. Cada tipo de transporte se 
elegirá según las características que tendrá cada viaje. Los camiones de tráiler poseen un seguro de carga, realizan 
viajes de hasta 500Km y pueden tener o no equipo de frío. Los camiones Mega Trailer también cuentan con un seguro de carga. 
Estos además poseen un costo fijo por viaje y un gasto de comida del conductor. Solamente pueden ser utilizado para 
viajes  con un recorrido mayor a 500Km. Por último, los fletes permiten acompañantes y tiene un costo fijo por cada uno, 
y no tienen la capacidad de poseer equipo frigorífico.

  Teniendo en cuenta todas estas características, la empresa debe poder realizar las siguientes acciones:
  Crear la empresa: Dado un CUIT y un nombre, se crea la empresa.
  Agregar un depósito a la empresa: Dado la capacidad máxima del depósito, si posee o no equipo frigorífico y si es 
propio o tercerizados, lo agrega a la lista de depósitos de la empresa y la empresa le designa un número de identificación.
  Incorporar paquete a un depósito: Recibe los datos del paquete (Destino, volumen, peso y si necesita estar en frío y no), 
y lo coloca en un depósito el cual tenga la capacidad de alojarlo. La empresa debe notificar si esta acción se pudo 
llevar a cabo o no.
  Agregar un transporte a la empresa: Recibe la identificación del transporte y sus características (peso y volumen de 
carga máxima, y si tiene o no cámara frigorífica) y lo agregar a la lista de transportes de la empresa.
  Asignar un destino a un transporte: Recibe la identificación de un transporte, un destino y cantidad de KM del viaje. 
La empresa crea un viaje con las características correspondientes y se los asigna al transporte. Se debe tener en cuenta 
que el transporte no debe contar con mercadería cargada.
  Cargar un transporte con mercarderia: Recibe la identificación del transporte y lo carga. Se tiene que notificar el 
volumen cargado. Se debe tener en cuenta que el transporte tenga un viaje asignado.
  Iniciar viaje: Recibe la identificación del transporte y lo registra como que está en viaje. El transporte no tiene que 
estar registrando como en viaje previamente, tiene que tener un viaje asignado y estar cargado.
  Finalizar viaje: Recibe la identificación del transporte y lo registra como que no está en viaje, vacía su carga y 
elimina el viaje asignado. El transporte tiene que estar registrado como en viaje previamente.
  Calcular costo: Recibe la identificación del transporte, y según las características del mismo, junto al viaje asignado, 
notificas el costo total. El transporte tiene que estar en viaje."
