  El proyecto tiene como objetivo el modelaje de de una empresa alimenticia y su funcionamiento. Para dicho modelaje se
utilizar� el lenguaje informativo Java. Se utilizaran propiedades de programaci�n orientada a objetos (Heredia, Polimorfismo,
Sobreescritura,encapsulamiento).

  "La Santafesina es una empresa alimenticia, la cual cuenta con distintos dep�sitos en los cuales alojan sus paquetes de 
productos. La empresa tambi�n cuenta con distintos tipos de transportes los cuales se encargan de trasladar los paquetes 
de productos a los distintos centros de distribuci�n a lo largo y ancho del pa�s. Estos paquetes tienen un peso,
un volumen, un destino y pueden necesitar o no fr�o.
  Los dep�sitos y transportes tienen sus atributos espec�ficos: 
Por un lado, los dep�sitos son los puntos donde los camiones cargan sus pedidos. Los dep�sitos pueden ser propios o 
tercerizados, tener o no c�mara frigor�fica para almacenar paquetes que necesiten fr�o, y deben tener una capacidad m�xima
(en volumen). Adem�s, los dep�sitos tercerizados con frigor�fico tienen un valor de costo por cada 1000Kg que carga un 
cami�n.
  Por otro lado, todos los transportes tienen caracter�sticas en com�n. Todos llevan un n�mero de identificaci�n, 
una carga m�xima (en peso), una capacidad m�xima (en volumen), costo por km y pueden tener o no refrigeraci�n. La empresa 
tiene distintos tipos de transportes. Estos son cami�n-Trailer, cami�n-MegaTrailer y Flete. Cada tipo de transporte se 
elegir� seg�n las caracter�sticas que tendr� cada viaje. Los camiones de tr�iler poseen un seguro de carga, realizan 
viajes de hasta 500Km y pueden tener o no equipo de fr�o. Los camiones Mega Trailer tambi�n cuentan con un seguro de carga. 
Estos adem�s poseen un costo fijo por viaje y un gasto de comida del conductor. Solamente pueden ser utilizado para 
viajes  con un recorrido mayor a 500Km. Por �ltimo, los fletes permiten acompa�antes y tiene un costo fijo por cada uno, 
y no tienen la capacidad de poseer equipo frigor�fico.

  Teniendo en cuenta todas estas caracter�sticas, la empresa debe poder realizar las siguientes acciones:
  Crear la empresa: Dado un CUIT y un nombre, se crea la empresa.
  Agregar un dep�sito a la empresa: Dado la capacidad m�xima del dep�sito, si posee o no equipo frigor�fico y si es 
propio o tercerizados, lo agrega a la lista de dep�sitos de la empresa y la empresa le designa un n�mero de identificaci�n.
  Incorporar paquete a un dep�sito: Recibe los datos del paquete (Destino, volumen, peso y si necesita estar en fr�o y no), 
y lo coloca en un dep�sito el cual tenga la capacidad de alojarlo. La empresa debe notificar si esta acci�n se pudo 
llevar a cabo o no.
  Agregar un transporte a la empresa: Recibe la identificaci�n del transporte y sus caracter�sticas (peso y volumen de 
carga m�xima, y si tiene o no c�mara frigor�fica) y lo agregar a la lista de transportes de la empresa.
  Asignar un destino a un transporte: Recibe la identificaci�n de un transporte, un destino y cantidad de KM del viaje. 
La empresa crea un viaje con las caracter�sticas correspondientes y se los asigna al transporte. Se debe tener en cuenta 
que el transporte no debe contar con mercader�a cargada.
  Cargar un transporte con mercarderia: Recibe la identificaci�n del transporte y lo carga. Se tiene que notificar el 
volumen cargado. Se debe tener en cuenta que el transporte tenga un viaje asignado.
  Iniciar viaje: Recibe la identificaci�n del transporte y lo registra como que est� en viaje. El transporte no tiene que 
estar registrando como en viaje previamente, tiene que tener un viaje asignado y estar cargado.
  Finalizar viaje: Recibe la identificaci�n del transporte y lo registra como que no est� en viaje, vac�a su carga y 
elimina el viaje asignado. El transporte tiene que estar registrado como en viaje previamente.
  Calcular costo: Recibe la identificaci�n del transporte, y seg�n las caracter�sticas del mismo, junto al viaje asignado, 
notificas el costo total. El transporte tiene que estar en viaje."
