# equipoCuatro — DogApp

DogApp - Agenda de citas para mascotas. Miniproyecto 1 de Desarrollo de Aplicaciones para
Dispositivos Moviles (Univalle). Equipo 4.

## Equipo 4

- Juan Manuel Rodriguez Ortiz
- Santiago Valencia Aguiño
- Andres Fernando Perea Castillo
- Andres Mauricio Ramirez Rengifo

## Enlaces

- Repositorio: https://github.com/santi4914/equipoCuatro
- Tablero Jira (proyecto EC): https://equipocuatro.atlassian.net/jira/software/projects/EC/boards/1

## Stack

| Componente | Versión |
|---|---|
| Lenguaje | Kotlin 2.3.20 |
| Android Gradle Plugin | 9.0.1 (Kotlin integrado de AGP 9) |
| Gradle (wrapper) | 9.1.0 |
| KSP | 2.3.14 |
| JDK | 17 o superior (el JBR que trae Android Studio sirve) |
| compileSdk / targetSdk / minSdk | 36 / 36 / 24 |
| Interfaz | Vistas XML + DataBinding (sin Compose) |

Librerías: Navigation 2.10.2, Room 2.8.5, Lifecycle (ViewModel y LiveData) 2.11.0,
corrutinas 1.11.0, RecyclerView 1.4.0, CardView 1.0.0, Retrofit 3.0.0 con convertidor Gson
(API https://dog.ceo/dog-api/), Lottie 6.7.1 y Biometric 1.1.0.

## Arquitectura

MVVM + Repository, como en la clase 8. Paquete base `com.equipocuatro.dogapp`:

| Paquete | Contenido |
|---|---|
| `model` | `Cita` (entidad de Room; el `id` autogenerado es el turno) |
| `data` | `CitaDao` y `CitaDB` (base de datos local SQLite con Room) |
| `repository` | `CitaRepository`: acceso a los datos en `Dispatchers.IO` con corrutinas |
| `viewmodel` | `CitaViewModel`: `LiveData` de la lista y del estado de carga |
| `view` | `MainActivity` (contenedor de Navigation) y `fragment`, `adapter`, `viewholder` |
| `utils` | `Constants` (nombre de la base de datos y claves de los `Bundle`) |

Navegación (`res/navigation/nav_graph.xml`), destino inicial **Login**:

```
Login ──> Home ──> Nueva Cita ──> Home
           └────> Detalle ──> Editar ──> Home
                     └──────> Home
```

La cita seleccionada viaja entre fragments en un `Bundle` con la clave
`Constants.CLAVE_CITA` (`Cita` es `Serializable`).

## Requisitos

- **Android Studio Otter 3 Feature Drop (2025.2.3) o más reciente**: es la versión mínima que
  soporta AGP 9.0. El proyecto se probó con Android Studio 2026.2.1.
- Android SDK Platform 36 instalado (SDK Manager).
- Un emulador o un teléfono con Android 7.0 (API 24) o superior.

## Cómo ejecutar

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/santi4914/equipoCuatro.git
   ```
2. Abrir la carpeta en Android Studio (**File › Open**) y esperar el *Gradle Sync*.
   Android Studio crea `local.properties` con la ruta del SDK (ese archivo no se sube).
3. Elegir el emulador y pulsar **Run ›** (`app`).

Desde la terminal (en la raíz del proyecto):

```bash
./gradlew assembleDebug      # Windows: gradlew.bat assembleDebug
```

El APK queda en `app/build/outputs/apk/debug/app-debug.apk`.

### Probar el login con huella en el emulador

La ventana de huella solo aparece si el dispositivo tiene una huella registrada (sin huella,
Android muestra su propio mensaje, por ejemplo «No fingerprints enrolled»). En el emulador:

1. Registrar una huella. Android exige un bloqueo de pantalla, así que primero se pone un PIN
   de prueba y luego se abre el registro de huellas:
   ```bash
   adb shell locksettings set-pin 1234
   adb shell am start -a android.settings.FINGERPRINT_ENROLL
   ```
   Escribir el PIN, aceptar y, cuando pida tocar el sensor, simular el dedo varias veces
   hasta que diga «Fingerprint added»:
   ```bash
   adb emu finger touch 1
   ```
2. Abrir DogApp y tocar la huella animada. Con la ventana abierta:
   - `adb emu finger touch 1` → huella registrada: entra al Home.
   - `adb emu finger touch 2` → huella no registrada: el dispositivo muestra su error y no deja
     entrar.

## Flujo de trabajo

- `main`: versión estable. Solo recibe PR desde `develop`.
- `develop`: integración del equipo.
- `feature/<tema>`: una rama por tarea de Jira, creada desde `develop`.

Recorrido: rama de trabajo → PR hacia `develop` (revisión de al menos un compañero) →
PR de `develop` hacia `main`. Cada integrante hace commits con **su propia cuenta de
GitHub**. No se hacen commits después de la entrega.

### Commits

[Conventional Commits](https://www.conventionalcommits.org/) en inglés, solo el asunto
(máximo 72 caracteres, sin cuerpo), un commit por cambio lógico. Ejemplos:

```
feat: add appointment list to home screen
fix: keep back button from returning to login
docs: describe how to run the app
```

### Secretos

La API de dog.ceo es pública y no necesita llaves. Si más adelante se necesita alguna, va
en un archivo `.env` (ignorado por git) y se documenta en un `.env.example` sin valores reales.

## Créditos de recursos

| Recurso | Dónde se usa | Autor y licencia |
|---|---|---|
| Cabeza de beagle (`res/drawable-*/img_perro.webp` y el ícono `res/mipmap-*`) | Login y ícono de la app | «[Beagle puppy's head](https://commons.wikimedia.org/wiki/File:Beagle_puppy%27s_head.png)», original de Juanelverdolaga, obra derivada de Saper (Wikimedia Commons), [CC BY-SA 4.0](https://creativecommons.org/licenses/by-sa/4.0/). Aquí se recortó, se escaló por densidad y se puso sobre fondo gris para el ícono; esas versiones quedan bajo la misma licencia. |
| Animación de huella (`res/raw/huella_escaneo.json`) | Login | «[Fingerprint biometric scan](https://lottiefiles.com/free-animation/fingerprint-biometric-scan-Ni4jlplq4h)», Ivan Kuznetsov (LottieFiles), Lottie Simple License (`licencias/Lottie_Simple_License.txt`). |
| Fuente Lilita One (`res/font/lilita_one.ttf`) | Título «DogApp» | Juan Montoreano, SIL Open Font License 1.1 (`licencias/OFL_LilitaOne.txt`). |
