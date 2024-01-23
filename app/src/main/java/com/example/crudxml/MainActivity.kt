package com.example.crudxml

import Ruta
import Rutas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.simpleframework.xml.core.Persister
import java.io.*
import javax.xml.parsers.SAXParserFactory

class MainActivity : AppCompatActivity() {
    var rutas = mutableListOf<Ruta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        copiarArchivoDesdeAssets()
        procesarArchivoAssetsXML()
        Log.d("prueba2", "probando procesado con Simple XML Framework")
        rutas.forEach {
            Log.d("prueba2", it.nombre)
        }

        val ruta=Ruta("El caminito de Santiago")
        addRuta(ruta)
        ProcesarArchivoXMLInterno()
        rutas.forEach {
            Log.d("prueba2", it.nombre)
        }

        procesarArchivoXMLSAX()

    }

    private fun procesarArchivoXMLSAX() {
        try {
            val factory = SAXParserFactory.newInstance()
            val parser = factory.newSAXParser()
            val handler = RutaHandlerXML()

            val inputStream = assets.open("profesores.xml")
            parser.parse(inputStream, handler)

            // Accede a la lista de profesores desde handler.profesores
            handler.rutas.forEach {
                Log.d("SAX", "Ruta: ${it.nombre}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun procesarArchivoAssetsXML() {
        val serializer = Persister()
        var inputStream: InputStream? = null
        var reader: InputStreamReader? = null

        try {
            inputStream = assets.open("rutas.xml")
            reader = InputStreamReader(inputStream)
            val rutasListType = serializer.read(Rutas::class.java, reader, false)
            rutas.addAll(rutasListType.rutas)
        } catch (e: Exception) {
            // Manejo de errores
            e.printStackTrace()
        } finally {
            // Cerrar inputStream y reader
            try {
                reader?.close()
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

    fun addRuta(ruta: Ruta) {
        try {
            val serializer = Persister()
            rutas.add(ruta)
            val rutasList = Rutas(rutas)
            val outputStream = openFileOutput("rutas.xml", MODE_PRIVATE)
            serializer.write(rutasList, outputStream)
            outputStream.close() // Asegúrate de cerrar el outputStream después de escribir
        } catch (e: Exception) {
            e.printStackTrace() // Manejo de errores adecuado
        }
    }
    private fun copiarArchivoDesdeAssets() {
        val nombreArchivo = "rutas.xml"
        val archivoEnAssets = assets.open(nombreArchivo)
        val archivoInterno = openFileOutput(nombreArchivo, MODE_PRIVATE)

        archivoEnAssets.copyTo(archivoInterno)
        archivoEnAssets.close()
        archivoInterno.close()
    }

    fun ProcesarArchivoXMLInterno() {
        val nombreArchivo = "rutas.xml"
        val serializer = Persister()

        try {
            // Abrir el archivo para lectura
            val file = File(filesDir, nombreArchivo)
            val inputStream = FileInputStream(file)
            val profesoresList = serializer.read(Rutas::class.java, inputStream)
            rutas.addAll(profesoresList.rutas)
            inputStream.close()
        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

}