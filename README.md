# Sistema Botica San Felipe (con sistema de seguridad)

Proyecto académico en Java con datos ficticios que une el **sistema de seguridad (login)** con el
**sistema de la botica**. Usa lo visto en los Temas 1 a 7 (herencia, Singleton, Factory, lambdas
con filter/map/reduce, excepciones, Swing con ActionListener, pruebas unitarias). Única dependencia externa: JUnit 5 (solo para pruebas).

## Abrir en NetBeans
1. Descomprime el ZIP.
2. Archivo > Abrir proyecto > selecciona la carpeta **SistemaBoticaSanFelipe** (la que tiene `pom.xml`).
3. Ejecutar proyecto (F6): abre la ventana de **login**. Necesita JDK 17 o superior.

## Usuario de prueba
- Usuario: `admin`
- Contraseña: `admin123` (se guarda y compara como hash SHA-256)

## Flujo del sistema
Login (`IngresoSistema`) -> si es correcto, `PanelPrincipal` con los botones Medicamentos, Personas, Ventas,
Ver reporte y Cerrar sesión (vuelve al login).

Dentro del panel:
1. **Personas**: registra un QUIMICO, un VENDEDOR y un CLIENTE (el código de empleado, p. ej. `QF-1`, aparece al registrar).
2. **Medicamentos**: registra medicamentos (marca *Sí requiere receta* si corresponde).
   **Inventario**: muestra todos los medicamentos con su stock, precio y vencimiento (marca [STOCK BAJO] y [VENCIDO]) y permite **reponer stock** escribiendo el código y la cantidad.
3. **Ventas** (ábrela después de registrar los datos): DNI del cliente y código del empleado > *Iniciar venta* >
   código y cantidad > *Agregar ítem* > *Confirmar venta*.
4. **Ver reporte**.

## Otros archivos para ejecutar
- Demo por consola: `AplicacionDemo.java`
- Pruebas JUnit 5: clic derecho sobre el proyecto > **Test** (Alt+F6). Esperado: 34 pruebas, 0 fallos.
  La primera vez Maven descarga JUnit (necesita internet), igual que una dependencia como Gson.
- Guía para el repositorio: `GUIA_GIT.md`

## Estructura
`seguridad` (Usuario, Encriptador, GestionSeguridad), `gui` (ControladorVentana, IngresoSistema, PanelPrincipal,
VentanaMedicamento, VentanaPersona, VentanaVenta), `modelo`, `patrones`, `reportes` y las pruebas en `src/test/java`, y
`diagramas` (UML en PlantUML, PNG y SVG).

Nota: los datos se guardan en memoria y el usuario `admin` está fijo en el código; es un prototipo
académico, no usar con datos reales.
