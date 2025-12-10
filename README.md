# MusicApp

> **Proyecto Integrador - Desarrollo de Aplicaciones Móviles**
>
> **Semestre:** 4°A
> **Fecha de entrega:** 11 de Diciembre

---

## Equipo de Desarrollo

| Nombre Completo | Rol / Tareas Principales | Usuario GitHub |
| :--- | :--- | :--- |
| Armas Arteaga Angel Zaid | Retrofit | @armasaz |
| Campuzano Hernandez Ulises | Backend | @ulisescam |
| Rosales Alanis Daniel | Designer, Backend | @DanielRosalesAlanis |

---

## Descripción del Proyecto

**¿Qué hace la aplicación?**
Este proyecto es una aplicación móvil que combina el uso de Retrofit con el sensor de proximidad del dispositivo para controlar la reproducción de música de una manera diferente y más intuitiva. La aplicación permite reproducir, pausar y reanudar canciones sin tocar la pantalla: basta con acercar la mano al sensor para que la canción se detenga, y realizar nuevamente el mismo gesto para que vuelva a reproducirse. Este control por proximidad hace que la interacción sea rápida, accesible y funcional incluso cuando el usuario no puede ver la pantalla.

Además del control por gestos, la aplicación se conecta a un servidor mediante Retrofit, lo que permite administrar un catálogo completo de canciones. Desde la misma app es posible subir archivos MP3 al servidor, editar la información de una canción, eliminarla y consultar la lista de canciones disponibles.

**Objetivo:**
Demostrar la implementación de una arquitectura robusta en Android utilizando servicios web y hardware del dispositivo.

---

## Stack Tecnológico y Características

Este proyecto ha sido desarrollado siguiendo estrictamente los lineamientos de la materia:

**Lenguaje:** Kotlin 100%.
**Interfaz de Usuario:** Jetpack Compose.
**Arquitectura:** MVVM (Model-View-ViewModel).
**Conectividad (API REST):** Retrofit.
    * **GET:** Obtiene nombre, artista, año y archivo para la reproduccion de la canción
    * **POST:** Envia, nombre, artista, año y el archivo de la canción
    * **UPDATE:** Permite actualizar el nombre, artista y año de una canción
    * **DELETE:** Borra la canción del retrofit
**Sensor Integrado:** Proximidad
    * Uso: Con un tap (pasar la mano) la canción se pausa, volver a dar otro tap reanudara la canción

---

## Instalación y Releases

El ejecutable firmado (.apk) se encuentra disponible en la sección de **Releases** de este repositorio.

(https://github.com/DanielRosalesAlanis/IntegradoraAplicacionesMoviles/releases/tag/version_1_0)

1.  Ve a la sección "Releases" (o haz clic https://github.com/DanielRosalesAlanis/IntegradoraAplicacionesMoviles/releases/tag/version_1_0).
2.  Descarga el archivo .apk de la última versión.
3.  Instálalo en tu dispositivo Android (asegúrate de permitir la instalación de orígenes desconocidos).
