# Photoshop Resource Editor
Edits the resource (image) files for Photoshop CC, such as for changing the splash screen

# Current Limitations
Right now a resource can only be replaced with one of equal or smaller size in bytes. This project is abandoned and it is unlikely that any further development will occur.

# Prerequisites
* Java 10 JDK or higher
* An installation of Photoshop CC
* An Internet connection with which to download dependencies (the build script will do this automatically)

# Building
A [Gradle](http://www.gradle.org) build script and wrapper are provided for convenience. To build this program, declare where your JDK is installed and then run the Gradle wrapper:
```
$ export JAVA_HOME=/c/Program\ Files/Java/jdk-10.0.2/
$ ./gradlew shadowJar
```
This will output a jar in `./build/libs/` with all the CLI apps and dependencies.

# Quick Start
### Dump all resource images
To dump all images in your Photoshop CC's resource directory, run the `Extract` CLI program:
```
$ java -cp ./build/libs/PhotoshopResourceEditor-1.0-SNAPSHOT-all.jar com.skoobalon.photoshop.cli.Extract
Photoshop path is currently: C:\Program Files\Adobe\Adobe Photoshop CC 2018\
If this is correct, press [Enter], otherwise type it in below

Output path is currently: C:\Users\Steven\Pictures\Adobe\
If this is correct, press [Enter], otherwise type it in below

By default I will overwrite preexisting files. If this is alright, press [Enter],
otherwise type "no" below.

Reading data...
Read 2384 low res pngs
Read 2161 high res pngs
Consistency check failed. (This is not critical.)
Exporting data...
Done!
```

Both the lores and hires variants of all resources will be output with their associated name, if present in the index file. Resources without a name in the index are named by their byte offset in the resource file.

### Replace splash screen image
In Photoshop CC 2018, the resource name for the splash screen is `Splash1080Background`. Edit this file as you see fit, but ensure that the size is lower than the current one (see limitations above). Then run the `ReplaceSingleResource` program:
```
$ java -cp ./build/libs/PhotoshopResourceEditor-1.0-SNAPSHOT-all.jar com.skoobalon.photoshop.cli.ReplaceSingleResource
Photoshop path is currently: C:\Program Files\Adobe\Adobe Photoshop CC 2018\
If this is correct, press [Enter], otherwise type it in below

Resource to replace is currently: Splash1080Background
If this is correct, press [Enter], otherwise type it in below

LoRes Replacement File is currently: C:\Users\Steven\Pictures\Adobe\lores\Splash1080Background.png
If this is correct, press [Enter], otherwise type it in below

HiRes Replacement File is currently: C:\Users\Steven\Pictures\Adobe\hires\Splash1080Background.png
If this is correct, press [Enter], otherwise type it in below

NOTE: It is recommended that you not overwrite your Photoshop resource files with this script.
Instead, make a backup first and then move these files over the originals.
Output path is currently: C:\Users\Steven\Pictures\Adobe\
If this is correct, press [Enter], otherwise type it in below

Reading data...
Replacing images...
Outputting data...
Done!
```
As is noted in the output, it is recommended that you make a backup of your original resource files first, then copy the new versions on top of the older versions.
