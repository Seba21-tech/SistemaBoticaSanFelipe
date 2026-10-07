# Guía de Git y GitHub (Semana 7)

El desafío pide un repositorio público en GitHub con **10 o más commits con mensajes descriptivos en español**.
Haz un commit cada vez que termines una parte real de tu trabajo (así el historial refleja lo que hizo cada integrante).

## Comandos básicos
```
git init
git add .
git commit -m "Mensaje en español"
git remote add origin https://github.com/USUARIO/SistemaBoticaSanFelipe.git
git push -u origin main
```
Para trabajar en equipo: `git checkout -b nombre-de-rama`, luego `git checkout main` y `git merge nombre-de-rama`.

## Mensajes de commit sugeridos (en este orden, uno por avance)
1. `Crear estructura del proyecto Maven y paquetes`
2. `Agregar clase abstracta Persona con encapsulamiento y validación del DNI`
3. `Agregar herencia: Cliente, PersonalFarmacia, QuimicoFarmaceutico y Vendedor`
4. `Agregar clase Medicamento con control de stock y vencimiento`
5. `Agregar Dispensacion y DetalleDispensacion (composición) con validaciones`
6. `Implementar patrones Singleton (GestorBotica) y Factory (FabricaPersonas)`
7. `Agregar reportes con filter, map y reduce`
8. `Proteger el DNI: encriptado con SHA-256 y mostrado enmascarado (Ley 29733)`
9. `Agregar sistema de seguridad con login y contraseña encriptada`
10. `Crear interfaz gráfica con eventos y criterios de aceptación`
11. `Agregar pruebas unitarias con JUnit 5`
12. `Agregar diagrama de clases UML`
13. `Refactorizar: aplicar responsabilidad única y mejorar nombres`
14. `Actualizar README con instrucciones de uso`

> Nota: crea los commits tú mismo a medida que avanzas; no copies el historial de otra persona.
